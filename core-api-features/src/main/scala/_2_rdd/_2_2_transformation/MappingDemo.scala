package _2_rdd._2_2_transformation

import org.apache.spark.{SparkConf, SparkContext}

object MappingDemo {
  def main(args: Array[String]): Unit = {
    println("========= Creating SparkContext ========")
    val conf = new SparkConf().
      setAppName("Action Demo").
      setMaster("local[2]")

    val sc = new SparkContext(conf)

    println("======= Creating RDD from text file ========")
    val orders = sc.textFile("/home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/retail_db/orders")
    val orderItems = sc.textFile("/home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/retail_db/order_items")

    println("******* Usage - map *******")

    // Map each order record into order id
    orders.
      map((rec: String) => rec.split(",")(0).toInt).
      take(20).
      foreach(println)

    // Map each order item record into order item id
    orderItems.
      map((rec: String) => rec.split(",")(0).toInt).
      take(10).
      foreach(println)

    println("******* Usage - filter *******")

    // Filter out all order records with order status marked as COMPLETE & CLOSED
    orders.
      filter((rec: String) => {
        val recArray = rec.split(",")
        recArray(3) == "COMPLETE" || recArray(3) == "CLOSED"
      }).
      take(20).
      foreach(println)

    // Filter out all order records with order status containing PENDING
    orders.
      filter((rec: String) => rec.split(",")(3).contains("PENDING")).
      take(10).
      foreach(println)

    // Filter out all order item records with sub total greater then 1000$
    orderItems.
      filter((rec: String) => rec.split(",")(4).toFloat > 1000).
      take(10).
      foreach(println)

    println("******* Usage - flatMap *******")

    val lines = Array("Hello World", "How are you", "Here we are trying", "to compute word count")

    // Scala way
    lines.
      flatMap((line: String) => line.split(" ")).
      foreach(println)

    // Spark way
    sc.
      parallelize(lines).
      flatMap((line: String) => line.split(" ")).
      collect().
      foreach(println)
  }
}
