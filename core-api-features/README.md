## Spark Core
TODO

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

## Spark on YARN (typical production mode)

* If spark cluster comes as part of Cloudera OR Hortonworks OR MapR OR any major distribution, it usually integrated with YARN and HDFS

* In YARN...
  * Driver program will be submitted on gateway machine/node
  * Cluster Manager is Resource Manager
  * There will be Application Master per submission
  * Worker machines/nodes are nothing but servers on which Data Node & Node Manager processes are running

![Alt text](_images/spark-architecture-on-YARN.png?raw=true "Spark Architecture On YARN")

### HDFS (Hadoop Distributed File System) - Recap

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

### YARN (Yet Another Resource Navigator) - Recap

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

### HDFS & YARN - Consolidated View

![Alt text](_images/hdfs-yarn-consolidated-view.png?raw=true "HDFS & YARN - Consolidated View")

### Spark Memory Model

![Alt text](_images/spark-memory-usage.jpg?raw=true "Spark Memory Usage")
