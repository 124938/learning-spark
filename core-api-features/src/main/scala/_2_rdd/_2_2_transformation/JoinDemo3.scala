package _2_rdd._2_2_transformation

import org.apache.spark.{SparkConf, SparkContext}

object JoinDemo3 {
  def main(args: Array[String]): Unit = {
    println("========= Creating SparkContext ========")
    val conf = new SparkConf().
      setAppName("Action Demo").
      setMaster("local[2]")

    val sc = new SparkContext(conf)

    println("======= Creating RDD from text file ========")
    val orders = sc.textFile("/home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/retail_db/orders")
    val orderItems = sc.textFile("/home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/retail_db/order_items")

    println("======= Usage - cogroup ========")

    println("**** Preview data by joining two RDDs - using cogroup ****")

    // Create RDD with tuple from order records i.e. (order_id, whole order record)
    val orderData = orders.
      map((rec: String) => (rec.split(",")(0).toInt, rec))

    // Create RDD with tuple from order item records i.e. (order_item_order_id, whole order item record)
    val orderItemData = orderItems.
      map((rec: String) => (rec.split(",")(1).toInt, rec))

    // Perform cogroup between two RDDs
    val orderCogroup = orderData.
      cogroup(orderItemData)

    // Calculate count
    orderCogroup.
      map((t: (Int, (Iterable[String], Iterable[String]))) => (t._1, (t._2._1.size, t._2._2.size))).
      take(10).
      foreach(println)

    println("======= Usage - cartesian ========")

    println("**** Find out cartesian between two RDDs - using cartesian ****")

    val numList = sc.parallelize(List(1, 2, 3, 4))
    val strList = sc.parallelize(List("Hello", "World"))

    numList.
      cartesian(strList).
      collect().
      foreach(println)
  }
}
