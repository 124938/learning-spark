package _2_file_format

import org.apache.spark.sql.{SQLContext, SaveMode}
import org.apache.spark.{SparkConf, SparkContext}

object DFAvroFileDemo {
  def main(args: Array[String]): Unit = {

    // Create Spark Configuration
    val conf = new SparkConf().
      setAppName("DF Avro File - Demo").
      setMaster("local[2]")

    // Create Spark Context
    val sc = new SparkContext(conf)

    // Below is used to perform operation with avro file
    import com.databricks.spark.avro._

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
    val orderAvroFileLocation = "/tmp/retail_db/orders/avro"

    println("**** Problem Statement : Write & Verify Avro data without compression ****")

    // Set compression codec of avro file as uncompressed
    sqlContext.
      setConf("spark.sql.avro.compression.codec", "uncompressed")

    orderDF.
      write.
      mode(SaveMode.Overwrite).
      avro(orderAvroFileLocation)

    // Verify Avro files
    sqlContext.
      read.
      avro(orderAvroFileLocation).
      show(20)

    println("**** Problem Statement : Write & Verify Avro data with Snappy compression ****")

    val orderAvroSnappyFileLocation = orderAvroFileLocation + "_snappy"

    // Set compression codec of avro file as snappy
    sqlContext.setConf("spark.sql.avro.compression.codec", "snappy")

    orderDF.
      write.
      mode(SaveMode.Overwrite).
      avro(orderAvroSnappyFileLocation)

    // Verify avro files
    sqlContext.
      read.
      avro(orderAvroSnappyFileLocation).
      show(20)

    println("**** Problem Statement : Write & Verify Avro data with Deflate compression ****")

    val orderAvroDeflateFileLocation = orderAvroFileLocation + "_deflate"

    // Set compression codec of avro file as gzip
    sqlContext.setConf("spark.sql.avro.compression.codec", "deflate")

    orderDF.
      write.
      mode(SaveMode.Overwrite).
      avro(orderAvroDeflateFileLocation)

    // Verify Avro file
    sqlContext.
      read.
      avro(orderAvroDeflateFileLocation).
      show(20)
  }
}
