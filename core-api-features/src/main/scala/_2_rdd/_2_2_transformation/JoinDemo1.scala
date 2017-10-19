package _2_rdd._2_2_transformation

import org.apache.spark.{SparkConf, SparkContext}

object JoinDemo1 {
  def main(args: Array[String]): Unit = {
    println("========= Creating SparkContext ========")
    val conf = new SparkConf().
      setAppName("Action Demo").
      setMaster("local[2]")

    val sc = new SparkContext(conf)

    println("======= Creating RDD from text file ========")
    val orders = sc.textFile("/home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/retail_db/orders")
    val orderItems = sc.textFile("/home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/retail_db/order_items")

    println("======= Usage - join ========")

    println("**** Generate revenue for each day - using join ****")

    // Create RDD with tuple from order records i.e. (order_id, order_date)
    val orderData = orders.
      map((rec: String) => {
        val recArray = rec.split(",")
        (recArray(0).toInt, recArray(1))
      })

    // Create RDD with tuple from order item records i.e. (order_item_order_id, order_item_sub_total)
    val orderItemData = orderItems.
      map((rec: String) => {
        val recArray = rec.split(",")
        (recArray(1).toInt, recArray(4).toFloat)
      })

    // Join two RDDs
    val orderJoin = orderData.
      join(orderItemData)

    // Calculate revenue per order_date
    orderJoin.
      map((t: (Int, (String, Float))) => t._2).
      reduceByKey((agg: Float, ele: Float) => agg + ele).
      take(10).
      foreach(println)

  }
}
