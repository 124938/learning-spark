## Cloudera QuickStart VM - Spark History Server

### Spark History Server - Configuration Changes
* **Step-1 : Stop the Spark History Server:**
~~~
[cloudera@quickstart ~]$ sudo service spark-history-server stop
~~~

* **Step-2 : Set ownership and permissions on /user/spark/applicationHistory directory in HDFS and  as follows:**
~~~
[cloudera@quickstart ~]$ sudo -u hdfs hadoop fs -chown -R spark:spark /user/spark
[cloudera@quickstart ~]$ sudo -u hdfs hadoop fs -chmod 1777 /user/spark/applicat Resource Manager Resource ManagerionHistory
~~~

* **Step-3 : Add following lines to /etc/spark/conf/spark-defaults.conf file to enable spark events log:**
~~~
spark.eventLog.enabled true
spark.eventLog.dir hdfs://quickstart.cloudera:8020/user/spark/applicationHistory
~~~
      
* **Step-4 : Add below line to /etc/spark/conf/spark-defaults.conf file to link YARN ResourceManager directly to the Spark History Server:**
~~~
spark.yarn.historyServer.address http://quickstart.cloudera:18088
~~~

* **Step-5 : Start the Spark History Server:**
~~~
[cloudera@quickstart ~]$ sudo service spark-history-server start
~~~

### Verify Spark Application
* **Step-1: Start `spark-shell` in YARN mode:**
~~~
[cloudera@quickstart conf]$ spark-shell --master yarn --num-executors 1
Setting default log level to "WARN".
To adjust logging level use sc.setLogLevel(newLevel).
Welcome to
      ____              __
     / __/__  ___ _____/ /__
    _\ \/ _ \/ _ `/ __/  '_/
   /___/ .__/\_,_/_/ /_/\_\   version 1.6.0
      /_/

Using Scala version 2.10.5 (Java HotSpot(TM) 64-Bit Server VM, Java 1.7.0_67)
Type in expressions to have them evaluated.
Type :help for more information.
17/12/19 05:13:57 WARN util.NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
17/12/19 05:13:57 WARN util.Utils: Your hostname, quickstart.cloudera resolves to a loopback address: 127.0.0.1; using 192.168.211.142 instead (on interface eth1)
17/12/19 05:13:57 WARN util.Utils: Set SPARK_LOCAL_IP if you need to bind to another address
17/12/19 05:13:59 WARN shortcircuit.DomainSocketFactory: The short-circuit local reads feature cannot be used because libhadoop cannot be loaded.
Spark context available as sc (master = yarn-client, app id = application_1513682465599_0015).
SQL context available as sqlContext.

~~~

* **Step-2: Verify UI of YARN Resource Manager:**

![Alt text](_images/yarn-resource-manager-ui.png?raw=true "YARN - Resource Manager - UI")

* **Step-3: Verify UI of HDFS Name Node:**

![Alt text](_images/hdfs-name-node-ui.png?raw=true "HDFS - Name Node -UI")

* **Step-4: Verify UI of Spark Application:**

![Alt text](_images/spark-application-master-ui.png?raw=true "Spark - Application Master UI")

* **Step-5: Verify UI of Spark History Server:**

![Alt text](_images/spark-history-server-ui.png?raw=true "Spark - History Server UI")


