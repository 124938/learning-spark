## Spark SQL

### Introduction
* Spark SQL is one of the most popular module of Spark, developed for structured data processing
* It provides a programming abstraction called DataFrame & DataSet, which act as a distributed SQL query engine
* As per Michael Armbrust, Spark SQL = Catalyst optimizer framework  + Implementation of SQL & HiveQL on Spark

### Features
* **Integrated:**
  * Seamlessly mix SQL queries with Spark programs
  * It lets developer to query structured data with Spark programs (written in Scala, Java, Python, R), which makes it easy to run SQL queries alongside complex analytic algorithm

* **Unified Data Access:**
  * Load and query data from variety of sources
  * DataFrame API provides a single interface for efficiently working with structure data including JSON files, Parquet files, Avro files, Hive tables, Casandra etc.

* **Hive Compatibility:**
  * Spark SQL reuses the Hive MetaStore, which gives developer full compatibility with existing Hive tables, Queries & UDFs
  
* **Inbuilt Optimization Engine:**
  * Catalyst Optimizer is at core of Spark SQL, which build an extensible query optimizer to process data
  * Irrespective of programming language used, execution will be done in same manner

* **Standard Connectivity:**
  * Spark SQL includes a server mode utility with industry standard JDBC & ODBC connectivity
  
### Architecture
* Spark SQL is a library on top of Spark Core execution engine (as shown in below figure)
* It exposes.. 
  * DataFrame & DataSet API to read/write data from and to variety of data sources using programming language Scala, Java, Python, R
  * SQL interfaces using JDBC/ODBC for data warehousing applications i.e. BI tools can connect to Spark SQL to perform analytics at memory speeds
  * SQL command-line console to execute query interactively

  ![Alt text](images/spark-sql-architecture.jpeg?raw=true "Spark SQL - Architecture")
  
### Getting Started
* **Start with REPL**

~~~
$ spark-shell 
log4j:WARN No appenders could be found for logger (org.apache.hadoop.metrics2.lib.MutableMetricsFactory).
log4j:WARN Please initialize the log4j system properly.
log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
Using Spark's repl log4j profile: org/apache/spark/log4j-defaults-repl.properties
To adjust logging level use sc.setLogLevel("INFO")
Welcome to
      ____              __
     / __/__  ___ _____/ /__
    _\ \/ _ \/ _ `/ __/  '_/
   /___/ .__/\_,_/_/ /_/\_\   version 1.6.3
      /_/

Using Scala version 2.10.5 (OpenJDK 64-Bit Server VM, Java 1.8.0_131)
Type in expressions to have them evaluated.
Type :help for more information.
17/11/04 20:44:01 WARN Utils: Your hostname, asus-GL553VD resolves to a loopback address: 127.0.1.1; using 192.168.0.103 instead (on interface enp3s0)
17/11/04 20:44:01 WARN Utils: Set SPARK_LOCAL_IP if you need to bind to another address
Spark context available as sc.
17/11/04 20:44:03 WARN Connection: BoneCP specified but not present in CLASSPATH (or one of dependencies)
17/11/04 20:44:03 WARN Connection: BoneCP specified but not present in CLASSPATH (or one of dependencies)
17/11/04 20:44:19 WARN ObjectStore: Version information not found in metastore. hive.metastore.schema.verification is not enabled so recording the schema version 1.2.0
17/11/04 20:44:19 WARN ObjectStore: Failed to get database default, returning NoSuchObjectException
17/11/04 20:44:22 WARN Connection: BoneCP specified but not present in CLASSPATH (or one of dependencies)
17/11/04 20:44:22 WARN Connection: BoneCP specified but not present in CLASSPATH (or one of dependencies)
SQL context available as sqlContext.

scala> import sqlContext._
import sqlContext._

scala> import sqlContext.implicits._
import sqlContext.implicits._

scala> case class Order(orderId: Int, orderDate: String, orderCustomerId: Int, orderStatus: String)
defined class Order

scala> val orderDF = sc.
     | textFile("/home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/retail_db/orders").
     | map((rec: String) => rec.split(",")).
     | map((rec: Array[String]) => Order(rec(0).toInt, rec(1), rec(2).toInt, rec(3))).
     | toDF
orderDF: org.apache.spark.sql.DataFrame = [orderId: int, orderDate: string, orderCustomerId: int, orderStatus: string]

scala> orderDF
res0: org.apache.spark.sql.DataFrame = [orderId: int, orderDate: string, orderCustomerId: int, orderStatus: string]

scala> orderDF.
     | show(20)
+-------+--------------------+---------------+---------------+
|orderId|           orderDate|orderCustomerId|    orderStatus|
+-------+--------------------+---------------+---------------+
|      1|2013-07-25 00:00:...|          11599|         CLOSED|
|      2|2013-07-25 00:00:...|            256|PENDING_PAYMENT|
|      3|2013-07-25 00:00:...|          12111|       COMPLETE|
|      4|2013-07-25 00:00:...|           8827|         CLOSED|
|      5|2013-07-25 00:00:...|          11318|       COMPLETE|
|      6|2013-07-25 00:00:...|           7130|       COMPLETE|
|      7|2013-07-25 00:00:...|           4530|       COMPLETE|
|      8|2013-07-25 00:00:...|           2911|     PROCESSING|
|      9|2013-07-25 00:00:...|           5657|PENDING_PAYMENT|
|     10|2013-07-25 00:00:...|           5648|PENDING_PAYMENT|
|     11|2013-07-25 00:00:...|            918| PAYMENT_REVIEW|
|     12|2013-07-25 00:00:...|           1837|         CLOSED|
|     13|2013-07-25 00:00:...|           9149|PENDING_PAYMENT|
|     14|2013-07-25 00:00:...|           9842|     PROCESSING|
|     15|2013-07-25 00:00:...|           2568|       COMPLETE|
|     16|2013-07-25 00:00:...|           7276|PENDING_PAYMENT|
|     17|2013-07-25 00:00:...|           2667|       COMPLETE|
|     18|2013-07-25 00:00:...|           1205|         CLOSED|
|     19|2013-07-25 00:00:...|           9488|PENDING_PAYMENT|
|     20|2013-07-25 00:00:...|           9198|     PROCESSING|
+-------+--------------------+---------------+---------------+
only showing top 20 rows

~~~
  