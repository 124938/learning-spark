## Spark Advance Concepts

### Deep Dive:
* **Find out number of executors in JOB**
  * Default value for number of executors are 2
  * Default value for number of executors can be override using `--num-executors 4` option, while launching spark application
  * _Note:_
    * Each executor is nothing but JVM instance launched on worker node
    
* **Find out number of tasks executed in JOB**
  * _First/Initial Stage:_
    * Default value for number of tasks (in first/initial stage) are dependent on block size of underlying file system from which data is getting read

  * _Second/Later Stage:_
    * Default value for number of tasks (in second/later stage) are inherited from previous stage
    * Default value for number of tasks (in second/later stage) can be override programmatically for many RDD operations, which does shuffling of data. e.g.:
      * `groupByKey`
      * `reduceByKey`
      * `aggregateByKey`
      * `sortByKey`
      * `join`
      * `cartesian`
      * `cogroup`
  
  * _How to determine number of tasks accurately?_
  
  * _Note:_
    * Task runs under executor
    * Refer below command to determine block size of file lying under HDFS
      * `hdfs fsck /user/cloudera/cards/largedeck.txt -files -blocks -locations`
    * Default value of block size for local file system is 32 Mb, which is controlled by below parameter:
      * `fs.local.block.size`
      
