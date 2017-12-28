## Spark

### Introduction

* As against a common belief... 
  * Spark is NOT a modified version of Hadoop 
  * Spark is NOT really dependent on Hadoop because it has its own cluster management (Hadoop is just one of the ways to implement Spark)
  * Spark is NOT programming language

* **What is Spark**?
  * Apache Spark is a cluster computing platform designed to be fast and general-purpose
  * Spark provides API to create distributed application for processing data in distributed fashion

* **Evolution:**
  * Spark is one of Hadoop’s sub project developed in 2009 in UC Berkeley’s AMPLab by Matei Zaharia.
  * It was Open Sourced in 2010 under a BSD license.
  * It was donated to Apache software foundation in 2013, and now Apache Spark has become a top level Apache project from Feb-2014.

* **Features:** Apache Spark has following features:
  * _Speed_
    * Spark helps to run an application in Hadoop cluster, up to 100 times faster in memory, and 10 times faster when running on disk. 
    * It is possible by reducing number of read/write operations to disk. 
    * It stores the intermediate processing data in memory.
  * _Supports multiple languages_
    * Spark provides built-in APIs in Java, Scala, or Python. Therefore, you can write applications in different languages. 
  * _Advanced Analytics_
    * Spark not only supports ‘Map’ and ‘reduce’. 
    * It also supports SQL queries, Streaming data, Machine learning (ML), and Graph algorithms.

### Architectural Overview

* **Spark Stack:**
  * _Spark Core_
    * Heart of the Spark architecture is core engine of Spark, commonly referred as spark-core, which forms the foundation of this powerful architecture.
    * Spark core provides services such as managing the memory pool, scheduling of tasks on the cluster, recovering failed jobs, and providing support to work with a wide variety of storage systems such as HDFS, S3, and so on.
    * Spark Core is also home to the API that defines resilient distributed datasets (RDDs), which are Spark’s main programming abstraction.
  * _Spark SQL_
    * Spark SQL is Spark’s package for working with structured data. 
    * It allows querying data via SQL as well as the Apache Hive variant of SQL—called the Hive Query Language (HQL)—and it supports many sources of data, including Hive tables, Parquet, and JSON.
  * _Spark Streaming_
    * Spark Streaming is a Spark component that enables processing of live streams of data.
  * _MLlib_
    * Spark comes with a library containing common machine learning (ML) functionality, called MLlib
    * MLlib provides multiple types of machine learning algorithms, including classification, regression, clustering, and collaborative filtering, as well as supporting functionality such as model evaluation and data import.
  * _GraphX_
    * GraphX is a library for manipulating graphs (e.g., a social network’s friend graph) and performing graph-parallel computations.
    * GraphX also provides various operators for manipulating graphs (e.g., subgraph and mapVertices) and a library of common graph algorithms (e.g., PageRank and triangle counting).

  ![Alt text](_images/spark-stack-diagram.png?raw=true "Spark Stack")

* **Development Language Support:**
  * Comprehensive support for the development languages with which developers are already familiar is important so that Spark can be leaned relatively easy and incorporated into existing application as straight forward as possible
  * Programming languages supported by Spark includes:
    * Scala
    * Java
    * Python
    * SQL
    * R

* **Storage Options:**
  * Spark mostly linked with HDFS, but it can be integrated with range of commercial or open source third party data storage system including
    * Apache Hadoop (HDFS, HBase, Hive)
    * Apache Cassandra
    * Amazon S3
    * Google Cloud
    * MapR (file system and database)

* **Deployment Options:**
  * Spark is easy to download and install on laptop or virtual machine (as mentioned in setting up spark section)
  * But for production workloads that are operating at scale, spark support following clusters:
    * Standalone
    * YARN
    * Mesos
    * Amazon EC2
    
* **Spark Architecture OR Execution Model:**
  * Spark follows a master/worker architecture
  * There is a driver that talks to a single coordinator called master that manages workers in which executor runs
  * The driver and executors runs in their own Java processes

  ![Alt text](_images/spark-architecture-high-level-view.png?raw=true "Spark Architecture")

## Spark - Installation (On local machine)

### Pre-Requisite:
  
* 64 bit OD

* 4 GB RAM

* Make sure to have scala configured

### Setup:
  
* Download apache spark gzip file from https://spark.apache.org/downloads.html
  
* Refer below command to unzip downloaded file
  * `tar -xvf spark-1.6.3-bin-hadoop2.6.tgz`
  
* Create environment variable called SPARK_HOME
  * `SPARK_HOME=/path/to/spark-1.6.3-bin-hadoop2.6`
  
* Update environment variable called PATH 
  * `PATH=$PATH:$SPARK_HOME/bin:$SPARK_HOME/sbin`

### Configuration:

* `$SPARK_HOME/conf` OR `/etc/spark/conf` contains following important configuration files:
  * `spark-defaults.conf` => Config file contains default parameters to be used while starting Spark application
  * `spark-env.sh` => Script contains environment variable to conrol run time behavior or Spark application
  * `hive-site.xml` => Should be present to create SQL Context to interact with hive

### Executable:

* `$SPARK_HOME/bin` contains following important binaries:
  * `spark-shell` => To launch spark shell using scala  
  * `pyspark` => To launch spark shell using python  
  * `spark-submit` => To submit spark application 
  * Many more...
  
* `$SPARK_HOME/sbin` contains following important binaries:
  * `start-master` => To start master of standalone cluster using 
  * `start-slave` => To start slave of standalone cluster using 
  * Many more...

### Verification:

* Refer below snippet to launch `spark-shell` on terminal

~~~
asus@asus-GL553VD:$ spark-shell

To adjust logging level use sc.setLogLevel("INFO")
Welcome to
      ____              __
     / __/__  ___ _____/ /__
    _\ \/ _ \/ _ `/ __/  '_/
   /___/ .__/\_,_/_/ /_/\_\   version 1.6.3
      /_/

Using Scala version 2.10.5 (OpenJDK 64-Bit Server VM, Java 1.8.0_131)
Type in expressions to have them evaluated.
Type :help for more information.
17/10/08 17:42:30 WARN Utils: Your hostname, asus-GL553VD resolves to a loopback address: 127.0.1.1; using 192.168.0.100 instead (on interface enp3s0)
17/10/08 17:42:30 WARN Utils: Set SPARK_LOCAL_IP if you need to bind to another address
Spark context available as sc.

scala> println("Hello world on spark REPL")
Hello world on spark REPL
~~~

## Launch Spark Shell

Spark provides supports of following execution modes:

### (1) Local Mode:

To be used by developer while doing development of spark application on local machine (default mode on local machine)

* **Launch spark shell in local mode using `spark-shell --master local[*]`**

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
~~~

~~~
scala> sc.getConf.getAll.foreach(println)
(spark.app.name,Spark shell)
(spark.driver.host,192.168.0.100)
(spark.repl.class.uri,http://192.168.0.100:38903)
(spark.app.id,local-1512833126267)
(spark.jars,)
(spark.master,local[*])
(spark.executor.id,driver)
(spark.submit.deployMode,client)
(spark.driver.port,34590)
(spark.externalBlockStore.folderName,spark-611ad3b6-d2dc-443c-b2dc-de8f2ea76fb3)
~~~

* **Monitor Spark UI for local mode (Refer below screenshot)**
    
![Alt text](_images/exec_mode/local/1.png?raw=true "Spark UI - Local Mode")

### (2) Standalone Mode: 

Recommended mode to be used by developer before deploying application to production (binaries available within spark distribution)

* **Start Master using `start-master.sh`**

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

* **Start Slave using `start-slave.sh spark://asus-GL553VD:7077`**
  
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

* **Launch spark shell in standalone mode using `spark-shell --master spark://asus-GL553VD:7077`**

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
17/12/09 20:39:51 WARN Utils: Your hostname, asus-GL553VD resolves to a loopback address: 127.0.1.1; using 192.168.0.100 instead (on interface enp3s0)
17/12/09 20:39:51 WARN Utils: Set SPARK_LOCAL_IP if you need to bind to another address
Spark context available as sc.
SQL context available as sqlContext.
~~~

~~~
scala> sc.getConf.getAll.foreach(println)
(spark.repl.class.uri,http://192.168.0.100:42838)
(spark.app.name,Spark shell)
(spark.driver.port,35794)
(spark.driver.host,192.168.0.100)
(spark.master,spark://asus-GL553VD:7077)
(spark.jars,)
(spark.app.id,app-20171209203952-0000)
(spark.executor.id,driver)
(spark.submit.deployMode,client)
(spark.externalBlockStore.folderName,spark-f247dc69-34e2-4b19-8a16-b10e36cf65af)
~~~~
    
* **Monitor Spark UI for Standalone cluster (Refer below screenshot)**

![Alt text](_images/exec_mode/standalone/1.png?raw=true "Spark UI - Standalone Mode")

### (3) YARN Mode: 

Default mode in popular distribution like Cloudera, Hortonworkds, MapR etc.
    
* **Pre-Requisite:**
  * Cloudera QuickStart VM should be up & running (Click [here](https://github.com/124938/learning-hadoop-vendors/tree/master/cloudera/_1_quickstart_vm/README.md) to know more details on it)
  * Make sure to configure Retail DataSet setup (Click [here](https://github.com/124938/learning-hadoop-vendors/tree/master/cloudera/_1_quickstart_vm/_1_1_retail_dataset_setup) to know more details on it)
  * Make sure to configure Spark History Server (Click [here](https://github.com/124938/learning-hadoop-vendors/tree/master/cloudera/_1_quickstart_vm/_1_2_spark_history_server_setup/README.md) to know more details on it)
    
* **Login to Quick Start VM or gateway node of hadoop cluster using `ssh cloudera@192.168.211.142`**
    
~~~
asus@asus-GL553VD:~$ ssh cloudera@192.168.211.142
cloudera@192.168.211.142's password: 
Last login: Sun Oct 29 18:49:10 2017 from 192.168.211.1
[cloudera@quickstart ~]$
~~~
    
* **Launch Spark Shell in YARN mode using `spark-shell --master yarn --num-executors 1 --conf spark.ui.port=56123`**

~~~
[cloudera@quickstart ~]$ spark-shell --master yarn --num-executors 1
Setting default log level to "WARN".
To adjust logging level use sc.setLogLevel(newLevel).
SLF4J: Class path contains multiple SLF4J bindings.
SLF4J: Found binding in [jar:file:/usr/lib/zookeeper/lib/slf4j-log4j12-1.7.5.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: Found binding in [jar:file:/usr/lib/flume-ng/lib/slf4j-log4j12-1.7.5.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: Found binding in [jar:file:/usr/lib/parquet/lib/slf4j-log4j12-1.7.5.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: Found binding in [jar:file:/usr/lib/avro/avro-tools-1.7.6-cdh5.12.0.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: See http://www.slf4j.org/codes.html#multiple_bindings for an explanation.
SLF4J: Actual binding is of type [org.slf4j.impl.Log4jLoggerFactory]
Welcome to
      ____              __
     / __/__  ___ _____/ /__
    _\ \/ _ \/ _ `/ __/  '_/
   /___/ .__/\_,_/_/ /_/\_\   version 1.6.0
      /_/

Using Scala version 2.10.5 (Java HotSpot(TM) 64-Bit Server VM, Java 1.7.0_67)
Type in expressions to have them evaluated.
Type :help for more information.
17/12/12 18:11:06 WARN util.NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
17/11/12 18:11:06 WARN util.Utils: Your hostname, quickstart.cloudera resolves to a loopback address: 127.0.0.1; using 192.168.211.142 instead (on interface eth1)
17/11/12 18:11:06 WARN util.Utils: Set SPARK_LOCAL_IP if you need to bind to another address
17/11/12 18:11:09 WARN shortcircuit.DomainSocketFactory: The short-circuit local reads feature cannot be used because libhadoop cannot be loaded.
Spark context available as sc (master = yarn-client, app id = application_1509278183296_0023).
SQL context available as sqlContext.
~~~
    
~~~
scala> sc.getConf.getAll.foreach(println)
(spark.driver.appUIAddress,http://192.168.211.142:4040)
(spark.master,yarn-client)
(spark.org.apache.hadoop.yarn.server.webproxy.amfilter.AmIpFilter.param.PROXY_HOSTS,quickstart.cloudera)
(spark.executor.id,driver)
(spark.repl.class.outputDir,/tmp/spark-721a484f-dce3-4880-85dc-306d36044585/repl-69794e3a-faf1-46f8-badb-325e0f285264)
(spark.app.name,Spark shell)
(spark.driver.host,192.168.211.142)
(spark.jars,)
(spark.submit.deployMode,client)
(spark.org.apache.hadoop.yarn.server.webproxy.amfilter.AmIpFilter.param.PROXY_URI_BASES,http://quickstart.cloudera:8088/proxy/application_1512816333799_0001)
(spark.app.id,application_1512816333799_0001)
(spark.repl.class.uri,spark://192.168.211.142:52257/classes)
(spark.ui.filters,org.apache.hadoop.yarn.server.webproxy.amfilter.AmIpFilter)
(spark.driver.port,52257)
(spark.externalBlockStore.folderName,spark-51c342df-5ea3-474d-9625-b059fd91a4d2)
~~~

* **Monitor Spark UI from YARN (Refer below screenshot)**

![Alt text](_images/exec_mode/yarn/1.png?raw=true "Resource Manager - YARN")

![Alt text](_images/exec_mode/yarn/2.png?raw=true "Spark UI - YARN Mode")

### (4) Mesos Mode: 

* It's used in exclusive spark cluster
  * Out of scope

### _Note:_

* Below API should get used to find out all configured parameter spark shell launched with
  * `scala> sc.getConf.getAll.foreach(println)`
  
* Below are few important configuration parameters
  * `spark.ui.port` => Port number on which spark UI should be accessible
  * `spark.executor.memory` => Size of memory allocated to executor

* By default spark launches with 2 executors
