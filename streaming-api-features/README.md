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

## Pre-Requisite

### Launch `netcat` web service 

* Start web service from terminal using `netcat` command
~~~
asus@asus-GL553VD:~$ netcat -lk 172.16.252.1 9999
~~~

## Spark Streaming - Getting started with REPL

### Launch `spark-shell` (in local model) to execute sample program

* Start `spark-shell` terminal
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

* Initialize `org.apache.spark.streaming.StreamingContext` **using `org.apache.spark.SparkContext`**
~~~
scala> import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.StreamingContext

scala> import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.Seconds

scala> val ssc = new StreamingContext(sc, Seconds(5))
ssc: org.apache.spark.streaming.StreamingContext = org.apache.spark.streaming.StreamingContext@7f6f4644
~~~

* Initialize `org.apache.spark.streaming.StreamingContext` **without using `org.apache.spark.SparkContext`**
~~~
scala> import org.apache.spark.SparkConf
import org.apache.spark.SparkConf

scala> val conf = new SparkConf().
setMaster("local[*]").
setAppName("Network Hello World")

scala> import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.StreamingContext

scala> import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.Seconds

scala> val ssc = new StreamingContext(conf, Seconds(5))
18/05/18 13:52:58 WARN Utils: Service 'SparkUI' could not bind on port 56256. Attempting port 56257.
org.apache.spark.SparkException: Only one SparkContext may be running in this JVM (see SPARK-2243). To ignore this error, set spark.driver.allowMultipleContexts = true. The currently running SparkContext was created at:
org.apache.spark.SparkContext.<init>(SparkContext.scala:82)
org.apache.spark.repl.SparkILoop.createSparkContext(SparkILoop.scala:1017)
$iwC$$iwC.<init>(<console>:15)
$iwC.<init>(<console>:24)
<init>(<console>:26)
.<init>(<console>:30)
.<clinit>(<console>)
.<init>(<console>:7)
.<clinit>(<console>)
$print(<console>)
sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
java.lang.reflect.Method.invoke(Method.java:498)
org.apache.spark.repl.SparkIMain$ReadEvalPrint.call(SparkIMain.scala:1065)
org.apache.spark.repl.SparkIMain$Request.loadAndRun(SparkIMain.scala:1346)
org.apache.spark.repl.SparkIMain.loadAndRunReq$1(SparkIMain.scala:840)
org.apache.spark.repl.SparkIMain.interpret(SparkIMain.scala:871)
org.apache.spark.repl.SparkIMain.interpret(SparkIMain.scala:819)
org.apache.spark.repl.SparkILoop.reallyInterpret$1(SparkILoop.scala:857)
	at org.apache.spark.SparkContext$$anonfun$assertNoOtherContextIsRunning$1.apply(SparkContext.scala:2257)
	at org.apache.spark.SparkContext$$anonfun$assertNoOtherContextIsRunning$1.apply(SparkContext.scala:2239)
	at scala.Option.foreach(Option.scala:236)
	at org.apache.spark.SparkContext$.assertNoOtherContextIsRunning(SparkContext.scala:2239)
	at org.apache.spark.SparkContext$.setActiveContext(SparkContext.scala:2325)
	at org.apache.spark.SparkContext.<init>(SparkContext.scala:2197)
	at org.apache.spark.streaming.StreamingContext$.createNewSparkContext(StreamingContext.scala:874)
	at org.apache.spark.streaming.StreamingContext.<init>(StreamingContext.scala:81)
	at $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC.<init>(<console>:30)
	at $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC.<init>(<console>:35)
	at $iwC$$iwC$$iwC$$iwC$$iwC$$iwC.<init>(<console>:37)
	at $iwC$$iwC$$iwC$$iwC$$iwC.<init>(<console>:39)
	at $iwC$$iwC$$iwC$$iwC.<init>(<console>:41)
	at $iwC$$iwC$$iwC.<init>(<console>:43)
	at $iwC$$iwC.<init>(<console>:45)
	at $iwC.<init>(<console>:47)
	at <init>(<console>:49)
	at .<init>(<console>:53)
	at .<clinit>(<console>)
	at .<init>(<console>:7)
	at .<clinit>(<console>)
	at $print(<console>)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.apache.spark.repl.SparkIMain$ReadEvalPrint.call(SparkIMain.scala:1065)
	at org.apache.spark.repl.SparkIMain$Request.loadAndRun(SparkIMain.scala:1346)
	at org.apache.spark.repl.SparkIMain.loadAndRunReq$1(SparkIMain.scala:840)
	at org.apache.spark.repl.SparkIMain.interpret(SparkIMain.scala:871)
	at org.apache.spark.repl.SparkIMain.interpret(SparkIMain.scala:819)
	at org.apache.spark.repl.SparkILoop.reallyInterpret$1(SparkILoop.scala:857)
	at org.apache.spark.repl.SparkILoop.interpretStartingWith(SparkILoop.scala:902)
	at org.apache.spark.repl.SparkILoop.command(SparkILoop.scala:814)
	at org.apache.spark.repl.SparkILoop.processLine$1(SparkILoop.scala:657)
	at org.apache.spark.repl.SparkILoop.innerLoop$1(SparkILoop.scala:665)
	at org.apache.spark.repl.SparkILoop.org$apache$spark$repl$SparkILoop$$loop(SparkILoop.scala:670)
	at org.apache.spark.repl.SparkILoop$$anonfun$org$apache$spark$repl$SparkILoop$$process$1.apply$mcZ$sp(SparkILoop.scala:997)
	at org.apache.spark.repl.SparkILoop$$anonfun$org$apache$spark$repl$SparkILoop$$process$1.apply(SparkILoop.scala:945)
	at org.apache.spark.repl.SparkILoop$$anonfun$org$apache$spark$repl$SparkILoop$$process$1.apply(SparkILoop.scala:945)
	at scala.tools.nsc.util.ScalaClassLoader$.savingContextLoader(ScalaClassLoader.scala:135)
	at org.apache.spark.repl.SparkILoop.org$apache$spark$repl$SparkILoop$$process(SparkILoop.scala:945)
	at org.apache.spark.repl.SparkILoop.process(SparkILoop.scala:1059)
	at org.apache.spark.repl.Main$.main(Main.scala:31)
	at org.apache.spark.repl.Main.main(Main.scala)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.apache.spark.deploy.SparkSubmit$.org$apache$spark$deploy$SparkSubmit$$runMain(SparkSubmit.scala:731)
	at org.apache.spark.deploy.SparkSubmit$.doRunMain$1(SparkSubmit.scala:181)
	at org.apache.spark.deploy.SparkSubmit$.submit(SparkSubmit.scala:206)
	at org.apache.spark.deploy.SparkSubmit$.main(SparkSubmit.scala:121)
	at org.apache.spark.deploy.SparkSubmit.main(SparkSubmit.scala)

scala> sc.stop

scala> val ssc = new StreamingContext(conf, Seconds(5))
~~~

* Create instance of `org.apache.spark.streaming.dstream.DStream` from socket
~~~
scala> val lines = ssc.socketTextStream("172.16.252.1", 9999)
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

* Verify output on `spark-shell` terminal
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

### Launch IDE to create application project

* Create SBT project called `streaming-api-features` in IntelliJ Idea/Eclipse
  
* Refer below code snippet to add Spark Streaming module dependency in `build.sbt`

~~~
name := "streaming-api-features"
version := "0.1"
scalaVersion := "2.10.6"
  
libraryDependencies += "org.apache.spark" % "spark-streaming_2.10" % "1.6.3"
~~~
  
* Refer below code snippet to create `NetworkWordCount` sample program in IDE

~~~
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

object NetworkWordCount {

  def main(args: Array[String]): Unit = {
    // Get arguments from program
    val mode = args(0)
    val host = args(1)
    val port = args(2).toInt

    // Create Spark Config
    val conf = new SparkConf().
      setMaster(mode).
      setAppName("Netcat word count")

    // Create Spark Context
    val sc = new SparkContext(conf)

    // Create Spark Streaming Context
    val ssc = new StreamingContext(sc, Seconds(5))

    // Create word count program
    val lines = ssc.socketTextStream(host, port)
    val words = lines.flatMap((line: String) => line.split(" "))
    val wordsMap = words.map((word: String) => (word, 1))
    val wordCount = wordsMap.reduceByKey((agg: Int, ele: Int) => agg + ele)

    // Print output
    wordCount.print

    // Start spark streaming
    ssc.start
    ssc.awaitTermination
  }

}
~~~
    
* Execute above program under IDE to see the result

### Launch `sbt` for executing application

* Start `sbt` terminal
~~~
asus@asus-GL553VD:~/source_code/github/124938/learning-spark/streaming-api-features$ sbt
[info] Loading global plugins from /home/asus/.sbt/0.13/plugins
[info] Loading project definition from /home/asus/source_code/github/124938/learning-spark/streaming-api-features/project
[info] Set current project to streaming-api-features (in build file:/home/asus/source_code/github/124938/learning-spark/streaming-api-features/)
~~~

* Execute `clean` command
~~~
> clean
[success] Total time: 0 s, completed 20 May, 2018 1:50:20 PM
~~~

* Execute `package` command
~~~
> package
[info] Updating {file:/home/asus/source_code/github/124938/learning-spark/streaming-api-features/}streaming-api-features...
[info] Resolving org.fusesource.jansi#jansi;1.4 ...
[info] Done updating.
[info] Compiling 1 Scala source to /home/asus/source_code/github/124938/learning-spark/streaming-api-features/target/scala-2.10/classes...
[info] Packaging /home/asus/source_code/github/124938/learning-spark/streaming-api-features/target/scala-2.10/streaming-api-features_2.10-0.1.jar ...
[info] Done packaging.
[success] Total time: 5 s, completed 20 May, 2018 1:50:28 PM
~~~

* Execute `runMain` command
~~~
> runMain NetcatWordCount local[*] 172.16.252.1 9999
[info] Running NetcatWordCount local[*] 172.16.252.1 9999
Using Spark's default log4j profile: org/apache/spark/log4j-defaults.properties
18/05/20 13:53:00 INFO SparkContext: Running Spark version 1.6.3
18/05/20 13:53:00 WARN NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
18/05/20 13:53:00 WARN Utils: Your hostname, asus-GL553VD resolves to a loopback address: 127.0.1.1; using 192.168.0.102 instead (on interface enp3s0)
18/05/20 13:53:00 WARN Utils: Set SPARK_LOCAL_IP if you need to bind to another address
18/05/20 13:53:05 INFO SparkContext: Created broadcast 3 from broadcast at DAGScheduler.scala:1006
18/05/20 13:53:05 INFO DAGScheduler: Submitting 3 missing tasks from ResultStage 6 (ShuffledRDD[4] at reduceByKey at NetworkWordCount.scala:29)
18/05/20 13:53:05 INFO TaskSchedulerImpl: Adding task set 6.0 with 3 tasks
18/05/20 13:53:05 INFO TaskSetManager: Starting task 0.0 in stage 6.0 (TID 6, 172.16.252.1, partition 5,PROCESS_LOCAL, 1894 bytes)
18/05/20 13:53:05 INFO TaskSetManager: Starting task 1.0 in stage 6.0 (TID 7, 172.16.252.1, partition 6,PROCESS_LOCAL, 1894 bytes)
18/05/20 13:53:05 INFO TaskSetManager: Starting task 2.0 in stage 6.0 (TID 8, 172.16.252.1, partition 7,PROCESS_LOCAL, 1894 bytes)
18/05/20 13:53:05 INFO Executor: Running task 0.0 in stage 6.0 (TID 6)
18/05/20 13:53:05 INFO Executor: Running task 1.0 in stage 6.0 (TID 7)
18/05/20 13:53:05 INFO ShuffleBlockFetcherIterator: Getting 0 non-empty blocks out of 0 blocks
18/05/20 13:53:05 INFO ShuffleBlockFetcherIterator: Started 0 remote fetches in 0 ms
18/05/20 13:53:05 INFO ShuffleBlockFetcherIterator: Getting 0 non-empty blocks out of 0 blocks
18/05/20 13:53:05 INFO ShuffleBlockFetcherIterator: Started 0 remote fetches in 0 ms
18/05/20 13:53:05 INFO Executor: Finished task 0.0 in stage 6.0 (TID 6). 1161 bytes result sent to driver
18/05/20 13:53:05 INFO TaskSetManager: Finished task 0.0 in stage 6.0 (TID 6) in 6 ms on 172.16.252.1 (1/3)
18/05/20 13:53:05 INFO Executor: Finished task 1.0 in stage 6.0 (TID 7). 1161 bytes result sent to driver
18/05/20 13:53:05 INFO Executor: Running task 2.0 in stage 6.0 (TID 8)
18/05/20 13:53:05 INFO TaskSetManager: Finished task 1.0 in stage 6.0 (TID 7) in 6 ms on 172.16.252.1 (2/3)
18/05/20 13:53:05 INFO ShuffleBlockFetcherIterator: Getting 0 non-empty blocks out of 0 blocks
18/05/20 13:53:05 INFO ShuffleBlockFetcherIterator: Started 0 remote fetches in 0 ms
18/05/20 13:53:05 INFO Executor: Finished task 2.0 in stage 6.0 (TID 8). 1161 bytes result sent to driver
18/05/20 13:53:05 INFO TaskSetManager: Finished task 2.0 in stage 6.0 (TID 8) in 11 ms on 172.16.252.1 (3/3)
18/05/20 13:53:05 INFO TaskSchedulerImpl: Removed TaskSet 6.0, whose tasks have all completed, from pool 
18/05/20 13:53:05 INFO DAGScheduler: ResultStage 6 (print at NetworkWordCount.scala:32) finished in 0.012 s
18/05/20 13:53:05 INFO DAGScheduler: Job 3 finished: print at NetworkWordCount.scala:32, took 0.016338 s
-------------------------------------------
Time: 1526804585000 ms
-------------------------------------------

18/05/20 13:53:10 INFO SparkContext: Created broadcast 6 from broadcast at DAGScheduler.scala:1006
18/05/20 13:53:10 INFO DAGScheduler: Submitting 3 missing tasks from ResultStage 12 (ShuffledRDD[8] at reduceByKey at NetworkWordCount.scala:29)
18/05/20 13:53:10 INFO TaskSchedulerImpl: Adding task set 12.0 with 3 tasks
18/05/20 13:53:10 INFO TaskSetManager: Starting task 0.0 in stage 12.0 (TID 14, 172.16.252.1, partition 5,PROCESS_LOCAL, 1894 bytes)
18/05/20 13:53:10 INFO TaskSetManager: Starting task 1.0 in stage 12.0 (TID 15, 172.16.252.1, partition 6,PROCESS_LOCAL, 1894 bytes)
18/05/20 13:53:10 INFO TaskSetManager: Starting task 2.0 in stage 12.0 (TID 16, 172.16.252.1, partition 7,PROCESS_LOCAL, 1894 bytes)
18/05/20 13:53:10 INFO Executor: Running task 2.0 in stage 12.0 (TID 16)
18/05/20 13:53:10 INFO Executor: Running task 0.0 in stage 12.0 (TID 14)
18/05/20 13:53:10 INFO Executor: Running task 1.0 in stage 12.0 (TID 15)
18/05/20 13:53:10 INFO ShuffleBlockFetcherIterator: Getting 0 non-empty blocks out of 0 blocks
18/05/20 13:53:10 INFO ShuffleBlockFetcherIterator: Started 0 remote fetches in 0 ms
18/05/20 13:53:10 INFO ShuffleBlockFetcherIterator: Getting 0 non-empty blocks out of 0 blocks
18/05/20 13:53:10 INFO ShuffleBlockFetcherIterator: Started 0 remote fetches in 0 ms
18/05/20 13:53:10 INFO Executor: Finished task 0.0 in stage 12.0 (TID 14). 1161 bytes result sent to driver
18/05/20 13:53:10 INFO Executor: Finished task 1.0 in stage 12.0 (TID 15). 1161 bytes result sent to driver
18/05/20 13:53:10 INFO TaskSetManager: Finished task 0.0 in stage 12.0 (TID 14) in 5 ms on 172.16.252.1 (1/3)
18/05/20 13:53:10 INFO TaskSetManager: Finished task 1.0 in stage 12.0 (TID 15) in 5 ms on 172.16.252.1 (2/3)
18/05/20 13:53:10 INFO ShuffleBlockFetcherIterator: Getting 0 non-empty blocks out of 0 blocks
18/05/20 13:53:10 INFO ShuffleBlockFetcherIterator: Started 0 remote fetches in 0 ms
18/05/20 13:53:10 INFO Executor: Finished task 2.0 in stage 12.0 (TID 16). 1161 bytes result sent to driver
18/05/20 13:53:10 INFO TaskSetManager: Finished task 2.0 in stage 12.0 (TID 16) in 8 ms on 172.16.252.1 (3/3)
18/05/20 13:53:10 INFO DAGScheduler: ResultStage 12 (print at NetworkWordCount.scala:32) finished in 0.009 s
18/05/20 13:53:10 INFO TaskSchedulerImpl: Removed TaskSet 12.0, whose tasks have all completed, from pool 
18/05/20 13:53:10 INFO DAGScheduler: Job 6 finished: print at NetworkWordCount.scala:32, took 0.012473 s

-------------------------------------------
Time: 1526804590000 ms
-------------------------------------------
18/05/20 13:53:15 INFO Executor: Running task 2.0 in stage 16.0 (TID 22)
18/05/20 13:53:15 INFO Executor: Running task 1.0 in stage 16.0 (TID 20)
18/05/20 13:53:15 INFO Executor: Running task 0.0 in stage 16.0 (TID 19)
18/05/20 13:53:15 INFO Executor: Running task 3.0 in stage 16.0 (TID 21)
18/05/20 13:53:15 INFO ShuffleBlockFetcherIterator: Getting 1 non-empty blocks out of 1 blocks
18/05/20 13:53:15 INFO ShuffleBlockFetcherIterator: Started 0 remote fetches in 0 ms
18/05/20 13:53:15 INFO ShuffleBlockFetcherIterator: Getting 1 non-empty blocks out of 1 blocks
18/05/20 13:53:15 INFO ShuffleBlockFetcherIterator: Started 0 remote fetches in 0 ms
18/05/20 13:53:15 INFO ShuffleBlockFetcherIterator: Getting 1 non-empty blocks out of 1 blocks
18/05/20 13:53:15 INFO ShuffleBlockFetcherIterator: Started 0 remote fetches in 0 ms
18/05/20 13:53:15 INFO Executor: Finished task 0.0 in stage 16.0 (TID 19). 1356 bytes result sent to driver
18/05/20 13:53:15 INFO ShuffleBlockFetcherIterator: Getting 0 non-empty blocks out of 1 blocks
18/05/20 13:53:15 INFO Executor: Finished task 3.0 in stage 16.0 (TID 21). 1308 bytes result sent to driver
18/05/20 13:53:15 INFO ShuffleBlockFetcherIterator: Started 0 remote fetches in 2 ms
18/05/20 13:53:15 INFO TaskSetManager: Finished task 3.0 in stage 16.0 (TID 21) in 5 ms on 172.16.252.1 (1/4)
18/05/20 13:53:15 INFO Executor: Finished task 2.0 in stage 16.0 (TID 22). 1161 bytes result sent to driver
18/05/20 13:53:15 INFO TaskSetManager: Finished task 0.0 in stage 16.0 (TID 19) in 5 ms on 172.16.252.1 (2/4)
18/05/20 13:53:15 INFO Executor: Finished task 1.0 in stage 16.0 (TID 20). 1351 bytes result sent to driver
18/05/20 13:53:15 INFO TaskSetManager: Finished task 2.0 in stage 16.0 (TID 22) in 5 ms on 172.16.252.1 (3/4)
18/05/20 13:53:15 INFO TaskSetManager: Finished task 1.0 in stage 16.0 (TID 20) in 6 ms on 172.16.252.1 (4/4)
18/05/20 13:53:15 INFO TaskSchedulerImpl: Removed TaskSet 16.0, whose tasks have all completed, from pool 
18/05/20 13:53:15 INFO DAGScheduler: ResultStage 16 (print at NetworkWordCount.scala:32) finished in 0.006 s
18/05/20 13:53:15 INFO DAGScheduler: Job 8 finished: print at NetworkWordCount.scala:32, took 0.010708 s
~~~

* Add lines on `netcat` terminal
~~~
hi hello world
Hi hello world
this is the first time i am doing testing
again doing testing
hi hello world
Hi hello world
this is the first time i am doing testing
again doing testing
hi hello world
hi hello world
Hi hello world
this is the first time i am doing testing
again doing testing
hi hello world
Hi hello world
this is the first time i am doing testing
again doing testing
hi hello world
~~~

* Verify sbt terminal to see the result
~~~

-------------------------------------------
Time: 1526804595000 ms
-------------------------------------------
(first,4)
(testing,8)
(again,4)
(hi,4)
(i,4)
(Hi,4)
(the,4)
(is,4)
(hello,8)
(world,8)
...

18/05/20 13:53:15 INFO JobScheduler: Finished job streaming job 1526804595000 ms.0 from job set of time 1526804595000 ms
18/05/20 13:53:15 INFO JobScheduler: Total delay: 0.102 s for time 1526804595000 ms (execution: 0.078 s)
18/05/20 13:53:15 INFO ShuffledRDD: Removing RDD 8 from persistence list
18/05/20 13:53:15 INFO BlockManager: Removing RDD 8
18/05/20 13:53:15 INFO MapPartitionsRDD: Removing RDD 7 from persistence list
18/05/20 13:53:15 INFO BlockManager: Removing RDD 7
18/05/20 13:53:15 INFO MapPartitionsRDD: Removing RDD 6 from persistence list
18/05/20 13:53:15 INFO BlockManager: Removing RDD 6
18/05/20 13:53:15 INFO BlockRDD: Removing RDD 5 from persistence list
18/05/20 13:53:15 INFO BlockManager: Removing RDD 5
18/05/20 13:53:15 INFO SocketInputDStream: Removing blocks of RDD BlockRDD[5] at socketTextStream at NetworkWordCount.scala:26 of time 1526804595000 ms
18/05/20 13:53:15 INFO ReceivedBlockTracker: Deleting batches ArrayBuffer(1526804585000 ms)
18/05/20 13:53:15 INFO InputInfoTracker: remove old batch metadata: 1526804585000 ms
^C
18/05/20 13:53:17 INFO StreamingContext: Invoking stop(stopGracefully=false) from shutdown hook
18/05/20 13:53:17 ERROR ReceiverTracker: Deregistered receiver for stream 0: Stopped by driver
18/05/20 13:53:18 INFO ReceiverSupervisorImpl: Stopped receiver without error
18/05/20 13:53:18 INFO Executor: Finished task 0.0 in stage 0.0 (TID 0). 915 bytes result sent to driver
18/05/20 13:53:18 INFO TaskSetManager: Finished task 0.0 in stage 0.0 (TID 0) in 15763 ms on 172.16.252.1 (1/1)
18/05/20 13:53:18 INFO TaskSchedulerImpl: Removed TaskSet 0.0, whose tasks have all completed, from pool 
18/05/20 13:53:18 INFO DAGScheduler: ResultStage 0 (start at NetworkWordCount.scala:35) finished in 15.770 s
18/05/20 13:53:18 INFO ReceiverTracker: All of the receivers have deregistered successfully
18/05/20 13:53:18 INFO ReceiverTracker: ReceiverTracker stopped
18/05/20 13:53:18 INFO JobGenerator: Stopping JobGenerator immediately
18/05/20 13:53:18 INFO RecurringTimer: Stopped timer for JobGenerator after time 1526804595000
18/05/20 13:53:18 INFO JobGenerator: Stopped JobGenerator
18/05/20 13:53:18 INFO JobScheduler: Stopped JobScheduler
18/05/20 13:53:18 INFO StreamingContext: StreamingContext stopped successfully
18/05/20 13:53:18 INFO SparkContext: Invoking stop() from shutdown hook
18/05/20 13:53:18 INFO SparkUI: Stopped Spark web UI at http://192.168.0.102:4040
18/05/20 13:53:18 INFO MapOutputTrackerMasterEndpoint: MapOutputTrackerMasterEndpoint stopped!
18/05/20 13:53:18 INFO MemoryStore: MemoryStore cleared
18/05/20 13:53:18 INFO BlockManager: BlockManager stopped
18/05/20 13:53:18 INFO BlockManagerMaster: BlockManagerMaster stopped
18/05/20 13:53:18 INFO OutputCommitCoordinator$OutputCommitCoordinatorEndpoint: OutputCommitCoordinator stopped!
18/05/20 13:53:18 INFO SparkContext: Successfully stopped SparkContext
18/05/20 13:53:18 INFO ShutdownHookManager: Shutdown hook called
18/05/20 13:53:18 INFO ShutdownHookManager: Deleting directory /tmp/spark-7907a965-755f-499b-88c7-8a29a67a2d2f
~~~

## Spark Streaming - Getting started with application deployment on hadoop cluster

### Copy generated artifact from local/dev machine to deployment machine

* Copy `streaming-api-features.jar` to Cloudera quickstart VM or gateway node of hadoop cluster
~~~
asus@asus-GL553VD:$ scp \
/home/asus/source_code/github/124938/learning-spark/streaming-api-features/target/scala-2.10/streaming-api-features_2.10-0.1.jar \
cloudera@192.168.211.142:~
~~~

### Login to deployment machine

* Execute `ssh` command for login to Cloudera quickstart VM or gateway node of hadoop cluster
~~~
asus@asus-GL553VD:$ ssh cloudera@192.168.211.142
cloudera@192.168.211.142's password: 
Last login: Mon May  7 07:31:16 2018 from 192.168.211.1
~~~

* Verify copied artifact on cluster
~~~
[cloudera@quickstart ~]$ ls -ltr streaming-api-features_2.10-0.1.jar
-rw-rw-r-- 1 cloudera cloudera 4928 May 20 04:16 streaming-api-features_2.10-0.1.jar
~~~

### Launch `spark-submit` (in YARN mode) to execute application

* Execute `spark-submit` network word count program
~~~
[cloudera@quickstart ~]$ spark-submit \
  --class NetcatWordCount \
  --master yarn \
  --conf spark.rpc.netty.dispatcher.numThreads=2 \
  streaming-api-features_2.10-0.1.jar yarn-client 172.16.252.1 9999

18/06/02 07:49:25 INFO spark.SparkContext: Running Spark version 1.6.0
18/06/02 07:49:26 WARN util.NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
18/06/02 07:49:26 WARN util.Utils: Your hostname, quickstart.cloudera resolves to a loopback address: 127.0.0.1; using 192.168.211.142 instead (on interface eth1)
18/06/02 07:49:26 WARN util.Utils: Set SPARK_LOCAL_IP if you need to bind to another address
18/06/02 07:49:26 INFO spark.SecurityManager: Changing view acls to: cloudera
18/06/02 07:49:26 INFO spark.SecurityManager: Changing modify acls to: cloudera
18/06/02 07:49:26 INFO spark.SecurityManager: SecurityManager: authentication disabled; ui acls disabled; users with view permissions: Set(cloudera); users with modify permissions: Set(cloudera)
18/06/02 07:49:27 INFO util.Utils: Successfully started service 'sparkDriver' on port 58704.
18/06/02 07:49:27 INFO slf4j.Slf4jLogger: Slf4jLogger started
18/06/02 07:49:27 INFO Remoting: Starting remoting
18/06/02 07:49:27 INFO Remoting: Remoting started; listening on addresses :[akka.tcp://sparkDriverActorSystem@192.168.211.142:50601]
18/06/02 07:49:27 INFO Remoting: Remoting now listens on addresses: [akka.tcp://sparkDriverActorSystem@192.168.211.142:50601]
18/06/02 07:49:27 INFO util.Utils: Successfully started service 'sparkDriverActorSystem' on port 50601.
18/06/02 07:49:27 INFO spark.SparkEnv: Registering MapOutputTracker
18/06/02 07:49:27 INFO spark.SparkEnv: Registering BlockManagerMaster
18/06/02 07:49:27 INFO storage.DiskBlockManager: Created local directory at /tmp/blockmgr-62ca7b81-f82f-4d6c-8e50-e60f9c467735
18/06/02 07:49:27 INFO storage.MemoryStore: MemoryStore started with capacity 534.5 MB
18/06/02 07:49:27 INFO spark.SparkEnv: Registering OutputCommitCoordinator
18/06/02 07:49:28 INFO server.Server: jetty-8.y.z-SNAPSHOT
18/06/02 07:49:28 INFO server.AbstractConnector: Started SelectChannelConnector@0.0.0.0:4040
18/06/02 07:49:28 INFO util.Utils: Successfully started service 'SparkUI' on port 4040.
18/06/02 07:49:28 INFO ui.SparkUI: Started SparkUI at http://192.168.211.142:4040
18/06/02 07:49:28 INFO spark.SparkContext: Added JAR file:/home/cloudera/streaming-api-features_2.10-0.1.jar at spark://192.168.211.142:58704/jars/streaming-api-features_2.10-0.1.jar with timestamp 1527950968246
18/06/02 07:49:28 INFO client.RMProxy: Connecting to ResourceManager at /0.0.0.0:8032
18/06/02 07:49:28 INFO yarn.Client: Requesting a new application from cluster with 1 NodeManagers
18/06/02 07:49:28 INFO yarn.Client: Verifying our application has not requested more than the maximum memory capability of the cluster (8192 MB per container)
18/06/02 07:49:28 INFO yarn.Client: Will allocate AM container, with 896 MB memory including 384 MB overhead
18/06/02 07:49:28 INFO yarn.Client: Setting up container launch context for our AM
18/06/02 07:49:28 INFO yarn.Client: Setting up the launch environment for our AM container
18/06/02 07:49:28 INFO yarn.Client: Preparing resources for our AM container
18/06/02 07:49:29 WARN shortcircuit.DomainSocketFactory: The short-circuit local reads feature cannot be used because libhadoop cannot be loaded.
18/06/02 07:49:29 INFO yarn.Client: Uploading resource file:/usr/lib/spark/lib/spark-assembly-1.6.0-cdh5.12.0-hadoop2.6.0-cdh5.12.0.jar -> hdfs://quickstart.cloudera:8020/user/cloudera/.sparkStaging/application_1525607376030_0045/spark-assembly-1.6.0-cdh5.12.0-hadoop2.6.0-cdh5.12.0.jar
18/06/02 07:49:30 INFO yarn.Client: Uploading resource file:/tmp/spark-b13c83e2-a407-404f-bb99-a2e6c69e4192/__spark_conf__5812997457036532763.zip -> hdfs://quickstart.cloudera:8020/user/cloudera/.sparkStaging/application_1525607376030_0045/__spark_conf__5812997457036532763.zip
18/06/02 07:49:30 INFO spark.SecurityManager: Changing view acls to: cloudera
18/06/02 07:49:30 INFO spark.SecurityManager: Changing modify acls to: cloudera
18/06/02 07:49:30 INFO spark.SecurityManager: SecurityManager: authentication disabled; ui acls disabled; users with view permissions: Set(cloudera); users with modify permissions: Set(cloudera)
18/06/02 07:49:30 INFO yarn.Client: Submitting application 45 to ResourceManager
18/06/02 07:49:30 INFO impl.YarnClientImpl: Submitted application application_1525607376030_0045
18/06/02 07:49:31 INFO yarn.Client: Application report for application_1525607376030_0045 (state: ACCEPTED)
18/06/02 07:49:31 INFO yarn.Client: 
	 client token: N/A
	 diagnostics: N/A
	 ApplicationMaster host: N/A
	 ApplicationMaster RPC port: -1
	 queue: root.cloudera
	 start time: 1527950970567
	 final status: UNDEFINED
	 tracking URL: http://quickstart.cloudera:8088/proxy/application_1525607376030_0045/
	 user: cloudera
18/06/02 07:49:32 INFO yarn.Client: Application report for application_1525607376030_0045 (state: ACCEPTED)
18/06/02 07:49:33 INFO yarn.Client: Application report for application_1525607376030_0045 (state: ACCEPTED)
18/06/02 07:49:34 INFO yarn.Client: Application report for application_1525607376030_0045 (state: ACCEPTED)
18/06/02 07:49:35 INFO cluster.YarnSchedulerBackend$YarnSchedulerEndpoint: ApplicationMaster registered as NettyRpcEndpointRef(null)
18/06/02 07:49:35 INFO cluster.YarnClientSchedulerBackend: Add WebUI Filter. org.apache.hadoop.yarn.server.webproxy.amfilter.AmIpFilter, Map(PROXY_HOSTS -> quickstart.cloudera, PROXY_URI_BASES -> http://quickstart.cloudera:8088/proxy/application_1525607376030_0045), /proxy/application_1525607376030_0045
18/06/02 07:49:35 INFO ui.JettyUtils: Adding filter: org.apache.hadoop.yarn.server.webproxy.amfilter.AmIpFilter
18/06/02 07:49:35 INFO yarn.Client: Application report for application_1525607376030_0045 (state: ACCEPTED)
18/06/02 07:49:36 INFO yarn.Client: Application report for application_1525607376030_0045 (state: RUNNING)
18/06/02 07:49:36 INFO yarn.Client: 
	 client token: N/A
	 diagnostics: N/A
	 ApplicationMaster host: 192.168.211.142
	 ApplicationMaster RPC port: 0
	 queue: root.cloudera
	 start time: 1527950970567
	 final status: UNDEFINED
	 tracking URL: http://quickstart.cloudera:8088/proxy/application_1525607376030_0045/
	 user: cloudera
18/06/02 07:49:36 INFO cluster.YarnClientSchedulerBackend: Application application_1525607376030_0045 has started running.
18/06/02 07:49:36 INFO util.Utils: Successfully started service 'org.apache.spark.network.netty.NettyBlockTransferService' on port 47762.
18/06/02 07:49:36 INFO netty.NettyBlockTransferService: Server created on 47762
18/06/02 07:49:36 INFO storage.BlockManagerMaster: Trying to register BlockManager
18/06/02 07:49:36 INFO storage.BlockManagerMasterEndpoint: Registering block manager 192.168.211.142:47762 with 534.5 MB RAM, BlockManagerId(driver, 192.168.211.142, 47762)
18/06/02 07:49:36 INFO storage.BlockManagerMaster: Registered BlockManager
18/06/02 07:49:50 INFO cluster.YarnScheduler: Adding task set 6.0 with 1 tasks
18/06/02 07:49:50 INFO scheduler.TaskSetManager: Starting task 0.0 in stage 6.0 (TID 72, 192.168.211.142, executor 1, partition 1, PROCESS_LOCAL, 1974 bytes)
18/06/02 07:49:50 INFO storage.BlockManagerInfo: Added broadcast_4_piece0 in memory on 192.168.211.142:55818 (size: 1612.0 B, free: 534.5 MB)
18/06/02 07:49:50 INFO scheduler.TaskSetManager: Finished task 0.0 in stage 6.0 (TID 72) in 29 ms on 192.168.211.142 (executor 1) (1/1)
18/06/02 07:49:50 INFO cluster.YarnScheduler: Removed TaskSet 6.0, whose tasks have all completed, from pool 
18/06/02 07:49:50 INFO scheduler.DAGScheduler: ResultStage 6 (print at NetcatWordCount.scala:30) finished in 0.020 s
18/06/02 07:49:50 INFO scheduler.DAGScheduler: Job 3 finished: print at NetcatWordCount.scala:30, took 0.044851 s
-------------------------------------------
Time: 1527950990000 ms
-------------------------------------------

18/06/02 07:49:50 INFO scheduler.JobScheduler: Finished job streaming job 1527950990000 ms.0 from job set of time 1527950990000 ms
18/06/02 07:49:50 INFO scheduler.JobScheduler: Total delay: 0.209 s for time 1527950990000 ms (execution: 0.119 s)
18/06/02 07:49:50 INFO scheduler.ReceivedBlockTracker: Deleting batches ArrayBuffer()
18/06/02 07:49:50 INFO scheduler.InputInfoTracker: remove old batch metadata: 
18/06/02 07:49:55 INFO scheduler.JobScheduler: Starting job streaming job 1527950995000 ms.0 from job set of time 1527950995000 ms
18/06/02 07:49:55 INFO scheduler.JobScheduler: Added jobs for time 1527950995000 ms
18/06/02 07:49:55 INFO spark.SparkContext: Starting job: print at NetcatWordCount.scala:30

~~~

* Add lines on `netcat` terminal
~~~
this is a first line
this is another line
this is one another line
this is a first line
this is another line
this is one another line
this is a first line
this is another line
this is one another line
this is a first line
this is another line
this is one another line
this is a first line
this is another line
this is one another line
this is a first line
this is another line
this is one another line
this is a first line
this is another line
this is one another line
this is a first line
this is another line
this is one another line
~~~

* Verify terminal to see the result
~~~
-------------------------------------------
Time: 1527951010000 ms
-------------------------------------------
(this,24)
(is,24)
(line,24)
(one,8)
(first,8)
(another,16)
(a,8)

18/06/02 07:50:10 INFO scheduler.JobScheduler: Finished job streaming job 1527951010000 ms.0 from job set of time 1527951010000 ms
18/06/02 07:50:10 INFO scheduler.JobScheduler: Total delay: 0.247 s for time 1527951010000 ms (execution: 0.204 s)
18/06/02 07:50:10 INFO rdd.ShuffledRDD: Removing RDD 19 from persistence list
18/06/02 07:50:10 INFO storage.BlockManager: Removing RDD 19
18/06/02 07:50:10 INFO rdd.MapPartitionsRDD: Removing RDD 18 from persistence list
18/06/02 07:50:10 INFO storage.BlockManager: Removing RDD 18
18/06/02 07:50:10 INFO rdd.MapPartitionsRDD: Removing RDD 17 from persistence list
18/06/02 07:50:10 INFO storage.BlockManager: Removing RDD 17
18/06/02 07:50:10 INFO rdd.BlockRDD: Removing RDD 16 from persistence list
18/06/02 07:50:10 INFO dstream.SocketInputDStream: Removing blocks of RDD BlockRDD[16] at socketTextStream at NetcatWordCount.scala:24 of time 1527951010000 ms
18/06/02 07:50:10 INFO scheduler.ReceivedBlockTracker: Deleting batches ArrayBuffer(1527951000000 ms)
18/06/02 07:50:10 INFO scheduler.InputInfoTracker: remove old batch metadata: 1527951000000 ms
18/06/02 07:50:10 INFO storage.BlockManager: Removing RDD 16
18/06/02 07:50:15 INFO scheduler.JobScheduler: Starting job streaming job 1527951015000 ms.0 from job set of time 1527951015000 ms
18/06/02 07:50:15 INFO scheduler.JobScheduler: Added jobs for time 1527951015000 ms
18/06/02 07:50:15 INFO spark.SparkContext: Starting job: print at NetcatWordCount.scala:30
18/06/02 07:50:15 INFO scheduler.DAGScheduler: Registering RDD 26 (map at NetcatWordCount.scala:26)
18/06/02 07:50:15 INFO scheduler.DAGScheduler: Got job 12 (print at NetcatWordCount.scala:30) with 1 output partitions
18/06/02 07:50:15 INFO scheduler.DAGScheduler: Final stage: ResultStage 24 (print at NetcatWordCount.scala:30)
18/06/02 07:50:15 INFO scheduler.DAGScheduler: Parents of final stage: List(ShuffleMapStage 23)
18/06/02 07:50:15 INFO scheduler.DAGScheduler: Missing parents: List()
18/06/02 07:50:15 INFO scheduler.DAGScheduler: Submitting ResultStage 24 (ShuffledRDD[27] at reduceByKey at NetcatWordCount.scala:27), which has no missing parents
18/06/02 07:50:15 INFO storage.MemoryStore: Block broadcast_14 stored as values in memory (estimated size 2.6 KB, free 534.4 MB)
18/06/02 07:50:15 INFO storage.MemoryStore: Block broadcast_14_piece0 stored as bytes in memory (estimated size 1616.0 B, free 534.4 MB)
18/06/02 07:50:15 INFO storage.BlockManagerInfo: Added broadcast_14_piece0 in memory on 192.168.211.142:47762 (size: 1616.0 B, free: 534.5 MB)
18/06/02 07:50:15 INFO spark.SparkContext: Created broadcast 14 from broadcast at DAGScheduler.scala:1004
18/06/02 07:50:15 INFO scheduler.DAGScheduler: Submitting 1 missing tasks from ResultStage 24 (ShuffledRDD[27] at reduceByKey at NetcatWordCount.scala:27) (first 15 tasks are for partitions Vector(0))
18/06/02 07:50:15 INFO cluster.YarnScheduler: Adding task set 24.0 with 1 tasks
18/06/02 07:50:15 INFO scheduler.TaskSetManager: Starting task 0.0 in stage 24.0 (TID 82, 192.168.211.142, executor 1, partition 0, PROCESS_LOCAL, 1974 bytes)
18/06/02 07:50:15 INFO storage.BlockManagerInfo: Added broadcast_14_piece0 in memory on 192.168.211.142:55818 (size: 1616.0 B, free: 534.5 MB)
18/06/02 07:50:15 INFO spark.MapOutputTrackerMasterEndpoint: Asked to send map output locations for shuffle 6 to 192.168.211.142:37174
18/06/02 07:50:15 INFO spark.MapOutputTrackerMaster: Size of output statuses for shuffle 6 is 83 bytes
18/06/02 07:50:15 INFO scheduler.TaskSetManager: Finished task 0.0 in stage 24.0 (TID 82) in 28 ms on 192.168.211.142 (executor 1) (1/1)
18/06/02 07:50:15 INFO cluster.YarnScheduler: Removed TaskSet 24.0, whose tasks have all completed, from pool 
18/06/02 07:50:15 INFO scheduler.DAGScheduler: ResultStage 24 (print at NetcatWordCount.scala:30) finished in 0.017 s
18/06/02 07:50:15 INFO scheduler.DAGScheduler: Job 12 finished: print at NetcatWordCount.scala:30, took 0.047274 s
18/06/02 07:50:15 INFO spark.SparkContext: Starting job: print at NetcatWordCount.scala:30
18/06/02 07:50:15 INFO scheduler.DAGScheduler: Got job 13 (print at NetcatWordCount.scala:30) with 1 output partitions
18/06/02 07:50:15 INFO scheduler.DAGScheduler: Final stage: ResultStage 26 (print at NetcatWordCount.scala:30)
18/06/02 07:50:15 INFO scheduler.DAGScheduler: Parents of final stage: List(ShuffleMapStage 25)
18/06/02 07:50:15 INFO scheduler.DAGScheduler: Missing parents: List()
18/06/02 07:50:15 INFO scheduler.DAGScheduler: Submitting ResultStage 26 (ShuffledRDD[27] at reduceByKey at NetcatWordCount.scala:27), which has no missing parents
18/06/02 07:50:15 INFO storage.MemoryStore: Block broadcast_15 stored as values in memory (estimated size 2.6 KB, free 534.4 MB)
18/06/02 07:50:15 INFO storage.MemoryStore: Block broadcast_15_piece0 stored as bytes in memory (estimated size 1616.0 B, free 534.4 MB)
18/06/02 07:50:15 INFO storage.BlockManagerInfo: Added broadcast_15_piece0 in memory on 192.168.211.142:47762 (size: 1616.0 B, free: 534.5 MB)
18/06/02 07:50:15 INFO spark.SparkContext: Created broadcast 15 from broadcast at DAGScheduler.scala:1004
18/06/02 07:50:15 INFO scheduler.DAGScheduler: Submitting 1 missing tasks from ResultStage 26 (ShuffledRDD[27] at reduceByKey at NetcatWordCount.scala:27) (first 15 tasks are for partitions Vector(1))
18/06/02 07:50:15 INFO cluster.YarnScheduler: Adding task set 26.0 with 1 tasks
18/06/02 07:50:15 INFO scheduler.TaskSetManager: Starting task 0.0 in stage 26.0 (TID 83, 192.168.211.142, executor 1, partition 1, PROCESS_LOCAL, 1974 bytes)
18/06/02 07:50:15 INFO storage.BlockManagerInfo: Added broadcast_15_piece0 in memory on 192.168.211.142:55818 (size: 1616.0 B, free: 534.5 MB)
18/06/02 07:50:15 INFO scheduler.TaskSetManager: Finished task 0.0 in stage 26.0 (TID 83) in 20 ms on 192.168.211.142 (executor 1) (1/1)
18/06/02 07:50:15 INFO cluster.YarnScheduler: Removed TaskSet 26.0, whose tasks have all completed, from pool 
18/06/02 07:50:15 INFO scheduler.DAGScheduler: ResultStage 26 (print at NetcatWordCount.scala:30) finished in 0.010 s
18/06/02 07:50:15 INFO scheduler.DAGScheduler: Job 13 finished: print at NetcatWordCount.scala:30, took 0.031542 s
~~~

### _Important Notes:_

* Make sure to pass `--conf spark.rpc.netty.dispatcher.numThreads=2` while executing `spark-submit` command else program will always run in waiting mode

## Spark - Understanding of different context

| Metrics| `org.apache.spark.SparkContext` | `org.apache.spark.sql.SQLContext` | `org.apache.spark.streaming.StreamingContext` |
|--------|---------------------------------|-----------------------------------|-----------------------------------------------|
| Responsibility | Primary responsibility of SparkContext is to execute batch job at less frequent interval (i.e. hourly/daily/weekly job) | Primary responsibility of SQLContext is to provide environment for executing SQL query | Primary responsibility of StreamingContext is process live streaming data at high frequently (i.e. 10 seconds, 1/5/10/30 min etc.) |
| Usage | Typical usage of SparkContext is to read, process & store processed data into file system | Usage of SQLContext is same as SparkContext but in SQL way | Typical usage of SparkStreaming is to process/analyze nearly real time data |
| Creation | In context of `spark-shell`, SparkContext will get automatically created, which can be monitored using exposed web service on specific port number | SQLContext can be created with help of SparkContext as input | StreamingContext can be created with/without SparkContext |

