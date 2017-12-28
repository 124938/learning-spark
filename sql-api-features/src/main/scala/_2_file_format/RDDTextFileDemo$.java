package _2_file_format;

object RDDTextFileDemo {
  def main(args: Array[String]): Unit = {

    // Create Spark Configuration
    val conf = new SparkConf().
      setAppName("DF Parquet File - Demo").
      setMaster("local[2]")

    // Create Spark Context
    val sc = new SparkContext(conf)

    val orderTextInputFileLocation = "/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/orders/text"

    // Create DataFrame from text file
    val orderRdd = sc.
      textFile(orderTextInputFileLocation)

    // Output location
    val orderTextFileOutputLocation = "/tmp/retail_db/orders/text"

    println("**** Problem Statement : Write & Verify Text data without compression ****")

    orderRdd.
      map((rec: String) => {
        val recArray = rec.split(",")
        recArray(0).toInt +"\t"+ recArray(1) +"\t"+ recArray(2).toInt +"\t"+ recArray(3)
      }).
      saveAsTextFile(orderTextFileOutputLocation)

    // Verify Text files
    sc.
      textFile(orderTextFileOutputLocation).
      take(20).
      foreach(println)

    println("**** Problem Statement : Write & Verify Text data with GZIP compression ****")

    val orderTextGzipFileLocation = orderTextFileOutputLocation + "_gzip"

    orderRdd.
      map((rec: String) => {
        val recArray = rec.split(",")
        recArray(0).toInt +"\t"+ recArray(1) +"\t"+ recArray(2).toInt +"\t"+ recArray(3)
      }).
      saveAsTextFile(orderTextGzipFileLocation, classOf[org.apache.hadoop.io.compress.GzipCodec])

    // Verify Text files
    sc.
      textFile(orderTextGzipFileLocation).
      take(20).
      foreach(println)

    println("**** Problem Statement : Write & Verify Text data with BZIP2 compression ****")

    val orderTextBzipFileLocation = orderTextFileOutputLocation + "_bzip2"

    orderRdd.
      map((rec: String) => {
        val recArray = rec.split(",")
        recArray(0).toInt +"\t"+ recArray(1) +"\t"+ recArray(2).toInt +"\t"+ recArray(3)
      }).
      saveAsTextFile(orderTextBzipFileLocation, classOf[org.apache.hadoop.io.compress.BZip2Codec])

    // Verify Text files
    sc.
      textFile(orderTextBzipFileLocation).
      take(20).
      foreach(println)


    println("**** Problem Statement : Write & Verify Text data with Snappy compression ****")

    println("!!! snappy with text file may not be working in local mode, but it should work in yarn mode !!!")
    val orderTextSnappyFileLocation = orderTextFileOutputLocation + "_snappy"

    orderRdd.
      map((rec: String) => {
        val recArray = rec.split(",")
        recArray(0).toInt +"\t"+ recArray(1) +"\t"+ recArray(2).toInt +"\t"+ recArray(3)
      }).
      saveAsTextFile(orderTextSnappyFileLocation, classOf[org.apache.hadoop.io.compress.SnappyCodec])

    // Verify Text files
    sc.
      textFile(orderTextBzipFileLocation).
      take(20).
      foreach(println)
  }
}
