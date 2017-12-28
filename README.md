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

## Getting Started - Using REPL (i.e. Spark Shell)

### Configure REPL

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
    * Launch spark shell using scala using `spark-shell` 
    * Launch spark shell using python using `pyspark` 
    * Submit spark application using `spark-submit`
    * Many more...
  
  * `$SPARK_HOME/sbin` contains following important binaries
    * Start master of standalone cluster using `start-master`
    * Start slave of standalone cluster using `start-slave`
    * Many more...

### Launch REPL

* Open terminal window and execute below command to start Spark Shell

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
~~~

~~~
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

## Getting Started - Using IDE

### Launch IDE

* Create new SBT project called `core-api-features` in IntelliJ Idea/Eclipse

* Update build.sbt file with below spark core dependency

~~~
name := "core-api-features"
version := "0.1"
scalaVersion := "2.10.6"

libraryDependencies += "org.apache.spark" % "spark-core_2.10" % "1.6.3"
~~~

### Launch SBT

* Open terminal window and execute below command to start SBT console

~~~
asus@asus-GL553VD:~$ cd source_code/github/124938/learning-spark/core-api-features/
asus@asus-GL553VD:~/source_code/github/124938/learning-spark/core-api-features$ sbt console
[info] Loading global plugins from /home/asus/.sbt/0.13/plugins
[info] Loading project definition from /home/asus/source_code/github/124938/learning-spark/core-api-features/project
[info] Set current project to core-api-features (in build file:/home/asus/source_code/github/124938/learning-spark/core-api-features/)
[info] Starting scala interpreter...
[info] 
Welcome to Scala version 2.10.6 (OpenJDK 64-Bit Server VM, Java 1.8.0_151).
Type in expressions to have them evaluated.
Type :help for more information.

scala> println("Hello world on spark REPL - Using SBT")
Hello world on spark REPL - Using SBT
~~~

~~~
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
