package _1_dataframe._1_0_file_format

import org.apache.spark.{SparkConf, SparkContext}

object RDDSequenceFileDemo {
  def main(args: Array[String]): Unit = {

    // Create Spark Configuration
    val conf = new SparkConf().
      setAppName("DF Sequence File - Demo").
      setMaster("local[2]")

    // Create Spark Context
    val sc = new SparkContext(conf)

    val orderTextInputFileLocation = "/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/orders/text"

    // Create DataFrame from text file
    val orderRdd = sc.
      textFile(orderTextInputFileLocation)

    // Output location
    val orderSequenceFileOutputLocation = "/tmp/retail_db/orders/sequence"

    println("**** Problem Statement : Write & Verify Sequence file without compression ****")

    orderRdd.
      map((rec: String) => (rec.split(",")(0).toInt, rec)).
      saveAsSequenceFile(orderSequenceFileOutputLocation)

    // Verify Sequence files
    sc.
      sequenceFile(orderSequenceFileOutputLocation, classOf[org.apache.hadoop.io.IntWritable], classOf[org.apache.hadoop.io.Text]).
      map((t: (org.apache.hadoop.io.IntWritable, org.apache.hadoop.io.Text)) => t._2.toString).
      take(20).
      foreach(println)

    println("**** Problem Statement : Write & Verify Sequence file data with BZIP2 compression ****")

    val orderSeqBzip2FileLocation = orderSequenceFileOutputLocation + "_bzip2"

    orderRdd.
      map((rec: String) => (rec.split(",")(0).toInt, rec)).
      saveAsSequenceFile(orderSeqBzip2FileLocation, Some(classOf[org.apache.hadoop.io.compress.BZip2Codec]))

    // Verify Sequence files
    sc.
      sequenceFile(orderSeqBzip2FileLocation, classOf[org.apache.hadoop.io.IntWritable], classOf[org.apache.hadoop.io.Text]).
      map((t: (org.apache.hadoop.io.IntWritable, org.apache.hadoop.io.Text)) => t._2.toString).
      take(20).
      foreach(println)



    println("**** Problem Statement : Write & Verify Sequence file data with Snappy compression ****")

    println("!!! snappy with sequence file may not be working in local mode, but it should work in yarn mode !!!")
    val orderSeqSnappyFileLocation = orderSequenceFileOutputLocation + "_snappy"

    orderRdd.
      map((rec: String) => (rec.split(",")(0).toInt, rec)).
      saveAsSequenceFile(orderSeqSnappyFileLocation, Some(classOf[org.apache.hadoop.io.compress.SnappyCodec]))

    // Verify Sequence files
    sc.
      sequenceFile(orderSeqSnappyFileLocation, classOf[org.apache.hadoop.io.IntWritable], classOf[org.apache.hadoop.io.Text]).
      map((t: (org.apache.hadoop.io.IntWritable, org.apache.hadoop.io.Text)) => t._2.toString).
      take(20).
      foreach(println)

    println("**** Problem Statement : Write & Verify Sequence file data with GZIP compression ****")

    println("!!! gzip with text file may not be working in local mode, but it should work in yarn mode !!!")
    val orderSeqGzipFileLocation = orderSequenceFileOutputLocation + "_gzip"

    orderRdd.
      map((rec: String) => (rec.split(",")(0).toInt, rec)).
      saveAsSequenceFile(orderSeqGzipFileLocation, Some(classOf[org.apache.hadoop.io.compress.GzipCodec]))

    // Verify Sequence files
    sc.
      sequenceFile(orderSeqGzipFileLocation, classOf[org.apache.hadoop.io.IntWritable], classOf[org.apache.hadoop.io.Text]).
      map((t: (org.apache.hadoop.io.IntWritable, org.apache.hadoop.io.Text)) => t._2.toString).
      take(20).
      foreach(println)
  }
}
