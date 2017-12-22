## Spark Advance Concepts

### Deep Dive:
* **Find out number of executors in JOB**
  * Default value for number of executors are 2
  * Default value for number of executors can be override using `--num-executors 4` option, while launching spark application
  * _Note:_
    * Each executor is nothing but an individual JVM instance launched on worker node
    
* **Find out number of tasks executed in JOB**
  * _First/Initial Stage:_
    * Default value for number of tasks (in first/initial stage) are dependent on block size of underlying file system, from which data is getting read
    
    * In case of HDFS:
      * Default block size is 128 Mb, which can be change using `hadoop fs -D dfs.block.size=file-size -put local_name remote_location`
      * Refer `hdfs fsck /user/cloudera/cards/largedeck.txt -files -blocks -locations` command to find out number of blocks allocated to file
      * Number of tasks = File Size / Block size of file (typically 128 Mb)
    
    * In case of local file system:
      * Default value of block size is controlled by `fs.local.block.size` parameter, which is set to 32 Mb
      * Number of tasks = File size / 32 Mb
    
  * _Second/Later Stage:_
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
      * how much data will be discarded (as part of filter operation)?
      * What could be the rate at which data volume will be reduced (as part of aggregation operation)?
      * What are the number of unique keys to be processed?
    
    * Determine number of tasks for word count : As key is sparse....
      * There are millions of words under input data but for each GB of data is generating approximate of 30 MB of data
      * So number of tasks in stage 2 can be determine based on input data
    
    * Determine number of tasks for card count by suit : As key is dense....
      * There are only 4 suits in millions of records
      * So number of tasks in stage 2 can be set to 1
          
  * _Note:_
    * Task runs under executor
      
