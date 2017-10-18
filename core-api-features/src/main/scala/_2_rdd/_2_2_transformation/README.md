## Map Reduce - Recap

### Working Model

* **Without combiner**

  ![Alt text](images/map-reduce-without-combiner.jpg?raw=true "Map Reduce Without Combiner - Word Count")
  
* **With combiner**
  
  ![Alt text](images/map-reduce-with-combiner.jpg?raw=true "Map Reduce With Combiner - Word Count")

### Important terms

* **What is Mapper?**
  * Applying row level transformation comes under mapper phase 
  * e.g. map, filter, flatMap (In context of spark)

* **What is Shuffling?**
  * The process of grouping & moving data is called as shuffling, which is typically provided by framework

* **What is Reducer?**
  * Applying transformation which requires at-least 2 records comes under reducer phase 
  * e.g. reduce, groupByKey, reduceByKey, aggregateByKey, join, union etc.  (In context of spark)

* **What is Combiner?**
  * Applying aggregation at mapper side called as combiner

## RDD - Transformation
At high level actions can be grouped into following categories:

### Row level transformations OR Mapping:
  
* Followings are typical use cases for applying row level transformations:
  * _Data Cleansing_ : e.g. Removing special characters (`map`)
  * _Data Standardization_ e.g. SSN number, phone numbers etc. should be in specific format (`map`) 
  * _Data Filtering_ i.e.  Removing un-necessary data (`map`)
  * _Data Un-Pivoting_ i.e. One row with multiple columns might have to return a collection of rows (`flatMap`)

* Below are few important APIs for applying row level transformations on RDD:
  
| Method Name | Description |
| ----------- | ----------- |
| map(func) | Return a new distributed dataset formed by passing each element of the source through a function func |
| filter(func) | Return a new dataset formed by selecting those elements of the source on which func returns true |
| flatMap(func) | Similar to map, but each input item can be mapped to 0 or more output items (so func should return a Seq rather than a single item) |

### Aggregation:

* Below are few important APIs for performing aggregations on RDD:

| Method Name | Description |
| ----------- | ----------- |
| groupByKey([numTasks]) | When called on a dataset of (K, V) pairs, returns a dataset of (K, Iterable<V>) pairs |
| reduceByKey(func, [numTasks]) | When called on a dataset of (K, V) pairs, returns a dataset of (K, V) pairs where the values for each key are aggregated using the given reduce function func, which must be of type (V,V) => V |
| aggregateByKey(zeroValue)(seqOp, combOp, [numTasks]) | When called on a dataset of (K, V) pairs, returns a dataset of (K, U) pairs where the values for each key are aggregated using the given combine functions and a neutral "zero" value. Allows an aggregated value type that is different than the input value type, while avoiding unnecessary allocations |

* Characteristics to be considered while implementing aggregation functionality for business use case:

| reduceByKey | aggregateByKey | groupByKey |
| ----------- | ----------- | ----------- |
| Take 1 parameter as function - for seqOp and combOp | Take 2 parameter as function 1st for seqOp and 2nd for combOp | No parameters as function - Generally followed by map or flatMap |
| Uses combiner implicitly | Uses combiner explicitly| Does not use combiner |
| seqOp (or combiner) logic is same as combOp (or reducer) logic | seqOp (or combiner) logic is different from combOp (or reducer) logic | No combiner |
| Performance is great for aggregation | Performance is good for aggregation | Relatively slower for aggregation |
| Applicable for aggregation only | Applicable for aggregation only | Applicable for aggregation, sorting & ranking etc. |
| e.g. sum, min, max etc. | e.g. average | e.g. any aggregation is possible but not preferred due to performance hit |

* Visual presentation for groupByKey & reduceByKey

![Alt text](images/group-by-key.jpg?raw=true "Group By Key - Word Count")

![Alt text](images/reduce-by-key.png?raw=true "Reduce By Key - Word Count")

* _Note:_
    * If you are grouping in order to perform an aggregation (such as a sum or average) over each key, using reduceByKey or aggregateByKey will yield much better performance
    * By default, the level of parallelism in the output depends on the number of partitions of the parent RDD. You can pass an optional numTasks argument to set a different number of tasks

### Join:

* Below are few important APIs for performing join operations between two RDDs:

| Method Name | Description |
| ----------- | ----------- |
| join(otherDataset, [numTasks]) | When called on datasets of type (K, V) and (K, W), returns a dataset of (K, (V, W)) pairs with all pairs of elements for each key |
| leftOuterJoin(otherDataset, [numTasks]) | When called on datasets of type (K, V) and (K, W), returns a dataset of (K, (V, Some(W))) pairs with all pairs of elements for each key |
| rightOuterJoin(otherDataset, [numTasks]) | When called on datasets of type (K, V) and (K, W), returns a dataset of (K, (Some(V), W)) pairs with all pairs of elements for each key |
| fullOuterJoin(otherDataset, [numTasks]) | When called on datasets of type (K, V) and (K, W), returns a dataset of (K, (Some(V), Some(W))) pairs with all pairs of elements for each key |

| Method Name | Description |
| ----------- | ----------- |
| cartesian(otherDataset) | When called on datasets of types T and U, returns a dataset of (T, U) pairs (all pairs of elements) |
| cogroup(otherDataset, [numTasks]) | When called on datasets of type (K, V) and (K, W), returns a dataset of (K, (Iterable<V>, Iterable<W>)) tuples |

### Set:

* Below are few important APIs for performing set operations on RDD:

| Method Name | Description |
| ----------- | ----------- |
| union(otherDataset) | Return a new dataset that contains the union of the elements in the source dataset and the argument |
| intersection(otherDataset) | Return a new RDD that contains the intersection of elements in the source dataset and the argument |
| distinct([numTasks])) | Return a new dataset that contains the distinct elements of the source dataset |

### Sorting & Ranking:

* Below are few important APIs for performing sorting operations on RDD:

| Method Name | Description |
| ----------- | ----------- |
| groupByKey([numTasks]) | When called on a dataset of (K, V) pairs, returns a dataset of (K, Iterable<V>) pairs |
| sortByKey([ascending], [numTasks]) | When called on a dataset of (K, V) pairs where K implements Ordered, returns a dataset of (K, V) pairs sorted by keys in ascending or descending order, as specified in the boolean ascending argument |