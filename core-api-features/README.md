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

    * Monitor Spark UI for local mode (Refer below screenshot)
      
    ![Alt text](_images/spark-ui-local-mode.png?raw=true "Spark UI - Local Mode")

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

    * `start-slave.sh spark://asus-GL553VD:7077` => Start Slave
    
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

    * `spark-shell --master spark://asus-GL553VD:7077` => Launch spark shell in standalone mode
    
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
    
    * Monitor Spark UI for Standalone cluster (Refer below screenshot)

    ![Alt text](_images/spark-ui-standalone-mode.png?raw=true "Spark UI - Standalone Mode")

  * **YARN:** It's a default mode in popular distribution like Cloudera, Hortonworkds, MapR etc.
    
    * **Pre-Requisite:**
      * Cloudera QuickStart VM should be up & running (Click [here](_quickstart-vm-setup/cloudera/README.md) to configure Cloudera QuickStart VM)
      * Make sure to configure Spark history server (Click [here](_quickstart-vm-setup/cloudera/spark_history_server_setup/README.md) to configure Spark history server)
    
    * `ssh cloudera@192.168.211.142` => Login to Quick Start VM or gateway node of hadoop cluster using ssh
    
    ~~~
    asus@asus-GL553VD:~$ ssh cloudera@192.168.211.142
    cloudera@192.168.211.142's password: 
    Last login: Sun Oct 29 18:49:10 2017 from 192.168.211.1
    [cloudera@quickstart ~]$
    ~~~
    
    * `spark-shell --master yarn --num-executors 1` => Launch spark shell in YARN mode
    
    * `spark-shell --master yarn --num-executors 1 --conf spark.ui.port=56123` => With overriding default config parameter
    
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

    * Monitor Spark UI from YARN (Refer below screenshot)

    ![Alt text](_images/yarn-resource-manager.png?raw=true "YARN - Resource Manager")

    ![Alt text](_images/spark-ui-yarn-mode.png?raw=true "Spark UI - From YARN")

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


## Spark Memory Model

![Alt text](_images/spark-memory-usage.jpg?raw=true "Spark Memory Usage")
