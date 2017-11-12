package _1_dataframe._1_0_file_format

import org.apache.spark.sql.{Row, SQLContext, SaveMode}
import org.apache.spark.{SparkConf, SparkContext}

object DFTextDemo {
  def main(args: Array[String]): Unit = {

    // Create Spark Configuration
    val conf = new SparkConf().
      setAppName("DF Parquet File - Demo").
      setMaster("local[2]")

    // Create Spark Context
    val sc = new SparkContext(conf)

    // Create SQL Context
    val sqlContext = new SQLContext(sc)

    // Below is used to create DataFrame from RDD
    import sqlContext.implicits._

    val orderTextInputFileLocation = "/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/orders/text"

    // Create DataFrame from text file
    val orderDF = sc.
      textFile(orderTextInputFileLocation).
      map((rec: String) => {
        val recArray = rec.split(",")
        (recArray(0).toInt, recArray(1), recArray(2).toInt, recArray(3))
      }).
      toDF("order_id", "order_date", "order_customer_id", "order_status")

    // Output location
    val orderTextFileOutputLocation = "/tmp/retail_db/orders/text"

    println("**** Problem Statement : Write & Verify Text data with GZIP compression ****")

    val orderTextGzipFileLocation = orderTextFileOutputLocation + "_gzip"

    orderDF.
      rdd.
      map((row :Row) => row.mkString(",")).
      saveAsTextFile(orderTextGzipFileLocation, classOf[org.apache.hadoop.io.compress.GzipCodec])

    // Verify Text files
    sqlContext.
      read.
      text(orderTextGzipFileLocation).
      show(20)

    println("**** Problem Statement : Write & Verify Text data with BZIP2 compression ****")

    val orderTextBzip2FileLocation = orderTextFileOutputLocation + "_bzip2"

    orderDF.
      rdd.
      map((row :Row) => row.mkString(",")).
      saveAsTextFile(orderTextBzip2FileLocation, classOf[org.apache.hadoop.io.compress.BZip2Codec])

    // Verify Text files
    sqlContext.
      read.
      text(orderTextBzip2FileLocation).
      show(20)

    println("**** Problem Statement : Write & Verify Text data without compression ****")

    orderDF.
      rdd.
      map((row :Row) => row.mkString(",")).
      saveAsTextFile(orderTextFileOutputLocation)

    // Verify Text files
    sqlContext.
      read.
      text(orderTextFileOutputLocation).
      show(20)
  }
}
