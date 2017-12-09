cd ./tmp
java -classpath ../lib/hsqldb.jar org.hsqldb.util.DatabaseManagerSwing --driver org.hsqldb.jdbcDriver --url jdbc:hsqldb:../data/tms;shutdown=true
