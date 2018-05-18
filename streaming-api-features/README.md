## What is Streaming Analytics?

### Typical life cycle of any streaming analytics application
* Get data from sources
* Process data to get insight
* Store processed data into target

### Few popular frameworks provides streaming analytics capabilities
* Apache Storm
* Apache Flink
* Apache Spark

### Role of Flume & Kafka in building streaming analytics application
* Important metrics to be considered while using Flume and or Kafka

| Feature | Kafka | Flume |
|---------|-------|-------|
| Scalability | As kafka topic provides supports of partitions in distributed manner, kafka is fully scalable | Though flume provides support of multi agent flow, scaling flume is not easy & maintaining multiple flume agent is overhead |
| Reliability | As kafka topic provides supports of replication in distributed manner, kafka is fault tolerant | Difficult to achieve practically |
| Usage | Kafka can be used for most of the applications. However, existing application needs to be refactor to publish the message on kafka cluster | Flume can be used with legacy applications (i.e. mission critical application & highly sensitive for any changes), which are already capturing messages as part of server logs.

* Combination of flume & kafka can complement each other in few use cases.

## Spark Streaming - Introduction
* Spark Streaming is an extension of the core spark API that enables scalable, high-throughput, fault tolerant processing of live streaming data
* Typical life cycle of spark streaming application includes following steps: 
  * Pulling data from various sources like Kafka, Flume, Kinesis, TCP sockets etc.
  * Processed data using complex algorithms expressed with high level functions like `map`, `reduce`, `join` and `window`. In fact, we can apply machine learning algorithms, graph algorithms on data streams.
  * Pushing processed data to file system, database, live dashboards etc. 

## Spark Streaming - Architecture

  ![Alt text](_images/_1_spark_streaming_model.png?raw=true "Spark Streaming - Architecture")

### Data Flow
* Spark Streaming application receives live data streams and divides the data into batches, which are then processed by the spark engine to generate the final stream of result in batches
* It provides a high-level abstraction called DStream (Discretized Stream), which can be considered as:
  * Continuous stream of data
  * Sequence of RDDs

  ![Alt text](_images/_2_spark_streaming_data_flow.png?raw=true "Spark Streaming - Data Flow")

## Spark Streaming - Getting started with REPL

### (1) Launch `spark-shell` in local model

* Start web service from terminal using `netcat` command

~~~
asus@asus-GL553VD:~$ netcat -lk 9999
~~~

* Launch spark from terminal using `spark-shell` command
~~~
asus@asus-GL553VD:~$ spark-shell \
  --master local[*] \
  --conf spark.ui.port=56256

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

Using Scala version 2.10.5 (OpenJDK 64-Bit Server VM, Java 1.8.0_171)
Type in expressions to have them evaluated.
Type :help for more information.
18/05/18 11:40:50 WARN Utils: Your hostname, asus-GL553VD resolves to a loopback address: 127.0.1.1; using 192.168.0.102 instead (on interface enp3s0)
18/05/18 11:40:50 WARN Utils: Set SPARK_LOCAL_IP if you need to bind to another address
Spark context available as sc.
SQL context available as sqlContext.

scala> 
~~~

* Create instance of `org.apache.spark.streaming.StreamingContext`
~~~
scala> import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.StreamingContext

scala> import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.Seconds

scala> val ssc = new StreamingContext(sc, Seconds(5))
ssc: org.apache.spark.streaming.StreamingContext = org.apache.spark.streaming.StreamingContext@7f6f4644
~~~

* Create instance of `org.apache.spark.streaming.dstream.DStream` from socket
~~~
scala> val lines = ssc.socketTextStream("localhost", 9999)
lines: org.apache.spark.streaming.dstream.ReceiverInputDStream[String] = org.apache.spark.streaming.dstream.SocketInputDStream@1c63aa5d
~~~

* Create word count program
~~~
scala> val words = lines.flatMap((line: String) => line.split(" "))
words: org.apache.spark.streaming.dstream.DStream[String] = org.apache.spark.streaming.dstream.FlatMappedDStream@452888c2

scala> val wordMap = words.map((word: String) => (word, 1))
wordMap: org.apache.spark.streaming.dstream.DStream[(String, Int)] = org.apache.spark.streaming.dstream.MappedDStream@1ca3d997

scala> val wordCount = wordMap.reduceByKey((aggCount: Int, element: Int) => aggCount + element)
wordCount: org.apache.spark.streaming.dstream.DStream[(String, Int)] = org.apache.spark.streaming.dstream.ShuffledDStream@1c49ae29

scala> wordCount.print
~~~

* Start streaming context
~~~
scala> ssc.start
~~~

* Add lines on `netcat` terminal
~~~
asus@asus-GL553VD:~$ netcat -lk 9999
this is the first message
another message    
testing it again
message again
again
this is the first message
another message    
testing it again
message again
again
this is the first message
another message    
testing it again
message again
again
~~~

* Verify output on `spark-shell`
~~~
18/05/18 12:04:51 WARN BlockManager: Block input-0-1526625291600 replicated to only 0 peer(s) instead of 1 peers
-------------------------------------------
Time: 1526625295000 ms
-------------------------------------------
(first,1)
(testing,1)
(again,3)
(the,1)
(is,1)
(it,1)
(another,1)
(this,1)
(message,3)

-------------------------------------------
Time: 1526625300000 ms
-------------------------------------------

18/05/18 12:05:04 WARN BlockManager: Block input-0-1526625304000 replicated to only 0 peer(s) instead of 1 peers
-------------------------------------------
Time: 1526625305000 ms
-------------------------------------------
(first,2)
(testing,2)
(again,6)
(the,2)
(is,2)
(it,2)
(another,2)
(this,2)
(message,6)

-------------------------------------------
Time: 1526625310000 ms
-------------------------------------------
~~~

## Spark Streaming - Getting started with application development

### Launch IDE

### Launch SBT

### Launch `spark-submit`

## Spark - Understanding of different context

| Metrics| `org.apache.spark.SparkContext` | `org.apache.spark.sql.SQLContext` | `org.apache.spark.streaming.StreamingContext` |
|--------|---------------------------------|-----------------------------------|-----------------------------------------------|
| Responsibility | Primary responsibility of SparkContext is to execute batch job at less frequent interval (i.e. hourly/daily/weekly job) | Primary responsibility of SQLContext is to provide environment for executing SQL query | Primary responsibility of StreamingContext is process live streaming data at high frequently (i.e. 10 seconds, 1/5/10/30 min etc.) |
| Usage | Typical usage of SparkContext is to read, process & store processed data into file system | Usage of SQLContext is same as SparkContext but in SQL way | Typical usage of SparkStreaming is to process/analyze nearly real time data |
| Creation | In context of `spark-shell`, SparkContext will get automatically created, which can be monitored using exposed web service on specific port number | SQLContext can be created with help of SparkContext as input | Don't know |

