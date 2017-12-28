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

