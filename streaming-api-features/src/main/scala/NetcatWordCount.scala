import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

object NetcatWordCount {

  def main(args: Array[String]): Unit = {
    // Get arguments from program
    val mode = args(0)
    val host = args(1)
    val port = args(2).toInt

    // Create Spark Config
    val conf = new SparkConf().
      setMaster(mode).
      setAppName("Network word count")

    // Create Spark Context
    //val sc = new SparkContext(conf)

    // Create Spark Streaming Context
    val ssc = new StreamingContext(conf, Seconds(5))

    // Create word count program
    val lines = ssc.socketTextStream(host, port)
    val words = lines.flatMap((line: String) => line.split(" "))
    val wordsMap = words.map((word: String) => (word, 1))
    val wordCount = wordsMap.reduceByKey((agg: Int, ele: Int) => agg + ele)

    // Print output
    wordCount.print

    // Start spark streaming
    ssc.start
    ssc.awaitTermination
  }

}
