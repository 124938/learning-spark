## DataFrame

### What is DataFrame?
* DataFrame is an abstraction, which gives a schema view on top of data i.e.
  * We can think DataFrame like a table in database
  * It provides data as columns with name & type
* Like RDD, execution in DataFrame too is lazily triggered
* It offers huge performance improvement over RDDs because of following powerful features:
  
  * **Optimized Execution Engine (aka Catalyst Optimizer):**
    * Query plans are created for execution using Spark catalyst optimizer
    * After an optimized execution plan is prepared going through some steps, the final execution happens internally on RDDs only but that's completely hidden from users
    
    ![Alt text](images/spark-sql-catalyst-optimizer-model.png?raw=true "Spark SQL - Catalyst Optimizer")
    
  * **Custom Memory Management (aka Project Tungsten):**
    * Data is stored in off-heap memory in binary format, which saves a lot of memory space & also there is no overhead of garbage collection
    * By knowing schema of data in advance and storing it efficiently in binary format, expensive java serialization is also avoided

### Creating DataFrame

**(1) : Using Native Context (aka org.apache.spark.sql.SQLContext)**
* With a SQLContext, application can crete DataFrame in following ways:
  
  * **From Existing RDD:**
    * Inferring the schema using reflection i.e. using case class
    * Programmatically specifying the schema using StructField
    * Programmatically specifying the schema using toDF
    
  * **From Data Source:**
    * JSON file
    * Parquet file
    * Avro file
    * JDBC
    * Many more...
    
**(2) : Using Hive Context (aka org.apache.spark.sql.hive.HiveContext)**
* HiveContext is an extension of SQLContext, which provides supports for reading/writing data from/to Apache Hive
* Pre-Requisites to use HiveContext
  
  * **Dependency:**
    * Since Hive has a large number of dependencies, it's not included in default spark dependencies
    * Add below dependency to use HiveContext in your project
    
    ~~~
    libraryDependencies += "org.apache.spark" % "spark-hive_2.10" % "1.6.3"
    ~~~
  
  * **Configuration:** 
    
    * _Approach-1:_ Copy following files under conf folder of Spark installation
      * `hive-site.xml`
      * `core-site.xml` => For security configuration
      * `hdfs-site.xml` => For HDFS configuration
    
    * _Approach-2:_ Create soft link of `hive-site.xml` under conf folder of Spark installation using `ln-s` command