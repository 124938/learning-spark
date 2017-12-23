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
    * Refer below command
    ~~~
    asus@asus-GL553VD:~$ spark-submit \
      --class _3_application._3_1_word_count.WordCount \
      --name "Word Count - demo application on local" \
      --conf spark.ui.port=54321 \
      /home/asus/source_code/github/124938/learning-spark/core-api-features/target/scala-2.10/core-api-features_2.10-0.1.jar \
      /home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/word/bible.txt \
      /home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/word/output \
      local
    ~~~
  
  * **Standalone mode:** 
    * Refer below command
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
      /home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/word/output \
      dev
    ~~~
    
    * Refer below screenshot
    
    ![Alt text](_images/standalone/1.png?raw=true "Standalone Cluster - Home Page")
    
    ![Alt text](_images/standalone/2.png?raw=true "Standalone Cluster - Word Count - Job Summary")
    
    ![Alt text](_images/standalone/3.png?raw=true "Standalone Cluster - Word Count - Job Details")
    
    ![Alt text](_images/standalone/4.png?raw=true "Standalone Cluster - Word Count - Stage 0")
    
    ![Alt text](_images/standalone/5.png?raw=true "Standalone Cluster - Word Count - Stage 1")
    
    
  * **YARN mode:**
    * Copy JAR file from local machine to Cloudera QuickStart VM or Gateway node using below command
    ~~~
    asus@asus-GL553VD:~$ scp /home/asus/source_code/github/124938/learning-spark/core-api-features/target/scala-2.10/core-api-features_2.10-0.1.jar cloudera@192.168.211.142:/home/cloudera/core-api-features_2.10-0.1.jar
    ~~~

    * Copy big text files folder from local machine to Cloudera QuickStart VM or Gateway node using below command
    ~~~
    asus@asus-GL553VD:~$ scp /home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/word/bible.txt cloudera@192.168.211.142:/home/cloudera/word
    ~~~
    
    * Login to Quick Start VM or gateway node of hadoop cluster using ssh & verify copied files
    ~~~
    asus@asus-GL553VD:~$ ssh cloudera@192.168.211.142
    cloudera@192.168.211.142's password: 
    Last login: Sat Dec  9 19:13:35 2017 from 192.168.211.1

    [cloudera@quickstart ~]$ ls -ltr core-api-features_2.10-0.1.jar 
    -rw-rw-r-- 1 cloudera cloudera 2650763 Dec 10 20:05 core-api-features_2.10-0.1.jar

    [cloudera@quickstart ~]$ ls -ltr word
    total 141240
    -rw-rw-r-- 1 cloudera cloudera 72313825 Dec 10 21:16 bible.txt
    ~~~
    
    * Copy text files folder from QuickStart VM or Gateway node to HDFS
    ~~~
    [cloudera@quickstart ~]$ hadoop fs -put /home/cloudera/word /user/cloudera/word
    
    [cloudera@quickstart ~]$ hadoop fs -ls /user/cloudera/word
    Found 1 item
    -rw-r--r--   1 cloudera cloudera   72313825 2017-12-10 21:24 /user/cloudera/word/bible.txt
    ~~~

    * Refer below command
    ~~~
    [cloudera@quickstart ~]$ spark-submit \
      --master yarn \
      --class _3_application._3_1_word_count.WordCount \
      --name "Word Count - demo application on YARN" \
      --conf spark.ui.port=54321 \
      --num-executors 4 \
      --executor-memory 512M \
      --executor-cores 2 \
      /home/cloudera/core-api-features_2.10-0.1.jar \
      /user/cloudera/word \
      /user/cloudera/word_output \
      prd
    ~~~
  
* **Explore Spark Web UI:**
  * Job
  * Stages
    * Aggregated Metrics by Executor
    * Tasks
  * Environment
  * Executors
