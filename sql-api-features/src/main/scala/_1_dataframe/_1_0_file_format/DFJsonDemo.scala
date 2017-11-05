package _1_dataframe._1_0_file_format

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{SQLContext, SaveMode}

object DFJsonDemo {
  def main(args: Array[String]): Unit = {

    // Create Spark Configuration
    val conf = new SparkConf().
      setAppName("DF Creation Using RDD").
      setMaster("local[2]")

    // Create Spark Context
    val sc = new SparkContext(conf)

    // Create SQL Context
    val sqlContext = new SQLContext(sc)

    // Below is used to create DataFrame from RDD
    import sqlContext.implicits._

    val orderTextFileLocation = "/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/orders/text"

    // Create DataFrame from text file
    val orderDF = sc.
      textFile(orderTextFileLocation).
      map((rec: String) => {
        val recArray = rec.split(",")
        (recArray(0).toInt, recArray(1), recArray(2).toInt, recArray(3))
      }).
      toDF("order_id", "order_date", "order_customer_id", "order_status")

    println("**** Problem Statement : Write JSON data without compression codec ****")

    println("!!!! WARN : As of 1.6.3 compression doesn't get supported while writing JSON file using  DataFrame !!!!")
    val orderJsonFileLocation = "/tmp/retail_db/orders/json"
    orderDF.
      write.
      mode(SaveMode.Overwrite).
      json(orderJsonFileLocation)

    // Verify JSON files
    sqlContext.
      read.
      json(orderJsonFileLocation).
      show(20)

    println("**** Problem Statement : Write JSON data with BZIP2 compression codec ****")
    val orderJsonBzip2FileLocation = orderJsonFileLocation + "_" + System.currentTimeMillis
    orderDF.
      toJSON.
      saveAsTextFile(orderJsonBzip2FileLocation, classOf[org.apache.hadoop.io.compress.BZip2Codec])

    // Verify JSON Bzip2 files
    sqlContext.
      read.
      json(orderJsonBzip2FileLocation).
      show(20)

    println("**** Problem Statement : Write JSON data with GZIP compression codec ****")
    val orderJsonGzipFileLocation = orderJsonFileLocation + "_" + System.currentTimeMillis
    orderDF.
      toJSON.
      saveAsTextFile(orderJsonGzipFileLocation, classOf[org.apache.hadoop.io.compress.GzipCodec])

    // Verify JSON Gzip files
    sqlContext.
      read.
      json(orderJsonGzipFileLocation).
      show(20)

    /* // Below is not working
    println("**** Problem Statement : Write JSON data with Snappy compression codec ****")
    val orderJsonSnappyFileLocation = orderJsonFileLocation + "_" + System.currentTimeMillis
    orderDF.
      toJSON.
      saveAsTextFile(orderJsonSnappyFileLocation, classOf[org.apache.hadoop.io.compress.SnappyCodec])
    */
  }
}
