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
    
    ![Alt text](images/spark-sql-catalyst-optimizer-model.png?raw=true "Spark SQL - Catalyst Optimizer")
    
  * **Custom Memory Management (aka Project Tungsten):**
    * Data is stored in off-heap memory in binary format, which saves a lot of memory space & also there is no overhead of garbage collection
    * By knowing schema of data in advance and storing it efficiently in binary format, expensive java serialization is also avoided

### Creating DataFrame

**Start `spark-shell` in local mode:**
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
17/12/24 18:11:03 WARN Utils: Your hostname, asus-GL553VD resolves to a loopback address: 127.0.1.1; using 192.168.0.103 instead (on interface enp3s0)
17/12/24 18:11:03 WARN Utils: Set SPARK_LOCAL_IP if you need to bind to another address
Spark context available as sc.
17/12/24 18:11:23 WARN ObjectStore: Version information not found in metastore. hive.metastore.schema.verification is not enabled so recording the schema version 1.2.0
17/12/24 18:11:23 WARN ObjectStore: Failed to get database default, returning NoSuchObjectException
SQL context available as sqlContext.

scala> import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.SQLContext

scala> val sqlContext = new SQLContext(sc)
sqlContext: org.apache.spark.sql.SQLContext = org.apache.spark.sql.SQLContext@7ab7a4eb

~~~

**(1) : Using Native Context (aka org.apache.spark.sql.SQLContext) :** With a SQLContext, application can create dataFrame in following ways:
  
  * **From Existing RDD:**
    *_1.1: Inferring the schema using reflection i.e. using case class_
    ~~~
    scala> import sqlContext.implicits._
    import sqlContext.implicits._

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
    ~~~
    
    *_1.2: Programmatically specifying the schema using StructField_
    ~~~
    scala> import org.apache.spark.sql.Row
    import org.apache.spark.sql.Row

    scala> val orderRowRDD = sc.
    textFile("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/orders/text").
    map((rec: String) => {
      val recArray = rec.split(",")
      Row(recArray(0).toInt, recArray(1), recArray(2).toInt, recArray(3))
    })
    orderRowRDD: org.apache.spark.rdd.RDD[org.apache.spark.sql.Row] = MapPartitionsRDD[7] at map at <console>:31

    scala> import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
    import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

    scala> val orderRowSchema = StructType(
    List(
      StructField("order_id", IntegerType),
      StructField("order_date", StringType),
      StructField("order_customer_id", IntegerType),
      StructField("order_status", StringType)
    ))
    orderRowSchema: org.apache.spark.sql.types.StructType = StructType(StructField(order_id,IntegerType,true), StructField(order_date,StringType,true), StructField  (order_customer_id,IntegerType,true), StructField(order_status,StringType,true))

    scala> val orderDF2 = sqlContext.
    createDataFrame(orderRowRDD, orderRowSchema)
    orderDF2: org.apache.spark.sql.DataFrame = [order_id: int, order_date: string, order_customer_id: int, order_status: string]

    scala> orderDF2.
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
    ~~~

    *_1.3: Programmatically specifying the schema using toDF_
    ~~~
    scala> val orderDF3 = sc.
    textFile("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/orders/text").
    map((rec: String) => {
    val recArray = rec.split(",")
      (recArray(0).toInt, recArray(1), recArray(2).toInt, recArray(3))
    }).
    toDF("order_id", "order_date", "order_customer_id", "order_status")
    orderDF3: org.apache.spark.sql.DataFrame = [order_id: int, order_date: string, order_customer_id: int, order_status: string]

    scala> orderDF3.
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
    ~~~

  * **From Data Source:**
    * JSON file
    * Parquet file
    * Avro file
    * JDBC
    * Many more...
    
**(2) : Using Hive Context (aka org.apache.spark.sql.hive.HiveContext)** HiveContext is an extension of SQLContext, which provides supports for reading/writing data from/to Apache Hive & below is Pre-Requisites to use HiveContext:
  
  * **Dependency:**
    * Since Hive has a large number of dependencies, it's not included in default spark dependencies
    * Add below dependency to use HiveContext in your project
    
    ~~~
    libraryDependencies += "org.apache.spark" % "spark-hive_2.10" % "1.6.3"
    ~~~
  
  * **Configuration:** 
    * _Approach-1:_ Copy following files under conf folder of Spark installation
      * `hive-site.xml`
      * `core-site.xml` => For security configuration
      * `hdfs-site.xml` => For HDFS configuration
    
    * _Approach-2:_ Create soft link of `hive-site.xml` under conf folder of Spark installation using `ln-s` command
    
### DataFrame Operations
* DataFrame supports following type of transformations:
  * Filter
  * Aggregation
  * Join
  * Set
  * Soring & Ranking
  * Analytical or Windowing functions
  * RDD conversion
  
