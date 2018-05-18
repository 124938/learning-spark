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


## Spark Streaming - Getting started with application development


## Understanding of different context in Spark

| Metrics| `org.apache.spark.SparkContext` | `org.apache.spark.sql.SQLContext` | `org.apache.spark.streaming.StreamingContext` |
|--------|---------------------------------|-----------------------------------|-----------------------------------------------|
| Responsibility | Primary responsibility of SparkContext is to execute batch job at less frequent interval (i.e. hourly/daily/weekly job) | Primary responsibility of SQLContext is to provide environment for executing SQL query | Primary responsibility of StreamingContext is process live streaming data at high frequently (i.e. 10 seconds, 1/5/10/30 min etc.) |
| Usage | Typical usage of SparkContext is to read, process & store processed data into file system | Usage of SQLContext is same as SparkContext but in SQL way | Typical usage of SparkStreaming is to process/analyze nearly real time data |
| Creation | In context of `spark-shell`, SparkContext will get automatically created, which can be monitored using exposed web service on specific port number | SQLContext can be created with help of SparkContext as input | Don't know |

