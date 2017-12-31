package _1_dataframe._1_1_native._1_1_1_transformation

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

object DFCartesianJoinDemo {

  def main(args: Array[String]): Unit = {

    // Create instance of SparkConf
    val conf = new SparkConf().
      setAppName("Cartesian Join Demo - Using DataFrame SQLContext").
      setMaster("local[2]")

    // Create instance of SparkContext
    val sc = new SparkContext(conf)

    // Create instance of SQLContext
    val sqlContext = new SQLContext(sc)
    sqlContext.
      setConf("spark.sql.shuffle.partitions", "2")

    // Below is used to convert RDD to DataFrame implicitly
    import sqlContext.implicits._

    println("***** Problem Statement : Generate cartesian of employees and departments *****")

    // Create DataFrame from employees
    val employeesDF = sc.
      parallelize[(String, Int)](
        Seq(
          ("Shreyash", 36),
          ("Bonika", 33),
          ("Dherya", 10),
          ("Hrisha", 3)
        )
      ).toDF("first_name", "age")

    // Create DataFrame from departments
    val departmentDF = sc.
      parallelize[(Int, String)](
      Seq(
        (1, "HR"),
        (2, "Admin"),
        (3, "Finance")
      )
    ).toDF("dept_id", "dept_name")

    println("===== Approach 1 - DSL Way (join) ======")
    employeesDF.
      join(departmentDF).
      show

    println("===== Approach 2 - SQL Way (join) ======")
    employeesDF.
      registerTempTable("EMPL")

    departmentDF.
      registerTempTable("DEPT")

    sqlContext.
      sql(
        " SELECT "+
        "   *  "+
        " FROM "+
        "   EMPL JOIN DEPT"
      ).
      show
  }
}
