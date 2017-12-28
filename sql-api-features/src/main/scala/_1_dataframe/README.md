## DataFrame

### What is DataFrame?
* DataFrame is an abstraction, which gives a schema view on top of data i.e.
  * We can think DataFrame like a table in database
  * It provides data as columns with name & type

* Like RDD, execution in DataFrame too is lazily triggered

* It offers huge performance improvement over RDDs because of following powerful features:
  
  * **Optimized Execution Engine (aka Catalyst Optimizer):**
    * Query plans are created for execution using Spark catalyst optimizer
    * After an optimized execution plan is prepared going through some steps, the final execution happens internally on RDDs only but that's completely hidden from users
    
    ![Alt text](_images/spark-sql-catalyst-optimizer-model.png?raw=true "Spark SQL - Catalyst Optimizer")
    
  * **Custom Memory Management (aka Project Tungsten):**
    * Data is stored in off-heap memory in binary format, which saves a lot of memory space & also there is no overhead of garbage collection
    * By knowing schema of data in advance and storing it efficiently in binary format, expensive java serialization is also avoided

### DataFrame - Creation

**(1) : Using Native Context (aka org.apache.spark.sql.SQLContext)** 
* With a SQLContext, application can create DataFrame in following ways:
  * From Existing RDD
  * From Data Sources i.e.
    * JSON File
    * Parquet File
    * Avro File
    * JDBC
    * Many more...

* Refer below snapshot for how to create DataFrame:
  
  * **_Start `spark-shell`:_**
  ~~~
  asus@asus-GL553VD:~$ spark-shell --master local[*]
  log4j:WARN Please initialize the log4j system properly.
  log4j:WARN No appenders could be found for logger (org.apache.hadoop.metrics2.lib.MutableMetricsFactory).
  log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
  Using Spark's repl log4j profile: org/apache/spark/log4j-defaults-repl.properties
  To adjust logging level use sc.setLogLevel("INFO")
  Welcome to
        ____              __
       / __/__  ___ _____/ /__
      _\ \/ _ \/ _ `/ __/  '_/
     /___/ .__/\_,_/_/ /_/\_\   version 1.6.3
        /_/

  Using Scala version 2.10.5 (OpenJDK 64-Bit Server VM, Java 1.8.0_151)
  Type in expressions to have them evaluated.
  Type :help for more information.
  Spark context available as sc.
  SQL context available as sqlContext.
  ~~~
  
  * **_Create instance of `org.apache.spark.sql.SQLContext`:_**
  ~~~
  scala> import org.apache.spark.sql.SQLContext
  import org.apache.spark.sql.SQLContext
  
  scala> val sqlContext = new SQLContext(sc)
  sqlContext: org.apache.spark.sql.SQLContext = org.apache.spark.sql.SQLContext@7ab7a4eb
  
  scala> import sqlContext.implicits._
  import sqlContext.implicits._
  ~~~
  
  * **_Create DataFrame from RDD_**
  ~~~
  scala> case class OrderRDD(order_id: Int, order_date: String, order_customer_id: Int, order_status: String) 
  defined class OrderRDD
  
  scala> val orderDF1 = sc.
  textFile("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/orders/text").
  map((rec: String) => {
    val recArray = rec.split(",")
    OrderRDD(recArray(0).toInt, recArray(1), recArray(2).toInt, recArray(3))
  }).
  toDF()
  orderDF1: org.apache.spark.sql.DataFrame = [order_id: int, order_date: string, order_customer_id: int, order_status: string]
  
  scala> orderDF1.
  show(5)
  +--------+--------------------+-----------------+---------------+
  |order_id|          order_date|order_customer_id|   order_status|
  +--------+--------------------+-----------------+---------------+
  |       1|2013-07-25 00:00:...|            11599|         CLOSED|
  |       2|2013-07-25 00:00:...|              256|PENDING_PAYMENT|
  |       3|2013-07-25 00:00:...|            12111|       COMPLETE|
  |       4|2013-07-25 00:00:...|             8827|         CLOSED|
  |       5|2013-07-25 00:00:...|            11318|       COMPLETE|
  +--------+--------------------+-----------------+---------------+
  only showing top 5 rows
  ~~~  

**(2) : Using Hive Context (aka org.apache.spark.sql.hive.HiveContext)** 
* HiveContext is an extension of SQLContext, which provides supports for reading/writing data from/to Apache Hive
* With a HiveContext, application can create DataFrame in following ways:
  * TODO
  
### DataFrame - Operations
* DataFrame supports following type of transformations:
  * Filter
  * Aggregation
  * Join
  * Set
  * Soring & Ranking
  * Analytical or Windowing functions
  * RDD conversion
  
