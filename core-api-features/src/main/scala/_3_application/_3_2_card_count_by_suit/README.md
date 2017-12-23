## Card Count By Suit - Application

### Development Life Cycle:

* **Start spark-shell in Standalone Cluster**
  * `spark-shell --master spark://asus-GL553VD:7077` => Launch spark shell in standalone mode
  * Refer below code to count card by suit

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
  
  scala> val cards = sc.textFile("/home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/card/largedeck.txt").map(rec => (rec.split("\\|")(1), 1))
  cards: org.apache.spark.rdd.RDD[(String, Int)] = MapPartitionsRDD[2] at map at <console>:27
  
  scala> cards.reduceByKey((total, ele) => total + ele).take(10).foreach(println)
  (SPADE,13631488)
  (HEART,13631488)
  (DIAMOND,13631488)
  (CLUB,13631488)

  ~~~
  
* **Use IDE for development**
  * Create card count by suit program under project

### Execution Life Cycle:

* **Use SBT to build artifact**
  * Execute below SBT command to build JAR file
  
  ~~~
  asus@asus-GL553VD:~/source_code/github/124938/learning-spark/core-api-features$ sbt package
  [info] Loading global plugins from /home/asus/.sbt/0.13/plugins
  [info] Loading project definition from /home/asus/source_code/github/124938/learning-spark/core-api-features/project
  [info] Set current project to core-api-features (in build file:/home/asus/source_code/github/124938/learning-spark/core-api-features/)
  [warn] Multiple main classes detected.  Run 'show discoveredMainClasses' to see the list
  [info] Packaging /home/asus/source_code/github/124938/learning-spark/core-api-features/target/scala-2.10/core-api-features_2.10-0.1.jar ...
  [info] Done packaging.
  [success] Total time: 40 s, completed 17 Dec, 2017 6:23:26 PM
  ~~~

* **Execute Application:**
  * **Local mode:** 
    * Refer below command to execute spark application
    ~~~
    asus@asus-GL553VD:~$ spark-submit \
      --class _3_application._3_2_card_count_by_suit.CardCountBySuit \
      --name "Card Count By Suit - demo application on local" \
      --conf spark.ui.port=54321 \
      /home/asus/source_code/github/124938/learning-spark/core-api-features/target/scala-2.10/core-api-features_2.10-0.1.jar \
      /home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/card/largedeck.txt \
      /tmp/card_output \
      local
    ~~~
  
  * **Standalone mode:** 
    * Refer below command to execute spark application
    ~~~
    asus@asus-GL553VD:~$ mkdir /tmp/spark-events
    asus@asus-GL553VD:~$ spark-submit \
      --master spark://asus-GL553VD:7077 \
      --class _3_application._3_2_card_count_by_suit.CardCountBySuit \
      --name "Card Count By Suit - demo application on stand alone" \
      --conf spark.ui.port=54321 \
      --conf spark.eventLog.enabled=true \
      --executor-memory 512M \
      /home/asus/source_code/github/124938/learning-spark/core-api-features/target/scala-2.10/core-api-features_2.10-0.1.jar \
      /home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/card/largedeck.txt \
      /tmp/card_output \
      dev
    ~~~
    
    * Refer below screenshot
    
    ![Alt text](_images/standalone/1.png?raw=true "Standalone - Home Page")
    
    ![Alt text](_images/standalone/2.png?raw=true "Standalone - Card Count By Suit - Job Summary")
    
    ![Alt text](_images/standalone/3.png?raw=true "Standalone - Card Count By Suit - Job Details")
    
    ![Alt text](_images/standalone/4.png?raw=true "Standalone - Card Count By Suit - Stage 0")
    
    ![Alt text](_images/standalone/5.png?raw=true "Standalone - Card Count By Suit - Stage 1")
        
  * **YARN mode:**
    * Refer below command to copy project JAR file from local machine to Cloudera QuickStart VM OR Gateway node of hadoop cluster
    ~~~
    asus@asus-GL553VD:~$ scp /home/asus/source_code/github/124938/learning-spark/core-api-features/target/scala-2.10/core-api-features_2.10-0.1.jar cloudera@192.168.211.142:/home/cloudera/core-api-features_2.10-0.1.jar
    ~~~

    * Refer below command to copy largedeck.txt file from local machine to Cloudera QuickStart VM OR Gateway node of hadoop cluster
    ~~~
    asus@asus-GL553VD:~$ scp /home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/card/largedeck.txt cloudera@192.168.211.142:/home/cloudera/largedeck.txt
    ~~~
    
    * Login to Quick Start VM or Gateway node of hadoop cluster using ssh & verify copied files
    ~~~
    asus@asus-GL553VD:~$ ssh cloudera@192.168.211.142
    cloudera@192.168.211.142's password: 
    Last login: Sat Dec 23 01:30:00 2017 from 192.168.211.1

    [cloudera@quickstart ~]$ ls -ltr core-api-features_2.10-0.1.jar
    -rw-rw-r-- 1 cloudera cloudera 196861582 Dec 23 01:24 core-api-features_2.10-0.1.jar

    [cloudera@quickstart ~]$ ls -ltr largedeck.txt
    -rw-rw-r-- 1 cloudera cloudera 726663168 Dec 23 03:40 largedeck.txt
    ~~~
    
    * Copy largedeck.txt file from local file system to HDFS
    ~~~
    [cloudera@quickstart ~]$ hadoop fs -mkdir /user/cloudera/card
    
    [cloudera@quickstart ~]$ hadoop fs -put /home/cloudera/largedeck.txt /user/cloudera/card
    
    [cloudera@quickstart ~]$ hadoop fs -ls /user/cloudera/card
    Found 1 items
    -rw-r--r--   1 cloudera cloudera  726663168 2017-12-23 03:41 /user/cloudera/card/largedeck.txt

    [cloudera@quickstart ~]$ hdfs fsck /user/cloudera/card/largedeck.txt -files -blocks -locations
    Connecting to namenode via http://quickstart.cloudera:50070/fsck?ugi=cloudera&files=1&blocks=1&locations=1&path=%2Fuser%2Fcloudera%2Fcard%2Flargedeck.txt
    FSCK started by cloudera (auth:SIMPLE) from /127.0.0.1 for path /user/cloudera/card/largedeck.txt at Sat Dec 23 03:43:49 PST 2017
    /user/cloudera/card/largedeck.txt 726663168 bytes, 6 block(s):  OK
    0. BP-1028023124-127.0.0.1-1500470886981:blk_1073743130_2309 len=134217728 Live_repl=1 [DatanodeInfoWithStorage[127.0.0.1:50010,DS-8e0b541e-8cba-4475-87f2-c3b9bd3de801,DISK]]
    1. BP-1028023124-127.0.0.1-1500470886981:blk_1073743131_2310 len=134217728 Live_repl=1 [DatanodeInfoWithStorage[127.0.0.1:50010,DS-8e0b541e-8cba-4475-87f2-c3b9bd3de801,DISK]]
    2. BP-1028023124-127.0.0.1-1500470886981:blk_1073743132_2311 len=134217728 Live_repl=1 [DatanodeInfoWithStorage[127.0.0.1:50010,DS-8e0b541e-8cba-4475-87f2-c3b9bd3de801,DISK]]
    3. BP-1028023124-127.0.0.1-1500470886981:blk_1073743133_2312 len=134217728 Live_repl=1 [DatanodeInfoWithStorage[127.0.0.1:50010,DS-8e0b541e-8cba-4475-87f2-c3b9bd3de801,DISK]]
    4. BP-1028023124-127.0.0.1-1500470886981:blk_1073743134_2313 len=134217728 Live_repl=1 [DatanodeInfoWithStorage[127.0.0.1:50010,DS-8e0b541e-8cba-4475-87f2-c3b9bd3de801,DISK]]
    5. BP-1028023124-127.0.0.1-1500470886981:blk_1073743135_2314 len=55574528 Live_repl=1 [DatanodeInfoWithStorage[127.0.0.1:50010,DS-8e0b541e-8cba-4475-87f2-c3b9bd3de801,DISK]]

    Status: HEALTHY
     Total size:	726663168 B
     Total dirs:	0
     Total files:	1
     Total symlinks:		  0
     Total blocks (validated):	  6 (avg. block size 121110528 B)
     Minimally replicated blocks: 6 (100.0 %)
     Over-replicated blocks:	  0 (0.0 %)
     Under-replicated blocks:	  0 (0.0 %)
     Mis-replicated blocks:	  0 (0.0 %)
     Default replication factor:  1
     Average block replication:	  1.0
     Corrupt blocks:	 	  0
     Missing replicas:		  0 (0.0 %)
     Number of data-nodes:	  1
     Number of racks:		  1
    FSCK ended at Sat Dec 23 03:43:49 PST 2017 in 2 milliseconds

    The filesystem under path '/user/cloudera/card/largedeck.txt' is HEALTHY
    ~~~

    * Refer below command to execute spark application
    ~~~
    [cloudera@quickstart ~]$ spark-submit \
      --master yarn \
      --class _3_application._3_2_card_count_by_suit.CardCountBySuit \
      --name "Card Count By Suit - demo application on YARN" \
      --conf spark.ui.port=54321 \
      --num-executors 2 \
      --executor-memory 512M \
      --executor-cores 2 \
      /home/cloudera/core-api-features_2.10-0.1.jar \
      /user/cloudera/card \
      /user/cloudera/card_output \
      prd
    ~~~

    * Refer below screenshot
    
    ![Alt text](_images/yarn/1.png?raw=true "YARN - Card Count By Suit - In Progress")
    
    ![Alt text](_images/yarn/2.png?raw=true "YARN - Card Count By Suit - Finished")

    ![Alt text](_images/yarn/3.png?raw=true "YARN - Card Count By Suit - Job Summary")
    
    ![Alt text](_images/yarn/4.png?raw=true "YARN - Card Count By Suit - Job Details")
    
    ![Alt text](_images/yarn/5.png?raw=true "YARN - Card Count By Suit - Stage 0")
    
    ![Alt text](_images/yarn/6.png?raw=true "YARN - Card Count By Suit - Stage 1")

* **Explore Spark Web UI:**
  * Job
  * Stages
    * Aggregated Metrics by Executor
    * Tasks
  * Environment
  * Executors
