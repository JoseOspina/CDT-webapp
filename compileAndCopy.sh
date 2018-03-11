rm -r backend/src/main/resources/public
mvn install
rm -r ../cdtcompiled/src
cp -r backend/src/ ../cdtcompiled
