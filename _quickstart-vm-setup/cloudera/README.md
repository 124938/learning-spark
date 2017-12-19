## Cloudera QuickStart VM - Setup

### Setup/Configuration:
* Download VMWare workstation player from below URL
  * `https://www.vmware.com/products/workstation-player/workstation-player-evaluation.html`
* Download latest version of Cloudera QuickStart VM from below URL
  * `https://www.cloudera.com/downloads.html`
* Configure/Start Cloudera QuickStart VM under VMWare player by allocating below minimum required resources to VM
  * 8 GB of RAM 
  * 2 Cores/CPU
* Explore Cloudera QuickStart VM by referring below screenshots:

![Alt text](_images/vmware_start.png?raw=true "Start - VMWare")

![Alt text](_images/vmware_quickstart_cloudera_open.png?raw=true "Open Cloudera Quickstart VM")

![Alt text](_images/vmware_quickstart_cloudera_settings.png?raw=true "Settings for Cloudera Quickstart VM")

![Alt text](_images/vmware_quickstart_cloudera_home.png?raw=true "Home for Cloudera Quickstart VM")

* Find out IP Address of configured Cloudera Quickstart VM by referring below screenshot and command `ip addr` on terminal

![Alt text](_images/vmware_quickstart_cloudera_ip.png?raw=true "Cloudera Quickstart VM - Find out IP Address")

### Verification:

* **Login To VM:** Refer below command to login to Cloudera Quickstart VM using IP address
~~~
asus@asus-GL553VD:~$ ssh cloudera@192.168.211.142
cloudera@192.168.211.142's password: 
Last login: Sun Nov 26 17:58:32 2017 from 192.168.211.1
[cloudera@quickstart ~]$
~~~
 
* **MySQL:** Verify MySQL installation on Cloudera QuickStart VM 
~~~
[cloudera@quickstart ~]$ mysql -h localhost -P 3306 -u root -p
Enter password: 
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 1063
Server version: 5.1.73 Source distribution

Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.

Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

mysql> show databases;
+--------------------+
| Database           |
+--------------------+
| information_schema |
| cm                 |
| firehose           |
| hue                |
| metastore          |
| mysql              |
| nav                |
| navms              |
| oozie              |
| retail_db          |
| rman               |
| sentry             |
+--------------------+
12 rows in set (0.07 sec)

mysql> exit;
Bye

[cloudera@quickstart ~]$ mysql -h localhost -P 3306 -u retail_dba -p
Enter password: 
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 1064
Server version: 5.1.73 Source distribution

Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.

Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

mysql> show databases;
+--------------------+
| Database           |
+--------------------+
| information_schema |
| retail_db          |
+--------------------+
2 rows in set (0.04 sec)

mysql> use retail_db;
Reading table information for completion of table and column names
You can turn off this feature to get a quicker startup with -A

Database changed
mysql> show tables;
+---------------------+
| Tables_in_retail_db |
+---------------------+
| categories          |
| customers           |
| departments         |
| order_items         |
| orders              |
| products            |
+---------------------+
6 rows in set (0.00 sec)

mysql> exit;
Bye
[cloudera@quickstart ~]$ 
~~~

* **HDFS:** Verify HDFS installation on Cloudera QuickStart VM using below commands 
~~~
[cloudera@quickstart ~]$ hadoop version
Hadoop 2.6.0-cdh5.12.0
Subversion http://github.com/cloudera/hadoop -r dba647c5a8bc5e09b572d76a8d29481c78d1a0dd
Compiled by jenkins on 2017-06-29T11:32Z
Compiled with protoc 2.5.0
From source with checksum 7c45ae7a4592ce5af86bc4598c5b4
This command was run using /usr/lib/hadoop/hadoop-common-2.6.0-cdh5.12.0.jar
  
[cloudera@quickstart ~]$ hadoop fs -ls /
Found 6 items
drwxrwxrwx   - hdfs  supergroup          0 2017-07-19 18:59 /benchmarks
drwxr-xr-x   - hbase supergroup          0 2017-10-29 17:35 /hbase
drwxr-xr-x   - solr  solr                0 2017-07-19 19:01 /solr
drwxrwxrwt   - hdfs  supergroup          0 2017-10-28 18:41 /tmp
drwxr-xr-x   - hdfs  supergroup          0 2017-07-19 19:01 /user
drwxr-xr-x   - hdfs  supergroup          0 2017-07-19 19:01 /var

[cloudera@quickstart ~]$ touch test_hdfs.txt
[cloudera@quickstart ~]$ hadoop fs -put test_hdfs.txt /user/cloudera
[cloudera@quickstart ~]$ hadoop fs -ls /user/cloudera
Found 4 items
-rw-r--r--   1 cloudera cloudera          0 2017-11-26 20:16 /user/cloudera/test_hdfs.txt
drwxr-xr-x   - cloudera cloudera          0 2017-11-12 18:19 /user/cloudera/tmp
[cloudera@quickstart ~]$ 
~~~

* **Sqoop:** Verify Sqoop installation on Cloudera QuickStart VM using below commands 
~~~
[cloudera@quickstart ~]$ sqoop version
Warning: /usr/lib/sqoop/../accumulo does not exist! Accumulo imports will fail.
Please set $ACCUMULO_HOME to the root of your Accumulo installation.
17/11/26 20:20:02 INFO sqoop.Sqoop: Running Sqoop version: 1.4.6-cdh5.12.0
Sqoop 1.4.6-cdh5.12.0
git commit id 
Compiled by jenkins on Thu Jun 29 04:30:40 PDT 2017
  
[cloudera@quickstart ~]$ sqoop help
Warning: /usr/lib/sqoop/../accumulo does not exist! Accumulo imports will fail.
Please set $ACCUMULO_HOME to the root of your Accumulo installation.
17/11/26 20:20:11 INFO sqoop.Sqoop: Running Sqoop version: 1.4.6-cdh5.12.0
usage: sqoop COMMAND [ARGS]

Available commands:
  codegen            Generate code to interact with database records
  create-hive-table  Import a table definition into Hive
  eval               Evaluate a SQL statement and display the results
  export             Export an HDFS directory to a database table
  help               List available commands
  import             Import a table from a database to HDFS
  import-all-tables  Import tables from a database to HDFS
  import-mainframe   Import datasets from a mainframe server to HDFS
  job                Work with saved jobs
  list-databases     List available databases on a server
  list-tables        List available tables in a database
  merge              Merge results of incremental imports
  metastore          Run a standalone Sqoop metastore
  version            Display version information

  See 'sqoop help COMMAND' for information on a specific command.

[cloudera@quickstart ~]$ sqoop list-databases \
--connect jdbc:mysql://quickstart.cloudera:3306/retail_db \
--username retail_dba \
--password cloudera
Warning: /usr/lib/sqoop/../accumulo does not exist! Accumulo imports will fail.
Please set $ACCUMULO_HOME to the root of your Accumulo installation.
17/11/26 20:22:27 INFO sqoop.Sqoop: Running Sqoop version: 1.4.6-cdh5.12.0
17/11/26 20:22:27 WARN tool.BaseSqoopTool: Setting your password on the command-line is insecure. Consider using -P instead.
17/11/26 20:22:27 INFO manager.MySQLManager: Preparing to use a MySQL streaming resultset.
information_schema
retail_db
  
[cloudera@quickstart ~]$ sqoop list-tables \
--connect jdbc:mysql://quickstart.cloudera:3306/retail_db \
--username retail_dba \
--password cloudera
Warning: /usr/lib/sqoop/../accumulo does not exist! Accumulo imports will fail.
Please set $ACCUMULO_HOME to the root of your Accumulo installation.
17/11/26 20:24:50 INFO sqoop.Sqoop: Running Sqoop version: 1.4.6-cdh5.12.0
17/11/26 20:24:50 WARN tool.BaseSqoopTool: Setting your password on the command-line is insecure. Consider using -P instead.
17/11/26 20:24:50 INFO manager.MySQLManager: Preparing to use a MySQL streaming resultset.
categories
customers
departments
order_items
orders
products
[cloudera@quickstart ~]
~~~

* **Flume:** Verify Flume installation on Cloudera QuickStart VM using below commands 
~~~
[cloudera@quickstart ~]$ flume-ng version
Flume 1.6.0-cdh5.12.0
Source code repository: https://git-wip-us.apache.org/repos/asf/flume.git
Revision: 4d37abdc65a53a0af1246de51bdcf1d8557669eb
Compiled by jenkins on Thu Jun 29 04:37:39 PDT 2017
From source with checksum af8d398eddf3e3b4ddbada14895722ac
  
[cloudera@quickstart ~]$ flume-ng help
Usage: /usr/lib/flume-ng/bin/flume-ng <command> [options]...

commands:
  help                      display this help text
  agent                     run a Flume agent
  avro-client               run an avro Flume client
  version                   show Flume version info

global options:
  --conf,-c <conf>          use configs in <conf> directory
  --classpath,-C <cp>       append to the classpath
  --dryrun,-d               do not actually start Flume, just print the command
  --plugins-path <dirs>     colon-separated list of plugins.d directories. See the
                            plugins.d section in the user guide for more details.
                            Default: $FLUME_HOME/plugins.d
  -Dproperty=value          sets a Java system property value
  -Xproperty=value          sets a Java -X option

agent options:
  --name,-n <name>          the name of this agent (required)
  --conf-file,-f <file>     specify a config file (required if -z missing)
  --zkConnString,-z <str>   specify the ZooKeeper connection to use (required if -f missing)
  --zkBasePath,-p <path>    specify the base path in ZooKeeper for agent configs
  --no-reload-conf          do not reload config file if changed
  --help,-h                 display help text

avro-client options:
  --rpcProps,-P <file>   RPC client properties file with server connection params
  --host,-H <host>       hostname to which events will be sent
  --port,-p <port>       port of the avro source
  --dirname <dir>        directory to stream to avro source
  --filename,-F <file>   text file to stream to avro source (default: std input)
  --headerFile,-R <file> File containing event headers as key/value pairs on each new line
  --help,-h              display help text

Either --rpcProps or both --host and --port must be specified.

Note that if <conf> directory is specified, then it is always included first
in the classpath.
~~~

* **Hive:** Verify Hive installation on Cloudera QuickStart VM using below commands 
~~~
[cloudera@quickstart ~]$ hive --version
Hive 1.1.0-cdh5.12.0
Subversion file:///data/jenkins/workspace/generic-package-rhel64-6-0/topdir/BUILD/hive-1.1.0-cdh5.12.0 -r Unknown
Compiled by jenkins on Thu Jun 29 04:10:23 PDT 2017
From source with checksum 99aa0becaa53fed482ba7d33b20edfcd

[cloudera@quickstart ~]$ hive
Logging initialized using configuration in file:/etc/hive/conf.dist/hive-log4j.properties
WARNING: Hive CLI is deprecated and migration to Beeline is recommended.
hive> show databases;
OK
default
Time taken: 0.651 seconds, Fetched: 1 row(s)
  
hive> exit;
WARN: The method class org.apache.commons.logging.impl.SLF4JLogFactory#release() was invoked.
WARN: Please see http://www.slf4j.org/codes.html#release for an explanation.
[cloudera@quickstart ~]$ 
~~~

* **Impala:** Verify Impala installation on Cloudera QuickStart VM using below commands 
~~~
[cloudera@quickstart ~]$ impala-shell --version
Impala Shell v2.9.0-cdh5.12.0 (03c6ddb) built on Thu Jun 29 04:17:31 PDT 2017

[cloudera@quickstart ~]$ impala-shell 
Starting Impala Shell without Kerberos authentication
Connected to quickstart.cloudera:21000
Server version: impalad version 2.9.0-cdh5.12.0 RELEASE (build 03c6ddbdcec39238be4f5b14a300d5c4f576097e)
***********************************************************************************
Welcome to the Impala shell.
(Impala Shell v2.9.0-cdh5.12.0 (03c6ddb) built on Thu Jun 29 04:17:31 PDT 2017)

The HISTORY command lists all shell commands in chronological order.
***********************************************************************************
  
[quickstart.cloudera:21000] > show databases;
Query: show databases
+------------------+----------------------------------------------+
| name             | comment                                      |
+------------------+----------------------------------------------+
| _impala_builtins | System database for Impala builtin functions |
| default          | Default Hive database                        |
+------------------+----------------------------------------------+
Fetched 2 row(s) in 7.09s
  
[quickstart.cloudera:21000] > exit;
Goodbye cloudera
[cloudera@quickstart ~]$ 

~~~

* **Spark Shell - Scala:** Verify Spark Shell installation on Cloudera QuickStart VM using below commands 

~~~
[cloudera@quickstart ~]$ spark-shell --version
Welcome to
      ____              __
     / __/__  ___ _____/ /__
    _\ \/ _ \/ _ `/ __/  '_/
   /___/ .__/\_,_/_/ /_/\_\   version 1.6.0
      /_/
                        
Type --help for more information.

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
17/11/26 20:41:57 WARN util.NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
17/11/26 20:41:57 WARN util.Utils: Your hostname, quickstart.cloudera resolves to a loopback address: 127.0.0.1; using 192.168.211.142 instead (on interface eth1)
17/11/26 20:41:57 WARN util.Utils: Set SPARK_LOCAL_IP if you need to bind to another address
17/11/26 20:41:59 WARN shortcircuit.DomainSocketFactory: The short-circuit local reads feature cannot be used because libhadoop cannot be loaded.
Spark context available as sc (master = yarn-client, app id = application_1509278183296_0027).
SQL context available as sqlContext.

scala> :quit
Stopping spark context.
[cloudera@quickstart ~]$ 
~~~

* **Spark Shell - Python:** Verify PySpark installation on Cloudera QuickStart VM using below commands 

~~~
[cloudera@quickstart ~]$ pyspark --version
Welcome to
      ____              __
     / __/__  ___ _____/ /__
    _\ \/ _ \/ _ `/ __/  '_/
   /___/ .__/\_,_/_/ /_/\_\   version 1.6.0
      /_/
                        
Type --help for more information.
[cloudera@quickstart ~]$ pyspark --master yarn
Python 2.6.6 (r266:84292, Jul 23 2015, 15:22:56) 
[GCC 4.4.7 20120313 (Red Hat 4.4.7-11)] on linux2
Type "help", "copyright", "credits" or "license" for more information.
Setting default log level to "WARN".
To adjust logging level use sc.setLogLevel(newLevel).
SLF4J: Class path contains multiple SLF4J bindings.
SLF4J: Found binding in [jar:file:/usr/lib/zookeeper/lib/slf4j-log4j12-1.7.5.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: Found binding in [jar:file:/usr/lib/flume-ng/lib/slf4j-log4j12-1.7.5.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: Found binding in [jar:file:/usr/lib/parquet/lib/slf4j-log4j12-1.7.5.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: Found binding in [jar:file:/usr/lib/avro/avro-tools-1.7.6-cdh5.12.0.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: See http://www.slf4j.org/codes.html#multiple_bindings for an explanation.
SLF4J: Actual binding is of type [org.slf4j.impl.Log4jLoggerFactory]
17/11/26 20:49:04 WARN util.NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
17/11/26 20:49:04 WARN util.Utils: Your hostname, quickstart.cloudera resolves to a loopback address: 127.0.0.1; using 192.168.211.142 instead (on interface eth1)
17/11/26 20:49:04 WARN util.Utils: Set SPARK_LOCAL_IP if you need to bind to another address
17/11/26 20:49:06 WARN shortcircuit.DomainSocketFactory: The short-circuit local reads feature cannot be used because libhadoop cannot be loaded.
Welcome to
      ____              __
     / __/__  ___ _____/ /__
    _\ \/ _ \/ _ `/ __/  '_/
   /__ / .__/\_,_/_/ /_/\_\   version 1.6.0
      /_/

Using Python version 2.6.6 (r266:84292, Jul 23 2015 15:22:56)
SparkContext available as sc, HiveContext available as sqlContext.
>>> exit()
[cloudera@quickstart ~]$ 
~~~

* **Avro Tool:** Verify Avro tool installation on Cloudera QuickStart VM using below commands

~~~
[cloudera@quickstart ~]$ avro-tools --version
Version 1.7.6-cdh5.12.0 of Apache Avro
Copyright 2010 The Apache Software Foundation

This product includes software developed at
The Apache Software Foundation (http://www.apache.org/).

C JSON parsing provided by Jansson and
written by Petri Lehtinen. The original software is
available from http://www.digip.org/jansson/.
----------------
Available tools:
          cat  extracts samples from files
      compile  Generates Java code for the given schema.
       concat  Concatenates avro files without re-compressing.
   fragtojson  Renders a binary-encoded Avro datum as JSON.
     fromjson  Reads JSON records and writes an Avro data file.
     fromtext  Imports a text file into an avro data file.
      getmeta  Prints out the metadata of an Avro data file.
    getschema  Prints out schema of an Avro data file.
          idl  Generates a JSON schema from an Avro IDL file
 idl2schemata  Extract JSON schemata of the types from an Avro IDL file
       induce  Induce schema/protocol from Java class/interface via reflection.
   jsontofrag  Renders a JSON-encoded Avro datum as binary.
       random  Creates a file with randomly generated instances of a schema.
      recodec  Alters the codec of a data file.
       repair  Recovers data from a corrupt Avro Data file
  rpcprotocol  Output the protocol of a RPC service
   rpcreceive  Opens an RPC Server and listens for one message.
      rpcsend  Sends a single RPC message.
       tether  Run a tethered mapreduce job.
       tojson  Dumps an Avro data file as JSON, record per line or pretty.
       totext  Converts an Avro data file to a text file.
     totrevni  Converts an Avro data file to a Trevni file.
  trevni_meta  Dumps a Trevni file's metadata as JSON.
trevni_random  Create a Trevni file filled with random instances of a schema.
trevni_tojson  Dumps a Trevni file as JSON.
[cloudera@quickstart ~]$ 
~~~

* **Parquet Tool:** Verify Parquet tool installation on Cloudera QuickStart VM using below commands

~~~
[cloudera@quickstart ~]$ parquet-tools --help
usage: parquet-tools cat [option...] <input>
where option is one of:
       --debug     Enable debug output
    -h,--help      Show this help string
    -j,--json      Show records in JSON format.
       --no-color  Disable color output even if supported
where <input> is the parquet file to print to stdout

usage: parquet-tools head [option...] <input>
where option is one of:
       --debug          Enable debug output
    -h,--help           Show this help string
    -n,--records <arg>  The number of records to show (default: 5)
       --no-color       Disable color output even if supported
where <input> is the parquet file to print to stdout

usage: parquet-tools schema [option...] <input>
where option is one of:
    -d,--detailed  Show detailed information about the schema.
       --debug     Enable debug output
    -h,--help      Show this help string
       --no-color  Disable color output even if supported
where <input> is the parquet file containing the schema to show

usage: parquet-tools meta [option...] <input>
where option is one of:
       --debug     Enable debug output
    -h,--help      Show this help string
       --no-color  Disable color output even if supported
where <input> is the parquet file to print to stdout
Step-1: Importing all tables from retail_db database of MySQL
usage: parquet-tools dump [option...] <input>
where option is one of:
    -c,--column <arg>  Dump only the given column, can be specified more than
                       once
    -d,--disable-data  Do not dump column data
       --debug         Enable debug output
    -h,--help          Show this help string
    -m,--disable-meta  Do not dump row group and page metadata
       --no-color      Disable color output even if supported
where <input> is the parquet file to print to stdout
~~~

## Cloudera QuickStart VM - Spark History Server

### Stop the Spark History Server:
~~~
[cloudera@quickstart ~]$ sudo service spark-history-server stop
~~~

### Set ownership and permissions on /user/spark/applicationHistory directory in HDFS and  as follows:
~~~
[cloudera@quickstart ~]$ sudo -u hdfs hadoop fs -chown -R spark:spark /user/spark
[cloudera@quickstart ~]$ sudo -u hdfs hadoop fs -chmod 1777 /user/spark/applicationHistory
~~~

### Add following lines to /etc/spark/conf/spark-defaults.conf file to enable spark events log:
~~~
spark.eventLog.enabled true
spark.eventLog.dir hdfs://quickstart.cloudera:8020/user/spark/applicationHistory
~~~
      
### Add below line to /etc/spark/conf/spark-defaults.conf file to link YARN ResourceManager directly to the Spark History Server:
~~~
spark.yarn.historyServer.address http://quickstart.cloudera:18088
~~~

### Start the Spark History Server:
~~~
[cloudera@quickstart ~]$ sudo service spark-history-server start
~~~

## Cloudera QuickStart VM - Cloudera Manager

### Start Cloudera Express Manger - Manually (This will require at least 8+ GB of RAM)
~~~
[cloudera@quickstart ~]$sudo ./cloudera-manager --express --force
~~~

### Start Cloudera Enterprise Manger - Manually (This will require at least 10+ GB of RAM)
~~~
[cloudera@quickstart ~]$sudo ./cloudera-manager --enterprise --force 
~~~


