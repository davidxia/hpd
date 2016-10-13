createuser --pwprompt --createdb --echo hpd

createdb hpd
dropdb hpd
psql --dbname=hpd --username=hpd < create_hpd_db_schema.sql


* update DB with values from enum classes Boro, DobBuildingClass, RecordStatus, and BuildingLifeCycle
