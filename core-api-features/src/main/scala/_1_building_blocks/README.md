## SparkConf and SparkContext

### In context of `spark-shell`
When we launch spark shell...
* SparkContext will be created automatically with implicit instance of SparkConf
* Below is the reference code to create instance of SparkConf & SparkContext manually from spark-shell

~~~
$spark-shell

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
17/10/15 17:18:18 WARN Utils: Your hostname, asus-GL553VD resolves to a loopback address: 127.0.1.1; using 192.168.0.103 instead (on interface enp3s0)
17/10/15 17:18:18 WARN Utils: Set SPARK_LOCAL_IP if you need to bind to another address
Spark context available as sc.
SQL context available as sqlContext.
~~~

~~~
scala> sc.stop

scala> import org.apache.spark.SparkConf
import org.apache.spark.SparkConf

scala> val conf = new SparkConf().setAppName("Spark demo").setMaster("local[2]")
conf: org.apache.spark.SparkConf = org.apache.spark.SparkConf@6080eac7

scala> import org.apache.spark.SparkContext
import org.apache.spark.SparkContext

scala> val sc = new SparkContext(conf)
sc: org.apache.spark.SparkContext = org.apache.spark.SparkContext@5ac26c55
~~~

### In context of `spark-submit`
When we submit spark application as a JAR file...
* Below is the reference code to create instance of SparkConf & SparkContext

~~~
import org.apache.spark.{SparkConf, SparkContext}

object SparkDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().
      setAppName("Spark Demo").
      setMaster("local[2]").
      set("spark.ui.port", "65869").
      set("spark.executor.memory", "1g")

    val sc = new SparkContext(conf)
  }
}
~~~

### SparkConf
Below are several important set methods to override parameter values OR environment variables
* `conf.setAppName("Spark Demo")` => Set unique name to application
* `conf.setMaster("local[2]")` => Should get used to start application in local mode
* `conf.setMaster("spark://localhost:7077")` => Should get used to start application in stand alone mode
* `conf.set("spark.ui.port", "65869")` => Set port on which spark UI should get exposed
* `conf.set("spark.executor.memory", "1g")` => Set executor memory

### SparkContext 
It's the starting point of spark application and typical life cycle of spark application performs following steps:
* _Read Data from file system:_ 
  
  * SparkContext supports following protocols:
    * `file://` => To read data from local file system
    * `hdfs://` => To read data from HDFS
  
  * SparkContext supports following APIs:
    * `sc.textFile`
    * `sc.sequenceFile`
    * `sc.objectFile`
    * `sc.hadoopFile`
    * `sc.newAPIHadoopFile`
  
* _Process data:_
  
  * RDD is getting used to process data in distributed fashion
  
* _Write data back to file system:_   
  
  * RDD supports following APIs
    * `rdd.saveAsTextFile`
    * `rdd.saveAsSequenceFile`
    * `rdd.saveAsObjectFile`
    * `rdd.saveAsHadoopFile`
    * `rdd.saveAsNewAPIHadoopFile`

## When to use which protocol?
  
* On Cloudera quickstart VM OR HortonWorks Sandbox: As spark is integrated with hadoop and `hdfs://` is set as default protocol
  * No need to specify `hdfs://` while reading/writing data from/to HDFS
  * Mandatory to specify `file://` while reading/writing data from/to local file system
  
* On local machine: As spark is independently installed on local machine and `file://` is set as default protocol
  * No need to specify `file://` while reading/writing data from/to local file system
  * Mandatory to specify `hdfs://` while reading/writing data from/to HDFS


## Reading & Writing File - Using SBT console
* Below is the sample code to read/write file (very famous word count program)

~~~
asus@asus-GL553VD:~$ cd /home/asus/source_code/github/124938/learning-spark/core-api-features
asus@asus-GL553VD:~/source_code/github/124938/learning-spark/core-api-features$ sbt console
[info] Loading global plugins from /home/asus/.sbt/0.13/plugins
[info] Loading project definition from /home/asus/source_code/github/124938/learning-spark/core-api-features/project
[info] Set current project to core-api-features (in build file:/home/asus/source_code/github/124938/learning-spark/core-api-features/)
[info] Starting scala interpreter...
[info] 
Welcome to Scala version 2.10.6 (OpenJDK 64-Bit Server VM, Java 1.8.0_151).
Type in expressions to have them evaluated.
Type :help for more information.
~~~

~~~
scala> import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.{SparkConf, SparkContext}

scala> val conf = new SparkConf().setAppName("Spark Demo - SBT Console").setMaster("local[2]")
conf: org.apache.spark.SparkConf = org.apache.spark.SparkConf@a1866f0

scala> val sc = new SparkContext(conf)
sc: org.apache.spark.SparkContext = org.apache.spark.SparkContext@238082b6

scala> val rdd = sc.textFile("/home/asus/tech_soft/apache-maven-3.5.0/README.txt")
rdd: org.apache.spark.rdd.RDD[String] = /home/asus/tech_soft/apache-maven-3.5.0/README.txt MapPartitionsRDD[1] at textFile at <console>:10

scala> val wordCountRdd = rdd.flatMap((line: String) => line.split(",")).map((word: String) => (word, 1)).reduceByKey((agg, ele) => agg + ele)
wordCountRdd: org.apache.spark.rdd.RDD[(String, Int)] = ShuffledRDD[7] at reduceByKey at <console>:11

scala> wordCountRdd.saveAsTextFile("/home/asus/tech_soft/apache-maven-3.5.0/out")
~~~

## Spark Web UI
* A typical spark application usually executes multiple JOBs
  
![Alt text](images/spark-web-ui-jobs.png?raw=true "Spark Web UI - Jobs")

* A JOB consists of multiple tasks and tasks are grouped as stages

![Alt text](images/spark-web-ui-job-details.png?raw=true "Spark Web UI - Job Details")

![Alt text](images/spark-web-ui-job-stage.png?raw=true "Spark Web UI - Stage")

* A JOB always ends with executing "action" on RDD e.g. `rdd.saveAsTextFile`

* Explore Spark Web UI....