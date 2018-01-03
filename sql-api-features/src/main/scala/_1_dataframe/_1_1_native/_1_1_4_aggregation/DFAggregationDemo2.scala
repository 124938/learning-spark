package _1_dataframe._1_1_native._1_1_4_aggregation

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

object DFAggregationDemo2 {

  def main(args: Array[String]): Unit = {

    // Create instance of SparkConf
    val conf = new SparkConf().
      setAppName("Aggregation Demo 2 - Using DataFrame SQLContext").
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

    println("******** Problem Statement : Find out lowest priced product by category *******")

    // Create DataFrame for products
    val productsDF = sqlContext.
      read.
      avro("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/products/avro")

    // Print schema from DataFrame
    productsDF.
      printSchema

    println("===== Approach 1 - Using DSL Way (groupBy, agg [min], orderBy) ===")
    val minPriceProduct = productsDF.
      groupBy($"product_category_id" as "pc_id").
      agg(org.apache.spark.sql.functions.min($"product_price") as "min_product_price")

    productsDF.
      join(minPriceProduct, $"pc_id" === $"product_category_id" and $"min_product_price" === $"product_price").
      orderBy($"product_category_id" asc).
      show(30)

    println("===== Approach 2 - Using SQL Way (GROUP BY, [MIN], ORDER BY) =====")
    productsDF.
      registerTempTable("products")

    sqlContext.
      sql(
        " SELECT "+
        "   * "+
        " FROM products p JOIN "+
        "   ( "+
        "     SELECT "+
        "       product_category_id as pc_id, "+
        "       MIN(product_price) as min_product_price "+
        "     FROM "+
        "       products "+
        "     GROUP BY "+
        "       product_category_id "+
        "   ) pc "+
        " ON (p.product_category_id = pc.pc_id and p.product_price = pc.min_product_price) "+
        " ORDER BY "+
        "   product_category_id"
      ).
      show(30)
  }
}
