package _1_dataframe._1_3_file_format

import org.apache.spark.sql.{SQLContext, SaveMode}
import org.apache.spark.{SparkConf, SparkContext}

object DFParquetFileDemo {
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

    val orderTextFileLocation = "/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/orders/text"

    // Create DataFrame from text file
    val orderDF = sc.
      textFile(orderTextFileLocation).
      map((rec: String) => {
        val recArray = rec.split(",")
        (recArray(0).toInt, recArray(1), recArray(2).toInt, recArray(3))
      }).
      toDF("order_id", "order_date", "order_customer_id", "order_status")

    // Output location
    val orderParquetFileLocation = "/tmp/retail_db/orders/parquet"

    println("**** Problem Statement : Write & Verify Parquet data without compression ****")

    // Set compression codec of parquet file as uncompressed
    sqlContext.setConf("spark.sql.parquet.compression.codec", "uncompressed")

    orderDF.
      write.
      mode(SaveMode.Overwrite).
      parquet(orderParquetFileLocation)

    // Verify Parquet files
    sqlContext.
      read.
      parquet(orderParquetFileLocation).
      show(20)

    println("**** Problem Statement : Write & Verify Parquet data with Snappy compression ****")

    val orderParquetSnappyFileLocation = orderParquetFileLocation + "_snappy"

    // Set compression codec of parquet file as snappy
    sqlContext.setConf("spark.sql.parquet.compression.codec", "snappy")

    orderDF.
      write.
      mode(SaveMode.Overwrite).
      parquet(orderParquetSnappyFileLocation)

    // Verify Parquet files
    sqlContext.
      read.
      parquet(orderParquetSnappyFileLocation).
      show(20)

    println("**** Problem Statement : Write & Verify Parquet data with GZIP compression ****")

    val orderParquetGzipFileLocation = orderParquetFileLocation + "_gzip"

    // Set compression codec of parquet file as gzip
    sqlContext.setConf("spark.sql.parquet.compression.codec", "gzip")

    orderDF.
      write.
      mode(SaveMode.Overwrite).
      parquet(orderParquetGzipFileLocation)

    // Verify Parquet files
    sqlContext.
      read.
      parquet(orderParquetGzipFileLocation).
      show(20)
  }
}
