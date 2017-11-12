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

### Text File - Supported Compression Codec

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

scala> sc.textFile("sqoop/import-all-tables-text/orders").saveAsTextFile("tmp/orders/text")

scala> sc.textFile("tmp/orders/text").take(2).foreach(println)
1,2013-07-25 00:00:00.0,11599,CLOSED
2,2013-07-25 00:00:00.0,256,PENDING_PAYMENT

scala> sc.textFile("sqoop/import-all-tables-text/orders").saveAsTextFile("tmp/orders/text_snappy", classOf[SnappyCodec])

scala> sc.textFile("tmp/orders/text_snappy").take(2).foreach(println)
1,2013-07-25 00:00:00.0,11599,CLOSED
2,2013-07-25 00:00:00.0,256,PENDING_PAYMENT

scala> sc.textFile("sqoop/import-all-tables-text/orders").saveAsTextFile("tmp/orders/text_gzip", classOf[GzipCodec])
                                                                                
scala> sc.textFile("tmp/orders/text_gzip").take(2).foreach(println)
1,2013-07-25 00:00:00.0,11599,CLOSED
2,2013-07-25 00:00:00.0,256,PENDING_PAYMENT

scala> sc.textFile("sqoop/import-all-tables-text/orders").saveAsTextFile("tmp/orders/text_bzip2", classOf[BZip2Codec])

scala> sc.textFile("tmp/orders/text_bzip2").take(2).foreach(println)
1,2013-07-25 00:00:00.0,11599,CLOSED
2,2013-07-25 00:00:00.0,256,PENDING_PAYMENT
~~~

* Verify text file using hadoop file system commands (Refer below snapshot)
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