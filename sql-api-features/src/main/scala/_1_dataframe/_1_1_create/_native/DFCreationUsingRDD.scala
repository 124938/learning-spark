package _1_dataframe._1_1_create._native

import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.{SparkConf, SparkContext}

case class OrderRDD(order_id: Int, order_date: String, order_customer_id: Int, order_status: String)

object DFCreationUsingRDD {
  def main(args: Array[String]): Unit = {

    // Create Spark Configuration
    val conf = new SparkConf().
      setAppName("DF Creation Using RDD").
      setMaster("local[2]")

    // Create Spark Context
    val sc = new SparkContext(conf)

    // Create SQL Context
    val sqlContext = new SQLContext(sc)

    // Below is used to create DataFrame from RDD
    import sqlContext.implicits._

    println("**** Problem Statement : Create DataFrame - By inferring the schema using reflection i.e. case class ****")
    val orderDF1 = sc.
      textFile("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/orders/text").
      map((rec: String) => {
        val recArray = rec.split(",")
        OrderRDD(recArray(0).toInt, recArray(1), recArray(2).toInt, recArray(3))
      }).
      toDF()

    // Preview data
    orderDF1.
      show(20)

    println("**** Problem Statement : Create DataFrame - By programmatically specifying the schema using StructField ****")
    val orderRowRDD = sc.
      textFile("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/orders/text").
      map((rec: String) => {
        val recArray = rec.split(",")
        Row(recArray(0).toInt, recArray(1), recArray(2).toInt, recArray(3))
      })

    val orderRowSchema = StructType(
      List(
        StructField("order_id", IntegerType),
        StructField("order_date", StringType),
        StructField("order_customer_id", IntegerType),
        StructField("order_status", StringType)
      )
    )

    val orderDF2 = sqlContext.
      createDataFrame(orderRowRDD, orderRowSchema)

    // Preview data
    orderDF2.
      show(20)

    println("**** Problem Statement : Create DataFrame - By programmatically specifying the schema using toDF ****")
    val orderDF3 = sc.
      textFile("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/orders/text").
      map((rec: String) => {
        val recArray = rec.split(",")
        (recArray(0).toInt, recArray(1), recArray(2).toInt, recArray(3))
      }).
      toDF("order_id", "order_date", "order_customer_id", "order_status")

    // Preview data
    orderDF3.
      show(20)
  }
}
