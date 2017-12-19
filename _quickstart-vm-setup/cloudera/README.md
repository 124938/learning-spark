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

## Cloudera QuickStart VM - Cloudera Manager

### Start Cloudera Express Manger - Manually:
~~~
[cloudera@quickstart ~]$sudo ./cloudera-manager --express --force
~~~

### Start Cloudera Enterprise Manger - Manually:
~~~
[cloudera@quickstart ~]$sudo ./cloudera-manager --enterprise --force
~~~

### Important Screenshot:

![Alt text](_images/cloudera-manager-ui.png?raw=true "Cloudera Manager - UI")

![Alt text](_images/yarn-resource-manager-ui.png?raw=true "YARN - Resource Manager - UI")

![Alt text](_images/hdfs-name-node-ui.png?raw=true "HDFS - Name Node -UI")

![Alt text](_images/spark-application-master-ui.png?raw=true "Spark - Application Master UI")

![Alt text](_images/spark-history-server-ui.png?raw=true "Spark - History Server UI")

## Cloudera QuickStart VM - Retail DataSet Setup

### Step-0: Login To VM
~~~
asus@asus-GL553VD:~$ ssh cloudera@192.168.211.142
cloudera@192.168.211.142's password: 
Last login: Sun Nov 26 17:58:32 2017 from 192.168.211.1
[cloudera@quickstart ~]$
~~~

### Step-1: Importing all tables from retail_db database of MySQL

~~~
[cloudera@quickstart ~]$ sqoop import-all-tables \
--connect jdbc:mysql://quickstart.cloudera:3306/retail_db \
--username retail_dba \
--password cloudera \
--num-mappers 1 \
--warehouse-dir /user/cloudera/sqoop/import-all-tables-text \
--as-textfile
~~~

### Step-2: Creation of database called retail_db and required tables in Hive

~~~
[cloudera@quickstart ~]$ hive
Logging initialized using configuration in file:/etc/hive/conf.dist/hive-log4j.properties
WARNING: Hive CLI is deprecated and migration to Beeline is recommended.
hive> show databases;
OK
default
Time taken: 0.651 seconds, Fetched: 1 row(s)
  
hive> create database retail_db;

hive> use retail_db;

hive> set hive.cli.print.current.db=true;

hive (retail_db)>  create external table categories(
  category_id int,
  category_department_id int,
  category_name string)
row format delimited 
  fields terminated by ','
stored as textfile
location '/user/cloudera/sqoop/import-all-tables-text/categories';

hive (retail_db)>  create external table customers(
  customer_id int,
  customer_fname string,
  customer_lname string,
  customer_email string,
  customer_password string,
  customer_street string,
  customer_city string,
  customer_state string,
  customer_zipcode string)
row format delimited 
  fields terminated by ','
stored as textfile
location '/user/cloudera/sqoop/import-all-tables-text/customers';

hive (retail_db)>  create external table departments(
  department_id int,
  department_name string)
row format delimited
  fields terminated by ','
stored as textfile
location '/user/cloudera/sqoop/import-all-tables-text/departments';

hive (retail_db)>  create external table order_items(
  order_item_id int,
  order_item_order_id int,
  order_item_product_id int,
  order_item_quantity int,
  order_item_subtotal float,
  order_item_product_price float)
row format delimited
  fields terminated by ','
stored as textfile
location '/user/cloudera/sqoop/import-all-tables-text/order_items';

hive (retail_db)>  create external table orders(
  order_id int,
  order_date string,
  order_customer_id int,
  order_status string)
row format delimited
  fields terminated by ','
stored as textfile
location '/user/cloudera/sqoop/import-all-tables-text/orders';

hive (retail_db)>  create external table products(
  product_id int,
  product_category_id int,
  product_name string,
  product_description string,
  product_price float,
  product_image string)
row format delimited
  fields terminated by ','
stored as textfile
location '/user/cloudera/sqoop/import-all-tables-text/products';
~~~

### Step-3: Execute join query in Hive

~~~
hive (retail_db)> SET hive.auto.convert.join=false;

hive (retail_db)> select o.order_date, sum(oi.order_item_subtotal)
from orders o join order_items oi on (o.order_id = oi.order_item_order_id)
group by o.order_date 
limit 10;

Query ID = cloudera_20171126213232_23711b96-18fa-4cd6-9e88-0836f97b0833
Total jobs = 2
Launching Job 1 out of 2
Number of reduce tasks not specified. Estimated from input data size: 1
In order to change the average load for a reducer (in bytes):
  set hive.exec.reducers.bytes.per.reducer=<number>
In order to limit the maximum number of reducers:
  set hive.exec.reducers.max=<number>
In order to set a constant number of reducers:
  set mapreduce.job.reduces=<number>
Starting Job = job_1509278183296_0029, Tracking URL = http://quickstart.cloudera:8088/proxy/application_1509278183296_0029/
Kill Command = /usr/lib/hadoop/bin/hadoop job  -kill job_1509278183296_0029
Hadoop job information for Stage-1: number of mappers: 2; number of reducers: 1
2017-11-26 21:32:43,839 Stage-1 map = 0%,  reduce = 0%
2017-11-26 21:32:50,231 Stage-1 map = 100%,  reduce = 0%, Cumulative CPU 5.56 sec
2017-11-26 21:32:55,445 Stage-1 map = 100%,  reduce = 100%, Cumulative CPU 8.23 sec
MapReduce Total cumulative CPU time: 8 seconds 230 msec
Ended Job = job_1509278183296_0029
Launching Job 2 out of 2
Number of reduce tasks not specified. Estimated from input data size: 1
In order to change the average load for a reducer (in bytes):
  set hive.exec.reducers.bytes.per.reducer=<number>
In order to limit the maximum number of reducers:
  set hive.exec.reducers.max=<number>
In order to set a constant number of reducers:
  set mapreduce.job.reduces=<number>
Starting Job = job_1509278183296_0030, Tracking URL = http://quickstart.cloudera:8088/proxy/application_1509278183296_0030/
Kill Command = /usr/lib/hadoop/bin/hadoop job  -kill job_1509278183296_0030
Hadoop job information for Stage-2: number of mappers: 1; number of reducers: 1
2017-11-26 21:33:02,308 Stage-2 map = 0%,  reduce = 0%
2017-11-26 21:33:06,478 Stage-2 map = 100%,  reduce = 0%, Cumulative CPU 0.83 sec
2017-11-26 21:33:12,787 Stage-2 map = 100%,  reduce = 100%, Cumulative CPU 2.0 sec
MapReduce Total cumulative CPU time: 2 seconds 0 msec
Ended Job = job_1509278183296_0030
MapReduce Jobs Launched: 
Stage-Stage-1: Map: 2  Reduce: 1   Cumulative CPU: 8.23 sec   HDFS Read: 8422614 HDFS Write: 17364 SUCCESS
Stage-Stage-2: Map: 1  Reduce: 1   Cumulative CPU: 2.0 sec   HDFS Read: 22571 HDFS Write: 407 SUCCESS
Total MapReduce CPU Time Spent: 10 seconds 230 msec
OK

2013-07-25 00:00:00.0	68153.83132743835
2013-07-26 00:00:00.0	136520.17266082764
2013-07-27 00:00:00.0	101074.34193611145
2013-07-28 00:00:00.0	87123.08192253113
2013-07-29 00:00:00.0	137287.09244918823
2013-07-30 00:00:00.0	102745.62186431885
2013-07-31 00:00:00.0	131878.06256484985
2013-08-01 00:00:00.0	129001.62241744995
2013-08-02 00:00:00.0	109347.00200462341
2013-08-03 00:00:00.0	95266.89186286926
Time taken: 36.754 seconds, Fetched: 10 row(s)
~~~


