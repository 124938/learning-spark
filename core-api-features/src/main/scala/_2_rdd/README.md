## RDD (Resilient distributed dataset)

### Definition:
* RDD is resilient and distributed collection of records spread over one OR many partitions
* One could compare RDD to collection in Scala i.e. RDD is computed on many JVMs while Scala collection lives on single JVM

### Core Features: (decomposing the name)
* _Resilient:_ i.e. Fault tolerant with help of RDD lineage graph and so it can recompute missing OR damaged partition due to node failure
* _Distributed:_ i.e. Data resides on multiple nodes in a cluster
* _Dataset:_ is a collection of partitioned data with primitive values OR values of values e.g. tuple OR any object
  
### Additional Features:
* **Immutable** OR **Read-Only** i.e.
  * It does not change once created and can only be transformed using transformation operation to new RDD
* **Lazy evaluated** i.e.
  * Data inside RDD is not available OR transformed until an action is executed that triggers execution
  * In other words, it will only create DAG (directed acyclic graph) and the job will be submitted only when action is performed
* **Types** i.e.
    * RDD records have types e.g.
      * `Long` in `RDD[Long]`
      * `(Int, String)` in `RDD[(Int, String)]`   
* **In Memory** i.e.
  * Data inside RDD is stored in memory as much (size) and long (time) as possible
* **Cachable** i.e.
  * It can hold all data in a persistent storage like memory (default and most preferred) OR disk (the least preferred due to access speed)
    * MEMORY_ONLY (default)
    * MEMORY_AND_DISK
    * MEMORY_ONLY_SER
    * DISK_ONLY
* **Partitioned** i.e.
  * Records are partitioned (split into logical partitions) and distributed across nodes in a cluster
    * Partitions are the unit of parallellism
    * The number of partitions can be controlled using repartition OR coalesce transformation
* **Location Stickiness** i.e.
  * RDD can define placement preference to compute partitions (as close to the records as possible)
* **Parallel** i.e.
  * Process data in parallel
      
### Motivation:
* Motivation behind creating RDD were following types of application that current computing frameworks handle in-efficiently
  * Iterative algorithms - In machine learning and graph computations
  * Interactive data mining tools - Ad-hoc queries on same data set
    
### Goal:
* Re-use intermediate in-memory results across multiple data intensive workload with no need for copying large amounts of data over the network (which was the core issue in Map Reduce)

## Creating RDD

### From Collection
~~~
scala> val rdd = sc.parallelize(1 to 1000)
rdd: org.apache.spark.rdd.RDD[Int] = ParallelCollectionRDD[0] at parallelize at <console>:27

scala> rdd.take(5).foreach(println)
1
2
3
4
5

scala> rdd.count
res1: Long = 1000
~~~

### From File i.e. _External Dataset_
~~~
scala> val orders = sc.textFile("/home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/retail_db/orders")
orders: org.apache.spark.rdd.RDD[String] = /home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/retail_db/orders MapPartitionsRDD[2] at textFile at <console>:27

scala> orders.first
res2: String = 1,2013-07-25 00:00:00.0,11599,CLOSED

scala> orders.take(3).foreach(println)
1,2013-07-25 00:00:00.0,11599,CLOSED
2,2013-07-25 00:00:00.0,256,PENDING_PAYMENT
3,2013-07-25 00:00:00.0,12111,COMPLETE

~~~

### From Existing RDD
~~~
scala> val orders = sc.textFile("/home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/retail_db/orders")
orders: org.apache.spark.rdd.RDD[String] = /home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/retail_db/orders MapPartitionsRDD[4] at textFile at <console>:27

scala> val completedOrders = orders.filter((rec: String) => rec.split(",")(3) == "COMPLETE")
completedOrders: org.apache.spark.rdd.RDD[String] = MapPartitionsRDD[5] at filter at <console>:29

scala> completedOrders.take(5).foreach(println)
3,2013-07-25 00:00:00.0,12111,COMPLETE
5,2013-07-25 00:00:00.0,11318,COMPLETE
6,2013-07-25 00:00:00.0,7130,COMPLETE
7,2013-07-25 00:00:00.0,4530,COMPLETE
15,2013-07-25 00:00:00.0,2568,COMPLETE

~~~
  
## RDD - Types
Types of RDDs are:
  
* `ParallelCollectionRDD`
  * It provides functionality for reading data from collection e.g. `sc.parallelize(0 to 1000)` 
* `HadoopRDD`
  * It provides core functionality for reading data stored in HDFS using the older map reduce API e.g. `sc.textFile("/path")`
* `MapPartitionsRDD`
  * A result of calling operations e.g. `map`, `filter`, `flatMap`, `mapPartitions`
* `PairRDD`
  * An RDD of key-value pairs that is a result of `groupByKey`, `join` operations
* `DoubleRDD`
  * An RDD of Double type
* `CoalescedRDD`
  * A result of repartition OR coalesce transformations
* `ShuffledRDD`
  * A result of shuffling
* `PipedRDD`
  * An RDD created by piping elements to a forked external process
* `SequenceFileRDD`
  * An RDD that can be saved as Sequence file
* Many more...  
    
## RDD - Operations
RDD supports following types of operations:

### Transformation

* Transformations are the ones, which defines logic to process data and generate DAG
* It performs operations lazily, which returns another RDD
* At high level transformations can be grouped into following categories:
  * Mapping : `map`, `flatMap`, `filter`, `mapPartitions`
  * Aggregation : `groupByKey`, `reduceByKey`, `aggregateByKey`
  * Joins : `join`, `leftOuterJoin`, `rightOuterJoin`, `fullOuterJoin`, `cogroup`, `cartesian`
  * Set : `union`, `intersection`
  * Sorting & Ranking : `groupByKey`, `sortByKey`
  * Controlling RDD partitions : `coalesce`, `repartition`
  
### Action
* Actions are the ones which executes DAG (generated by transformations)
* At high level actions can be grouped into following categories:
  * Converting RDD to collection & preview data: `first`, `take`, `collect`
  * Aggregation : `reduce`
  * Sorting & Ranking : `top`, `takeOrdered`
  * Saving : `saveAsTextFile`, `saveAsSequenceFile` etc.

## What is Lazy Evaluation?

### Definition:
* As name suggests, variable will not be evaluated immediately but it will be evaluated when those variables are used and until then variables will act as reference

### In Context of Scala:
* We need to define variable as lazy to use lazy evaluation
~~~
scala> val a = {
     |   Thread.sleep(50000)
     | }
a: Unit = ()

scala> lazy val a = {
     |   Thread.sleep(5000)
     | }
a: Unit = <lazy>

scala> a
~~~

### In Context of Spark:
* Lazy evaluation is by default implemented for all transformations operations provided on top of RDD
* Until action is performed, Spark will not evaluate the program but instead it will build something called DAG (Directed Acyclic Graph)
* When action is performed, Spark will execute program as per DAG instruction
~~~
scala> val rdd = sc.parallelize(1 to 1000)
rdd: org.apache.spark.rdd.RDD[Int] = ParallelCollectionRDD[6] at parallelize at <console>:27

scala> rdd.count
res6: Long = 1000
~~~

## Spark Application - Life Cycle
Here is the typical life cycle of Spark application

* Identify file system:
  * `file://` => To read data from local file system
  * `hdfs://` => To read data from HDFS
  * `s3://` => To read data from amazone S3
* Understand file format, structure of data & use appropriate API to read data. SparkContext supports following APIs
  * `sc.textFile`
  * `sc.sequenceFile`
  * `sc.objectFile`
  * `sc.hadoopFile`
  * `sc.newAPIHadoopFile`
* Data will typically stored in distributed in-memory collection called RDD
* Apply one OR more transformation on data based on business requirement
* Use appropriate action to write data back to underlying system
