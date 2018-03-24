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
CREATE SERVER master FOREIGN DATA WRAPPER postgres_fdw OPTIONS (host 'localhost', dbname 'cdtdb-bk', port '5432');
CREATE USER MAPPING FOR postgres SERVER master OPTIONS (user 'postgres', password 'iristra');
IMPORT FOREIGN SCHEMA public FROM SERVER master INTO masterschema;

-- Create K8 Organizatoin
INSERT INTO organizations
  (id, creation_date, description, name, creator_id)
SELECT 
  id, creation_date, description, name, v.creator_id
FROM masterschema.organizations org 
INNER JOIN 
   (VALUES (uuid_in('ac131b2a-6219-1aff-8162-19b94ef5000e'),uuid_in('ac109216-6253-164a-8162-581ce3f5000b'))) AS v (org_id, creator_id) 
ON org.id = v.org_id 
WHERE org.id = 'ac131b2a-6219-1aff-8162-19b94ef5000e';

UPDATE organizations SET status = 'OPEN';

INSERT INTO polls 
  (id, creation_date, description, is_public_template, is_template, status, title, creator_id, organization_id)
SELECT
  id, creation_date, description, is_public_template, is_template, status, title, v.creator_id, organization_id
FROM masterschema.polls pol
INNER JOIN 
   (VALUES (uuid_in('ac12ce76-6243-157d-8162-4844b6980091'),uuid_in('ac109216-6253-164a-8162-581ce3f5000b'))) AS v (poll_id, creator_id) 
ON pol.id = v.poll_id 
WHERE pol.id = 'ac12ce76-6243-157d-8162-4844b6980091';

INSERT INTO polls_config 
  (id, audience, poll_id)  
SELECT 
  id, audience, poll_id
FROM masterschema.polls_config conf
WHERE conf.poll_id = 'ac12ce76-6243-157d-8162-4844b6980091';

INSERT INTO axes 
  (id, description, title, poll_id)
SELECT 
  id, description, title, poll_id
FROM masterschema.axes ax
WHERE ax.id IN (
SELECT 
  axes_id
FROM masterschema.polls_axes axes
WHERE axes.poll_id = 'ac12ce76-6243-157d-8162-4844b6980091');

UPDATE axes SET include_in_plot = TRUE;

INSERT INTO polls_axes 
  (poll_id, axes_id, axes_order)
SELECT 
  poll_id, axes_id, axes_order
FROM masterschema.polls_axes p_ax
WHERE p_ax.axes_id IN (
  SELECT 
    axes_id
  FROM masterschema.polls_axes axes
  WHERE axes.poll_id = 'ac12ce76-6243-157d-8162-4844b6980091'
);

INSERT INTO questions 
  (id, text, type, weight)
SELECT 
  qw.id, q.text, q.type, qw.weight
FROM 
  masterschema.question_and_weights qw
JOIN 
  masterschema.questions q
ON qw.question_id = q.id
WHERE qw.id IN (
  SELECT 
    questions_and_weights_id 
  FROM
    masterschema.axes_questions_and_weights
  WHERE
    axis_id IN (
    SELECT 
      axes_id
    FROM masterschema.polls_axes p_axes
    WHERE p_axes.poll_id = 'ac12ce76-6243-157d-8162-4844b6980091'
  )  
);

INSERT INTO axes_questions 
  (axis_id, questions_id, questions_order)
SELECT 
  axis_id, questions_and_weights_id, questions_order
FROM 
  masterschema.axes_questions_and_weights a_qw
WHERE a_qw.axis_id IN (
  SELECT 
    axes_id
  FROM masterschema.polls_axes axes
  WHERE axes.poll_id = 'ac12ce76-6243-157d-8162-4844b6980091'
);

INSERT INTO answer_batches 
  (id, poll_id)
SELECT 
  id, poll_id
FROM 
  masterschema.answer_batches
WHERE
  poll_id = 'ac12ce76-6243-157d-8162-4844b6980091';

INSERT INTO answers
  (id, rate, text, batch_id, question_id)
SELECT
  ans.id, rate, text, batch_id, qw.id
FROM
  masterschema.answers ans
JOIN 
  masterschema.question_and_weights qw
ON ans.question_id =  qw.question_id
WHERE 
  ans.batch_id 
IN (
  SELECT 
    id
  FROM 
    masterschema.answer_batches
  WHERE
    poll_id = 'ac12ce76-6243-157d-8162-4844b6980091'  
);

INSERT INTO organizations_admins 
  (organization_id, admins_id)
VALUES (uuid_in('ac131b2a-6219-1aff-8162-19b94ef5000e'),uuid_in('ac126a5a-6252-1d25-8162-52cd78ff0000'));

--------------------------------------------------------
-- DROP masterschema
---------------------------------------------------------
DROP SCHEMA masterschema CASCADE;
DROP EXTENSION postgres_fdw CASCADE;
