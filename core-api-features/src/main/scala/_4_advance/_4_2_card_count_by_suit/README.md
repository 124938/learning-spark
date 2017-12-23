## Updated Card Count By Suit - Application

### Determine number of tasks for last stage 
  * As key is dense....
    * There are only 4 suits in millions of records
    * So, number of tasks in stage 2 can be set to 1

### Development Life Cycle:

* **Use IDE for development**
  * Create updated card count by suit program under project

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
  [success] Total time: 24 s, completed 23 Dec, 2017 12:20:43 PM
  ~~~

* **Execute Application:**
  * **Local mode:** 
    * Refer below command
    ~~~
    asus@asus-GL553VD:~$ spark-submit \
      --class _4_advance._4_2_card_count_by_suit.CardCountBySuitUpdated \
      --name "Card Count By Suit Updated - demo application on local" \
      --conf spark.ui.port=54321 \
      /home/asus/source_code/github/124938/learning-spark/core-api-features/target/scala-2.10/core-api-features_2.10-0.1.jar \
      /home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/card/largedeck.txt \
      /tmp/card_updated_output \
      local
    ~~~
  
  * **Standalone mode:** 
    * Refer below command
    ~~~
    asus@asus-GL553VD:~$ mkdir /tmp/spark-events
    asus@asus-GL553VD:~$ spark-submit \
      --master spark://asus-GL553VD:7077 \
      --class _4_advance._4_2_card_count_by_suit.CardCountBySuitUpdated \
      --name "Card Count By Suit Updated - demo application on stand alone" \
      --conf spark.ui.port=54321 \
      --conf spark.eventLog.enabled=true \
      --executor-memory 512M \
      /home/asus/source_code/github/124938/learning-spark/core-api-features/target/scala-2.10/core-api-features_2.10-0.1.jar \
      /home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/card/largedeck.txt \
      /tmp/card_updated_output \
      dev
    ~~~
    
    * Refer below screenshot
    
    ![Alt text](_images/standalone/1.png?raw=true "Standalone Cluster - Home Page")
    
    ![Alt text](_images/standalone/2.png?raw=true "Standalone Cluster - Card Count By Suit Updated - Job Summary")
    
    ![Alt text](_images/standalone/3.png?raw=true "Standalone Cluster - Card Count By Suit Updated - Job Details")
    
    ![Alt text](_images/standalone/4.png?raw=true "Standalone Cluster - Card Count By Suit Updated - Stage 0")
    
    ![Alt text](_images/standalone/5.png?raw=true "Standalone Cluster - Card Count By Suit Updated - Stage 1")
        
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
      --class _4_advance._4_2_card_count_by_suit.CardCountBySuitUpdated \
      --name "Card Count By Suit - demo application on YARN" \
      --conf spark.ui.port=54321 \
      --num-executors 4 \
      --executor-memory 512M \
      --executor-cores 2 \
      /home/cloudera/core-api-features_2.10-0.1.jar \
      /user/cloudera/card \
      /user/cloudera/card_updated_output \
      prd
    ~~~
  
* **Explore Spark Web UI:**
  * Job
  * Stages
    * Aggregated Metrics by Executor
    * Tasks
  * Environment
  * Executors
