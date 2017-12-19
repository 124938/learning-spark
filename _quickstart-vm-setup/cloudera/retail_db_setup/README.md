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


