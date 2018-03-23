---------------------------------------------------------
-- This script will only fill the contents of the initiatives and initiatives_meta
-- tables. All other tables and all other sequences are generated
-- using a DB dump which excludes the decisions table data.
-- /usr/bin/pg_dump --host ec2-23-21-246-11.compute-1.amazonaws.com --port 5432 --username "fiobqmzrthifdx" --no-password  --format plain --data- only --disable-triggers --verbose --file "/home/pepo/workspace/c1-data/c1-v2-bk-000" --exclude-table "public.initiatives" "d7lpi8u3gn8hak"
---------------------------------------------------------

DROP SCHEMA IF EXISTS masterschema CASCADE;
CREATE SCHEMA masterschema;

---------------------------------------------------------
-- Connect to other server
---------------------------------------------------------
CREATE EXTENSION postgres_fdw;
CREATE SERVER master FOREIGN DATA WRAPPER postgres_fdw OPTIONS (host 'ec2-54-228-181-43.eu-west-1.compute.amazonaws.com', dbname 'd2fp5ait05f6c4', port '5432');
CREATE USER MAPPING FOR postgres SERVER master OPTIONS (user 'gznmpoelerghpw', password '140d57ef559ea6a92bec90914bc375190b82801896da4a819870dffe55276c12');
IMPORT FOREIGN SCHEMA public FROM SERVER master INTO masterschema;

-- Fill the questions using the weights from the questions and weights table
INSERT INTO questions 
  (id, text, type, weight)
SELECT 
  q.id, q.text, q.type, qw.weight 
FROM masterschema.questions q
INNER JOIN masterschema.question_and_weights qw 
ON q.id = qw.question_id;

-- Fill answer batches ignoring the responderType column
/* 
INSERT INTO answer_batches 
  (id, answered_by_id, poll_id)
SELECT 
  id, answered_by_id, poll_id
FROM masterschema.answer_batches;
*/

--------------------------------------------------------
-- DROP masterschema
---------------------------------------------------------
DROP SCHEMA masterschema CASCADE;
DROP EXTENSION postgres_fdw CASCADE;
