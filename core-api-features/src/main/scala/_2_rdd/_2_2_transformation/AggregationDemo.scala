package _2_rdd._2_2_transformation

import org.apache.spark.{SparkConf, SparkContext}

object AggregationDemo {
  def main(args: Array[String]): Unit = {
    println("========= Creating SparkContext ========")
    val conf = new SparkConf().
      setAppName("Action Demo").
      setMaster("local[2]")

    val sc = new SparkContext(conf)

    println("======= Creating RDD from text file ========")
    val orderItems = sc.textFile("/home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/retail_db/order_items")

    // Verification code
    orderItems.
      filter((rec: String) => rec.split(",")(1).toInt == 65722).
      collect().
      foreach(println)

    println("**** Generate revenue for each order - using groupByKey ****")
    val orderRevGBK = orderItems.
      map((rec: String) => {
        val recArray = rec.split(",")
        (recArray(1).toInt, recArray(4).toFloat)
      }).
      groupByKey()

    // Print DAG for groupByKey
    orderRevGBK.
      toDebugString

    // Calculate order revenue
    orderRevGBK.
      map((t: (Int, Iterable[Float])) => (t._1, t._2.sum)).
      foreach(println)

    println("**** Generate revenue for each order - using reduceByKey ****")
    val orderRevRBK = orderItems.
      map((rec: String) => {
        val recArray = rec.split(",")
        (recArray(1).toInt, recArray(4).toFloat)
      }).
      reduceByKey((aggTotal: Float, ele: Float) => aggTotal + ele)

    // Print DAG for reduceByKey
    orderRevRBK.
      toDebugString

    // Calculate order revenue
    orderRevRBK.
      take(10).
      foreach(println)

    println("**** Generate revenue with number of quantity for each order - using reduceByKey ****")
    orderItems.
      map((rec: String) => {
        val recArray = rec.split(",")
        (recArray(1).toInt, (recArray(4).toFloat, 1))
      }).
      reduceByKey((agg: (Float, Int), ele: (Float, Int)) => (agg._1 + ele._1, agg._2 + ele._2)).
      take(10).
      foreach(println)

    println("**** Generate revenue with number of quantity for each order - using aggregateByKey ****")
    orderItems.
      map((rec: String) => {
        val recArray = rec.split(",")
        (recArray(1).toInt, recArray(4).toFloat)
      }).
      aggregateByKey[(Float, Int)]((0.0F, 0))(
        (aggComb: (Float, Int), eleComb:Float) => (aggComb._1 + eleComb, aggComb._2 + 1),
        (agg: (Float, Int), ele: (Float, Int)) => (agg._1 + ele._1, agg._2 + ele._2)).
      take(10).
      foreach(println)
  }
}
