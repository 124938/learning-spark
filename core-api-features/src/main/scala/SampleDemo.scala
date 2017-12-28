import org.apache.spark.{SparkConf, SparkContext}

object SampleDemo {
  def main(args: Array[String]): Unit = {

    println("========= Creating SparkContext ========")
    val conf = new SparkConf().
      setAppName("Action Demo").
      setMaster("local[2]")

    val sc = new SparkContext(conf)

    println("======= Creating RDD from text file ========")
    val orders = sc.textFile("/home/asus/source_code/github/124938/learning-spark/core-api-features/src/main/resources/retail_db/orders")

    println("======= Previewing Data : Usage ========")

    orders.take(10).foreach(println)
  }
}
