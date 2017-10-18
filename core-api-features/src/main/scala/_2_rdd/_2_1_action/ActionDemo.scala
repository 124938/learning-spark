package _2_rdd._2_1_action

import org.apache.spark.{SparkConf, SparkContext}

object ActionDemo {
  def main(args: Array[String]): Unit = {
    println("========= Creating SparkContext ========")
    val conf = new SparkConf().
      setAppName("Action Demo").
      setMaster("local[2]")

    val sc = new SparkContext(conf)

    println("======= Creating RDD from text file ========")
    val orders = sc.textFile("/home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/retail_db/orders")
    val orderItems = sc.textFile("/home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/retail_db/order_items")

    println("======= Previewing Data : Usage ========")

    // For Order
    orders.first()
    orders.take(3).foreach(println)
    orders.collect().foreach(println)

    // For Order Items
    orderItems.first()
    orderItems.take(3).foreach(println)
    orderItems.collect().foreach(println)

    println("======= Aggregation : Usage ========")

    // For Order
    orders.count()

    println("****** Find out order record with min of customer id *******")
    orders.reduce((min: String, ele: String) => {
      if (min.split(",")(2).toInt < ele.split(",")(2).toInt)
        min
      else
        ele
    })

    // For Order Items
    orderItems.count()

    println("****** Find out sum of all order items *******")
    orderItems.
      map[Float]((rec: String) => rec.split(",")(4).toFloat).
      sum()

    orderItems.
      map[Float]((rec: String) => rec.split(",")(4).toFloat).
      reduce((aggTotal: Float, ele: Float) => aggTotal + ele)

    println("======= Sorting : Usage ========")

    // For Order
    println("***** Sort order data (with default implementation) & fetch top 10 records ******")
    orders.top(5).foreach(println)

    println("****** Sort order data based on order id (max to min) & fetch 10 records out of it *******")
    orders.
      takeOrdered(10)(Ordering[Int].on((rec: String) => -rec.split(",")(0).toInt)).
      foreach(println)

    // For Order Items
    println("***** Sort order items data (with default implementation) & fetch top 20 records ******")
    orderItems.top(20).foreach(println)

    println("****** Sort order items data based on order item id (max to min) & fetch 20 records out of it *******")
    orderItems.
      top(20)(Ordering[Int].on((rec: String) => rec.split(",")(0).toInt)).
      foreach(println)

    println("======= Save data : Usage ========")

    // For Order
    orders.
      filter((rec: String) => rec.split(",")(3).contains("PENDING")).
      saveAsTextFile("/home/asus/source_code/github/124938/learning-spark/core-api-features/out")

    // For Order Items
    orderItems.
      filter((rec: String) => rec.split(",")(4).toFloat >= 1500).
      saveAsTextFile("/home/asus/source_code/github/124938/learning-spark/core-api-features/out1")
  }
}
