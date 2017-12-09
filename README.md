## Spark - Introduction
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

## Spark - Setting up development environment (On local machine)
* **Pre-Requisite**
  * 64 bit OD
  * 4 GB RAM
  * Make sure to have scala configured
* **Setup**
  * Download apache spark gzip file from https://spark.apache.org/downloads.html
  * Unzip downloaded file using below command
    * `tar -xvf spark-1.6.3-bin-hadoop2.6.tgz`
  * Create environment variable called SPARK_HOME
    * `SPARK_HOME=/path/to/spark-1.6.3-bin-hadoop2.6`
  * Update environment variable called PATH 
    * `PATH=$PATH:$SPARK_HOME/bin:$SPARK_HOME/sbin`
  * `$SPARK_HOME/bin` contains following important binaries
    * spark-shell  => To launch spark shell using scala
    * pyspark      => To launch spark shell using python
    * spark-submit => To submit spark application
    * Many more...
  * `$SPARK_HOME/sbin` contains following important binaries
    * start-master => To start master of standalone cluster
    * start-slave  => To start slave of standalone cluster
    * Many more...
* **REPL**
  * Open terminal window and execute below command to start Spark REPL
~~~
$spark-shell

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

scala> sc.getConf.getAll.foreach(println)
(spark.repl.class.uri,http://192.168.0.100:34219)
(spark.externalBlockStore.folderName,spark-2f849430-ad28-4992-8d0a-a5bd3a75db55)
(spark.app.name,Spark shell)
(spark.driver.host,192.168.0.100)
(spark.driver.port,37767)
(spark.jars,)
(spark.master,local[*])
(spark.executor.id,driver)
(spark.submit.deployMode,client)
(spark.app.id,local-1507464750766)
~~~

* **IDE**
  * Create new SBT project called core-api-features in IntelliJ Idea/Eclipse
  * Update build.sbt file with below spark core dependency
~~~
name := "core-api-features"
version := "0.1"
scalaVersion := "2.10.6"

libraryDependencies += "org.apache.spark" % "spark-core_2.10" % "1.6.3"
~~~

* **SBT Console**
  * Open terminal window and execute below command to start SBT console
~~~
$cd /path/to/project
$sbt console

Welcome to Scala version 2.10.6 (OpenJDK 64-Bit Server VM, Java 1.8.0_131).
Type in expressions to have them evaluated.
Type :help for more information.

scala> import org.apache.spark.SparkConf
import org.apache.spark.SparkConf

scala> val conf = new SparkConf().setMaster("local[2]").setAppName("first spark demo")
conf: org.apache.spark.SparkConf = org.apache.spark.SparkConf@47b1c217

scala> import org.apache.spark.SparkContext
import org.apache.spark.SparkContext

scala> val sc = new SparkContext(conf)
sc: org.apache.spark.SparkContext = org.apache.spark.SparkContext@71ea9c44

scala> sc.textFile("/path/to/file.txt").count
res1: Long = 187
~~~

## Spark - Use existing configured environment (Of Cloudera QuickStart VM) 

* **Pre-Requisite**
  * Cloudera QuickStart VM should be up & running

* **REPL**
  * Login to Cloudera QuickStart VM using ssh & start spark shell

~~~
asus@asus-GL553VD:~$ ssh cloudera@192.168.211.142
cloudera@192.168.211.142's password: 
Last login: Sun Oct 29 18:49:10 2017 from 192.168.211.1
[cloudera@quickstart ~]$

[cloudera@quickstart ~]$ spark-shell --master yarn
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
17/11/12 18:11:06 WARN util.NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
17/11/12 18:11:06 WARN util.Utils: Your hostname, quickstart.cloudera resolves to a loopback address: 127.0.0.1; using 192.168.211.142 instead (on interface eth1)
17/11/12 18:11:06 WARN util.Utils: Set SPARK_LOCAL_IP if you need to bind to another address
17/11/12 18:11:09 WARN shortcircuit.DomainSocketFactory: The short-circuit local reads feature cannot be used because libhadoop cannot be loaded.
Spark context available as sc (master = yarn-client, app id = application_1509278183296_0023).
SQL context available as sqlContext.

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

## Spark Architectural Overview

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

## HDFS (Hadoop Distributed File System) - Recap
* **Overview:**
  * HDFS is a hadoop’s storage layer which can store files in range of Terabytes and Petabytes in distributed manner with features like high availability, reliability and fault tolerance
  * HDFS follows master/slave architecture and comprises of following components:
    * NameNode : It is a _deamon process_ runs on master node (Only 1)
    * SecondaryNameNode : It is a _deamon process_ to perform backup and record-keeping functions for the NameNode (typically 1)
    * DataNode : It is a _deamon process_ runs on slave nodes (typically many)
  * **NameNode:**
    * It manages the HDFS filesystem namespace
    * It keeps the record of all the files present in the HDFS
  * **SecondaryNameNode:**
    * It periodically pulls the data from NameNode, so if NameNode goes down we can manually make secondary NN as Namenode
    * One important point, it is not a hot standby of namenode
  * **DataNode:**
    * The function of DataNode is to store data in the HDFS
    * It contains the actual data blocks
    * HDFS cluster usually has more than one DataNodes and data is replicated across the other machines present in the HDFS cluster

* **Configuration:**
  * Followings are important files configured on gateway node (to access hadoop cluster)
    * `/etc/hadoop/conf/core-site.xml`
    * `/etc/hadoop/conf/hdfs-site.xml`
  * Below are few important properties available under `/etc/hadoop/conf/core-site.xml`

  ~~~
  <!-- Below property provides information about namenode's host & port-->
  <property>
      <name>fs.defaultFS</name>
      <value>hdfs://namenode01.domain.com</value>
  </property>
  ~~~

  * Below are few important properties available under `/etc/hadoop/conf/hdfs-site.xml`
    * Block Size - default is 128 Mb (a file of 1.1 GB will be divided into 8 blocks of 128 Mb and 1 block of 100 Mb)
      * Block size makes HDFS distributes
    * Replication Factor - default is 3 (each block will have 3 copies)
      * Replication factor makes HDFS fault tolerant & reliable

  ~~~
  <!-- Below property provides information about default block size -->
  <property>
      <name>dfs.blocksize</name>
      <value>134217728</value>
  </property>

  <!-- Below property provides information about default replication factor -->
  <property>
      <name>dfs.replication</name>
      <value>3</value>
  </property>
  ~~~

* **Architecture:**

![Alt text](_images/hdfs-architecture.jpg?raw=true "HDFS Architecture")

* **Useful Commands:**

_Developer:_

| Command | Description |
| ------- | ------------|
| hadoop version | Print hadoop version |
| hadoop fs | List all the hadoop file system shell commands |
| hadoop fs -help [hdfs_cmd] | Print usage of command in HDFS |
| hadoop fs -ls [hdfs_path] | List the contents of the directory/file in HDFS |
| hadoop fs -ls -h [hdfs_path] | List the contents of the directory/file in HDFS |
| hadoop fs -ls -h -R [hdfs_path] | List the contents of the directory/file recursively in HDFS|
| hadoop fs -copyFromLocal [local_fs_path] [hdfs_path] | Copy directory/file from local file system to HDFS|
| hadoop fs -put [local_fs_path] [hdfs_path] | Same as -copyFromLocal |
| hadoop fs -get [hdfs_path] [local_fs_path] | Copy directory/file from HDFS to local file system |
| hadoop fs -mkdir [hdfs_path] | Create new directory in HDFS |
| hadoop fs -rm [hdfs_path] | Remove existing file from HDFS |
| hadoop fs -rmdir [hdfs_path] | Remove existing directory from HDFS |
| hadoop fs -touchz [hdfs_path] | Create file in HDFS |
| hadoop fs -cat [hdfs_path] | Print content of file from HDFS |
| hadoop fs -tail [hdfs_path] | Print last 1 Kb of content from HDFS file to standard output (typically useful for previewing data from file) |

_Admin:_

| Command | Description |
| ------- | ------------|
| hadoop fs -df -h | Shows the capacity, free and used space of HDFS |
| hadoop fs -du -s -h [hdfs_path] | Prints aggregate length of file from HDFS |
| hdfs fsck [hdfs_path] | Prints meta-data of file stored in HDFS |

_Note:_
* -du => Stands for disk usage
* -h  => It displays the size of file in readable format
* -R  => Stands for recursive 

## YARN (Yet Another Resource Navigator) - Recap
* **Overview:**
  * Hadoop YARN is designed to provide a generic and flexible framework to administer for computing resources in the Hadoop cluster
  * It follows master/slave architecture and comprises of following components:
    * Resource Manager : It is a _deamon process_ runs on master node (typically 1 OR 2)
    * Node Manager : It is a _deamon process_ runs on slave node (typically many)
  * **Node Manager:**
    * Runs on all the slave nodes, which will keep track of resources on individual node
    * It sends information to Resource Manager at regular intervals
  * **Resource Manager:**
    * Runs on master nodes, which will keep track of all the resources on cluster level
    * It receives information from all Node Managers
  * Per job **Application Master:** When JOB is submitted (in context of spark => via spark-shell or spark-submit)
    * Request goes to Resource Manager
    * Resource Manager will pick one node and Application Master will get started from that point onwards
      * Application Master will talk to Node Manager and create executor
      * Each executor will take care of execuring the job in form of tasks
  * **Cluster Manager:**
    * In YARN... **Resource Manager + Application Master** will act as Cluster Manager

* **Configuration:**
  * Followings are important files configured on gateway node (to access hadoop cluster)
    * `/etc/hadoop/conf/yarn-site.xml`
  * Below are few important properties available under `/etc/hadoop/conf/site-site.xml`

  ~~~
  <!-- Resource Manager can be accessible from below URL -->
  <property>
      <name>yarn.resourcemanager.webapp.address</name>
      <value>quickstart.cloudera:8088</value>
  </property>
  
  <!-- Application log location -->
  <property>
    <name>yarn.nodemanager.remote-app-log-dir</name>
    <value>/tmp/logs</value>
  </property>
  ~~~

* **Architecture:**

![Alt text](_images/yarn-architecture.png?raw=true "YARN Architecture")

* **Useful Commands:**

| Command | Description |
| ------- | ------------|
| yarn version | Print YARN version |
| yarn | Print list of commands supported by YARN |
| yarn application | Print list of sub-commands supported by application command |
| yarn application --list | List all applications running under YARN |
| yarn application --kill <application_id> | Kill application running under YARN |
| yarn application --status <application_id> | Prints the status of application |

## HDFS & YARN - Consolidated View

![Alt text](_images/hdfs-yarn-consolidated-view.png?raw=true "HDFS & YARN - Consolidated View")


