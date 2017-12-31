package _1_dataframe._1_1_native._1_1_1_transformation

import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions._
import org.apache.spark.{SparkConf, SparkContext}

object DFAggregationDemo1 {

  def main(args: Array[String]): Unit = {

    // Create instance of SparkConf
    val conf = new SparkConf().
      setAppName("Aggregation Demo - Using DataFrame SQLContext").
      setMaster("local[2]")

    // Create instance of SparkContext
    val sc = new SparkContext(conf)

    // Create instance of SQLContext
    val sqlContext = new SQLContext(sc)
    sqlContext.
      setConf("spark.sql.shuffle.partitions", "2")

    // this is used to implicitly convert an RDD to DataFrame
    import sqlContext.implicits._

    // this is used to import
    import com.databricks.spark.avro._

    println("******** Problem Statement : Generate order revenue with item count for each order *******")

    // Create DataFrame for order_items
    val orderItemsDF = sqlContext.
      read.
      avro("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/order_items/avro")

    // Print schema from DataFrame
    orderItemsDF.
      printSchema()

    // Preview records from DataFrame
    orderItemsDF.
      show(10)

    println("===== Approach 1 - Using DSL Way (groupBy, agg [sum, count], orderBy, select) ===")
    orderItemsDF.
      groupBy($"order_item_order_id".as("order_id")).
      agg(sum($"order_item_subtotal").as("order_revenue"), count($"order_item_order_id").as("order_item_count")).
      orderBy($"order_id".asc).
      select(
        $"order_id",
        $"order_revenue",
        $"order_item_count"
      ).
      show(30)

    println("===== Approach 2 - Using SQL Way (GROUP BY, [SUM, COUNT], ORDER BY) =====")
    orderItemsDF.
      registerTempTable("order_items")

    sqlContext.
      sql(
        " SELECT "+
        "   order_item_order_id as order_id, "+
        "   SUM(order_item_subtotal) as order_revenue, "+
        "   COUNT(order_item_order_id) as order_item_count "+
        " FROM "+
        "   order_items "+
        " GROUP BY "+
        "   order_item_order_id "+
        " ORDER BY "+
        "   order_id"
      ).
      show(30)
  }
}
