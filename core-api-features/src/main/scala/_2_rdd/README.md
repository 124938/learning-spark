## RDD (Resilient distributed dataset)
* **Definition:**
  * RDD is resilient and distributed collection of records spread over one OR many partitions
  * One could compare RDD to collection in Scala i.e. RDD is computed on many JVMs while Scala collection lives on single JVM

* **Core Features:** (decomposing the name)
  * _Resilient:_ i.e. Fault tolerant with help of RDD lineage graph and so it can recompute missing OR damaged partition due to node failure
  * _Distributed:_ i.e. Data resides on multiple nodes in a cluster
  * _Dataset:_ is a collection of partitioned data with primitive values OR values of values e.g. tuple OR any object
  
* **Additional Features:**
  * _Immutable OR Read-Only_ i.e.
    * It does not change once created and can only be transformed using transformation operation to new RDD
  * _Lazy evaluated_ i.e.
    * Data inside RDD is not available OR transformed until an action is executed that triggers execution
    * In other words, it will only create DAG (directed acyclic graph) and the job will be submitted only when action is performed
  * _Types_ i.e.
      * RDD records have types e.g.
        * `Long` in `RDD[Long]`
        * `(Int, String)` in `RDD[(Int, String)]`   
  * _In Memory_ i.e.
    * Data inside RDD is stored in memory as much (size) and long (time) as possible
  * _Cachable_ i.e.
    * It can hold all data in a persistent storage like memory (default and most preferred) OR disk (the least preferred due to access speed)
      * MEMORY_ONLY (default)
      * MEMORY_AND_DISK
      * MEMORY_ONLY_SER
      * DISK_ONLY
  * _Partitioned_ i.e.
    * Records are partitioned (split into logical partitions) and distributed across nodes in a cluster
      * Partitions are the unit of parallellism
      * The number of partitions can be controlled using repartition OR coalesce transformation
  * _Location Stickiness_ i.e.
    * RDD can define placement preference to compute partitions (as close to the records as possible)
  * _Parallel_ i.e.
    * Process data in parallel
      
* **Motivation:**
  * Motivation behind creating RDD were following types of application that current computing frameworks handle in-efficiently
    * Iterative algorithms - In machine learning and graph computations
    * Interactive data mining tools - Ad-hoc queries on same data set
    
* **Goal:**
  * Re-use intermediate in-memory results across multiple data intensive workload with no need for copying large amounts of data over the network (which was the core issue in Map Reduce)

## Creating RDD

* From **Collection**
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

* From **File** i.e. _External Dataset_
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

* From **Existing RDD**
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
* Types of RDDs are:
  
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
* RDD supports following types of operations:

  * **Transformation**
    * Lazy operations that return another RDD like `map`, `flatMap`, `filter`, `reduceByKey`, `join`, `cogroup` etc.

  * **Action**
    * Operations that trigger computation and return values like `count`, `take`, `collect` etc.