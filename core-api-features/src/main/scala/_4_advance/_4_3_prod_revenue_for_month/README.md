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
    
    ![Alt text](_images/standalone/1.png?raw=true "Standalone - Home Page")
    
    ![Alt text](_images/standalone/2.png?raw=true "Standalone - Product Revenue for month updated - Job Summary")
    
    ![Alt text](_images/standalone/3.png?raw=true "Standalone - Product Revenue for month updated - Job Details")

    ![Alt text](_images/standalone/4.png?raw=true "Standalone - Product Revenue for month updated - Stage 0")
    
    ![Alt text](_images/standalone/5.png?raw=true "Standalone - Product Revenue for month updated - Stage 1")
    
    ![Alt text](_images/standalone/6.png?raw=true "Standalone - Product Revenue for month updated - Stage 2")
    
    ![Alt text](_images/standalone/7.png?raw=true "Standalone - Product Revenue for month updated - Stage 3")
    
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
      --class _4_advance._4_3_prod_revenue_for_month.ProductRevenueForMonthUpdated \
      --name "Product Revenue For Month Updated - demo application on YARN" \
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
