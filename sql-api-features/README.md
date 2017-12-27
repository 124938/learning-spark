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

  ![Alt text](_images/spark-sql-architecture.jpeg?raw=true "Spark SQL - Architecture")
  
### Getting Started
* **Start with REPL:**
~~~
$ spark-shell  --master local[*]
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
Spark context available as sc.
SQL context available as sqlContext.

scala> import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.SQLContext

scala> val sqlContext = new SQLContext(sc)
sqlContext: org.apache.spark.sql.SQLContext = org.apache.spark.sql.SQLContext@743e906b

scala> import sqlContext.implicits._
import sqlContext.implicits._

scala> case class OrderRDD(orderId: Int, orderDate: String, orderCustomerId: Int, orderStatus: String)
defined class Order

scala> val orderDF = sc.
textFile("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/orders/text").
map((rec: String) => {
  val recArray = rec.split(",")
  OrderRDD(recArray(0).toInt, recArray(1), recArray(2).toInt, recArray(3))
}).
toDF()
orderDF: org.apache.spark.sql.DataFrame = [orderId: int, orderDate: string, orderCustomerId: int, orderStatus: string]

scala> orderDF.
show(10)
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
+-------+--------------------+---------------+---------------+
only showing top 10 rows

scala> orderDF.
take(10).
foreach(println)
[1,2013-07-25 00:00:00.0,11599,CLOSED]
[2,2013-07-25 00:00:00.0,256,PENDING_PAYMENT]
[3,2013-07-25 00:00:00.0,12111,COMPLETE]
[4,2013-07-25 00:00:00.0,8827,CLOSED]
[5,2013-07-25 00:00:00.0,11318,COMPLETE]
[6,2013-07-25 00:00:00.0,7130,COMPLETE]
[7,2013-07-25 00:00:00.0,4530,COMPLETE]
[8,2013-07-25 00:00:00.0,2911,PROCESSING]
[9,2013-07-25 00:00:00.0,5657,PENDING_PAYMENT]
[10,2013-07-25 00:00:00.0,5648,PENDING_PAYMENT]
~~~

* **Using IDE:**
  * Create SBT project called sql-api-features in IntelliJ Idea
  
  * Add below dependency to use Spark SQL module
  ~~~
  name := "sql-api-features"
  version := "0.1"
  scalaVersion := "2.10.6"
  
  libraryDependencies += "org.apache.spark" % "spark-sql_2.10" % "1.6.3"
  ~~~
  
  * Create below sample scala program
  ~~~
  import org.apache.spark.sql.SQLContext
  import org.apache.spark.{SparkConf, SparkContext}
    
  object DFDemo {
    def main(args: Array[String]): Unit = {
  
      // Create Spark Context
      val conf = new SparkConf().
        setAppName("DataFrame - Demo").
        setMaster("local[2]")
  
      val sc = new SparkContext(conf)
  
      // Create SQL Context
      val sqlContext = new SQLContext(sc)
  
      // Below is used to implicitly convert RDD to DataFrame
      import sqlContext.implicits._
  
      // Create DataFrame using RDD
      val orderDF = sc.
        textFile("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/orders/text").
        map((rec: String) => rec.split(",")).
        map((recArray: Array[String]) => (recArray(0).toInt, recArray(1), recArray(2).toInt, recArray(3))).
        toDF("order_id", "order_date", "order_customer_id", "order_status")
  
      // Print schema of orders DataFrame
      orderDF.
        printSchema
  
      // Preview 20 records from orders DataFrame
      orderDF.
        show(20)
    }
  }
  ~~~
    
  * Run above program from IDE to see the result
    
* **Using SBT:** 
  
  * Open terminal window and execute below code to see the result
  ~~~
  asus@asus-GL553VD:~$ cd /home/asus/source_code/github/124938/learning-spark/sql-api-features
  asus@asus-GL553VD:~/source_code/github/124938/learning-spark/sql-api-features$ sbt console
  [info] Loading project definition from /home/asus/source_code/github/124938/learning-spark/sql-api-features/project
  [info] Set current project to sql-api-features (in build file:/home/asus/source_code/github/124938/learning-spark/sql-api-features/)
  [info] Compiling 1 Scala source to /home/asus/source_code/github/124938/learning-spark/sql-api-features/target/scala-2.10/classes...
  [info] Starting scala interpreter...
  [info] 
  Welcome to Scala version 2.10.6 (OpenJDK 64-Bit Server VM, Java 1.8.0_131).
  Type in expressions to have them evaluated.
  Type :help for more information.
  
  scala> import org.apache.spark.SparkConf
  import org.apache.spark.SparkConf
  
  scala> val conf = new SparkConf().
  setAppName("Data Frame - Demo").
  setMaster("local[2]")
  conf: org.apache.spark.SparkConf = org.apache.spark.SparkConf@19306988
  
  scala> import org.apache.spark.SparkContext
  import org.apache.spark.SparkContext
      
  scala> val sc = new SparkContext(conf)
  sc: org.apache.spark.SparkContext = org.apache.spark.SparkContext@2f45c5cb
  
  scala> import org.apache.spark.sql.SQLContext
  import org.apache.spark.sql.SQLContext
  
  scala> val sqlContext = new SQLContext(sc)
  sqlContext: org.apache.spark.sql.SQLContext = org.apache.spark.sql.SQLContext@2da04442
  
  scala> import sqlContext.implicits._
  import sqlContext.implicits._
  
  scala> val ordersDF = sc.
  textFile("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/orders/text").
  map((rec: String) => rec.split(",")).
  map((recArray: Array[String]) => (recArray(0).toInt, recArray(1), recArray(2).toInt, recArray(3))).
  toDF("order_id", "order_date", "order_customer_id", "order_status")
  ordersDF: org.apache.spark.sql.DataFrame = [order_id: int, order_date: string, order_customer_id: int, order_status: string]
  
  scala> ordersDF.
  printSchema
  root
   |-- order_id: integer (nullable = false)
   |-- order_date: string (nullable = true)
   |-- order_customer_id: integer (nullable = false)
   |-- order_status: string (nullable = true)
  
  scala> ordersDF.
  show(10)
  +--------+--------------------+-----------------+---------------+
  |order_id|          order_date|order_customer_id|   order_status|
  +--------+--------------------+-----------------+---------------+
  |       1|2013-07-25 00:00:...|            11599|         CLOSED|
  |       2|2013-07-25 00:00:...|              256|PENDING_PAYMENT|
  |       3|2013-07-25 00:00:...|            12111|       COMPLETE|
  |       4|2013-07-25 00:00:...|             8827|         CLOSED|
  |       5|2013-07-25 00:00:...|            11318|       COMPLETE|
  |       6|2013-07-25 00:00:...|             7130|       COMPLETE|
  |       7|2013-07-25 00:00:...|             4530|       COMPLETE|
  |       8|2013-07-25 00:00:...|             2911|     PROCESSING|
  |       9|2013-07-25 00:00:...|             5657|PENDING_PAYMENT|
  |      10|2013-07-25 00:00:...|             5648|PENDING_PAYMENT|
  +--------+--------------------+-----------------+---------------+
  only showing top 10 rows
  
  scala> ordersDF.
  take(10).
  foreach(println)
  [1,2013-07-25 00:00:00.0,11599,CLOSED]
  [2,2013-07-25 00:00:00.0,256,PENDING_PAYMENT]
  [3,2013-07-25 00:00:00.0,12111,COMPLETE]
  [4,2013-07-25 00:00:00.0,8827,CLOSED]
  [5,2013-07-25 00:00:00.0,11318,COMPLETE]
  [6,2013-07-25 00:00:00.0,7130,COMPLETE]
  [7,2013-07-25 00:00:00.0,4530,COMPLETE]
  [8,2013-07-25 00:00:00.0,2911,PROCESSING]
  [9,2013-07-25 00:00:00.0,5657,PENDING_PAYMENT]
  [10,2013-07-25 00:00:00.0,5648,PENDING_PAYMENT]
  ~~~
