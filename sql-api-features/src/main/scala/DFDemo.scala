import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

object DFDemo {
  def main(args: Array[String]): Unit = {

    // Create Spark Context
    val conf = new SparkConf().
      setAppName("DataFrame - Demo").
      setMaster("local[2]")

    val sc = new SparkContext(conf)

    // Create SQL Context
    val sqlContext = new SQLContext(sc)

    // Below is used to implicitly convert RDD to DataFrame
    import sqlContext.implicits._

    // Create DataFrame using RDD
    val orderDF = sc.
      textFile("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/orders/text").
      map((rec: String) => rec.split(",")).
      map((recArray: Array[String]) => (recArray(0).toInt, recArray(1), recArray(2).toInt, recArray(3))).
      toDF("order_id", "order_date", "order_customer_id", "order_status")

    // Print schema of orders DataFrame
    orderDF.
      printSchema

    // Preview 20 records from orders DataFrame
    orderDF.
      show(20)
  }
}
