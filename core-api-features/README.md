## Launching Spark Shell
* **Configuration:**

  * Followings are few important files used to configure Spark located under `/etc/spark/conf` OR `$SPARK_HOME/conf`
    * `spark-defaults.conf` => Contains default parameter available while running spark application
    * `spark-env.sh` => Script contains environment variable to conrol run time behavior or spark application
    * `hive-site.xml` => Should be present to create SQL Context to interact with hive

* **Execution Modes:** Spark provides supports of following execution modes:

  * **Local:** Mode to be used by developer while doing development of spark application on local machine (default mode on local machine)

~~~
asus@asus-GL553VD:~$ spark-shell --master local[*]
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
17/11/09 20:16:34 WARN Utils: Your hostname, asus-GL553VD resolves to a loopback address: 127.0.1.1; using 192.168.0.100 instead (on interface enp3s0)
17/11/09 20:16:34 WARN Utils: Set SPARK_LOCAL_IP if you need to bind to another address
Spark context available as sc.
17/11/09 20:16:35 WARN Connection: BoneCP specified but not present in CLASSPATH (or one of dependencies)
17/11/09 20:16:35 WARN Connection: BoneCP specified but not present in CLASSPATH (or one of dependencies)
17/11/09 20:16:54 WARN ObjectStore: Version information not found in metastore. hive.metastore.schema.verification is not enabled so recording the schema version 1.2.0
17/11/09 20:16:54 WARN ObjectStore: Failed to get database default, returning NoSuchObjectException
17/11/09 20:16:57 WARN Connection: BoneCP specified but not present in CLASSPATH (or one of dependencies)
17/11/09 20:16:57 WARN Connection: BoneCP specified but not present in CLASSPATH (or one of dependencies)
SQL context available as sqlContext.

scala> 
~~~

  * **Stand Alone:** Recommended mode to be used by developer before deploying application to production (binaries available within spark distribution)
    * `start-master.sh` => Start Master
    * `start-slave.sh spark://localhost:7077` => Start Slave
    * `spark-shell --master spark://localhost:7077` => Launch spark shell in standalone mode

  * **YARN:** It's a default mode in popular distribution like Cloudera, Hortonworkds, MapR etc.
    * `ssh cloudera@192.168.211.142` => Login to Quick Start VM or gateway node of hadoop cluster using ssh
    * `spark-shell --master yarn` => Launch spark shell in YARN mode
    * `spark-shell --master yarn --conf spark.ui.port=56123` => With overriding default config parameter

  * **Mesos:** It's used in exclusive spark cluster
    * Out of scope

* _Note:_

  * Below API should get used to find out all configured parameter spark shell launched with
    * `scala> sc.getConf.getAll.foreach(println)`
  * Below are few important configuration parameters
    * `spark.ui.port` => Port number on which spark UI should be accessible
    * `spark.executor.memory` => Size of memory allocated to executor
  * By default spark launches with 2 executors

## Spark on YARN (typical production mode)
* If spark cluster comes as part of Cloudera OR Hortonworks OR MapR OR any major distribution, it usually integrated with YARN and HDFS
* In YARN...
  * Driver program will be submitted on gateway machine/node
  * Cluster Manager is Resource Manager
  * There will be Application Master per submission
  * Worker machines/nodes are nothing but servers on which Data Node & Node Manager processes are running

![Alt text](_images/spark-architecture-on-YARN.png?raw=true "Spark Architecture On YARN")

## Spark Memory Model

![Alt text](_images/spark-memory-usage.jpg?raw=true "Spark Memory Usage")
