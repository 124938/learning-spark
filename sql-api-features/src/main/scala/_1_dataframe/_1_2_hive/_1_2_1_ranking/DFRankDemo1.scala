package _1_dataframe._1_2_hive._1_2_1_ranking

import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}

object DFRankDemo1 {

  def main(args: Array[String]): Unit = {

    // Create instance of SparkConf
    val conf = new SparkConf().
      setAppName("Rank Demo 1 - Using DataFrame HiveContext").
      setMaster("local[2]")

    // Create instance of SparkContext
    val sc = new SparkContext(conf)

    // Create instance of HiveContext
    val hiveContext = new HiveContext(sc)

    hiveContext.
      setConf("spark.sql.shuffle.partitions", "2")

    println("**** Problem Statement : Find out top 5 priced product per category (including product category, product id, product price) *****")
    val query = """SELECT
                    *
                  FROM
                  (
                    SELECT
                      product_category_id,
                      product_id,
                      product_name,
                      product_price,
                      dense_rank() over (PARTITION BY product_category_id ORDER BY product_price DESC) as product_rank
                    FROM
                      retail_db.products
                  ) q
                  WHERE
                    product_rank <= 5"""

    // Preview data by executing query
    hiveContext.
      sql(query).
      show(50)
  }
}
