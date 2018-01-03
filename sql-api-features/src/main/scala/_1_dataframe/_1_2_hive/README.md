## DataFrame - Using Hive Context (aka org.apache.spark.sql.hive.HiveContext)
* HiveContext is an extension of SQLContext, which provides supports for reading/writing data from/to Apache Hive
  
### Pre-Requisite
* **Dependency:**
  * Since Hive has a large number of dependencies, it's not included in default spark dependencies
  * Add below dependency to use HiveContext in your project
    
  ~~~
  libraryDependencies += "org.apache.spark" % "spark-hive_2.10" % "1.6.3"
  ~~~
  
* **Hive integration with Spark:**
  * _YARN:_
    * Make sure to integrate Hive with Spark (Click [here](https://github.com/124938/learning-hadoop-vendors/blob/master/cloudera/_1_quickstart_vm/_1_3_spark_hive_setup/README.md) to know more details on it)
  * _Local:_
    * Not sure

### Create
    
### Operations
* DataFrame created from Hive Context (aka org.apache.spark.sql.hive.HiveContext) supports following type of transformations:
  * Filter
  * Aggregation
  * Join
  * Set
  * Soring & Ranking
  * Analytical or Windowing functions
  * RDD conversion
