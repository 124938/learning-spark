name := "sql-api-features"
version := "0.1"
scalaVersion := "2.10.6"

libraryDependencies += "org.apache.spark" % "spark-core_2.10" % "1.6.3"
libraryDependencies += "org.apache.spark" % "spark-sql_2.10" % "1.6.3"
libraryDependencies += "org.apache.spark" % "spark-hive_2.10" % "1.6.3"

libraryDependencies += "com.databricks" % "spark-avro_2.10" % "2.0.1"