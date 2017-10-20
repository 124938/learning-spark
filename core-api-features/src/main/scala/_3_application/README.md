## Spark Application

### Development Life Cycle:

* **Start With REPL**
  * Start spark-shell i.e. REPL in standalone mode
    * `start-master.sh` => Start Master
    * `start-slave.sh spark://localhost:7077` => Start Slave
    * `spark-shell --master spark://localhost:7077` => Launch spark shell in standalone mode
  * Create Word Count program - Refer below code

  ~~~
  scala> val lines = sc.textFile("/home/asus/tech_soft/spark-1.6.3-bin-hadoop2.6/README.md")
  lines: org.apache.spark.rdd.RDD[String] = /home/asus/tech_soft/spark-1.6.3-bin-hadoop2.6/README.md MapPartitionsRDD[207] at textFile at <console>:27

  scala> val words = lines.flatMap((line: String) => line.split(" ").map((word: String) => (word.toLowerCase, 1)))
  words: org.apache.spark.rdd.RDD[(String, Int)] = MapPartitionsRDD[211] at flatMap at <console>:29

  scala> words.reduceByKey(_ + _).take(10).foreach(println)
  (scala,2)
  (package,1)
  (this,3)
  (hive,2)
  (its,1)

  ~~~
  
* **Use IDE for development**
  * Create Scala project (using SBT) under IntelliJ Idea
  * Add following dependencies to build.sbt file
  * Create Word Count program
  
  ~~~
  libraryDependencies += "com.typesafe" % "config" % "1.3.1"
  libraryDependencies += "org.apache.spark" % "spark-core_2.10" % "1.6.3"
  ~~~
  
* **Use SBT to build artifact**
  * Execute below SBT command to build JAR file
  
  ~~~
  $cd /path/to/project
  sbt package
  ~~~

### Execution Life Cycle:

* Copy JAR file to VM or Gateway node of cluster using below command