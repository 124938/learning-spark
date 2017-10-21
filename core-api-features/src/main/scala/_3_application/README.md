## Spark Application

### Development Life Cycle:

* **Start With REPL**
  * Start spark-shell i.e. REPL in standalone mode
    * `start-master.sh` => Start Master
    * `start-slave.sh spark://localhost:7077` => Start Slave
    * `spark-shell --master spark://localhost:7077` => Launch spark shell in standalone mode
  * Create Word Count program - Refer below code

  ~~~
  scala> val lines = sc.textFile("/home/asus/tech_soft/spark-1.6.3-bin-hadoop2.6/README.md")
  lines: org.apache.spark.rdd.RDD[String] = /home/asus/tech_soft/spark-1.6.3-bin-hadoop2.6/README.md MapPartitionsRDD[207] at textFile at <console>:27

  scala> val words = lines.flatMap((line: String) => line.split(" ").map((word: String) => (word.toLowerCase, 1)))
  words: org.apache.spark.rdd.RDD[(String, Int)] = MapPartitionsRDD[211] at flatMap at <console>:29

  scala> words.reduceByKey(_ + _).take(10).foreach(println)
  (scala,2)
  (package,1)
  (this,3)
  (hive,2)
  (its,1)

  ~~~
  
* **Use IDE for development**
  * Create Scala project (using SBT) under IntelliJ Idea
  * Add following dependencies to build.sbt file
  * Create Word Count program
  
  ~~~
  libraryDependencies += "com.typesafe" % "config" % "1.3.1"
  libraryDependencies += "org.apache.spark" % "spark-core_2.10" % "1.6.3"
  ~~~
  
* **Use SBT to build artifact**
  * Execute below SBT command to build JAR file
  
  ~~~
  $cd /path/to/project
  sbt package
  ~~~

### Execution Life Cycle:

* **Copy JAR file** 
  * To quick start VM OR gateway node of cluster using below command

   ~~~
   ~~~

* **Usage of spark-submit**:

   ~~~
    $ spark-submit
    Usage: spark-submit [options] <app jar | python file> [app arguments]
    Usage: spark-submit --kill [submission ID] --master [spark://...]
    Usage: spark-submit --status [submission ID] --master [spark://...]
    
    Options:
      --master MASTER_URL         spark://host:port, mesos://host:port, yarn, or local.
      --deploy-mode DEPLOY_MODE   Whether to launch the driver program locally ("client") or
                                  on one of the worker machines inside the cluster ("cluster")
                                  (Default: client).
      --class CLASS_NAME          Your application's main class (for Java / Scala apps).
      --name NAME                 A name of your application.
      --jars JARS                 Comma-separated list of local jars to include on the driver
                                  and executor classpaths.
      --packages                  Comma-separated list of maven coordinates of jars to include
                                  on the driver and executor classpaths. Will search the local
                                  maven repo, then maven central and any additional remote
                                  repositories given by --repositories. The format for the
                                  coordinates should be groupId:artifactId:version.
      --exclude-packages          Comma-separated list of groupId:artifactId, to exclude while
                                  resolving the dependencies provided in --packages to avoid
                                  dependency conflicts.
      --repositories              Comma-separated list of additional remote repositories to
                                  search for the maven coordinates given with --packages.
      --py-files PY_FILES         Comma-separated list of .zip, .egg, or .py files to place
                                  on the PYTHONPATH for Python apps.
      --files FILES               Comma-separated list of files to be placed in the working
                                  directory of each executor.
    
      --conf PROP=VALUE           Arbitrary Spark configuration property.
      --properties-file FILE      Path to a file from which to load extra properties. If not
                                  specified, this will look for conf/spark-defaults.conf.
    
      --driver-memory MEM         Memory for driver (e.g. 1000M, 2G) (Default: 1024M).
      --driver-java-options       Extra Java options to pass to the driver.
      --driver-library-path       Extra library path entries to pass to the driver.
      --driver-class-path         Extra class path entries to pass to the driver. Note that
                                  jars added with --jars are automatically included in the
                                  classpath.
    
      --executor-memory MEM       Memory per executor (e.g. 1000M, 2G) (Default: 1G).
    
      --proxy-user NAME           User to impersonate when submitting the application.
    
      --help, -h                  Show this help message and exit
      --verbose, -v               Print additional debug output
      --version,                  Print the version of current Spark
    
     Spark standalone with cluster deploy mode only:
      --driver-cores NUM          Cores for driver (Default: 1).
    
     Spark standalone or Mesos with cluster deploy mode only:
      --supervise                 If given, restarts the driver on failure.
      --kill SUBMISSION_ID        If given, kills the driver specified.
      --status SUBMISSION_ID      If given, requests the status of the driver specified.
    
     Spark standalone and Mesos only:
      --total-executor-cores NUM  Total cores for all executors.
    
     Spark standalone and YARN only:
      --executor-cores NUM        Number of cores per executor. (Default: 1 in YARN mode,
                                  or all available cores on the worker in standalone mode)
    
     YARN-only:
      --driver-cores NUM          Number of cores used by the driver, only in cluster mode
                                  (Default: 1).
      --queue QUEUE_NAME          The YARN queue to submit to (Default: "default").
      --num-executors NUM         Number of executors to launch (Default: 2).
      --archives ARCHIVES         Comma separated list of archives to be extracted into the
                                  working directory of each executor.
      --principal PRINCIPAL       Principal to be used to login to KDC, while running on
                                  secure HDFS.
      --keytab KEYTAB             The full path to the file that contains the keytab for the
                                  principal specified above. This keytab will be copied to
                                  the node running the Application Master via the Secure
                                  Distributed Cache, for renewing the login tickets and the
                                  delegation tokens periodically.
   ~~~

* **Execute Application:**
  * _In local mode_: Use below command...
  ~~~
  spark-submit \
    --class _3_application.WordCount \
    --name "Word Count - demo application on local" \
    --conf spark.ui.port=54321 \
    /home/asus/source_code/github/124938/learning-spark/core-api-features/target/scala-2.10/core-api-features_2.10-0.1.jar \
    /home/asus/tech_soft/spark-1.6.3-bin-hadoop2.6/README.md \
    /home/asus/tech_soft/spark-1.6.3-bin-hadoop2.6/output_1 \
    local
  ~~~
  
  * _In standalone mode_: Use below command...
  ~~~
  mkdir /tmp/spark-events
  spark-submit \
    --class _3_application.WordCount \
    --name "Word Count - demo application on stand alone" \
    --conf spark.ui.port=54321 \
    --conf spark.eventLog.enabled=true \
    --executor-memory 512M \
    /home/asus/source_code/github/124938/learning-spark/core-api-features/target/scala-2.10/core-api-features_2.10-0.1.jar \
    /home/asus/tech_soft/spark-1.6.3-bin-hadoop2.6/README.md \
    /home/asus/tech_soft/spark-1.6.3-bin-hadoop2.6/output_1 \
    dev
  ~~~
    
  * _In YARN mode_: Use below command...
  ~~~
  spark-submit \
    --class _3_application.WordCount \
    --name "Word Count - demo application on YARN" \
    --conf spark.ui.port=54321 \
    --num-executors 4 \
    --executor-memory 512M \
    /home/cloudera/core-api-features_2.10-0.1.jar \
    /user/cloudera/random-words-input \
    /user/cloudera/random-words-output \
    prd
  ~~~
  
* **Explore Spark Web UI:**
  * Job
  * Stages
    * Aggregated Metrics by Executor
    * Tasks
  * Environment
  * Executors