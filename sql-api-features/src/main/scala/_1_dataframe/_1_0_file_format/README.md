## File Format

### Pre-Requisite

* Login to Cloudera Quick Start VM using ssh (Refer below snapshot)

~~~
asus@asus-GL553VD:~$ ssh cloudera@192.168.211.142
cloudera@192.168.211.142's password: 
Last login: Sun Oct 29 18:49:10 2017 from 192.168.211.1
[cloudera@quickstart ~]$
~~~

* Start `spark-shell` (Refer below snapshot)

~~~
[cloudera@quickstart ~]$ spark-shell --master yarn
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
17/11/12 18:11:06 WARN util.NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
17/11/12 18:11:06 WARN util.Utils: Your hostname, quickstart.cloudera resolves to a loopback address: 127.0.0.1; using 192.168.211.142 instead (on interface eth1)
17/11/12 18:11:06 WARN util.Utils: Set SPARK_LOCAL_IP if you need to bind to another address
17/11/12 18:11:09 WARN shortcircuit.DomainSocketFactory: The short-circuit local reads feature cannot be used because libhadoop cannot be loaded.
Spark context available as sc (master = yarn-client, app id = application_1509278183296_0023).
SQL context available as sqlContext.
~~~

### (1) Text File

* Text file supports following compression codec:
  * Snappy i.e. `org.apache.hadoop.io.compress.SnappyCodec`
  * GZip i.e. `org.apache.hadoop.io.compress.GzipCodec`
  * BZip2 i.e. `org.apache.hadoop.io.compress.BZip2Codec`

* Read/Write text file using above compression codec (Refer below snapshot)
~~~
scala> import org.apache.hadoop.io.compress.SnappyCodec
import org.apache.hadoop.io.compress.SnappyCodec

scala> import org.apache.hadoop.io.compress.GzipCodec
import org.apache.hadoop.io.compress.GzipCodec

scala> import org.apache.hadoop.io.compress.BZip2Codec
import org.apache.hadoop.io.compress.BZip2Codec

scala> sc.
     textFile("sqoop/import-all-tables-text/orders").
     saveAsTextFile("tmp/orders/text")

scala> sc.
     textFile("tmp/orders/text").
     take(2).
     foreach(println)
1,2013-07-25 00:00:00.0,11599,CLOSED
2,2013-07-25 00:00:00.0,256,PENDING_PAYMENT

scala> sc.
     textFile("sqoop/import-all-tables-text/orders").
     saveAsTextFile("tmp/orders/text_snappy", classOf[SnappyCodec])

scala> sc.
     textFile("tmp/orders/text_snappy").
     take(2).
     foreach(println)
1,2013-07-25 00:00:00.0,11599,CLOSED
2,2013-07-25 00:00:00.0,256,PENDING_PAYMENT

scala> sc.
     textFile("sqoop/import-all-tables-text/orders").
     saveAsTextFile("tmp/orders/text_gzip", classOf[GzipCodec])
                                                                                
scala> sc.
     textFile("tmp/orders/text_gzip").
     take(2).
     foreach(println)
1,2013-07-25 00:00:00.0,11599,CLOSED
2,2013-07-25 00:00:00.0,256,PENDING_PAYMENT

scala> sc.
     textFile("sqoop/import-all-tables-text/orders").
     saveAsTextFile("tmp/orders/text_bzip2", classOf[BZip2Codec])

scala> sc.
     textFile("tmp/orders/text_bzip2").
     take(2).
     foreach(println)
1,2013-07-25 00:00:00.0,11599,CLOSED
2,2013-07-25 00:00:00.0,256,PENDING_PAYMENT
~~~

* Display list of generated text files using HDFS commands (Refer below snapshot)
~~~
[cloudera@quickstart ~]$ hadoop fs -ls -h -R tmp/orders
drwxr-xr-x   - cloudera cloudera          0 2017-11-12 18:19 tmp/orders/text
-rw-r--r--   1 cloudera cloudera          0 2017-11-12 18:19 tmp/orders/text/_SUCCESS
-rw-r--r--   1 cloudera cloudera      1.4 M 2017-11-12 18:19 tmp/orders/text/part-00000
-rw-r--r--   1 cloudera cloudera      1.4 M 2017-11-12 18:19 tmp/orders/text/part-00001
drwxr-xr-x   - cloudera cloudera          0 2017-11-12 18:21 tmp/orders/text_bzip2
-rw-r--r--   1 cloudera cloudera          0 2017-11-12 18:21 tmp/orders/text_bzip2/_SUCCESS
-rw-r--r--   1 cloudera cloudera    187.6 K 2017-11-12 18:21 tmp/orders/text_bzip2/part-00000.bz2
-rw-r--r--   1 cloudera cloudera    191.0 K 2017-11-12 18:21 tmp/orders/text_bzip2/part-00001.bz2
drwxr-xr-x   - cloudera cloudera          0 2017-11-12 18:21 tmp/orders/text_gzip
-rw-r--r--   1 cloudera cloudera          0 2017-11-12 18:21 tmp/orders/text_gzip/_SUCCESS
-rw-r--r--   1 cloudera cloudera    228.0 K 2017-11-12 18:21 tmp/orders/text_gzip/part-00000.gz
-rw-r--r--   1 cloudera cloudera    232.4 K 2017-11-12 18:21 tmp/orders/text_gzip/part-00001.gz
drwxr-xr-x   - cloudera cloudera          0 2017-11-12 18:20 tmp/orders/text_snappy
-rw-r--r--   1 cloudera cloudera          0 2017-11-12 18:20 tmp/orders/text_snappy/_SUCCESS
-rw-r--r--   1 cloudera cloudera    429.3 K 2017-11-12 18:20 tmp/orders/text_snappy/part-00000.snappy
-rw-r--r--   1 cloudera cloudera    441.0 K 2017-11-12 18:20 tmp/orders/text_snappy/part-00001.snappy
~~~

### (2) Sequence File

* Sequence file supports following compression codec:
  * Snappy i.e. `org.apache.hadoop.io.compress.SnappyCodec`
  * GZip i.e. `org.apache.hadoop.io.compress.GzipCodec`
  * BZip2 i.e. `org.apache.hadoop.io.compress.BZip2Codec`

* Read/Write sequence file using above compression codec (Refer below snapshot)
~~~
scala> import org.apache.hadoop.io.IntWritable
import org.apache.hadoop.io.IntWritable

scala> import org.apache.hadoop.io.Text
import org.apache.hadoop.io.Text

scala> sc.
     textFile("sqoop/import-all-tables-text/orders").
     map((rec: String) => (rec.split(",")(0).toInt, rec)).
     saveAsSequenceFile("tmp/orders/seq")
                                                                                
scala> sc.
     sequenceFile("tmp/orders/seq", classOf[IntWritable], classOf[Text]).
     map((t: (IntWritable, Text)) => t._2.toString).
     take(5).
     foreach(println)
1,2013-07-25 00:00:00.0,11599,CLOSED
2,2013-07-25 00:00:00.0,256,PENDING_PAYMENT
3,2013-07-25 00:00:00.0,12111,COMPLETE
4,2013-07-25 00:00:00.0,8827,CLOSED
5,2013-07-25 00:00:00.0,11318,COMPLETE

scala> sc.
     textFile("sqoop/import-all-tables-text/orders").
     map((rec: String) => (rec.split(",")(0).toInt, rec)).
     saveAsSequenceFile("tmp/orders/seq_snappy", Some(classOf[SnappyCodec]))

scala> sc.
     sequenceFile("tmp/orders/seq_snappy", classOf[IntWritable], classOf[Text]).
     map((t: (IntWritable, Text)) => t._2.toString).
     take(5).
     foreach(println)
1,2013-07-25 00:00:00.0,11599,CLOSED
2,2013-07-25 00:00:00.0,256,PENDING_PAYMENT
3,2013-07-25 00:00:00.0,12111,COMPLETE
4,2013-07-25 00:00:00.0,8827,CLOSED
5,2013-07-25 00:00:00.0,11318,COMPLETE

scala> sc.
     textFile("sqoop/import-all-tables-text/orders").
     map((rec: String) => (rec.split(",")(0).toInt, rec)).
     saveAsSequenceFile("tmp/orders/seq_gzip", Some(classOf[GzipCodec]))

scala> sc.
     sequenceFile("tmp/orders/seq_gzip", classOf[IntWritable], classOf[Text]).
     map((t: (IntWritable, Text)) => t._2.toString).
     take(5).
     foreach(println)
1,2013-07-25 00:00:00.0,11599,CLOSED
2,2013-07-25 00:00:00.0,256,PENDING_PAYMENT
3,2013-07-25 00:00:00.0,12111,COMPLETE
4,2013-07-25 00:00:00.0,8827,CLOSED
5,2013-07-25 00:00:00.0,11318,COMPLETE

scala> sc.
     textFile("sqoop/import-all-tables-text/orders").
     map((rec: String) => (rec.split(",")(0).toInt, rec)).
     saveAsSequenceFile("tmp/orders/seq_bzip2", Some(classOf[BZip2Codec]))

scala> sc.
     sequenceFile("tmp/orders/seq_bzip2", classOf[IntWritable], classOf[Text]).
     map((t: (IntWritable, Text)) => t._2.toString).
     take(5).
     foreach(println)
1,2013-07-25 00:00:00.0,11599,CLOSED
2,2013-07-25 00:00:00.0,256,PENDING_PAYMENT
3,2013-07-25 00:00:00.0,12111,COMPLETE
4,2013-07-25 00:00:00.0,8827,CLOSED
5,2013-07-25 00:00:00.0,11318,COMPLETE

~~~

* Display list of generated sequence files using HDFS commands (Refer below snapshot)
~~~
[cloudera@quickstart ~]$ hadoop fs -ls -h -R tmp/orders
drwxr-xr-x   - cloudera cloudera          0 2017-11-12 18:54 tmp/orders/seq
-rw-r--r--   1 cloudera cloudera          0 2017-11-12 18:54 tmp/orders/seq/_SUCCESS
-rw-r--r--   1 cloudera cloudera      1.8 M 2017-11-12 18:54 tmp/orders/seq/part-00000
-rw-r--r--   1 cloudera cloudera      1.8 M 2017-11-12 18:54 tmp/orders/seq/part-00001
drwxr-xr-x   - cloudera cloudera          0 2017-11-12 19:07 tmp/orders/seq_bzip2
-rw-r--r--   1 cloudera cloudera          0 2017-11-12 19:07 tmp/orders/seq_bzip2/_SUCCESS
-rw-r--r--   1 cloudera cloudera    231.0 K 2017-11-12 19:07 tmp/orders/seq_bzip2/part-00000
-rw-r--r--   1 cloudera cloudera    230.9 K 2017-11-12 19:07 tmp/orders/seq_bzip2/part-00001
drwxr-xr-x   - cloudera cloudera          0 2017-11-12 19:06 tmp/orders/seq_gzip
-rw-r--r--   1 cloudera cloudera          0 2017-11-12 19:06 tmp/orders/seq_gzip/_SUCCESS
-rw-r--r--   1 cloudera cloudera    336.1 K 2017-11-12 19:06 tmp/orders/seq_gzip/part-00000
-rw-r--r--   1 cloudera cloudera    338.6 K 2017-11-12 19:06 tmp/orders/seq_gzip/part-00001
drwxr-xr-x   - cloudera cloudera          0 2017-11-12 19:04 tmp/orders/seq_snappy
-rw-r--r--   1 cloudera cloudera          0 2017-11-12 19:04 tmp/orders/seq_snappy/_SUCCESS
-rw-r--r--   1 cloudera cloudera    616.2 K 2017-11-12 19:04 tmp/orders/seq_snappy/part-00000
-rw-r--r--   1 cloudera cloudera    616.1 K 2017-11-12 19:04 tmp/orders/seq_snappy/part-00001
~~~

* View Sequence file using HDFS command (Refer below snapshot)
~~~
[cloudera@quickstart ~]$ hadoop fs -text tmp/orders/text_snappy/part-00000.snappy | more
17/11/12 19:21:46 INFO compress.CodecPool: Got brand-new decompressor [.snappy]
1,2013-07-25 00:00:00.0,11599,CLOSED
2,2013-07-25 00:00:00.0,256,PENDING_PAYMENT
3,2013-07-25 00:00:00.0,12111,COMPLETE
4,2013-07-25 00:00:00.0,8827,CLOSED
5,2013-07-25 00:00:00.0,11318,COMPLETE
6,2013-07-25 00:00:00.0,7130,COMPLETE
7,2013-07-25 00:00:00.0,4530,COMPLETE
~~~

### (3) JSON File

* JSON file supports following compression codec:
  * Snappy i.e. `org.apache.hadoop.io.compress.SnappyCodec`
  * GZip i.e. `org.apache.hadoop.io.compress.GzipCodec`
  * BZip2 i.e. `org.apache.hadoop.io.compress.BZip2Codec`

* Read/Write JSON file using above compression codec (Refer below snapshot)
~~~
scala> import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.SQLContext

scala> val sqlContext = new SQLContext(sc)
sqlContext: org.apache.spark.sql.SQLContext = org.apache.spark.sql.SQLContext@4afb3566

scala> import sqlContext.implicits._
import sqlContext.implicits._

scala> val ordersDF = sc.
     textFile("sqoop/import-all-tables-text/orders").
     map((rec: String) => {
       val recArray = rec.split(",")
       (recArray(0).toInt, recArray(1), recArray(2).toInt, recArray(3))
     }).
     toDF("order_id", "order_date", "order_customer_id", "order_status")
ordersDF: org.apache.spark.sql.DataFrame = [order_id: int, order_date: string, order_customer_id: int, order_status: string]

scala> ordersDF.
     show(3)
+--------+--------------------+-----------------+---------------+
|order_id|          order_date|order_customer_id|   order_status|
+--------+--------------------+-----------------+---------------+
|       1|2013-07-25 00:00:...|            11599|         CLOSED|
|       2|2013-07-25 00:00:...|              256|PENDING_PAYMENT|
|       3|2013-07-25 00:00:...|            12111|       COMPLETE|
+--------+--------------------+-----------------+---------------+
only showing top 3 rows


scala> import org.apache.spark.sql.SaveMode
import org.apache.spark.sql.SaveMode

scala> ordersDF.
     write.
     mode(SaveMode.Overwrite).
     json("tmp/orders/json")
                                                                                
scala> sqlContext.
     read.
     json("tmp/orders/json").
     show(3)
+-----------------+--------------------+--------+---------------+               
|order_customer_id|          order_date|order_id|   order_status|
+-----------------+--------------------+--------+---------------+
|            11599|2013-07-25 00:00:...|       1|         CLOSED|
|              256|2013-07-25 00:00:...|       2|PENDING_PAYMENT|
|            12111|2013-07-25 00:00:...|       3|       COMPLETE|
+-----------------+--------------------+--------+---------------+
only showing top 3 rows

scala> ordersDF.
     toJSON.
     saveAsTextFile("tmp/orders/json_gzip", classOf[org.apache.hadoop.io.compress.GzipCodec])

scala> sqlContext.
     read.
     json("tmp/orders/json_gzip").
     show(3)
+-----------------+--------------------+--------+---------------+
|order_customer_id|          order_date|order_id|   order_status|
+-----------------+--------------------+--------+---------------+
|            11599|2013-07-25 00:00:...|       1|         CLOSED|
|              256|2013-07-25 00:00:...|       2|PENDING_PAYMENT|
|            12111|2013-07-25 00:00:...|       3|       COMPLETE|
+-----------------+--------------------+--------+---------------+
only showing top 3 rows

scala> ordersDF.
     toJSON.
     saveAsTextFile("tmp/orders/json_bzip2", classOf[org.apache.hadoop.io.compress.BZip2Codec])
                                                                                
scala> sqlContext.
     read.
     json("tmp/orders/json_bzip2").
     show(3)
+-----------------+--------------------+--------+---------------+
|order_customer_id|          order_date|order_id|   order_status|
+-----------------+--------------------+--------+---------------+
|            11599|2013-07-25 00:00:...|       1|         CLOSED|
|              256|2013-07-25 00:00:...|       2|PENDING_PAYMENT|
|            12111|2013-07-25 00:00:...|       3|       COMPLETE|
+-----------------+--------------------+--------+---------------+
only showing top 3 rows

scala> ordersDF.
     toJSON.
     saveAsTextFile("tmp/orders/json_snappy", classOf[org.apache.hadoop.io.compress.SnappyCodec])

scala> sqlContext.
     read.
     json("tmp/orders/json_snappy").
     show(3)
+-----------------+--------------------+--------+---------------+
|order_customer_id|          order_date|order_id|   order_status|
+-----------------+--------------------+--------+---------------+
|            11599|2013-07-25 00:00:...|       1|         CLOSED|
|              256|2013-07-25 00:00:...|       2|PENDING_PAYMENT|
|            12111|2013-07-25 00:00:...|       3|       COMPLETE|
+-----------------+--------------------+--------+---------------+
only showing top 3 rows
~~~

* Display list of generated sequence files using HDFS commands (Refer below snapshot)
~~~
[cloudera@quickstart ~]$ hadoop fs -ls -h -R tmp/orders
drwxr-xr-x   - cloudera cloudera          0 2017-11-12 19:52 tmp/orders/json
-rw-r--r--   1 cloudera cloudera          0 2017-11-12 19:52 tmp/orders/json/_SUCCESS
-rw-r--r--   1 cloudera cloudera      3.6 M 2017-11-12 19:52 tmp/orders/json/part-r-00000-7e356a9d-8d82-4e0d-8916-6b47f71e0213
-rw-r--r--   1 cloudera cloudera      3.6 M 2017-11-12 19:52 tmp/orders/json/part-r-00001-7e356a9d-8d82-4e0d-8916-6b47f71e0213
drwxr-xr-x   - cloudera cloudera          0 2017-11-12 20:03 tmp/orders/json_bzip2
-rw-r--r--   1 cloudera cloudera          0 2017-11-12 20:03 tmp/orders/json_bzip2/_SUCCESS
-rw-r--r--   1 cloudera cloudera    183.6 K 2017-11-12 20:03 tmp/orders/json_bzip2/part-00000.bz2
-rw-r--r--   1 cloudera cloudera    188.8 K 2017-11-12 20:03 tmp/orders/json_bzip2/part-00001.bz2
drwxr-xr-x   - cloudera cloudera          0 2017-11-12 20:00 tmp/orders/json_gzip
-rw-r--r--   1 cloudera cloudera          0 2017-11-12 20:00 tmp/orders/json_gzip/_SUCCESS
-rw-r--r--   1 cloudera cloudera    282.0 K 2017-11-12 20:00 tmp/orders/json_gzip/part-00000.gz
-rw-r--r--   1 cloudera cloudera    289.4 K 2017-11-12 20:00 tmp/orders/json_gzip/part-00001.gz
drwxr-xr-x   - cloudera cloudera          0 2017-11-12 20:05 tmp/orders/json_snappy
-rw-r--r--   1 cloudera cloudera          0 2017-11-12 20:05 tmp/orders/json_snappy/_SUCCESS
-rw-r--r--   1 cloudera cloudera    513.4 K 2017-11-12 20:05 tmp/orders/json_snappy/part-00000.snappy
-rw-r--r--   1 cloudera cloudera    523.0 K 2017-11-12 20:05 tmp/orders/json_snappy/part-00001.snappy
~~~

* View JSON file using HDFS command (Refer below snapshot)
~~~
[cloudera@quickstart ~]$ hadoop fs -text tmp/orders/text_bzip2/part-00000.bz2 | more
17/11/12 20:10:51 INFO bzip2.Bzip2Factory: Successfully loaded & initialized native-bzip2 library system-native
17/11/12 20:10:51 INFO compress.CodecPool: Got brand-new decompressor [.bz2]
1,2013-07-25 00:00:00.0,11599,CLOSED
2,2013-07-25 00:00:00.0,256,PENDING_PAYMENT
3,2013-07-25 00:00:00.0,12111,COMPLETE
4,2013-07-25 00:00:00.0,8827,CLOSED
5,2013-07-25 00:00:00.0,11318,COMPLETE
6,2013-07-25 00:00:00.0,7130,COMPLETE
7,2013-07-25 00:00:00.0,4530,COMPLETE
8,2013-07-25 00:00:00.0,2911,PROCESSING
9,2013-07-25 00:00:00.0,5657,PENDING_PAYMENT
10,2013-07-25 00:00:00.0,5648,PENDING_PAYMENT
11,2013-07-25 00:00:00.0,918,PAYMENT_REVIEW
12,2013-07-25 00:00:00.0,1837,CLOSED
~~~
