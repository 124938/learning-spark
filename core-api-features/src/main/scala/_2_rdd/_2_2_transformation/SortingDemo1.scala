package _2_rdd._2_2_transformation

import org.apache.spark.{SparkConf, SparkContext}

object SortingDemo1 {
  def main(args: Array[String]): Unit = {
    println("========= Creating SparkContext ========")
    val conf = new SparkConf().
      setAppName("Sorting - Demo 1").
      setMaster("local[2]")

    val sc = new SparkContext(conf)

    println("======= Creating RDD from text file ========")
    val orders = sc.textFile("/home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/retail_db/orders")
    val orderItems = sc.textFile("/home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/retail_db/order_items")

    println("======= Usage - sortByKey ========")

    println("**** Sort order data based on order_customer_id in ascending order - using sortByKey ****")
    orders.
      map((rec: String) => {
        val recArray = rec.split(",")
        (recArray(2).toInt, rec)
      }).
      sortByKey().
      map((t: (Int, String)) => t._2).
      take(50).
      foreach(println)

    println("**** Sort order data based on order_customer_id + order_status both in ascending order - using sortByKey ****")
    orders.
      map((rec: String) => {
        val recArray = rec.split(",")
        ((recArray(2).toInt, recArray(3)), rec)
      }).
      sortByKey().
      map((t: ((Int, String), String)) => t._2).
      take(50).
      foreach(println)

    println("**** Sort order data based on order_customer_id + order_status both in descending order - using sortByKey ****")
    orders.
      map((rec: String) => {
        val recArray = rec.split(",")
        ((recArray(2).toInt, recArray(3)), rec)
      }).
      sortByKey(false).
      map((t: ((Int, String), String)) => t._2).
      take(50).
      foreach(println)

    println("**** Sort order data based on order_status in ascending order + order_customer_id in descending order - using sortByKey ****")
    orders.
      map((rec: String) => {
        val recArray = rec.split(",")
        ((recArray(3), -recArray(2).toInt), rec)
      }).
      sortByKey().
      map((t: ((String, Int), String)) => t._2).
      take(50).
      foreach(println)
  }
}
