## Updated Product Revenue For Month - Application

### Use accumulator to count:
  * Number of orders for provided month
  * Number of order items for provided month

### Usage broadcast variable to:
  * Lookup product name based on product id

### Development Life Cycle:

* **Use IDE for development**
  * Create updated product revenue for month program under project

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
  [success] Total time: 24 s, completed 23 Dec, 2017 1:03:00 PM
  ~~~

* **Execute Application:**
  * **Local mode:** 
    * Refer below command
    ~~~
    asus@asus-GL553VD:~$ spark-submit \
      --class _4_advance._4_3_prod_revenue_for_month.ProductRevenueForMonthUpdated \
      --name "Product Revenue For Month Updated - demo application on local" \
      --conf spark.ui.port=54321 \
      /home/asus/source_code/github/124938/learning-spark/core-api-features/target/scala-2.10/core-api-features_2.10-0.1.jar \
      /home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/retail_db \
      /tmp/prod_rev_for_month_updated_output \
      2013-12 \
      local
    ~~~
  
  * **Standalone mode:** 
    * Refer below command
    ~~~
    asus@asus-GL553VD:~$ mkdir /tmp/spark-events
    asus@asus-GL553VD:~$ spark-submit \
      --master spark://asus-GL553VD:7077 \
      --class _4_advance._4_3_prod_revenue_for_month.ProductRevenueForMonthUpdated \
      --name "Product Revenue For Month - demo application on stand alone" \
      --conf spark.ui.port=54321 \
      --conf spark.eventLog.enabled=true \
      --executor-memory 512M \
      /home/asus/source_code/github/124938/learning-spark/core-api-features/target/scala-2.10/core-api-features_2.10-0.1.jar \
      /home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/retail_db \
      /tmp/prod_rev_for_month_updated_output \
      2013-12 \
      dev
    ~~~
    
    * Refer below screenshot
    
    ![Alt text](_images/standalone/1.png?raw=true "Standalone Cluster - Home Page")
    
    ![Alt text](_images/standalone/2.png?raw=true "Standalone Cluster - Product Revenue for month updated - Job Summary")
    
    ![Alt text](_images/standalone/3.png?raw=true "Standalone Cluster - Product Revenue for month updated - Job Details")
    
  * **YARN mode:**
    * Copy JAR file from local machine to Cloudera QuickStart VM or Gateway node using below command
    ~~~
    asus@asus-GL553VD:~$ scp /home/asus/source_code/github/124938/learning-spark/core-api-features/target/scala-2.10/core-api-features_2.10-0.1.jar cloudera@192.168.211.142:/home/cloudera/core-api-features_2.10-0.1.jar
    ~~~

    * Copy deck of card text file from local machine to Cloudera QuickStart VM or Gateway node using below command
    ~~~
    asus@asus-GL553VD:~$ scp /home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/card/largedeck.txt cloudera@192.168.211.142:/home/cloudera/card
    ~~~
    
    * Login to Quick Start VM or gateway node of hadoop cluster using ssh & verify copied files
    ~~~
    asus@asus-GL553VD:~$ ssh cloudera@192.168.211.142
    cloudera@192.168.211.142's password: 
    Last login: Sat Dec  9 19:13:35 2017 from 192.168.211.1

    [cloudera@quickstart ~]$ ls -ltr core-api-features_2.10-0.1.jar 
    -rw-rw-r-- 1 cloudera cloudera 2650763 Dec 10 20:05 core-api-features_2.10-0.1.jar

    [cloudera@quickstart ~]$ ls -ltr card
    total 141240
    -rw-rw-r-- 1 cloudera cloudera 72313825 Dec 10 21:16 largedeck.txt
    ~~~
    
    * Copy text files folder from QuickStart VM or Gateway node to HDFS
    ~~~
    [cloudera@quickstart ~]$ hadoop fs -put /home/cloudera/card /user/cloudera/card
    
    [cloudera@quickstart ~]$ hadoop fs -ls /user/cloudera/card
    Found 1 item
    -rw-r--r--   1 cloudera cloudera   72313825 2017-12-10 21:24 /user/cloudera/card/largedeck.txt
    ~~~

    * Refer below command
    ~~~
    [cloudera@quickstart ~]$ spark-submit \
      --master yarn \
      --class _3_application._3_3_prod_revenue_for_month.ProductRevenueForMonth \
      --name "Product Revenue For Month - demo application on YARN" \
      --conf spark.ui.port=54321 \
      --num-executors 1 \
      --executor-memory 512M \
      --executor-cores 2 \
      /home/cloudera/core-api-features_2.10-0.1.jar \
      /user/cloudera/sqoop/import-all-tables-text \
      /user/cloudera/prod_rev_for_month_updated_output \
      prd
    ~~~
  
* **Explore Spark Web UI:**
  * Job
  * Stages
    * Aggregated Metrics by Executor
    * Tasks
  * Environment
  * Executors
