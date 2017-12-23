## Word Count - Application

### Development Life Cycle:

* **Start spark-shell in Standalone Cluster**
  * `spark-shell --master spark://asus-GL553VD:7077` => Launch spark shell in standalone mode
  * Refer below code to write word count program

  ~~~
  asus@asus-GL553VD:~$ spark-shell --master spark://asus-GL553VD:7077 --conf spark.eventLog.enabled=true
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
  17/12/17 19:40:15 WARN Utils: Your hostname, asus-GL553VD resolves to a loopback address: 127.0.1.1; using 192.168.0.102 instead (on interface enp3s0)
  17/12/17 19:40:15 WARN Utils: Set SPARK_LOCAL_IP if you need to bind to another address
  Spark context available as sc.
  SQL context available as sqlContext.
  
  scala> val lines = sc.textFile("/home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/word/bible.txt")
  lines: org.apache.spark.rdd.RDD[String] = /home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/word/bible.txt MapPartitionsRDD[1] at textFile at <console>:27
  
  scala> val words = lines.flatMap((line: String) => line.split(" ").map((word: String) => (word.toLowerCase, 1)))
  words: org.apache.spark.rdd.RDD[(String, Int)] = MapPartitionsRDD[2] at flatMap at <console>:29
  
  scala> words.reduceByKey(_ + _).take(10).foreach(println)
  (hori.,160)
  (abiud;,160)
  (bone,1920)
  (couching,320)
  (also;,1440)
  (chilmad,,160)
  (foreordained,160)
  (ship,,3840)
  (ahasuerus',160)
  (female,,960)
  
  ~~~
  
* **Use IDE for development**
  * Create word count program under project

### Execution Life Cycle:

* **Use SBT to build artifact**
  * Execute below SBT command to build JAR file
  
  ~~~
  asus@asus-GL553VD:~$ cd /home/asus/source_code/github/124938/learning-spark/core-api-features
  
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
      --class _3_application._3_1_word_count.WordCount \
      --name "Word Count - demo application on local" \
      --conf spark.ui.port=54321 \
      /home/asus/source_code/github/124938/learning-spark/core-api-features/target/scala-2.10/core-api-features_2.10-0.1.jar \
      /home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/word/bible.txt \
      /tmp/word_output \
      local
    ~~~
  
  * **Standalone mode:** 
    * Refer below command to execute spark application
    ~~~
    asus@asus-GL553VD:~$ mkdir /tmp/spark-events
    asus@asus-GL553VD:~$ spark-submit \
      --master spark://asus-GL553VD:7077 \
      --class _3_application._3_1_word_count.WordCount \
      --name "Word Count - demo application on stand alone" \
      --conf spark.ui.port=54321 \
      --conf spark.eventLog.enabled=true \
      --executor-memory 512M \
      /home/asus/source_code/github/124938/learning-spark/core-api-features/target/scala-2.10/core-api-features_2.10-0.1.jar \
      /home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/word/bible.txt \
      /tmp/word_output \
      dev
    ~~~
    
    * Refer below screenshot
    
    ![Alt text](_images/standalone/1.png?raw=true "Standalone - Home Page")
    
    ![Alt text](_images/standalone/2.png?raw=true "Standalone - Word Count - Job Summary")
    
    ![Alt text](_images/standalone/3.png?raw=true "Standalone - Word Count - Job Details")
    
    ![Alt text](_images/standalone/4.png?raw=true "Standalone - Word Count - Stage 0")
    
    ![Alt text](_images/standalone/5.png?raw=true "Standalone - Word Count - Stage 1")
    
    
  * **YARN mode:**
    * Refer below command to copy project JAR file from local machine to Cloudera QuickStart VM OR Gateway node of hadoop cluster
    ~~~
    asus@asus-GL553VD:~$ scp /home/asus/source_code/github/124938/learning-spark/core-api-features/target/scala-2.10/core-api-features_2.10-0.1.jar cloudera@192.168.211.142:/home/cloudera/core-api-features_2.10-0.1.jar
    ~~~

    * Refer below command to copy bible.txt file from local machine to Cloudera QuickStart VM OR Gateway node of hadoop cluster
    ~~~
    asus@asus-GL553VD:~$ scp /home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/word/bible.txt cloudera@192.168.211.142:/home/cloudera/bible.txt
    ~~~
    
    * Login to Quick Start VM or Gateway node of hadoop cluster using ssh & verify copied files
    ~~~
    asus@asus-GL553VD:~$ ssh cloudera@192.168.211.142
    cloudera@192.168.211.142's password: 
    Last login: Sat Dec 23 01:30:00 2017 from 192.168.211.1

    [cloudera@quickstart ~]$ ls -ltr core-api-features_2.10-0.1.jar
    -rw-rw-r-- 1 cloudera cloudera 196861582 Dec 23 01:24 core-api-features_2.10-0.1.jar

    [cloudera@quickstart ~]$ ls -ltr bible.txt
    -rw-rw-r-- 1 cloudera cloudera 323791282 Dec 23 01:29 bible.txt
    ~~~
    
    * Copy text files folder from QuickStart VM or Gateway node to HDFS
    ~~~
    [cloudera@quickstart ~]$ hadoop fs -mkdir /user/cloudera/word
    
    [cloudera@quickstart ~]$ hadoop fs -put /home/cloudera/bible.txt /user/cloudera/word
    
    [cloudera@quickstart ~]$ hadoop fs -ls /user/cloudera/word
    Found 1 items
    -rw-r--r--   1 cloudera cloudera  323791282 2017-12-23 01:33 /user/cloudera/word/bible.txt

    [cloudera@quickstart ~]$ hdfs fsck /user/cloudera/word/bible.txt -files -blocks -locations
    Connecting to namenode via http://quickstart.cloudera:50070/fsck?ugi=cloudera&files=1&blocks=1&locations=1&path=%2Fuser%2Fcloudera%2Fword%2Fbible.txt
    FSCK started by cloudera (auth:SIMPLE) from /127.0.0.1 for path /user/cloudera/word/bible.txt at Sat Dec 23 03:48:07 PST 2017
    /user/cloudera/word/bible.txt 323791282 bytes, 3 block(s):  OK
    0. BP-1028023124-127.0.0.1-1500470886981:blk_1073743120_2299 len=134217728 Live_repl=1 [DatanodeInfoWithStorage[127.0.0.1:50010,DS-8e0b541e-8cba-4475-87f2-c3b9bd3de801,DISK]]
    1. BP-1028023124-127.0.0.1-1500470886981:blk_1073743121_2300 len=134217728 Live_repl=1 [DatanodeInfoWithStorage[127.0.0.1:50010,DS-8e0b541e-8cba-4475-87f2-c3b9bd3de801,DISK]]
    2. BP-1028023124-127.0.0.1-1500470886981:blk_1073743122_2301 len=55355826 Live_repl=1 [DatanodeInfoWithStorage[127.0.0.1:50010,DS-8e0b541e-8cba-4475-87f2-c3b9bd3de801,DISK]]

    Status: HEALTHY
     Total size:	323791282 B
     Total dirs:	0
     Total files:	1
     Total symlinks:		  0
     Total blocks (validated):	  3 (avg. block size 107930427 B)
     Minimally replicated blocks: 3 (100.0 %)
     Over-replicated blocks:	  0 (0.0 %)
     Under-replicated blocks:	  0 (0.0 %)
     Mis-replicated blocks:	  0 (0.0 %)
     Default replication factor:  1
     Average block replication:	  1.0
     Corrupt blocks:		  0
     Missing replicas:		  0 (0.0 %)
     Number of data-nodes:	  1
     Number of racks:		  1
    FSCK ended at Sat Dec 23 03:48:07 PST 2017 in 1 milliseconds


    The filesystem under path '/user/cloudera/word/bible.txt' is HEALTHY
    ~~~

    * Refer below command to execute spark application
    ~~~
    [cloudera@quickstart ~]$ spark-submit \
      --master yarn \
      --class _3_application._3_1_word_count.WordCount \
      --name "Word Count - demo application on YARN" \
      --conf spark.ui.port=54321 \
      --num-executors 1 \
      --executor-memory 512M \
      --executor-cores 2 \
      /home/cloudera/core-api-features_2.10-0.1.jar \
      /user/cloudera/word/bible.txt \
      /user/cloudera/word_output \
      prd
    ~~~

    * Refer below screenshot
    
    ![Alt text](_images/yarn/1.png?raw=true "YARN - Word Count - In Progress")
    
    ![Alt text](_images/yarn/2.png?raw=true "YARN - Word Count - Finished")

    ![Alt text](_images/yarn/3.png?raw=true "YARN - Word Count - Job Summary")
    
    ![Alt text](_images/yarn/4.png?raw=true "YARN - Word Count - Job Details")
    
    ![Alt text](_images/yarn/5.png?raw=true "YARN - Word Count - Stage 0")
    
    ![Alt text](_images/yarn/6.png?raw=true "YARN - Word Count - Stage 1")

* **Explore Spark Web UI:**
  * Job
  * Stages
    * Aggregated Metrics by Executor
    * Tasks
  * Environment
  * Executors
