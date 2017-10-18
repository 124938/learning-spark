## Action 
RDD supports many actions and at high level actions can be grouped into following categories:

* **Previewing Data:**

| Method Name | Description |
| ----------- | ----------- |
| first() | Return the first element of the dataset (similar to take(1)) |
| take(n) | Return an array with the first n elements of the dataset |
| takeSample(withReplacement, num, [seed]) | Return an array with a random sample of num elements of the dataset, with or without replacement, optionally pre-specifying a random number generator seed |
| collect() | Return all the elements of the dataset as an array at the driver program. This is usually useful after a filter or other operation that returns a sufficiently small subset of the data |

_Note:_
  * Typical use case for above APIs are to preview OR validate data (for development purpose)
  * On top of `take` & `collect`, `foreach` is used to display result in readable as well as custom format
  * Never use any of above APIs as part of applications which is suppose to deploy in production, unless and until it is inevitable
 
* **Aggregation:**

| Method Name | Description |
| ----------- | ----------- |
| count() |	Return the number of elements in the dataset |
| countByKey() | Only available on RDDs of type (K, V). Returns a hashmap of (K, Int) pairs with the count of each key |
| reduce(func) | Aggregate the elements of the dataset using a function func (which takes two arguments and returns one). The function should be commutative and associative so that it can be computed correctly in parallel e.g. sum, min, max etc. |

* **Sorting & Ranking:**

| Method Name | Description |
| ----------- | ----------- |
| top(n) | Return an array with the top n elements of the dataset (Sorting happens based on natural order or element present in RDD) |
| takeOrdered(n, [ordering]) | Return the first n elements of the RDD using either their natural order or a custom comparator |

* **Saving Output:**

| Method Name | Description |
| ----------- | ----------- |
| saveAsTextFile(path) | Write the elements of the dataset as a text file (or set of text files) in a given directory in the local filesystem, HDFS or any other Hadoop-supported file system. Spark will call toString on each element to convert it to a line of text in the file |
| saveAsSequenceFile(path) | Write the elements of the dataset as a Hadoop SequenceFile in a given path in the local filesystem, HDFS or any other Hadoop-supported file system. This is available on RDDs of key-value pairs that implement Hadoop's Writable interface. In Scala, it is also available on types that are implicitly convertible to Writable (Spark includes conversions for basic types like Int, Double, String, etc) |
| saveAsObjectFile(path) | Write the elements of the dataset in a simple format using Java serialization, which can then be loaded using SparkContext.objectFile() |
