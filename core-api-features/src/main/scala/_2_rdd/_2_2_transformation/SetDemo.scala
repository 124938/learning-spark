package _2_rdd._2_2_transformation

import org.apache.spark.{SparkConf, SparkContext}

object SetDemo {
  def main(args: Array[String]): Unit = {
    println("========= Creating SparkContext ========")
    val conf = new SparkConf().
      setAppName("Set - Demo").
      setMaster("local[2]")

    val sc = new SparkContext(conf)

    println("======= Creating RDD from text file ========")
    val orders = sc.textFile("/home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/retail_db/orders")
    val orderItems = sc.textFile("/home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/retail_db/order_items")

    println("======= Usage - union & distinct ========")

    println("**** Find out all distinct product ids sold on 25-dec-2013 & 26-dec-2013 - using union ****")

    val orderItemsData = orderItems.
      map((rec: String) => {
        val recArray = rec.split(",")
        (recArray(1).toInt, recArray(2).toInt)
      })

    // Create RDD by filtering 25-dec-2013 records only with tuple (order_id, order_date)
    val orders25DEC2013 = orders.
      filter((rec: String) => rec.split(",")(1).contains("2013-12-25")).
      map((rec: String) => {
        val recArray = rec.split(",")
        (recArray(0).toInt, recArray(1))
      })

    // Find out product ids sold on 25-dec-2013
    val productIds25DEC2013 = orders25DEC2013.
      join(orderItemsData).
      map((t: (Int, (String, Int))) => t._2._2)

    // Create RDD by filtering 25-dec-2013 records only with tuple (order_id, order_date)
    val orders26DEC2013 = orders.
      filter((rec: String) => rec.split(",")(1).contains("2013-12-26")).
      map((rec: String) => {
        val recArray = rec.split(",")
        (recArray(0).toInt, recArray(1))
      })

    // Find out product ids sold on 26-dec-2013
    val productIds26DEC2013 = orders26DEC2013.
      join(orderItemsData).
      map((t: (Int, (String, Int))) => t._2._2)

    // Count number of product ids sold on 25-dec-2013 & 26-dec-2013
    productIds25DEC2013.
      union(productIds26DEC2013).
      distinct().
      count()

    println("======= Usage - intersection ========")

    println("**** Find out common product ids sold on 25-dec-2013 & 26-dec-2013 - using intersection ****")
    productIds25DEC2013.
      intersection(productIds26DEC2013).
      count()
  }
}
