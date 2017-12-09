## Launching Spark Shell
* **Configuration:**

  * Followings are few important files used to configure Spark located under `/etc/spark/conf` OR `$SPARK_HOME/conf`
    * `spark-defaults.conf` => Contains default parameter available while running spark application
    * `spark-env.sh` => Script contains environment variable to conrol run time behavior or spark application
    * `hive-site.xml` => Should be present to create SQL Context to interact with hive

* **Execution Modes:** Spark provides supports of following execution modes:

  * **Local:** Mode to be used by developer while doing development of spark application on local machine (default mode on local machine)
    * `spark-shell --master local[*]` => Launch spark shell in local mode

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
    SQL context available as sqlContext.

    scala> 
    ~~~

  * **Stand Alone:** Recommended mode to be used by developer before deploying application to production (binaries available within spark distribution)
    * `start-master.sh` => Start Master

    ~~~
    asus@asus-GL553VD:~$ start-master.sh 
    starting org.apache.spark.deploy.master.Master, logging to /home/asus/tech_soft/spark-1.6.3-bin-hadoop2.6/logs/spark-asus-org.apache.spark.deploy.master.Master-1-asus-GL553VD.out
    
    asus@asus-GL553VD:~$ tail -f /home/asus/tech_soft/spark-1.6.3-bin-hadoop2.6/logs/spark-asus-org.apache.spark.deploy.master.Master-1-asus-GL553VD.out
    17/12/09 20:25:14 INFO SecurityManager: Changing modify acls to: asus
    17/12/09 20:25:14 INFO SecurityManager: SecurityManager: authentication disabled; ui acls disabled; users with view permissions: Set(asus); users with modify permissions: Set(asus)
    17/12/09 20:25:14 INFO Utils: Successfully started service 'sparkMaster' on port 7077.
    17/12/09 20:25:14 INFO Master: Starting Spark master at spark://asus-GL553VD:7077
    17/12/09 20:25:14 INFO Master: Running Spark version 1.6.3
    17/12/09 20:25:14 INFO Utils: Successfully started service 'MasterUI' on port 8080.
    17/12/09 20:25:14 INFO MasterWebUI: Started MasterWebUI at http://192.168.0.100:8080
    17/12/09 20:25:14 INFO Utils: Successfully started service on port 6066.
    17/12/09 20:25:14 INFO StandaloneRestServer: Started REST server for submitting applications on port 6066
    17/12/09 20:25:14 INFO Master: I have been elected leader! New state: ALIVE
    ~~~

    * `start-slave.sh spark://localhost:7077` => Start Slave

    ~~~
    asus@asus-GL553VD:~$ start-slave.sh spark://asus-GL553VD:7077
    starting org.apache.spark.deploy.worker.Worker, logging to /home/asus/tech_soft/spark-1.6.3-bin-hadoop2.6/logs/spark-asus-org.apache.spark.deploy.worker.Worker-1-asus-GL553VD.out
    asus@asus-GL553VD:~$ tail -f /home/asus/tech_soft/spark-1.6.3-bin-hadoop2.6/logs/spark-asus-org.apache.spark.deploy.worker.Worker-1-asus-GL553VD.out
    17/12/09 20:34:30 INFO SecurityManager: Changing modify acls to: asus
    17/12/09 20:34:30 INFO SecurityManager: SecurityManager: authentication disabled; ui acls disabled; users with view permissions: Set(asus); users with modify permissions: Set(asus)
    17/12/09 20:34:30 INFO Utils: Successfully started service 'sparkWorker' on port 35893.
    17/12/09 20:34:30 INFO Worker: Starting Spark worker 192.168.0.100:35893 with 8 cores, 14.6 GB RAM
    17/12/09 20:34:30 INFO Worker: Running Spark version 1.6.3
    17/12/09 20:34:30 INFO Worker: Spark home: /home/asus/tech_soft/spark-1.6.3-bin-hadoop2.6
    17/12/09 20:34:30 INFO Utils: Successfully started service 'WorkerUI' on port 8081.
    17/12/09 20:34:30 INFO WorkerWebUI: Started WorkerWebUI at http://192.168.0.100:8081
    17/12/09 20:34:30 INFO Worker: Connecting to master asus-GL553VD:7077...
    17/12/09 20:34:30 INFO Worker: Successfully registered with master spark://asus-GL553VD:7077
    ~~~
    
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
