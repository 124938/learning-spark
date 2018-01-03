## DataFrame - Using Hive Context (aka org.apache.spark.sql.hive.HiveContext)
  
### Configuration

* **Dependency:**
  * Since Hive has a large number of dependencies, it's not included in default spark dependencies
  * Add below dependency to use HiveContext in your project
    
  ~~~
  libraryDependencies += "org.apache.spark" % "spark-hive_2.10" % "1.6.3"
  ~~~
  
* **Hive integration with Spark:**
  * _YARN:_
    * Make sure to integrate Hive with Spark (Click [here](https://github.com/124938/learning-hadoop-vendors/blob/master/cloudera/_1_quickstart_vm/_1_3_spark_hive_setup/README.md) to know more details on it)
  * _Local OR Standalone:_
    * Not sure

### Pre-Requisite

* **Start `spark-shell` in YARN mode:**

~~~
[cloudera@quickstart ~]$ spark-shell --master yarn --num-executors 1 --executor-memory 512M
Setting default log level to "WARN".
To adjust logging level use sc.setLogLevel(newLevel).
SLF4J: Class path contains multiple SLF4J bindings.
SLF4J: Found binding in [jar:file:/usr/lib/zookeeper/lib/slf4j-log4j12-1.7.5.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: Found binding in [jar:file:/usr/lib/flume-ng/lib/slf4j-log4j12-1.7.5.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: Found binding in [jar:file:/usr/lib/parquet/lib/slf4j-log4j12-1.7.5.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: Found binding in [jar:file:/usr/lib/avro/avro-tools-1.7.6-cdh5.12.0.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: See http://www.slf4j.org/codes.html#multiple_bindings for an explanation.
SLF4J: Actual binding is of type [org.slf4j.impl.Log4jLoggerFactory]
Welcome to
      ____              __
     / __/__  ___ _____/ /__
    _\ \/ _ \/ _ `/ __/  '_/
   /___/ .__/\_,_/_/ /_/\_\   version 1.6.0
      /_/

Using Scala version 2.10.5 (Java HotSpot(TM) 64-Bit Server VM, Java 1.7.0_67)
Type in expressions to have them evaluated.
Type :help for more information.
Spark context available as sc (master = yarn-client, app id = application_1514521302404_0007).
SQL context available as sqlContext.

scala> sqlContext
res0: org.apache.spark.sql.SQLContext = org.apache.spark.sql.hive.HiveContext@5c153530

scala> sqlContext.
setConf("spark.sql.shuffle.partitions", "2")

scala> sqlContext.
sql("show databases").
show
+------------------+
|            result|
+------------------+
|           default|
|         retail_db|
|    retail_db_avro|
| retail_db_parquet|
|retail_db_sequence|
+------------------+

scala> sqlContext.
sql("use retail_db_avro")
res3: org.apache.spark.sql.DataFrame = [result: string]

scala> sqlContext.
sql("show tables").
show
+-----------+-----------+
|  tableName|isTemporary|
+-----------+-----------+
| categories|      false|
|  customers|      false|
|departments|      false|
|order_items|      false|
|     orders|      false|
|   products|      false|
+-----------+-----------+

scala> 
~~~

* **Create instance of `org.apache.spark.sql.hive.HiveContext`**:

~~~
scala> import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.sql.hive.HiveContext

scala> val hiveContext = new HiveContext(sc)
hiveContext: org.apache.spark.sql.hive.HiveContext = org.apache.spark.sql.hive.HiveContext@634b5f0c

scala> hiveContext.
setConf("spark.sql.shuffle.partitions", "2")

scala> hiveContext.
sql("show databases").
show
+------------------+
|            result|
+------------------+
|           default|
|         retail_db|
|    retail_db_avro|
| retail_db_parquet|
|retail_db_sequence|
+------------------+

scala> hiveContext.
sql("use retail_db_parquet")
res8: org.apache.spark.sql.DataFrame = [result: string]

scala> hiveContext.
sql("show tables").
show
+-----------+-----------+
|  tableName|isTemporary|
+-----------+-----------+
| categories|      false|
|  customers|      false|
|departments|      false|
|order_items|      false|
|     orders|      false|
|   products|      false|
+-----------+-----------+

scala> hiveContext.
sql("select * from orders limit 10").
show
+--------+-------------+-----------------+---------------+                      
|order_id|   order_date|order_customer_id|   order_status|
+--------+-------------+-----------------+---------------+
|       1|1374735600000|            11599|         CLOSED|
|       2|1374735600000|              256|PENDING_PAYMENT|
|       3|1374735600000|            12111|       COMPLETE|
|       4|1374735600000|             8827|         CLOSED|
|       5|1374735600000|            11318|       COMPLETE|
|       6|1374735600000|             7130|       COMPLETE|
|       7|1374735600000|             4530|       COMPLETE|
|       8|1374735600000|             2911|     PROCESSING|
|       9|1374735600000|             5657|PENDING_PAYMENT|
|      10|1374735600000|             5648|PENDING_PAYMENT|
+--------+-------------+-----------------+---------------+

scala> 
~~~

* **_Note:_**
  * In case of YARN mode, sqlContext is by default created with `org.apache.spark.sql.hive.HiveContext`

### Operations
* DataFrame created from Hive Context (aka org.apache.spark.sql.hive.HiveContext) supports following type of transformations:
  * Filter
  * Aggregation
  * Join
  * Set
  * Soring & Ranking
  * Analytical or Windowing functions
  * RDD conversion
