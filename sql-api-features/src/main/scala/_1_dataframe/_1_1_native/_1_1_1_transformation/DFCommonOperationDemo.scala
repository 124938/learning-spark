package _1_dataframe._1_1_native._1_1_1_transformation

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

object DFCommonOperationDemo {
  def main(args: Array[String]): Unit = {

    // Create instance of SparkConf
    val conf = new SparkConf()
      .setAppName("Common Operation Demo - Using DataFrame - SQLContext")
      .setMaster("local[2]")

    // Create instance of SparkContext
    val sc = new SparkContext(conf)

    // Create instance of SQLContext
    val sqlContext = new SQLContext(sc)
    sqlContext.
      setConf("spark.sql.shuffle.partitions", "2")

    // Below is required to convert RDD into DataFrame implicitly
    import sqlContext.implicits._

    // Create DataFrame of orders
    val ordersDF = sc.
      textFile("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/orders/text").
      map((rec: String) => {
        val recArray = rec.split(",")
        (recArray(0).toInt, recArray(1), recArray(2).toInt, recArray(3))
      }).
      toDF("order_id", "order_date", "order_customer_id", "order_status")

    println("****** Print Schema of DataFrame ******")
    ordersDF.
      printSchema()

    println("****** Preview records from DataFrame ******")
    ordersDF.
      show(20)

    println("****** Preview records from DataFrame ******")
    ordersDF.
      take(20).
      foreach(println)

    println("****** Select single column from DataFrame ******")
    ordersDF.
      select($"order_id").
      show(20)

    println("****** Select multiple columns from DataFrame ******")
    ordersDF.
      select(
        $"order_date",
        $"order_status"
      ).
      show(20)

    println("****** Find maximum order_id from DataFrame ******")
    ordersDF.
      select(
        org.apache.spark.sql.functions.max($"order_id").as("max_order_id")
      ).
      show(20)

    println("****** Find minimum order_id from DataFrame ******")
    ordersDF.
      select(
        org.apache.spark.sql.functions.min($"order_id").as("min_order_id")
      ).
      show(20)

    println("****** Find average order_id from DataFrame ******")
    ordersDF.
      select(
        org.apache.spark.sql.functions.avg($"order_id").as("avg_order_id")
      ).
      show(20)
  }
}
