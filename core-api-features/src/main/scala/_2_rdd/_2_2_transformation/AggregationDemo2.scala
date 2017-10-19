package _2_rdd._2_2_transformation

import org.apache.spark.{SparkConf, SparkContext}

object AggregationDemo2 {
  def main(args: Array[String]): Unit = {
    println("========= Creating SparkContext ========")
    val conf = new SparkConf().
      setAppName("Action Demo").
      setMaster("local[2]")

    val sc = new SparkContext(conf)

    println("======= Creating RDD from text file ========")
    val products = sc.textFile("/home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/retail_db/products")

    println("======= Usage - Aggregation ========")

    println("**** Find out lowest priced product by category - using reduceByKey ****")
    products.
      filter((rec: String) => rec.split(",")(4) != "").
      map((rec: String) => (rec.split(",")(1).toInt, rec)).
      reduceByKey((minProduct: String, eleProduct: String) => {
        if (minProduct.split(",")(4).toFloat < eleProduct.split(",")(4).toFloat)
          minProduct
        else
          eleProduct
      }).
      map((t: (Int, String)) => t._2).
      take(10).
      foreach(println)

    // Verification code
    products.
      filter((rec: String) => rec.split(",")(1).toInt == 34).
      collect().
      foreach(println)
  }
}
