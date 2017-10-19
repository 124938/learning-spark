package _2_rdd._2_2_transformation

import org.apache.spark.{SparkConf, SparkContext}

object SortingDemo2 {
  def main(args: Array[String]): Unit = {
    println("========= Creating SparkContext ========")
    val conf = new SparkConf().
      setAppName("Sorting - Demo 2").
      setMaster("local[2]")

    val sc = new SparkContext(conf)

    println("======= Creating RDD from text file ========")
    val products = sc.textFile("/home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/retail_db/products")

    println("======= Usage - groupByKey ========")
    val productsByCategory = products.
      filter((rec: String) => rec.split(",")(4) != "").
      map((rec: String) => (rec.split(",")(1).toInt, rec)).
      groupByKey()

    println("**** Find out top N products by price for each category i.e. parsed ranking - using groupByKey ****")
    productsByCategory.
      flatMap((t: (Int, Iterable[String])) => getTopNProductByPrice(t, 15)).
      take(100).
      foreach(println)

    println("**** Find out top N priced product for each category i.e. dense ranking - using groupByKey ****")
    productsByCategory.
      flatMap((t: (Int, Iterable[String])) => getTopNPricedProduct(t, 4)).
      take(100).
      foreach(println)
  }

  def getTopNProductByPrice(t: (Int, Iterable[String]), n: Int) :Iterable[String] = {
    t._2.
      toList.
      sortBy((rec: String) => rec.split(",")(4).toFloat).
      take(n)
  }

  def getTopNPricedProduct(t: (Int, Iterable[String]), n: Int) :Iterable[String] = {
    val sortedProductList = t._2.
      toList.
      sortBy((rec: String) => -rec.split(",")(4).toFloat)

    val priceSet = sortedProductList.
      map((rec: String) => rec.split(",")(4).toFloat).
      toSet.
      take(n)

    sortedProductList.filter((rec: String) => priceSet.contains(rec.split(",")(4).toFloat))
  }
}
