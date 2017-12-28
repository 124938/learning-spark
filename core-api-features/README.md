## Spark Core
TODO

## Getting Started - Using REPL

~~~
$ spark-shell  --master local[*]
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

Using Scala version 2.10.5 (OpenJDK 64-Bit Server VM, Java 1.8.0_131)
Type in expressions to have them evaluated.
Type :help for more information.
Spark context available as sc.
SQL context available as sqlContext.
~~~

~~~
scala> val orders = sc.
textFile("/home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/retail_db/orders")
orders: org.apache.spark.rdd.RDD[String] = /home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/retail_db/orders MapPartitionsRDD[3] at textFile at <console>:12

scala> orders.
take(5).
foreach(println)
1,2013-07-25 00:00:00.0,11599,CLOSED
2,2013-07-25 00:00:00.0,256,PENDING_PAYMENT
3,2013-07-25 00:00:00.0,12111,COMPLETE
4,2013-07-25 00:00:00.0,8827,CLOSED
5,2013-07-25 00:00:00.0,11318,COMPLETE
~~~

## Getting Started - Using IDE

### Launch IDE

* Create SBT project called `core-api-features` in IntelliJ Idea/Edlipse
  
* Refer below code snippet to add Spark SQL module dependency in `build.sbt`

~~~
name := "core-api-features"
version := "0.1"
scalaVersion := "2.10.6"

libraryDependencies += "org.apache.spark" % "spark-core_2.10" % "1.6.3"
~~~

* Refer below code snippet to create sample scala program in IDE

~~~
import org.apache.spark.{SparkConf, SparkContext}

object SampleDemo {
  def main(args: Array[String]): Unit = {

    println("========= Creating SparkContext ========")
    val conf = new SparkConf().
      setAppName("Action Demo").
      setMaster("local[2]")

    val sc = new SparkContext(conf)

    println("======= Creating RDD from text file ========")
    val orders = sc.
    textFile("/home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/retail_db/orders")

    println("======= Previewing Data : Usage ========")
    orders.
    take(10).
    foreach(println)
  }
}

~~~

* Execute above program under IDE to see the result

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

scala> val conf = new SparkConf().
setMaster("local[2]").
setAppName("first spark demo")
conf: org.apache.spark.SparkConf = org.apache.spark.SparkConf@47b1c217

scala> import org.apache.spark.SparkContext
import org.apache.spark.SparkContext

scala> val sc = new SparkContext(conf)
sc: org.apache.spark.SparkContext = org.apache.spark.SparkContext@71ea9c44

scala> val orders = sc.
textFile("/home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/retail_db/orders")
orders: org.apache.spark.rdd.RDD[String] = /home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/retail_db/orders MapPartitionsRDD[3] at textFile at <console>:12

scala> orders.
take(5).
foreach(println)
1,2013-07-25 00:00:00.0,11599,CLOSED
2,2013-07-25 00:00:00.0,256,PENDING_PAYMENT
3,2013-07-25 00:00:00.0,12111,COMPLETE
4,2013-07-25 00:00:00.0,8827,CLOSED
5,2013-07-25 00:00:00.0,11318,COMPLETE
~~~

