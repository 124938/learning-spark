## DataFrame - Using Native Context (aka org.apache.spark.sql.SQLContext)

### DataFrame - Creation
With a SQLContext, application can create DataFrame in multiple ways:

* **Pre-Requisite:**
  
  * **Start `spark-shell`:**
  
  ~~~
  asus@asus-GL553VD:~$ spark-shell --master local[*]
  log4j:WARN Please initialize the log4j system properly.
  log4j:WARN No appenders could be found for logger (org.apache.hadoop.metrics2.lib.MutableMetricsFactory).
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
  Spark context available as sc.
  SQL context available as sqlContext.
  ~~~
  
  * **Create instance of `org.apache.spark.sql.SQLContext`:**
  
  ~~~
  scala> import org.apache.spark.sql.SQLContext
  import org.apache.spark.sql.SQLContext
  
  scala> val sqlContext = new SQLContext(sc)
  sqlContext: org.apache.spark.sql.SQLContext = org.apache.spark.sql.SQLContext@7ab7a4eb
  
  scala> import sqlContext.implicits._
  import sqlContext.implicits._
  ~~~

* **From Existing RDD:**
  
  * **By inferring the schema using reflection i.e. using case class:**
  
  ~~~
  scala> case class OrderRDD(order_id: Int, order_date: String, order_customer_id: Int, order_status: String) 
  defined class OrderRDD
  
  scala> val orderDF1 = sc.
  textFile("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/orders/text").
  map((rec: String) => {
    val recArray = rec.split(",")
    OrderRDD(recArray(0).toInt, recArray(1), recArray(2).toInt, recArray(3))
  }).
  toDF()
  orderDF1: org.apache.spark.sql.DataFrame = [order_id: int, order_date: string, order_customer_id: int, order_status: string]
  
  scala> orderDF1.
  show(5)
  +--------+--------------------+-----------------+---------------+
  |order_id|          order_date|order_customer_id|   order_status|
  +--------+--------------------+-----------------+---------------+
  |       1|2013-07-25 00:00:...|            11599|         CLOSED|
  |       2|2013-07-25 00:00:...|              256|PENDING_PAYMENT|
  |       3|2013-07-25 00:00:...|            12111|       COMPLETE|
  |       4|2013-07-25 00:00:...|             8827|         CLOSED|
  |       5|2013-07-25 00:00:...|            11318|       COMPLETE|
  +--------+--------------------+-----------------+---------------+
  only showing top 5 rows
  ~~~
  
  * **By specifying the schema using StructField:**
  
  ~~~
  scala> import org.apache.spark.sql.Row
  import org.apache.spark.sql.Row
  
  scala> val orderRowRDD = sc.
  textFile("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/orders/text").
  map((rec: String) => {
    val recArray = rec.split(",")
    Row(recArray(0).toInt, recArray(1), recArray(2).toInt, recArray(3))
  })
  orderRowRDD: org.apache.spark.rdd.RDD[org.apache.spark.sql.Row] = MapPartitionsRDD[7] at map at <console>:31
  
  scala> import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
  import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
  
  scala> val orderRowSchema = StructType(
  List(
    StructField("order_id", IntegerType),
    StructField("order_date", StringType),
    StructField("order_customer_id", IntegerType),
    StructField("order_status", StringType)
  ))
  orderRowSchema: org.apache.spark.sql.types.StructType = StructType(StructField(order_id,IntegerType,true), StructField(order_date,StringType,true), StructField  (der_customer_id,IntegerType,true), StructField(order_status,StringType,true))  
  
  scala> val orderDF2 = sqlContext.
  createDataFrame(orderRowRDD, orderRowSchema)
  orderDF2: org.apache.spark.sql.DataFrame = [order_id: int, order_date: string, order_customer_id: int, order_status: string]
  
  scala> orderDF2.
  show(5)
  +--------+--------------------+-----------------+---------------+
  |order_id|          order_date|order_customer_id|   order_status|
  +--------+--------------------+-----------------+---------------+
  |       1|2013-07-25 00:00:...|            11599|         CLOSED|
  |       2|2013-07-25 00:00:...|              256|PENDING_PAYMENT|
  |       3|2013-07-25 00:00:...|            12111|       COMPLETE|
  |       4|2013-07-25 00:00:...|             8827|         CLOSED|
  |       5|2013-07-25 00:00:...|            11318|       COMPLETE|
  +--------+--------------------+-----------------+---------------+
  only showing top 5 rows
  ~~~
   
  * **By specifying the schema using toDF:**
  
  ~~~
  scala> val orderDF3 = sc.
  textFile("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/orders/text").
  map((rec: String) => {
  val recArray = rec.split(",")
    (recArray(0).toInt, recArray(1), recArray(2).toInt, recArray(3))
  }).
  toDF("order_id", "order_date", "order_customer_id", "order_status")
  orderDF3: org.apache.spark.sql.DataFrame = [order_id: int, order_date: string, order_customer_id: int, order_status: string]
  
  scala> orderDF3.
  show(5)
  +--------+--------------------+-----------------+---------------+
  |order_id|          order_date|order_customer_id|   order_status|
  +--------+--------------------+-----------------+---------------+
  |       1|2013-07-25 00:00:...|            11599|         CLOSED|
  |       2|2013-07-25 00:00:...|              256|PENDING_PAYMENT|
  |       3|2013-07-25 00:00:...|            12111|       COMPLETE|
  |       4|2013-07-25 00:00:...|             8827|         CLOSED|
  |       5|2013-07-25 00:00:...|            11318|       COMPLETE|
  +--------+--------------------+-----------------+---------------+
  only showing top 5 rows
  ~~~

* **From Data Source:**
  
  * **JSON File**
  
  ~~~
  scala> val categoriesDF = sqlContext.
  read.
  json("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/categories/json")
  categoriesDF: org.apache.spark.sql.DataFrame = [category_department_id: struct<int:bigint>, category_id: struct<int:bigint>, category_name: struct<string:string>]
  
  scala> categoriesDF.
  show(5)
  +----------------------+-----------+--------------------+
  |category_department_id|category_id|       category_name|
  +----------------------+-----------+--------------------+
  |                   [2]|        [1]|          [Football]|
  |                   [2]|        [2]|            [Soccer]|
  |                   [2]|        [3]|[Baseball & Softb...|
  |                   [2]|        [4]|        [Basketball]|
  |                   [2]|        [5]|          [Lacrosse]|
  +----------------------+-----------+--------------------+
  only showing top 5 rows
  ~~~
        
  ~~~
  scala> val customersDF = sqlContext.
  read.
  json("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/customers/json")
  customersDF: org.apache.spark.sql.DataFrame = [customer_city: struct<string:string>, customer_email: struct<string:string>, customer_fname: struct<string:string>, stomer_id: struct<int:bigint>, customer_lname: struct<string:string>, customer_password: struct<string:string>, customer_state: struct<string:string>, customer_street: ruct<string:string>, customer_zipcode: struct<string:string>]
  
  scala> customersDF.
  show(5)
  +-------------+--------------+--------------+-----------+--------------+-----------------+--------------+--------------------+----------------+
  |customer_city|customer_email|customer_fname|customer_id|customer_lname|customer_password|customer_state|     customer_street|customer_zipcode|
  +-------------+--------------+--------------+-----------+--------------+-----------------+--------------+--------------------+----------------+
  |[Brownsville]|   [XXXXXXXXX]|     [Richard]|        [1]|   [Hernandez]|      [XXXXXXXXX]|          [TX]|[6303 Heather Plaza]|         [78521]|
  |  [Littleton]|   [XXXXXXXXX]|        [Mary]|        [2]|     [Barrett]|      [XXXXXXXXX]|          [CO]|[9526 Noble Ember...|         [80126]|
  |     [Caguas]|   [XXXXXXXXX]|         [Ann]|        [3]|       [Smith]|      [XXXXXXXXX]|          [PR]|[3422 Blue Pionee...|         [00725]|
  | [San Marcos]|   [XXXXXXXXX]|        [Mary]|        [4]|       [Jones]|      [XXXXXXXXX]|          [CA]|[8324 Little Common]|         [92069]|
  |     [Caguas]|   [XXXXXXXXX]|      [Robert]|        [5]|      [Hudson]|      [XXXXXXXXX]|          [PR]|[10 Crystal River...|         [00725]|
  +-------------+--------------+--------------+-----------+--------------+-----------------+--------------+--------------------+----------------+
  only showing top 5 rows
  ~~~
      
  ~~~
  scala> val departmentsDF = sqlContext.
  read.
  json("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/departments/json")
  departmentsDF: org.apache.spark.sql.DataFrame = [department_id: struct<int:bigint>, department_name: struct<string:string>]
  
  scala> departmentsDF.
  show(5)
  +-------------+---------------+
  |department_id|department_name|
  +-------------+---------------+
  |          [2]|      [Fitness]|
  |          [3]|     [Footwear]|
  |          [4]|      [Apparel]|
  |          [5]|         [Golf]|
  |          [6]|     [Outdoors]|
  +-------------+---------------+
  only showing top 5 rows
  ~~~
        
  ~~~
  scala> val orderItemsDF = sqlContext.
  read.
  json("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/order_items/json")
  orderItemsDF: org.apache.spark.sql.DataFrame = [order_item_id: struct<int:bigint>, order_item_order_id: struct<int:bigint>, order_item_product_id: struct<int:bigint>, order_item_product_price: struct<float:double>, order_item_quantity: struct<int:bigint>, order_item_subtotal: struct<float:double>]
    
  scala> orderItemsDF.
  show(5)
  +-------------+-------------------+---------------------+------------------------+-------------------+-------------------+
  |order_item_id|order_item_order_id|order_item_product_id|order_item_product_price|order_item_quantity|order_item_subtotal|
  +-------------+-------------------+---------------------+------------------------+-------------------+-------------------+
  |          [1]|                [1]|                [957]|                [299.98]|                [1]|           [299.98]|
  |          [2]|                [2]|               [1073]|                [199.99]|                [1]|           [199.99]|
  |          [3]|                [2]|                [502]|                  [50.0]|                [5]|            [250.0]|
  |          [4]|                [2]|                [403]|                [129.99]|                [1]|           [129.99]|
  |          [5]|                [4]|                [897]|                 [24.99]|                [2]|            [49.98]|
  +-------------+-------------------+---------------------+------------------------+-------------------+-------------------+
  only showing top 5 rows
  ~~~
      
  ~~~
  scala> val ordersDF = sqlContext.
  read.
  json("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/orders/json")
  ordersDF: org.apache.spark.sql.DataFrame = [order_customer_id: struct<int:bigint>, order_date: struct<long:bigint>, order_id: struct<int:bigint>, order_status: struct<string:string>]
  
  scala> ordersDF.
  show(5)
  +-----------------+---------------+--------+-----------------+
  |order_customer_id|     order_date|order_id|     order_status|
  +-----------------+---------------+--------+-----------------+
  |          [11599]|[1374735600000]|     [1]|         [CLOSED]|
  |            [256]|[1374735600000]|     [2]|[PENDING_PAYMENT]|
  |          [12111]|[1374735600000]|     [3]|       [COMPLETE]|
  |           [8827]|[1374735600000]|     [4]|         [CLOSED]|
  |          [11318]|[1374735600000]|     [5]|       [COMPLETE]|
  +-----------------+---------------+--------+-----------------+
  only showing top 5 rows
  ~~~
        
  ~~~
  scala> val productsDF = sqlContext.
  read.
  json("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/products/json")
  productsDF: org.apache.spark.sql.DataFrame = [product_category_id: struct<int:bigint>, product_description: struct<string:string>, product_id: struct<int:bigint>, product_image: struct<string:string>, product_name: struct<string:string>, product_price: struct<float:double>]
    
  scala> productsDF.
  show(5)
  +-------------------+-------------------+----------+--------------------+--------------------+-------------+
  |product_category_id|product_description|product_id|       product_image|        product_name|product_price|
  +-------------------+-------------------+----------+--------------------+--------------------+-------------+
  |                [2]|                 []|       [1]|[http://images.ac...|[Quest Q64 10 FT....|      [59.98]|
  |                [2]|                 []|       [2]|[http://images.ac...|[Under Armour Men...|     [129.99]|
  |                [2]|                 []|       [3]|[http://images.ac...|[Under Armour Men...|      [89.99]|
  |                [2]|                 []|       [4]|[http://images.ac...|[Under Armour Men...|      [89.99]|
  |                [2]|                 []|       [5]|[http://images.ac...|[Riddell Youth Re...|     [199.99]|
  +-------------------+-------------------+----------+--------------------+--------------------+-------------+
  only showing top 5 rows
  
  ~~~
      
  * **Parquet File**
    
  ~~~
  scala> val categoriesDF = sqlContext.
  read.
  parquet("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/categories/parquet")
  categoriesDF: org.apache.spark.sql.DataFrame = [category_id: int, category_department_id: int, category_name: string]
  
  scala> categoriesDF.
  show(5)
  +-----------+----------------------+-------------------+
  |category_id|category_department_id|      category_name|
  +-----------+----------------------+-------------------+
  |          1|                     2|           Football|
  |          2|                     2|             Soccer|
  |          3|                     2|Baseball & Softball|
  |          4|                     2|         Basketball|
  |          5|                     2|           Lacrosse|
  +-----------+----------------------+-------------------+
  only showing top 5 rows
  ~~~
        
  ~~~
  scala> val customersDF = sqlContext.
  read.
  parquet("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/customers/parquet")
  customersDF: org.apache.spark.sql.DataFrame = [customer_id: int, customer_fname: string, customer_lname: string, customer_email: string, customer_password: string, customer_street: string, customer_city: string, customer_state: string, customer_zipcode: string]
  
  scala> customersDF.
  show(5)
  +-----------+--------------+--------------+--------------+-----------------+--------------------+-------------+--------------+----------------+
  |customer_id|customer_fname|customer_lname|customer_email|customer_password|     customer_street|customer_city|customer_state|customer_zipcode|
  +-----------+--------------+--------------+--------------+-----------------+--------------------+-------------+--------------+----------------+
  |          1|       Richard|     Hernandez|     XXXXXXXXX|        XXXXXXXXX|  6303 Heather Plaza|  Brownsville|            TX|           78521|
  |          2|          Mary|       Barrett|     XXXXXXXXX|        XXXXXXXXX|9526 Noble Embers...|    Littleton|            CO|           80126|
  |          3|           Ann|         Smith|     XXXXXXXXX|        XXXXXXXXX|3422 Blue Pioneer...|       Caguas|            PR|           00725|
  |          4|          Mary|         Jones|     XXXXXXXXX|        XXXXXXXXX|  8324 Little Common|   San Marcos|            CA|           92069|
  |          5|        Robert|        Hudson|     XXXXXXXXX|        XXXXXXXXX|10 Crystal River ...|       Caguas|            PR|           00725|
  +-----------+--------------+--------------+--------------+-----------------+--------------------+-------------+--------------+----------------+
  only showing top 5 rows
  ~~~
        
  ~~~
  scala> val departmentsDF = sqlContext.
  read.
  parquet("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/departments/parquet")
  departmentsDF: org.apache.spark.sql.DataFrame = [department_id: int, department_name: string]
  
  scala> departmentsDF.
  show(5)
  +-------------+---------------+
  |department_id|department_name|
  +-------------+---------------+
  |            2|        Fitness|
  |            3|       Footwear|
  |            4|        Apparel|
  |            5|           Golf|
  |            6|       Outdoors|
  +-------------+---------------+
  only showing top 5 rows
  ~~~
        
  ~~~
  scala> val orderItemsDF = sqlContext.
  read.
  parquet("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/order_items/parquet")
  orderItemsDF: org.apache.spark.sql.DataFrame = [order_item_id: int, order_item_order_id: int, order_item_product_id: int, order_item_quantity: int, order_item_subtotal: float, order_item_product_price: float]
    
  scala> orderItemsDF.
  show(5)
  +-------------+-------------------+---------------------+-------------------+-------------------+------------------------+
  |order_item_id|order_item_order_id|order_item_product_id|order_item_quantity|order_item_subtotal|order_item_product_price|
  +-------------+-------------------+---------------------+-------------------+-------------------+------------------------+
  |            1|                  1|                  957|                  1|             299.98|                  299.98|
  |            2|                  2|                 1073|                  1|             199.99|                  199.99|
  |            3|                  2|                  502|                  5|              250.0|                    50.0|
  |            4|                  2|                  403|                  1|             129.99|                  129.99|
  |            5|                  4|                  897|                  2|              49.98|                   24.99|
  +-------------+-------------------+---------------------+-------------------+-------------------+------------------------+
  only showing top 5 rows
  ~~~
        
  ~~~
  scala> val ordersDF = sqlContext.
  read.
  parquet("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/orders/parquet")
  ordersDF: org.apache.spark.sql.DataFrame = [order_id: int, order_date: bigint, order_customer_id: int, order_status: string]
   
  scala> ordersDF.
  show(5)
  +--------+-------------+-----------------+---------------+
  |order_id|   order_date|order_customer_id|   order_status|
  +--------+-------------+-----------------+---------------+
  |       1|1374735600000|            11599|         CLOSED|
  |       2|1374735600000|              256|PENDING_PAYMENT|
  |       3|1374735600000|            12111|       COMPLETE|
  |       4|1374735600000|             8827|         CLOSED|
  |       5|1374735600000|            11318|       COMPLETE|
  +--------+-------------+-----------------+---------------+
  only showing top 5 rows
  ~~~
        
  ~~~
  scala> val productsDF = sqlContext.
  read.
  parquet("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/products/parquet")
  productsDF: org.apache.spark.sql.DataFrame = [product_id: int, product_category_id: int, product_name: string, product_description: string, product_price: float, product_image: string]
    
  scala> productsDF.
  show(5)
  +----------+-------------------+--------------------+-------------------+-------------+--------------------+
  |product_id|product_category_id|        product_name|product_description|product_price|       product_image|
  +----------+-------------------+--------------------+-------------------+-------------+--------------------+
  |         1|                  2|Quest Q64 10 FT. ...|                   |        59.98|http://images.acm...|
  |         2|                  2|Under Armour Men'...|                   |       129.99|http://images.acm...|
  |         3|                  2|Under Armour Men'...|                   |        89.99|http://images.acm...|
  |         4|                  2|Under Armour Men'...|                   |        89.99|http://images.acm...|
  |         5|                  2|Riddell Youth Rev...|                   |       199.99|http://images.acm...|
  +----------+-------------------+--------------------+-------------------+-------------+--------------------+
  only showing top 5 rows
  ~~~
    
  * **Avro File**
  
  ~~~
  asus@asus-GL553VD:~/source_code/github/124938/learning-spark/sql-api-features$ spark-shell --master local[*] --packages com.databricks:spark-avro_2.10:2.0.1
  Ivy Default Cache set to: /home/asus/.ivy2/cache
  The jars for the packages stored in: /home/asus/.ivy2/jars
  :: loading settings :: url = jar:file:/home/asus/tech_soft/spark-1.6.3-bin-hadoop2.6/lib/spark-assembly-1.6.3-hadoop2.6.0.jar!/org/apache/ivy/core/settings/ivysettings.xml
  com.databricks#spark-avro_2.10 added as a dependency
  :: resolving dependencies :: org.apache.spark#spark-submit-parent;1.0
  	confs: [default]
  	found com.databricks#spark-avro_2.10;2.0.1 in central
  	found org.apache.avro#avro;1.7.6 in central
  	found org.codehaus.jackson#jackson-core-asl;1.9.13 in list
  	found org.codehaus.jackson#jackson-mapper-asl;1.9.13 in list
  	found com.thoughtworks.paranamer#paranamer;2.3 in list
  	found org.xerial.snappy#snappy-java;1.0.5 in central
  	found org.apache.commons#commons-compress;1.4.1 in list
  	found org.tukaani#xz;1.0 in list
  	found org.slf4j#slf4j-api;1.6.4 in central
  :: resolution report :: resolve 11674ms :: artifacts dl 7ms
  	:: modules in use:
  	com.databricks#spark-avro_2.10;2.0.1 from central in [default]
  	com.thoughtworks.paranamer#paranamer;2.3 from list in [default]
  	org.apache.avro#avro;1.7.6 from central in [default]
  	org.apache.commons#commons-compress;1.4.1 from list in [default]
  	org.codehaus.jackson#jackson-core-asl;1.9.13 from list in [default]
  	org.codehaus.jackson#jackson-mapper-asl;1.9.13 from list in [default]
  	org.slf4j#slf4j-api;1.6.4 from central in [default]
  	org.tukaani#xz;1.0 from list in [default]
  	org.xerial.snappy#snappy-java;1.0.5 from central in [default]
  	---------------------------------------------------------------------
  	|                  |            modules            ||   artifacts   |
  	|       conf       | number| search|dwnlded|evicted|| number|dwnlded|
  	---------------------------------------------------------------------
  	|      default     |   9   |   2   |   2   |   0   ||   9   |   0   |
  	---------------------------------------------------------------------
  
  :: problems summary ::
  :::: ERRORS
  	unknown resolver null
  
  	unknown resolver null
  
  
  :: USE VERBOSE OR DEBUG MESSAGE LEVEL FOR MORE DETAILS
  :: retrieving :: org.apache.spark#spark-submit-parent
  	confs: [default]
  	0 artifacts copied, 9 already retrieved (0kB/6ms)
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
  17/12/27 21:03:51 WARN Utils: Your hostname, asus-GL553VD resolves to a loopback address: 127.0.1.1; using 192.168.0.100 instead (on interface enp3s0)
  17/12/27 21:03:51 WARN Utils: Set SPARK_LOCAL_IP if you need to bind to another address
  Spark context available as sc.
  17/12/27 21:04:12 WARN ObjectStore: Version information not found in metastore. hive.metastore.schema.verification is not enabled so recording the schema version 1.2.0
  17/12/27 21:04:12 WARN ObjectStore: Failed to get database default, returning NoSuchObjectException
  SQL context available as sqlContext.
  ~~~
  
  ~~~
  scala> import org.apache.spark.sql.SQLContext
  import org.apache.spark.sql.SQLContext
  
  scala> val sqlContext = new SQLContext(sc)
  sqlContext: org.apache.spark.sql.SQLContext = org.apache.spark.sql.SQLContext@27192b0c
  
  scala> import sqlContext.implicits._
  import sqlContext.implicits._
  
  scala> import com.databricks.spark.avro._
  import com.databricks.spark.avro._
  ~~~
  
  ~~~
  scala> val categoriesDF = sqlContext.
  read.
  format("com.databricks.spark.avro").
  load("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/categories/avro")
  categoriesDF: org.apache.spark.sql.DataFrame = [category_id: int, category_department_id: int, category_name: string]
  
  scala> categoriesDF.
  show(5)
  +-----------+----------------------+-------------------+
  |category_id|category_department_id|      category_name|
  +-----------+----------------------+-------------------+
  |          1|                     2|           Football|
  |          2|                     2|             Soccer|
  |          3|                     2|Baseball & Softball|
  |          4|                     2|         Basketball|
  |          5|                     2|           Lacrosse|
  +-----------+----------------------+-------------------+
  only showing top 5 rows
  ~~~

  ~~~
  scala> val customersDF = sqlContext.
  read.
  format("com.databricks.spark.avro").
  load("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/customers/avro")
  customersDF: org.apache.spark.sql.DataFrame = [customer_id: int, customer_fname: string, customer_lname: string, customer_email: string, customer_password: string, customer_street: string, customer_city: string, customer_state: string, customer_zipcode: string]
  
  scala> customersDF.
  show(5)
  +-----------+--------------+--------------+--------------+-----------------+--------------------+-------------+--------------+----------------+
  |customer_id|customer_fname|customer_lname|customer_email|customer_password|     customer_street|customer_city|customer_state|customer_zipcode|
  +-----------+--------------+--------------+--------------+-----------------+--------------------+-------------+--------------+----------------+
  |          1|       Richard|     Hernandez|     XXXXXXXXX|        XXXXXXXXX|  6303 Heather Plaza|  Brownsville|            TX|           78521|
  |          2|          Mary|       Barrett|     XXXXXXXXX|        XXXXXXXXX|9526 Noble Embers...|    Littleton|            CO|           80126|
  |          3|           Ann|         Smith|     XXXXXXXXX|        XXXXXXXXX|3422 Blue Pioneer...|       Caguas|            PR|           00725|
  |          4|          Mary|         Jones|     XXXXXXXXX|        XXXXXXXXX|  8324 Little Common|   San Marcos|            CA|           92069|
  |          5|        Robert|        Hudson|     XXXXXXXXX|        XXXXXXXXX|10 Crystal River ...|       Caguas|            PR|           00725|
  +-----------+--------------+--------------+--------------+-----------------+--------------------+-------------+--------------+----------------+
  only showing top 5 rows
  ~~~  

  ~~~
  scala> val departmentsDF = sqlContext.
  read.
  format("com.databricks.spark.avro").
  load("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/departments/avro")
  departmentsDF: org.apache.spark.sql.DataFrame = [department_id: int, department_name: string]
  
  scala> departmentsDF.
  show(5)
  +-------------+---------------+
  |department_id|department_name|
  +-------------+---------------+
  |            2|        Fitness|
  |            3|       Footwear|
  |            4|        Apparel|
  |            5|           Golf|
  |            6|       Outdoors|
  +-------------+---------------+
  only showing top 5 rows
  ~~~  

  ~~~
  scala> val orderItemsDF = sqlContext.
  read.
  format("com.databricks.spark.avro").
  load("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/order_items/avro")
  orderItemsDF: org.apache.spark.sql.DataFrame = [order_item_id: int, order_item_order_id: int, order_item_product_id: int, order_item_quantity: int, order_item_subtotal: float, order_item_product_price: float]
  
  scala> orderItemsDF.
  show(5)
  +-------------+-------------------+---------------------+-------------------+-------------------+------------------------+
  |order_item_id|order_item_order_id|order_item_product_id|order_item_quantity|order_item_subtotal|order_item_product_price|
  +-------------+-------------------+---------------------+-------------------+-------------------+------------------------+
  |            1|                  1|                  957|                  1|             299.98|                  299.98|
  |            2|                  2|                 1073|                  1|             199.99|                  199.99|
  |            3|                  2|                  502|                  5|              250.0|                    50.0|
  |            4|                  2|                  403|                  1|             129.99|                  129.99|
  |            5|                  4|                  897|                  2|              49.98|                   24.99|
  +-------------+-------------------+---------------------+-------------------+-------------------+------------------------+
  only showing top 5 rows
  ~~~  

  ~~~
  scala> val ordersDF = sqlContext.
  read.
  format("com.databricks.spark.avro").
  load("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/orders/avro")
  ordersDF: org.apache.spark.sql.DataFrame = [order_id: int, order_date: bigint, order_customer_id: int, order_status: string]
  
  scala> ordersDF.
  show(5)
  +--------+-------------+-----------------+---------------+
  |order_id|   order_date|order_customer_id|   order_status|
  +--------+-------------+-----------------+---------------+
  |       1|1374735600000|            11599|         CLOSED|
  |       2|1374735600000|              256|PENDING_PAYMENT|
  |       3|1374735600000|            12111|       COMPLETE|
  |       4|1374735600000|             8827|         CLOSED|
  |       5|1374735600000|            11318|       COMPLETE|
  +--------+-------------+-----------------+---------------+
  only showing top 5 rows
  ~~~  

  ~~~
  scala> val productsDF = sqlContext.
  read.
  format("com.databricks.spark.avro").
  load("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/products/avro")
  productsDF: org.apache.spark.sql.DataFrame = [product_id: int, product_category_id: int, product_name: string, product_description: string, product_price: float, product_image: string]
  
  scala> productsDF.
  show(5)
  +----------+-------------------+--------------------+-------------------+-------------+--------------------+
  |product_id|product_category_id|        product_name|product_description|product_price|       product_image|
  +----------+-------------------+--------------------+-------------------+-------------+--------------------+
  |         1|                  2|Quest Q64 10 FT. ...|                   |        59.98|http://images.acm...|
  |         2|                  2|Under Armour Men'...|                   |       129.99|http://images.acm...|
  |         3|                  2|Under Armour Men'...|                   |        89.99|http://images.acm...|
  |         4|                  2|Under Armour Men'...|                   |        89.99|http://images.acm...|
  |         5|                  2|Riddell Youth Rev...|                   |       199.99|http://images.acm...|
  +----------+-------------------+--------------------+-------------------+-------------+--------------------+
  only showing top 5 rows
  ~~~  
   
  * **Others**
    * JDBC
    * Many more...
    
### DataFrame - Operation