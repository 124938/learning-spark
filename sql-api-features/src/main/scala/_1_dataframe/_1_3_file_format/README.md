## File Format

### Launch `spark-shell` in YARN mode:

* **Pre-Requisite:**
  * Cloudera QuickStart VM should be up & running (Click [here](https://github.com/124938/learning-hadoop-vendors/tree/master/cloudera/_1_quickstart_vm/README.md) to know more details on same)
  * Make sure to configure Retail dataset setup (Click [here](https://github.com/124938/learning-hadoop-vendors/tree/master/cloudera/_1_quickstart_vm/_1_1_retail_dataset_setup) to know more details on same)
  * Make sure to configure Spark history server (Click [here](https://github.com/124938/learning-hadoop-vendors/tree/master/cloudera/_1_quickstart_vm/_1_2_spark_history_server_setup/README.md) to know more details on same)
  * Make sure to configure Hive under spark (Click [here](https://github.com/124938/learning-hadoop-vendors/blob/master/cloudera/_1_quickstart_vm/_1_3_spark_hive_setup/README.md) to know more details on same)

* **Login to Quick Start VM or gateway node of hadoop cluster using ssh:**
~~~
asus@asus-GL553VD:~$ ssh cloudera@192.168.211.142
cloudera@192.168.211.142's password: 
Last login: Sun Oct 29 18:49:10 2017 from 192.168.211.1
[cloudera@quickstart ~]$
~~~

* **Start `spark-shell`:**
~~~
[cloudera@quickstart ~]$ spark-shell --master yarn --num-executors 1 --packages com.databricks:spark-avro_2.10:2.0.1
Ivy Default Cache set to: /home/cloudera/.ivy2/cache
The jars for the packages stored in: /home/cloudera/.ivy2/jars
:: loading settings :: url = jar:file:/usr/lib/spark/lib/spark-assembly-1.6.0-cdh5.12.0-hadoop2.6.0-cdh5.12.0.jar!/org/apache/ivy/core/settings/ivysettings.xml
com.databricks#spark-avro_2.10 added as a dependency
:: resolving dependencies :: org.apache.spark#spark-submit-parent;1.0
	confs: [default]
	found com.databricks#spark-avro_2.10;2.0.1 in central
	found org.apache.avro#avro;1.7.6 in central
	found org.codehaus.jackson#jackson-core-asl;1.9.13 in central
	found org.codehaus.jackson#jackson-mapper-asl;1.9.13 in central
	found com.thoughtworks.paranamer#paranamer;2.3 in central
	found org.xerial.snappy#snappy-java;1.0.5 in central
	found org.apache.commons#commons-compress;1.4.1 in central
	found org.tukaani#xz;1.0 in central
	found org.slf4j#slf4j-api;1.6.4 in central
downloading https://repo1.maven.org/maven2/com/databricks/spark-avro_2.10/2.0.1/spark-avro_2.10-2.0.1.jar ...
	[SUCCESSFUL ] com.databricks#spark-avro_2.10;2.0.1!spark-avro_2.10.jar (1131ms)
downloading https://repo1.maven.org/maven2/org/apache/avro/avro/1.7.6/avro-1.7.6.jar ...
	[SUCCESSFUL ] org.apache.avro#avro;1.7.6!avro.jar(bundle) (3255ms)
downloading https://repo1.maven.org/maven2/org/codehaus/jackson/jackson-core-asl/1.9.13/jackson-core-asl-1.9.13.jar ...
	[SUCCESSFUL ] org.codehaus.jackson#jackson-core-asl;1.9.13!jackson-core-asl.jar (1943ms)
downloading https://repo1.maven.org/maven2/org/codehaus/jackson/jackson-mapper-asl/1.9.13/jackson-mapper-asl-1.9.13.jar ...
	[SUCCESSFUL ] org.codehaus.jackson#jackson-mapper-asl;1.9.13!jackson-mapper-asl.jar (4492ms)
downloading https://repo1.maven.org/maven2/com/thoughtworks/paranamer/paranamer/2.3/paranamer-2.3.jar ...
	[SUCCESSFUL ] com.thoughtworks.paranamer#paranamer;2.3!paranamer.jar (669ms)
downloading https://repo1.maven.org/maven2/org/xerial/snappy/snappy-java/1.0.5/snappy-java-1.0.5.jar ...
	[SUCCESSFUL ] org.xerial.snappy#snappy-java;1.0.5!snappy-java.jar(bundle) (5487ms)
downloading https://repo1.maven.org/maven2/org/apache/commons/commons-compress/1.4.1/commons-compress-1.4.1.jar ...
	[SUCCESSFUL ] org.apache.commons#commons-compress;1.4.1!commons-compress.jar (1157ms)
downloading https://repo1.maven.org/maven2/org/slf4j/slf4j-api/1.6.4/slf4j-api-1.6.4.jar ...
	[SUCCESSFUL ] org.slf4j#slf4j-api;1.6.4!slf4j-api.jar (617ms)
downloading https://repo1.maven.org/maven2/org/tukaani/xz/1.0/xz-1.0.jar ...
	[SUCCESSFUL ] org.tukaani#xz;1.0!xz.jar (747ms)
:: resolution report :: resolve 55805ms :: artifacts dl 19550ms
	:: modules in use:
	com.databricks#spark-avro_2.10;2.0.1 from central in [default]
	com.thoughtworks.paranamer#paranamer;2.3 from central in [default]
	org.apache.avro#avro;1.7.6 from central in [default]
	org.apache.commons#commons-compress;1.4.1 from central in [default]
	org.codehaus.jackson#jackson-core-asl;1.9.13 from central in [default]
	org.codehaus.jackson#jackson-mapper-asl;1.9.13 from central in [default]
	org.slf4j#slf4j-api;1.6.4 from central in [default]
	org.tukaani#xz;1.0 from central in [default]
	org.xerial.snappy#snappy-java;1.0.5 from central in [default]
	---------------------------------------------------------------------
	|                  |            modules            ||   artifacts   |
	|       conf       | number| search|dwnlded|evicted|| number|dwnlded|
	---------------------------------------------------------------------
	|      default     |   9   |   9   |   9   |   0   ||   9   |   9   |
	---------------------------------------------------------------------
:: retrieving :: org.apache.spark#spark-submit-parent
	confs: [default]
	9 artifacts copied, 0 already retrieved (3100kB/22ms)
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
17/12/28 22:34:46 WARN util.NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
17/12/28 22:34:47 WARN util.Utils: Your hostname, quickstart.cloudera resolves to a loopback address: 127.0.0.1; using 192.168.211.142 instead (on interface eth1)
17/12/28 22:34:47 WARN util.Utils: Set SPARK_LOCAL_IP if you need to bind to another address
17/12/28 22:34:48 WARN shortcircuit.DomainSocketFactory: The short-circuit local reads feature cannot be used because libhadoop cannot be loaded.
Spark context available as sc (master = yarn-client, app id = application_1514521302404_0003).
SQL context available as sqlContext.

scala>
~~~

* **Create instance of `org.apache.spark.sql.SQLContext`:**

~~~
scala> import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.SQLContext

scala> val sqlContext = new SQLContext(sc)
sqlContext: org.apache.spark.sql.SQLContext = org.apache.spark.sql.SQLContext@4afb3566

scala> import sqlContext.implicits._
import sqlContext.implicits._
~~~

### (1) Text File

* Text file supports following compression codec:
  * Snappy i.e. `org.apache.hadoop.io.compress.SnappyCodec`
  * GZip i.e. `org.apache.hadoop.io.compress.GzipCodec`
  * BZip2 i.e. `org.apache.hadoop.io.compress.BZip2Codec`

* Refer below code snippet to Read/Write text file without compression

~~~
scala> sc.
textFile("sqoop/import-all-tables-text/orders").
saveAsTextFile("tmp/orders/text")

scala> sc.
textFile("tmp/orders/text").
take(2).
foreach(println)
1,2013-07-25 00:00:00.0,11599,CLOSED
2,2013-07-25 00:00:00.0,256,PENDING_PAYMENT
~~~

* Refer below code snippet to Read/Write text file with `org.apache.hadoop.io.compress.SnappyCodec` compression

~~~
scala> sc.
textFile("sqoop/import-all-tables-text/orders").
saveAsTextFile("tmp/orders/text_snappy", classOf[org.apache.hadoop.io.compress.SnappyCodec])

scala> sc.
textFile("tmp/orders/text_snappy").
take(2).
foreach(println)
1,2013-07-25 00:00:00.0,11599,CLOSED
2,2013-07-25 00:00:00.0,256,PENDING_PAYMENT
~~~

* Refer below code snippet to Read/Write text file with `org.apache.hadoop.io.compress.GzipCodec` compression

~~~
scala> sc.
textFile("sqoop/import-all-tables-text/orders").
saveAsTextFile("tmp/orders/text_gzip", classOf[org.apache.hadoop.io.compress.GzipCodec])

scala> sc.
textFile("tmp/orders/text_gzip").
take(2).
foreach(println)
1,2013-07-25 00:00:00.0,11599,CLOSED
2,2013-07-25 00:00:00.0,256,PENDING_PAYMENT
~~~

* Refer below code snippet to Read/Write text file with `org.apache.hadoop.io.compress.BZip2Codec` compression

~~~
scala> sc.
textFile("sqoop/import-all-tables-text/orders").
saveAsTextFile("tmp/orders/text_bzip2", classOf[org.apache.hadoop.io.compress.BZip2Codec])

scala> sc.
textFile("tmp/orders/text_bzip2").
take(2).
foreach(println)
1,2013-07-25 00:00:00.0,11599,CLOSED
2,2013-07-25 00:00:00.0,256,PENDING_PAYMENT
~~~

* Refer below HDFS command to display list of generated text files
~~~
[cloudera@quickstart ~]$ hadoop fs -ls -h -R tmp/orders | grep text
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

* Refer below HDFS command to view text file on terminal
~~~
[cloudera@quickstart conf]$ hadoop fs -text tmp/orders/text/part-00000 | more
1,2013-07-25 00:00:00.0,11599,CLOSED
2,2013-07-25 00:00:00.0,256,PENDING_PAYMENT
3,2013-07-25 00:00:00.0,12111,COMPLETE
4,2013-07-25 00:00:00.0,8827,CLOSED
5,2013-07-25 00:00:00.0,11318,COMPLETE
~~~

~~~
[cloudera@quickstart conf]$ hadoop fs -text tmp/orders/text_snappy/part-00000.snappy | more
17/12/24 03:43:59 INFO compress.CodecPool: Got brand-new decompressor [.snappy]
1,2013-07-25 00:00:00.0,11599,CLOSED
2,2013-07-25 00:00:00.0,256,PENDING_PAYMENT
3,2013-07-25 00:00:00.0,12111,COMPLETE
4,2013-07-25 00:00:00.0,8827,CLOSED
5,2013-07-25 00:00:00.0,11318,COMPLETE
~~~

~~~
[cloudera@quickstart conf]$ hadoop fs -text tmp/orders/text_gzip/part-00000.gz | more
1,2013-07-25 00:00:00.0,11599,CLOSED
2,2013-07-25 00:00:00.0,256,PENDING_PAYMENT
3,2013-07-25 00:00:00.0,12111,COMPLETE
4,2013-07-25 00:00:00.0,8827,CLOSED
5,2013-07-25 00:00:00.0,11318,COMPLETE
~~~

~~~
[cloudera@quickstart conf]$ hadoop fs -text tmp/orders/text_bzip2/part-00000.bz2 | more
17/12/24 03:46:00 INFO bzip2.Bzip2Factory: Successfully loaded & initialized native-bzip2 library system-native
17/12/24 03:46:00 INFO compress.CodecPool: Got brand-new decompressor [.bz2]
1,2013-07-25 00:00:00.0,11599,CLOSED
2,2013-07-25 00:00:00.0,256,PENDING_PAYMENT
3,2013-07-25 00:00:00.0,12111,COMPLETE
4,2013-07-25 00:00:00.0,8827,CLOSED
5,2013-07-25 00:00:00.0,11318,COMPLETE
~~~

### (2) Sequence File

* Sequence file supports following compression codec:
  * Snappy i.e. `org.apache.hadoop.io.compress.SnappyCodec`
  * GZip i.e. `org.apache.hadoop.io.compress.GzipCodec`
  * BZip2 i.e. `org.apache.hadoop.io.compress.BZip2Codec`

* Import classes required to write sequence file

~~~
scala> import org.apache.hadoop.io.IntWritable
import org.apache.hadoop.io.IntWritable

scala> import org.apache.hadoop.io.Text
import org.apache.hadoop.io.Text
~~~

* Refer below code snippet to Read/Write Sequence file without compression

~~~
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
~~~

* Refer below code snippet to Read/Write Sequence file with `org.apache.hadoop.io.compress.SnappyCodec` compression

~~~
scala> sc.
textFile("sqoop/import-all-tables-text/orders").
map((rec: String) => (rec.split(",")(0).toInt, rec)).
saveAsSequenceFile("tmp/orders/seq_snappy", Some(classOf[org.apache.hadoop.io.compress.SnappyCodec]))

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
~~~

* Refer below code snippet to Read/Write Sequence file with `org.apache.hadoop.io.compress.GzipCodec` compression

~~~
scala> sc.
textFile("sqoop/import-all-tables-text/orders").
map((rec: String) => (rec.split(",")(0).toInt, rec)).
saveAsSequenceFile("tmp/orders/seq_gzip", Some(classOf[org.apache.hadoop.io.compress.GzipCodec]))

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
~~~

* Refer below code snippet to Read/Write Sequence file with `org.apache.hadoop.io.compress.BZip2Codec` compression

~~~
scala> sc.
textFile("sqoop/import-all-tables-text/orders").
map((rec: String) => (rec.split(",")(0).toInt, rec)).
saveAsSequenceFile("tmp/orders/seq_bzip2", Some(classOf[org.apache.hadoop.io.compress.BZip2Codec]))

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

* Refer below HDFS command to display list of generated sequence files
~~~
[cloudera@quickstart ~]$ hadoop fs -ls -h -R tmp/orders | grep seq
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

* Refer below HDFS command to view sequence file (in text format) on terminal
~~~
[cloudera@quickstart conf]$ hadoop fs -text tmp/orders/seq/part-00000 | more
1	1,2013-07-25 00:00:00.0,11599,CLOSED
2	2,2013-07-25 00:00:00.0,256,PENDING_PAYMENT
3	3,2013-07-25 00:00:00.0,12111,COMPLETE
4	4,2013-07-25 00:00:00.0,8827,CLOSED
5	5,2013-07-25 00:00:00.0,11318,COMPLETE
~~~

~~~
[cloudera@quickstart conf]$ hadoop fs -text tmp/orders/seq_snappy/part-00000 | more
17/12/24 03:38:38 INFO compress.CodecPool: Got brand-new decompressor [.snappy]
17/12/24 03:38:38 INFO compress.CodecPool: Got brand-new decompressor [.snappy]
17/12/24 03:38:38 INFO compress.CodecPool: Got brand-new decompressor [.snappy]
17/12/24 03:38:38 INFO compress.CodecPool: Got brand-new decompressor [.snappy]
1	1,2013-07-25 00:00:00.0,11599,CLOSED
2	2,2013-07-25 00:00:00.0,256,PENDING_PAYMENT
3	3,2013-07-25 00:00:00.0,12111,COMPLETE
4	4,2013-07-25 00:00:00.0,8827,CLOSED
5	5,2013-07-25 00:00:00.0,11318,COMPLETE
~~~

~~~
[cloudera@quickstart conf]$ hadoop fs -text tmp/orders/seq_gzip/part-00000 | more
17/12/24 03:40:33 INFO zlib.ZlibFactory: Successfully loaded & initialized native-zlib library
17/12/24 03:40:33 INFO compress.CodecPool: Got brand-new decompressor [.gz]
17/12/24 03:40:33 INFO compress.CodecPool: Got brand-new decompressor [.gz]
17/12/24 03:40:33 INFO compress.CodecPool: Got brand-new decompressor [.gz]
17/12/24 03:40:33 INFO compress.CodecPool: Got brand-new decompressor [.gz]
1	1,2013-07-25 00:00:00.0,11599,CLOSED
2	2,2013-07-25 00:00:00.0,256,PENDING_PAYMENT
3	3,2013-07-25 00:00:00.0,12111,COMPLETE
4	4,2013-07-25 00:00:00.0,8827,CLOSED
5	5,2013-07-25 00:00:00.0,11318,COMPLETE
~~~

~~~
[cloudera@quickstart conf]$ hadoop fs -text tmp/orders/seq_bzip2/part-00000 | more
17/12/24 03:41:05 INFO bzip2.Bzip2Factory: Successfully loaded & initialized native-bzip2 library system-native
17/12/24 03:41:05 INFO compress.CodecPool: Got brand-new decompressor [.bz2]
17/12/24 03:41:05 INFO compress.CodecPool: Got brand-new decompressor [.bz2]
17/12/24 03:41:05 INFO compress.CodecPool: Got brand-new decompressor [.bz2]
17/12/24 03:41:05 INFO compress.CodecPool: Got brand-new decompressor [.bz2]
1	1,2013-07-25 00:00:00.0,11599,CLOSED
2	2,2013-07-25 00:00:00.0,256,PENDING_PAYMENT
3	3,2013-07-25 00:00:00.0,12111,COMPLETE
4	4,2013-07-25 00:00:00.0,8827,CLOSED
5	5,2013-07-25 00:00:00.0,11318,COMPLETE
~~~

### (3) JSON File

* JSON file supports following compression codec:
  * Snappy i.e. `org.apache.hadoop.io.compress.SnappyCodec`
  * GZip i.e. `org.apache.hadoop.io.compress.GzipCodec`
  * BZip2 i.e. `org.apache.hadoop.io.compress.BZip2Codec`

* Refer below code snippet to Read/Write JSON file without compression

~~~
scala> sc.
textFile("sqoop/import-all-tables-text/orders").
map((rec: String) => {
  val recArray = rec.split(",")
  (recArray(0).toInt, recArray(1), recArray(2).toInt, recArray(3))
}).
toDF("order_id", "order_date", "order_customer_id", "order_status").
write.
mode(org.apache.spark.sql.SaveMode.Overwrite).
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
~~~

* Refer below code snippet to Read/Write JSON file with `org.apache.hadoop.io.compress.GzipCodec` compression

~~~
scala> sc.
textFile("sqoop/import-all-tables-text/orders").
map((rec: String) => {
  val recArray = rec.split(",")
  (recArray(0).toInt, recArray(1), recArray(2).toInt, recArray(3))
}).
toDF("order_id", "order_date", "order_customer_id", "order_status").
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
~~~

* Refer below code snippet to Read/Write JSON file with `org.apache.hadoop.io.compress.BZip2Codec` compression

~~~
scala> sc.
textFile("sqoop/import-all-tables-text/orders").
map((rec: String) => {
  val recArray = rec.split(",")
  (recArray(0).toInt, recArray(1), recArray(2).toInt, recArray(3))
}).
toDF("order_id", "order_date", "order_customer_id", "order_status").
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
~~~

* Refer below code snippet to Read/Write JSON file with `org.apache.hadoop.io.compress.SnappyCodec` compression

~~~
scala> sc.
textFile("sqoop/import-all-tables-text/orders").
map((rec: String) => {
  val recArray = rec.split(",")
  (recArray(0).toInt, recArray(1), recArray(2).toInt, recArray(3))
}).
toDF("order_id", "order_date", "order_customer_id", "order_status").
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

* Refer below HDFS command to display list of generated JSON files
~~~
[cloudera@quickstart ~]$ hadoop fs -ls -h -R tmp/orders | grep json
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

* Refer below HDFS command to view JSON file on terminal
~~~
[cloudera@quickstart ~]$ hadoop fs -text tmp/orders/json/part-r-00000-8d09d113-b984-40c4-b875-0ae400fce382 | more
{"order_id":1,"order_date":"2013-07-25 00:00:00.0","order_customer_id":11599,"order_status":"CLOSED"}
{"order_id":2,"order_date":"2013-07-25 00:00:00.0","order_customer_id":256,"order_status":"PENDING_PAYMENT"}
{"order_id":3,"order_date":"2013-07-25 00:00:00.0","order_customer_id":12111,"order_status":"COMPLETE"}
{"order_id":4,"order_date":"2013-07-25 00:00:00.0","order_customer_id":8827,"order_status":"CLOSED"}
{"order_id":5,"order_date":"2013-07-25 00:00:00.0","order_customer_id":11318,"order_status":"COMPLETE"}
~~~

~~~
[cloudera@quickstart ~]$ hadoop fs -text tmp/orders/json_bzip2/part-00000.bz2 | more
17/12/28 20:43:30 INFO bzip2.Bzip2Factory: Successfully loaded & initialized native-bzip2 library system-native
17/12/28 20:43:30 INFO compress.CodecPool: Got brand-new decompressor [.bz2]
{"order_id":1,"order_date":"2013-07-25 00:00:00.0","order_customer_id":11599,"order_status":"CLOSED"}
{"order_id":2,"order_date":"2013-07-25 00:00:00.0","order_customer_id":256,"order_status":"PENDING_PAYMENT"}
{"order_id":3,"order_date":"2013-07-25 00:00:00.0","order_customer_id":12111,"order_status":"COMPLETE"}
{"order_id":4,"order_date":"2013-07-25 00:00:00.0","order_customer_id":8827,"order_status":"CLOSED"}
{"order_id":5,"order_date":"2013-07-25 00:00:00.0","order_customer_id":11318,"order_status":"COMPLETE"}
~~~

~~~
[cloudera@quickstart ~]$ hadoop fs -text tmp/orders/json_gzip/part-00000.gz | more
{"order_id":1,"order_date":"2013-07-25 00:00:00.0","order_customer_id":11599,"order_status":"CLOSED"}
{"order_id":2,"order_date":"2013-07-25 00:00:00.0","order_customer_id":256,"order_status":"PENDING_PAYMENT"}
{"order_id":3,"order_date":"2013-07-25 00:00:00.0","order_customer_id":12111,"order_status":"COMPLETE"}
{"order_id":4,"order_date":"2013-07-25 00:00:00.0","order_customer_id":8827,"order_status":"CLOSED"}
{"order_id":5,"order_date":"2013-07-25 00:00:00.0","order_customer_id":11318,"order_status":"COMPLETE"}
~~~

~~~
[cloudera@quickstart ~]$ hadoop fs -text tmp/orders/json_snappy/part-00000.snappy | more
17/12/28 20:45:25 INFO compress.CodecPool: Got brand-new decompressor [.snappy]
{"order_id":1,"order_date":"2013-07-25 00:00:00.0","order_customer_id":11599,"order_status":"CLOSED"}
{"order_id":2,"order_date":"2013-07-25 00:00:00.0","order_customer_id":256,"order_status":"PENDING_PAYMENT"}
{"order_id":3,"order_date":"2013-07-25 00:00:00.0","order_customer_id":12111,"order_status":"COMPLETE"}
{"order_id":4,"order_date":"2013-07-25 00:00:00.0","order_customer_id":8827,"order_status":"CLOSED"}
{"order_id":5,"order_date":"2013-07-25 00:00:00.0","order_customer_id":11318,"order_status":"COMPLETE"}
~~~

### (4) Parquet File

* Parquet file supports following compression codec:
  * GZip i.e. `org.apache.hadoop.io.compress.GzipCodec`
  * Snappy i.e. `org.apache.hadoop.io.compress.SnappyCodec`

* Refer below code snippet to Read/Write Parquet file without compression

~~~
scala> sqlContext.
setConf("spark.sql.parquet.compression.codec", "uncompressed")

scala> sc.
textFile("sqoop/import-all-tables-text/orders").
map((rec: String) => {
  val recArray = rec.split(",")
  (recArray(0).toInt, recArray(1), recArray(2).toInt, recArray(3))
}).
toDF("order_id", "order_date", "order_customer_id", "order_status").
write.
mode(org.apache.spark.sql.SaveMode.Overwrite).
parquet("tmp/orders/parquet")

scala> sqlContext.
read.
parquet("tmp/orders/parquet").
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

* Refer below code snippet to Read/Write JSON file with `org.apache.hadoop.io.compress.SnappyCodec` compression

~~~
scala> sqlContext.
setConf("spark.sql.parquet.compression.codec", "snappy")

scala> sc.
textFile("sqoop/import-all-tables-text/orders").
map((rec: String) => {
  val recArray = rec.split(",")
  (recArray(0).toInt, recArray(1), recArray(2).toInt, recArray(3))
}).
toDF("order_id", "order_date", "order_customer_id", "order_status").
write.
mode(org.apache.spark.sql.SaveMode.Overwrite).
parquet("tmp/orders/parquet_snappy")

scala> sqlContext.
read.
parquet("tmp/orders/parquet_snappy").
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

* Refer below code snippet to Read/Write JSON file with `org.apache.hadoop.io.compress.GzipCodec` compression

~~~
scala> sqlContext.
setConf("spark.sql.parquet.compression.codec", "gzip")

scala> sc.
textFile("sqoop/import-all-tables-text/orders").
map((rec: String) => {
  val recArray = rec.split(",")
  (recArray(0).toInt, recArray(1), recArray(2).toInt, recArray(3))
}).
toDF("order_id", "order_date", "order_customer_id", "order_status").
write.
mode(org.apache.spark.sql.SaveMode.Overwrite).
parquet("tmp/orders/parquet_gzip")

scala> sqlContext.
read.
parquet("tmp/orders/parquet_gzip").
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

* Refer below code snippet to Read/Write JSON file with `com.hadoop.compression.lzo.LzoCodec` compression

~~~
scala> sqlContext.
setConf("spark.sql.parquet.compression.codec", "lzo")

scala> sc.
textFile("sqoop/import-all-tables-text/orders").
map((rec: String) => {
  val recArray = rec.split(",")
  (recArray(0).toInt, recArray(1), recArray(2).toInt, recArray(3))
}).
toDF("order_id", "order_date", "order_customer_id", "order_status").
write.
mode(org.apache.spark.sql.SaveMode.Overwrite).
parquet("tmp/orders/parquet_lzo")

17/12/28 21:52:58 WARN scheduler.TaskSetManager: Lost task 0.0 in stage 21.0 (TID 35, 192.168.211.142, executor 1): parquet.hadoop.BadConfigurationException: Class com.hadoop.compression.lzo.LzoCodec was not found
	at parquet.hadoop.CodecFactory.getCodec(CodecFactory.java:161)
	at parquet.hadoop.CodecFactory.getCompressor(CodecFactory.java:168)
	at parquet.hadoop.ParquetOutputFormat.getRecordWriter(ParquetOutputFormat.java:326)
	at parquet.hadoop.ParquetOutputFormat.getRecordWriter(ParquetOutputFormat.java:282)
	at org.apache.spark.sql.execution.datasources.parquet.ParquetOutputWriter.<init>(ParquetRelation.scala:94)
	at org.apache.spark.sql.execution.datasources.parquet.ParquetRelation$$anon$3.newInstance(ParquetRelation.scala:286)
	at org.apache.spark.sql.execution.datasources.BaseWriterContainer.newOutputWriter(WriterContainer.scala:129)
	at org.apache.spark.sql.execution.datasources.DefaultWriterContainer.writeRows(WriterContainer.scala:255)
	at org.apache.spark.sql.execution.datasources.InsertIntoHadoopFsRelation$$anonfun$run$1$$anonfun$apply$mcV$sp$3.apply(InsertIntoHadoopFsRelation.scala:148)
	at org.apache.spark.sql.execution.datasources.InsertIntoHadoopFsRelation$$anonfun$run$1$$anonfun$apply$mcV$sp$3.apply(InsertIntoHadoopFsRelation.scala:148)
	at org.apache.spark.scheduler.ResultTask.runTask(ResultTask.scala:66)
	at org.apache.spark.scheduler.Task.run(Task.scala:89)
	at org.apache.spark.executor.Executor$TaskRunner.run(Executor.scala:242)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1145)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:615)
	at java.lang.Thread.run(Thread.java:745)
Caused by: java.lang.ClassNotFoundException: com.hadoop.compression.lzo.LzoCodec
	at java.net.URLClassLoader$1.run(URLClassLoader.java:366)
	at java.net.URLClassLoader$1.run(URLClassLoader.java:355)
	at java.security.AccessController.doPrivileged(Native Method)
	at java.net.URLClassLoader.findClass(URLClassLoader.java:354)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:425)
	at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:308)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:358)
	at java.lang.Class.forName0(Native Method)
	at java.lang.Class.forName(Class.java:190)
	at parquet.hadoop.CodecFactory.getCodec(CodecFactory.java:156)
	... 15 more
~~~

* Refer below HDFS command to display list of generated Parquet files

~~~
[cloudera@quickstart ~]$ hadoop fs -ls -h -R tmp/orders | grep parquet
drwxr-xr-x   - cloudera cloudera          0 2017-12-28 21:46 tmp/orders/parquet
-rw-r--r--   1 cloudera cloudera          0 2017-12-28 21:46 tmp/orders/parquet/_SUCCESS
-rw-r--r--   1 cloudera cloudera        534 2017-12-28 21:46 tmp/orders/parquet/_common_metadata
-rw-r--r--   1 cloudera cloudera      1.6 K 2017-12-28 21:46 tmp/orders/parquet/_metadata
-rw-r--r--   1 cloudera cloudera    263.9 K 2017-12-28 21:46 tmp/orders/parquet/part-r-00000-aad03abf-b696-4cce-a739-cf1863ebe7bd.parquet
-rw-r--r--   1 cloudera cloudera    268.6 K 2017-12-28 21:46 tmp/orders/parquet/part-r-00001-aad03abf-b696-4cce-a739-cf1863ebe7bd.parquet
drwxr-xr-x   - cloudera cloudera          0 2017-12-28 21:49 tmp/orders/parquet_gzip
-rw-r--r--   1 cloudera cloudera          0 2017-12-28 21:49 tmp/orders/parquet_gzip/_SUCCESS
-rw-r--r--   1 cloudera cloudera        534 2017-12-28 21:49 tmp/orders/parquet_gzip/_common_metadata
-rw-r--r--   1 cloudera cloudera      1.6 K 2017-12-28 21:49 tmp/orders/parquet_gzip/_metadata
-rw-r--r--   1 cloudera cloudera    148.3 K 2017-12-28 21:49 tmp/orders/parquet_gzip/part-r-00000-173a801a-5273-4124-9cb3-03655a151edb.gz.parquet
-rw-r--r--   1 cloudera cloudera    149.9 K 2017-12-28 21:49 tmp/orders/parquet_gzip/part-r-00001-173a801a-5273-4124-9cb3-03655a151edb.gz.parquet
drwxr-xr-x   - cloudera cloudera          0 2017-12-28 21:52 tmp/orders/parquet_lzo
drwxr-xr-x   - cloudera cloudera          0 2017-12-28 21:48 tmp/orders/parquet_snappy
-rw-r--r--   1 cloudera cloudera          0 2017-12-28 21:48 tmp/orders/parquet_snappy/_SUCCESS
-rw-r--r--   1 cloudera cloudera        534 2017-12-28 21:48 tmp/orders/parquet_snappy/_common_metadata
-rw-r--r--   1 cloudera cloudera      1.6 K 2017-12-28 21:48 tmp/orders/parquet_snappy/_metadata
-rw-r--r--   1 cloudera cloudera    259.7 K 2017-12-28 21:48 tmp/orders/parquet_snappy/part-r-00000-0edb18bc-a295-4228-aaa4-68c5844216b2.snappy.parquet
-rw-r--r--   1 cloudera cloudera    261.4 K 2017-12-28 21:48 tmp/orders/parquet_snappy/part-r-00001-0edb18bc-a295-4228-aaa4-68c5844216b2.snappy.parquet
~~~

* Refer below HDFS command to view Parquet file on terminal

~~~
[cloudera@quickstart ~]$ parquet-tools cat hdfs://quickstart.cloudera/user/cloudera/tmp/orders/parquet/part-r-00000-aad03abf-b696-4cce-a739-cf1863ebe7bd.parquet | more
order_id = 1
order_date = 2013-07-25 00:00:00.0
order_customer_id = 11599
order_status = CLOSED

order_id = 2
order_date = 2013-07-25 00:00:00.0
order_customer_id = 256
order_status = PENDING_PAYMENT

order_id = 3
order_date = 2013-07-25 00:00:00.0
order_customer_id = 12111
order_status = COMPLETE
~~~

~~~
[cloudera@quickstart ~]$ parquet-tools cat hdfs://quickstart.cloudera/user/cloudera/tmp/orders/parquet_gzip/part-r-00000-173a801a-5273-4124-9cb3-03655a151edb.gz.parquet | more
order_id = 1
order_date = 2013-07-25 00:00:00.0
order_customer_id = 11599
order_status = CLOSED

order_id = 2
order_date = 2013-07-25 00:00:00.0
order_customer_id = 256
order_status = PENDING_PAYMENT

order_id = 3
order_date = 2013-07-25 00:00:00.0
order_customer_id = 12111
order_status = COMPLETE
~~~

~~~
[cloudera@quickstart ~]$ parquet-tools cat hdfs://quickstart.cloudera/user/cloudera/tmp/orders/parquet_snappy/part-r-00000-0edb18bc-a295-4228-aaa4-68c5844216b2.snappy.parquet | more
order_id = 1
order_date = 2013-07-25 00:00:00.0
order_customer_id = 11599
order_status = CLOSED

order_id = 2
order_date = 2013-07-25 00:00:00.0
order_customer_id = 256
order_status = PENDING_PAYMENT

order_id = 3
order_date = 2013-07-25 00:00:00.0
order_customer_id = 12111
order_status = COMPLETE
~~~

### (5) Avro File

* Avro file supports following compression codec:
  * Deflate i.e. `org.apache.hadoop.io.compress.DeflateCodec`
  * Snappy i.e. `org.apache.hadoop.io.compress.SnappyCodec`

* Import Avro classes from `com.databricks.spark.avro`:

~~~
scala> import com.databricks.spark.avro._
import com.databricks.spark.avro._
~~~

* Create instance of `org.apache.spark.sql.SQLContext`:

~~~
scala> import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.SQLContext

scala> val sqlContext = new SQLContext(sc)
sqlContext: org.apache.spark.sql.SQLContext = org.apache.spark.sql.SQLContext@4afb3566

scala> import sqlContext.implicits._
import sqlContext.implicits._
~~~

* Refer below code snippet to Read/Write Avro file without compression

~~~
scala> sqlContext.
setConf("spark.sql.avro.compression.codec", "uncompressed")
       
scala> sc.
textFile("sqoop/import-all-tables-text/orders").
map((rec: String) => {
  val recArray = rec.split(",")
  (recArray(0).toInt, recArray(1), recArray(2).toInt, recArray(3))
}).
toDF("order_id", "order_date", "order_customer_id", "order_status").
write.
mode(org.apache.spark.sql.SaveMode.Overwrite).
avro("tmp/orders/avro")

scala> sqlContext.
read.
avro("tmp/orders/avro").
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

* Refer below code snippet to Read/Write Avro file with `org.apache.hadoop.io.compress.DeflateCodec` compression

~~~
scala> sqlContext.
setConf("spark.sql.avro.compression.codec", "deflate")
       
scala> sqlContext.
setConf("spark.sql.avro.deflate.level", "5")

scala> sc.
textFile("sqoop/import-all-tables-text/orders").
map((rec: String) => {
  val recArray = rec.split(",")
  (recArray(0).toInt, recArray(1), recArray(2).toInt, recArray(3))
}).
toDF("order_id", "order_date", "order_customer_id", "order_status").
write.
mode(org.apache.spark.sql.SaveMode.Overwrite).
avro("tmp/orders/avro_deflate")

scala> sqlContext.
read.
avro("tmp/orders/avro_deflate").
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

* Refer below code snippet to Read/Write Avro file with `org.apache.hadoop.io.compress.SnappyCodec` compression

~~~
scala> sqlContext.
setConf("spark.sql.avro.compression.codec", "snappy")

scala> sc.
textFile("sqoop/import-all-tables-text/orders").
map((rec: String) => {
  val recArray = rec.split(",")
  (recArray(0).toInt, recArray(1), recArray(2).toInt, recArray(3))
}).
toDF("order_id", "order_date", "order_customer_id", "order_status").
write.
mode(org.apache.spark.sql.SaveMode.Overwrite).
avro("tmp/orders/avro_snappy")

scala> sqlContext.
read.
avro("tmp/orders/avro_snappy").
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

* Refer below HDFS command to display list of generated Avro files

~~~
[cloudera@quickstart ~]$ hadoop fs -ls -h -R tmp/orders | grep avro
drwxr-xr-x   - cloudera cloudera          0 2017-12-28 23:18 tmp/orders/avro
-rw-r--r--   1 cloudera cloudera          0 2017-12-28 23:18 tmp/orders/avro/_SUCCESS
-rw-r--r--   1 cloudera cloudera      1.4 M 2017-12-28 23:18 tmp/orders/avro/part-r-00000-60d67d0d-a9ea-4da2-841f-907b4012365d.avro
-rw-r--r--   1 cloudera cloudera      1.4 M 2017-12-28 23:18 tmp/orders/avro/part-r-00001-60d67d0d-a9ea-4da2-841f-907b4012365d.avro
drwxr-xr-x   - cloudera cloudera          0 2017-12-28 23:07 tmp/orders/avro_deflate
-rw-r--r--   1 cloudera cloudera          0 2017-12-28 23:07 tmp/orders/avro_deflate/_SUCCESS
-rw-r--r--   1 cloudera cloudera    213.4 K 2017-12-28 23:07 tmp/orders/avro_deflate/part-r-00000-53b50484-60e3-41d6-91c1-cb8f1389123c.avro
-rw-r--r--   1 cloudera cloudera    214.2 K 2017-12-28 23:07 tmp/orders/avro_deflate/part-r-00001-53b50484-60e3-41d6-91c1-cb8f1389123c.avro
drwxr-xr-x   - cloudera cloudera          0 2017-12-28 23:13 tmp/orders/avro_snappy
-rw-r--r--   1 cloudera cloudera          0 2017-12-28 23:13 tmp/orders/avro_snappy/_SUCCESS
-rw-r--r--   1 cloudera cloudera    359.7 K 2017-12-28 23:13 tmp/orders/avro_snappy/part-r-00000-0122beab-39de-42d2-bf8d-79d3230afe28.avro
-rw-r--r--   1 cloudera cloudera    360.7 K 2017-12-28 23:13 tmp/orders/avro_snappy/part-r-00001-0122beab-39de-42d2-bf8d-79d3230afe28.avro
~~~

* Refer below HDFS command to view Avro file on terminal

~~~
[cloudera@quickstart ~]$ avro-tools tojson hdfs://quickstart.cloudera/user/cloudera/tmp/orders/avro/part-r-00000-60d67d0d-a9ea-4da2-841f-907b4012365d.avro | tail
log4j:WARN No appenders could be found for logger (org.apache.hadoop.metrics2.lib.MutableMetricsFactory).
log4j:WARN Please initialize the log4j system properly.
log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
{"order_id":{"int":34555},"order_date":{"string":"2014-02-23 00:00:00.0"},"order_customer_id":{"int":443},"order_status":{"string":"CLOSED"}}
{"order_id":{"int":34556},"order_date":{"string":"2014-02-23 00:00:00.0"},"order_customer_id":{"int":10877},"order_status":{"string":"PENDING"}}
{"order_id":{"int":34557},"order_date":{"string":"2014-02-23 00:00:00.0"},"order_customer_id":{"int":5585},"order_status":{"string":"PENDING_PAYMENT"}}
{"order_id":{"int":34558},"order_date":{"string":"2014-02-23 00:00:00.0"},"order_customer_id":{"int":1024},"order_status":{"string":"PENDING_PAYMENT"}}
{"order_id":{"int":34559},"order_date":{"string":"2014-02-23 00:00:00.0"},"order_customer_id":{"int":8676},"order_status":{"string":"PROCESSING"}}
{"order_id":{"int":34560},"order_date":{"string":"2014-02-23 00:00:00.0"},"order_customer_id":{"int":7036},"order_status":{"string":"PENDING"}}
{"order_id":{"int":34561},"order_date":{"string":"2014-02-23 00:00:00.0"},"order_customer_id":{"int":2027},"order_status":{"string":"COMPLETE"}}
{"order_id":{"int":34562},"order_date":{"string":"2014-02-23 00:00:00.0"},"order_customer_id":{"int":1497},"order_status":{"string":"CLOSED"}}
{"order_id":{"int":34563},"order_date":{"string":"2014-02-23 00:00:00.0"},"order_customer_id":{"int":477},"order_status":{"string":"CLOSED"}}
{"order_id":{"int":34564},"order_date":{"string":"2014-02-23 00:00:00.0"},"order_customer_id":{"int":12151},"order_status":{"string":"CLOSED"}}
~~~

~~~
[cloudera@quickstart ~]$ avro-tools tojson hdfs://quickstart.cloudera/user/cloudera/tmp/orders/avro_deflate/part-r-00000-53b50484-60e3-41d6-91c1-cb8f1389123c.avro | tail
log4j:WARN No appenders could be found for logger (org.apache.hadoop.metrics2.lib.MutableMetricsFactory).
log4j:WARN Please initialize the log4j system properly.
log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
{"order_id":{"int":34555},"order_date":{"string":"2014-02-23 00:00:00.0"},"order_customer_id":{"int":443},"order_status":{"string":"CLOSED"}}
{"order_id":{"int":34556},"order_date":{"string":"2014-02-23 00:00:00.0"},"order_customer_id":{"int":10877},"order_status":{"string":"PENDING"}}
{"order_id":{"int":34557},"order_date":{"string":"2014-02-23 00:00:00.0"},"order_customer_id":{"int":5585},"order_status":{"string":"PENDING_PAYMENT"}}
{"order_id":{"int":34558},"order_date":{"string":"2014-02-23 00:00:00.0"},"order_customer_id":{"int":1024},"order_status":{"string":"PENDING_PAYMENT"}}
{"order_id":{"int":34559},"order_date":{"string":"2014-02-23 00:00:00.0"},"order_customer_id":{"int":8676},"order_status":{"string":"PROCESSING"}}
{"order_id":{"int":34560},"order_date":{"string":"2014-02-23 00:00:00.0"},"order_customer_id":{"int":7036},"order_status":{"string":"PENDING"}}
{"order_id":{"int":34561},"order_date":{"string":"2014-02-23 00:00:00.0"},"order_customer_id":{"int":2027},"order_status":{"string":"COMPLETE"}}
{"order_id":{"int":34562},"order_date":{"string":"2014-02-23 00:00:00.0"},"order_customer_id":{"int":1497},"order_status":{"string":"CLOSED"}}
{"order_id":{"int":34563},"order_date":{"string":"2014-02-23 00:00:00.0"},"order_customer_id":{"int":477},"order_status":{"string":"CLOSED"}}
{"order_id":{"int":34564},"order_date":{"string":"2014-02-23 00:00:00.0"},"order_customer_id":{"int":12151},"order_status":{"string":"CLOSED"}}
~~~

~~~
[cloudera@quickstart ~]$ avro-tools tojson hdfs://quickstart.cloudera/user/cloudera/tmp/orders/avro_snappy/part-r-00000-0122beab-39de-42d2-bf8d-79d3230afe28.avro | tail
log4j:WARN No appenders could be found for logger (org.apache.hadoop.metrics2.lib.MutableMetricsFactory).
log4j:WARN Please initialize the log4j system properly.
log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
{"order_id":{"int":34555},"order_date":{"string":"2014-02-23 00:00:00.0"},"order_customer_id":{"int":443},"order_status":{"string":"CLOSED"}}
{"order_id":{"int":34556},"order_date":{"string":"2014-02-23 00:00:00.0"},"order_customer_id":{"int":10877},"order_status":{"string":"PENDING"}}
{"order_id":{"int":34557},"order_date":{"string":"2014-02-23 00:00:00.0"},"order_customer_id":{"int":5585},"order_status":{"string":"PENDING_PAYMENT"}}
{"order_id":{"int":34558},"order_date":{"string":"2014-02-23 00:00:00.0"},"order_customer_id":{"int":1024},"order_status":{"string":"PENDING_PAYMENT"}}
{"order_id":{"int":34559},"order_date":{"string":"2014-02-23 00:00:00.0"},"order_customer_id":{"int":8676},"order_status":{"string":"PROCESSING"}}
{"order_id":{"int":34560},"order_date":{"string":"2014-02-23 00:00:00.0"},"order_customer_id":{"int":7036},"order_status":{"string":"PENDING"}}
{"order_id":{"int":34561},"order_date":{"string":"2014-02-23 00:00:00.0"},"order_customer_id":{"int":2027},"order_status":{"string":"COMPLETE"}}
{"order_id":{"int":34562},"order_date":{"string":"2014-02-23 00:00:00.0"},"order_customer_id":{"int":1497},"order_status":{"string":"CLOSED"}}
{"order_id":{"int":34563},"order_date":{"string":"2014-02-23 00:00:00.0"},"order_customer_id":{"int":477},"order_status":{"string":"CLOSED"}}
{"order_id":{"int":34564},"order_date":{"string":"2014-02-23 00:00:00.0"},"order_customer_id":{"int":12151},"order_status":{"string":"CLOSED"}}
~~~