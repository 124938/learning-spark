## Spark Advance Concepts

### Deep Dive
* **Find out number of executors in JOB:**
  * Default value for number of executors are 2
  * Default value for number of executors can be override using `--num-executors 4` option, while launching spark application
    
* **Find out number of tasks executed for first/initial stage in JOB:**
  * Default value for number of tasks (in first/initial stage) are dependent on block size of underlying file system, from which data is getting read
  * _In case of HDFS_:
    * Default block size is 128 Mb, which can be change using `hadoop fs -D dfs.block.size=file-size -put local_name remote_location`
    * Refer `hdfs fsck /user/cloudera/cards/largedeck.txt -files -blocks -locations` command to find out number of blocks allocated to file
    * Number of tasks = File Size / Block size of file (typically 128 Mb)
  * _In case of Local File System_:
    * Default value of block size is controlled by `fs.local.block.size` parameter, which is set to 32 Mb
    * Number of tasks = File size / 32 Mb
    
* **Find out number of tasks executed for second/later stage in JOB:**    
  * Default value for number of tasks (in second/later stage) are inherited from previous stage
  * Default value for number of tasks (in second/later stage) can be override programmatically for RDD operations, which does shuffling of data. e.g.:
    * `groupByKey`
    * `reduceByKey`
    * `aggregateByKey`
    * `sortByKey`
    * `join`
    * `cartesian`
    * `cogroup`
  * Followings are important points to be considered, while determining the number of tasks accurately (before shuffling):
    * How much data will be discarded (as part of filter operation)?
    * What could be the rate at which data volume will be reduced (as part of aggregation operation)?
    * What are the number of unique keys to be processed?
  * Determine number of tasks for word count : As key is sparse....
    * There are millions of words under input data but for each GB of data is generating approximate of 30 MB of data
    * So, number of tasks in stage 2 can be determine based on input data
  * Determine number of tasks for card count by suit : As key is dense....
    * There are only 4 suits in millions of records
    * So, number of tasks in stage 2 can be set to 1

* **Find out number of output files generated after executing JOB:**    
  * Default value for number of output files are inherited from number of task executed in last stage of JOB
  * Few files may have output data and few files may be empty. Reason being while shuffling, data will be grouped and partition by key based on formula `mod(hash(key), numTasks)`

* _**Note:**_
  * Each executor is nothing but an individual JVM instance launched on worker node
  * Task runs under executor

### Accumulator
* **Pre-Requisite:**
  * Before understanding accumulator, we need to understand the scope of driver program and scope of task getting executed in distributed manner

* **Introduction:**
  * In Map-Reduce world, concept called Counter is known as Accumulator in Spark
  * In Spark program, accumulator is typically initialized in driver program and scope of accumulator is managed across all the executors OR tasks

* **When to use?**
  * Followings are typical use case of Accumulator:
    * Unit testing
    * Data quality

* **How to create?**
  ~~~
  val ordersCountAcc = sc.accumulator(0, "Number of orders")
  val orderItemsCountAcc = sc.accumulator(0, "Number of order items")
  ~~~
  
* **How to update?**
  ~~~
  ordersCountAcc += 1
  orderItemsCountAcc += 1
  ~~~
  
* **Known Issues:**
  * Unless tasks are finished, we will not be able to see details of accumulator
  * Spark guarantees accumulator to be updated only in first execution i.e. if any task is re-executed the result can be inconsistent

* _**Important Note:**_
  * Accumulator should get used to manage counter instead of creating global variable in spark program

### Broadcast Variable
* **Introduction:**
  * In Map-Reduce world, concept called Map Side Join is known as Broadcast Variable in Spark
  * Broadcast Variable is immutable i.e. it can't be change

* **When to use?**
  * Many times, there is a requirement to share data/information required by executors to complete their task
  * Usage of broadcast variable in below use case provides considerable performance boost:
    * Large data set (fact) is getting join with smaller data set (dimension)

* **How to create?**
  * Broadcast variable can be created in many ways and followings are few common ways:
    * Reading data from local file system
    * Reading data from HDFS
    * Reading data from configuration 
    
    ~~~
    // Create broadcast variable from local file system
    import scala.io.Source
    
    val productMapBrd = Source.
      fromFile("/home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/retail_db/products/part-00000").
      getLines.
      toList.
      map((rec: String) => {
        val recArray = rec.split(",")
        (recArray(0).toInt, recArray(2))
      }).
      toMap  
    ~~~

* **How to use broadcast variable?**
  ~~~
  // lookup product name for product id = 1
  productMapBrd.
    value.
    get(1).
    get
    
  // lookup product name for product id = 25
  productMapBrd.
    value.
    get(25).
    get
  ~~~

* _**Important Note:**_
  * Size of broadcast variable should fit into memory used by task of executor
