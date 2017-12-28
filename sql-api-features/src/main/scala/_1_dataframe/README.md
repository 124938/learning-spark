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
    
    ![Alt text](_images/spark-sql-catalyst-optimizer-model.png?raw=true "Spark SQL - Catalyst Optimizer")
    
  * **Custom Memory Management (aka Project Tungsten):**
    * Data is stored in off-heap memory in binary format, which saves a lot of memory space & also there is no overhead of garbage collection
    * By knowing schema of data in advance and storing it efficiently in binary format, expensive java serialization is also avoided

### DataFrame - Creation

**(1) Using Native Context (aka org.apache.spark.sql.SQLContext)** 
* With a SQLContext, application can create DataFrame in following ways:
  * From Existing RDD
  * From Data Sources i.e.
    * JSON File
    * Parquet File
    * Avro File
    * JDBC
    * Many more...

**(2) Using Hive Context (aka org.apache.spark.sql.hive.HiveContext)** 
* HiveContext is an extension of SQLContext, which provides supports for reading/writing data from/to Apache Hive
* With a HiveContext, application can create DataFrame in following ways:
  * TODO
  
### DataFrame - Operations
* DataFrame supports following type of transformations:
  * Filter
  * Aggregation
  * Join
  * Set
  * Soring & Ranking
  * Analytical or Windowing functions
  * RDD conversion
  
