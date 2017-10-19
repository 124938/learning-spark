package _2_rdd._2_2_transformation

import org.apache.spark.{SparkConf, SparkContext}

object JoinDemo2 {
  def main(args: Array[String]): Unit = {
    println("========= Creating SparkContext ========")
    val conf = new SparkConf().
      setAppName("Action Demo").
      setMaster("local[2]")

    val sc = new SparkContext(conf)

    println("======= Creating RDD from text file ========")
    val orders = sc.textFile("/home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/retail_db/orders")
    val orderItems = sc.textFile("/home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/retail_db/order_items")

    println("======= Usage - leftOuterJoin ========")

    println("**** Find out order records, which does not have any order items - using leftOuterJoin ****")

    // Create RDD with tuple from order records i.e. (order_id, whole order record)
    val orderData = orders.
      map((rec: String) => (rec.split(",")(0).toInt, rec))

    // Create RDD with tuple from order item records i.e. (order_item_order_id, whole order item record)
    val orderItemData = orderItems.
      map((rec: String) => (rec.split(",")(1).toInt, rec))

    // Perform left outer join between two RDDs
    val orderLeftOuterJoin = orderData.
      leftOuterJoin(orderItemData)

    // Calculate count
    orderLeftOuterJoin.
      filter((t: (Int, (String, Option[String]))) => t._2._2 == None).
      map(t => t._2._1).
      count()

    println("======= Usage - rightOuterJoin ========")

    println("**** Find out order item records, which does not linked with any order - using rightOuterJoin ****")

    // Perform right outer join between two RDDs
    val orderRightOuterJoin = orderData.
      rightOuterJoin(orderItemData)

    // Calculate count
    orderRightOuterJoin.
      filter((t: (Int, (Option[String], String))) => t._2._1 == None).
      count()

    println("======= Usage - fullOuterJoin ========")

    println("**** Preview data - using rightOuterJoin ****")

    // Perform right outer join between two RDDs
    val orderFullOuterJoin = orderData.
      fullOuterJoin(orderItemData)

    // Preview data
    orderFullOuterJoin.
      take(100).
      foreach(println)

    // Count
    orderFullOuterJoin.
      count()
  }
}
