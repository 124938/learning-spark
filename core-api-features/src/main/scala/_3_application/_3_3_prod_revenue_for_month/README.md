## Product Revenue For Month - Application

### Development Life Cycle:

* **Start spark-shell in Standalone Cluster**
  * `spark-shell --master spark://asus-GL553VD:7077` => Launch spark shell in standalone mode
  * Refer below code to generate product revenue for month

  ~~~
  asus@asus-GL553VD:~$ spark-shell --master spark://asus-GL553VD:7077
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
  
  Using Scala version 2.10.5 (OpenJDK 64-Bit Server VM, Java 1.8.0_151)
  Type in expressions to have them evaluated.
  Type :help for more information.
  17/12/17 21:19:01 WARN Utils: Your hostname, asus-GL553VD resolves to a loopback address: 127.0.1.1; using 192.168.0.102 instead (on interface enp3s0)
  17/12/17 21:19:01 WARN Utils: Set SPARK_LOCAL_IP if you need to bind to another address
  Spark context available as sc.
  SQL context available as sqlContext.
  
  scala> val inputFolderPath = "/home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/retail_db"
  inputFolderPath: String = /home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/retail_db

  scala> val productsPath = inputFolderPath + "/products"
  productsPath: String = /home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/retail_db/products
  
  scala> val ordersPath = inputFolderPath + "/orders"
  ordersPath: String = /home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/retail_db/orders
  
  scala> val orderItemsPath = inputFolderPath + "/order_items"
  orderItemsPath: String = /home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/retail_db/order_items
  
  scala> val month = "2013-12"
  month: String = 2013-12
  
  scala> val ordersForMonthRdd = sc.
           textFile(ordersPath).
           filter((rec: String) => rec.split(",")(1).contains(month)).
           map((rec: String) => (rec.split(",")(0).toInt, rec))
  ordersForMonthRdd: org.apache.spark.rdd.RDD[(Int, String)] = MapPartitionsRDD[3] at map at <console>:36
  
  scala> val orderItemsRdd = sc.
           textFile(orderItemsPath).
           map((rec: String) => (rec.split(",")(1).toInt, rec))
  orderItemsRdd: org.apache.spark.rdd.RDD[(Int, String)] = MapPartitionsRDD[6] at map at <console>:33
  
  scala> val productRevenueRDD = orderItemsRdd.
           join(ordersForMonthRdd).
           map((t: (Int, (String, String))) => {
             val orderItemsRecArray = t._2._1.split(",")
             val ordersRecArray = t._2._2.split(",")
             ((orderItemsRecArray(2).toInt, ordersRecArray(3).toUpperCase), orderItemsRecArray(4).toFloat)
           }).
           reduceByKey((totalRev, eleRev) => totalRev + eleRev).
           map((t: ((Int, String), Float)) => (t._1._1, (t._1._2, t._2)))
  productRevenueRDD: org.apache.spark.rdd.RDD[(Int, (String, Float))] = MapPartitionsRDD[12] at map at <console>:47
  
  scala> val productsRdd = sc.
           textFile(productsPath).
           map((rec: String) => (rec.split(",")(0).toInt, rec))
  productsRdd: org.apache.spark.rdd.RDD[(Int, String)] = MapPartitionsRDD[15] at map at <console>:33
  
  scala> productRevenueRDD.
           join(productsRdd).
           map((t: (Int, ((String, Float), String))) => (t._2._2.split(",")(2), t._2._1._1, t._2._1._2)).
           map((t: (String, String, Float)) => t.productIterator.mkString("\t")).
           take(50).
           foreach(println)
  ~~~
  
* **Use IDE for development**
  * Create product revenue for month program under project

### Execution Life Cycle:

* **Use SBT to build artifact**
  * Execute below SBT command to build JAR file
  
  ~~~
  asus@asus-GL553VD:~/source_code/github/124938/learning-spark/core-api-features$ sbt package
  [info] Loading global plugins from /home/asus/.sbt/0.13/plugins
  [info] Loading project definition from /home/asus/source_code/github/124938/learning-spark/core-api-features/project
  [info] Set current project to core-api-features (in build file:/home/asus/source_code/github/124938/learning-spark/core-api-features/)
  [info] Compiling 1 Scala source to /home/asus/source_code/github/124938/learning-spark/core-api-features/target/scala-2.10/classes...
  [warn] Multiple main classes detected.  Run 'show discoveredMainClasses' to see the list
  [info] Packaging /home/asus/source_code/github/124938/learning-spark/core-api-features/target/scala-2.10/core-api-features_2.10-0.1.jar ...
  [info] Done packaging.
  [success] Total time: 25 s, completed 22 Dec, 2017 3:52:45 PM
  ~~~

* **Execute Application:**
  * **Local mode:** 
    * Refer below command to execute spark application
    ~~~
    asus@asus-GL553VD:~$ spark-submit \
      --class _3_application._3_3_prod_revenue_for_month.ProductRevenueForMonth \
      --name "Product Revenue For Month - demo application on local" \
      --conf spark.ui.port=54321 \
      /home/asus/source_code/github/124938/learning-spark/core-api-features/target/scala-2.10/core-api-features_2.10-0.1.jar \
      /home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/retail_db \
      /tmp/prod_rev_for_month_output \
      2013-12 \
      local
    ~~~
  
  * **Standalone mode:** 
    * Refer below command to execute spark application
    ~~~
    asus@asus-GL553VD:~$ mkdir /tmp/spark-events
    asus@asus-GL553VD:~$ spark-submit \
      --master spark://asus-GL553VD:7077 \
      --class _3_application._3_3_prod_revenue_for_month.ProductRevenueForMonth \
      --name "Product Revenue For Month - demo application on stand alone" \
      --conf spark.ui.port=54321 \
      --conf spark.eventLog.enabled=true \
      --executor-memory 512M \
      /home/asus/source_code/github/124938/learning-spark/core-api-features/target/scala-2.10/core-api-features_2.10-0.1.jar \
      /home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/retail_db \
      /tmp/prod_rev_for_month_output \
      2013-12 \
      dev
    ~~~
    
    * Refer below screenshot
    
    ![Alt text](_images/standalone/1.png?raw=true "Standalone - Home Page")
    
    ![Alt text](_images/standalone/2.png?raw=true "Standalone - Product Revenue for month - Job Summary")
    
    ![Alt text](_images/standalone/3.png?raw=true "Standalone - Product Revenue for month - Job Details")
    
  * **YARN mode:**
    * Refer below command to copy project JAR file from local machine to Cloudera QuickStart VM OR Gateway node of hadoop cluster
    ~~~
    asus@asus-GL553VD:~$ scp /home/asus/source_code/github/124938/learning-spark/core-api-features/target/scala-2.10/core-api-features_2.10-0.1.jar cloudera@192.168.211.142:/home/cloudera/core-api-features_2.10-0.1.jar
    ~~~

    * Make sure to configure Retail dataset setup (Click [here](https://github.com/124938/learning-hadoop-vendors/tree/master/cloudera/_1_quickstart_vm/_1_1_retail_dataset_setup/README.md) to know more details on it)
    
    * Login to Quick Start VM or Gateway node of hadoop cluster using ssh & verify copied files
    ~~~
    asus@asus-GL553VD:~$ ssh cloudera@192.168.211.142
    cloudera@192.168.211.142's password: 
    Last login: Sat Dec 23 01:30:00 2017 from 192.168.211.1

    [cloudera@quickstart ~]$ ls -ltr core-api-features_2.10-0.1.jar
    -rw-rw-r-- 1 cloudera cloudera 196861582 Dec 23 01:24 core-api-features_2.10-0.1.jar
    ~~~
    
    * Verify required files on HDFS
    ~~~
    [cloudera@quickstart ~]$ hadoop fs -ls -R /user/cloudera/sqoop/import-all-tables-text
    drwxr-xr-x   - cloudera cloudera          0 2017-12-19 03:24 /user/cloudera/sqoop/import-all-tables-text/categories
    -rw-r--r--   1 cloudera cloudera          0 2017-12-19 03:24 /user/cloudera/sqoop/import-all-tables-text/categories/_SUCCESS
    -rw-r--r--   1 cloudera cloudera       1029 2017-12-19 03:24 /user/cloudera/sqoop/import-all-tables-text/categories/part-m-00000
    drwxr-xr-x   - cloudera cloudera          0 2017-12-19 03:24 /user/cloudera/sqoop/import-all-tables-text/customers
    -rw-r--r--   1 cloudera cloudera          0 2017-12-19 03:24 /user/cloudera/sqoop/import-all-tables-text/customers/_SUCCESS
    -rw-r--r--   1 cloudera cloudera     953525 2017-12-19 03:24 /user/cloudera/sqoop/import-all-tables-text/customers/part-m-00000
    drwxr-xr-x   - cloudera cloudera          0 2017-12-19 03:25 /user/cloudera/sqoop/import-all-tables-text/departments
    -rw-r--r--   1 cloudera cloudera          0 2017-12-19 03:25 /user/cloudera/sqoop/import-all-tables-text/departments/_SUCCESS
    -rw-r--r--   1 cloudera cloudera         60 2017-12-19 03:25 /user/cloudera/sqoop/import-all-tables-text/departments/part-m-00000
    drwxr-xr-x   - cloudera cloudera          0 2017-12-19 03:25 /user/cloudera/sqoop/import-all-tables-text/order_items
    -rw-r--r--   1 cloudera cloudera          0 2017-12-19 03:25 /user/cloudera/sqoop/import-all-tables-text/order_items/_SUCCESS
    -rw-r--r--   1 cloudera cloudera    5408880 2017-12-19 03:25 /user/cloudera/sqoop/import-all-tables-text/order_items/part-m-00000
    drwxr-xr-x   - cloudera cloudera          0 2017-12-19 03:25 /user/cloudera/sqoop/import-all-tables-text/orders
    -rw-r--r--   1 cloudera cloudera          0 2017-12-19 03:25 /user/cloudera/sqoop/import-all-tables-text/orders/_SUCCESS
    -rw-r--r--   1 cloudera cloudera    2999944 2017-12-19 03:25 /user/cloudera/sqoop/import-all-tables-text/orders/part-m-00000
    drwxr-xr-x   - cloudera cloudera          0 2017-12-19 03:25 /user/cloudera/sqoop/import-all-tables-text/products
    -rw-r--r--   1 cloudera cloudera          0 2017-12-19 03:25 /user/cloudera/sqoop/import-all-tables-text/products/_SUCCESS
    -rw-r--r--   1 cloudera cloudera     173993 2017-12-19 03:25 /user/cloudera/sqoop/import-all-tables-text/products/part-m-00000
    ~~~

    * Refer below command to execute spark application
    ~~~
    [cloudera@quickstart ~]$ spark-submit \
      --master yarn \
      --class _3_application._3_3_prod_revenue_for_month.ProductRevenueForMonth \
      --name "Product Revenue For Month - demo application on YARN" \
      --conf spark.ui.port=54321 \
      --num-executors 2 \
      --executor-memory 512M \
      --executor-cores 2 \
      /home/cloudera/core-api-features_2.10-0.1.jar \
      /user/cloudera/sqoop/import-all-tables-text \
      /user/cloudera/prod_rev_for_month_output \
      2013-12 \
      prd
    ~~~
    
    * Refer below screenshot
    
    ![Alt text](_images/yarn/1.png?raw=true "YARN - Product Revenue for month - In Progress")
    
    ![Alt text](_images/yarn/2.png?raw=true "YARN - Product Revenue for month - Finished")

    ![Alt text](_images/yarn/3.png?raw=true "YARN - Product Revenue for month - Job Summary")
    
    ![Alt text](_images/yarn/4.png?raw=true "YARN - Product Revenue for month - Job Details")

* **Explore Spark Web UI:**
  * Job
  * Stages
    * Aggregated Metrics by Executor
    * Tasks
  * Environment
  * Executors
