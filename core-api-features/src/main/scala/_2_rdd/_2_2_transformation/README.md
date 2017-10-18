## RDD - Transformation
At high level actions can be grouped into following categories:

### Row Level Transformations:
  
* Followings are typical use cases for applying row level transformations:
  * _Data Cleansing_ : e.g. Removing special characters (`map`)
  * _Data Standardization_ e.g. SSN number, phone numbers etc. should be in specific format (`map`) 
  * _Data Filtering_ i.e.  Removing un-necessary data (`map`)
  * _Data Un-Pivoting_ i.e. One row with multiple columns might have to return a collection of rows (`flatMap`)

* Below are few important APIs for applying row level transformations:
  
| Method Name | Description |
| ----------- | ----------- |
| map(func) | Return a new distributed dataset formed by passing each element of the source through a function func |
| filter(func) | Return a new dataset formed by selecting those elements of the source on which func returns true |
| flatMap(func) | Similar to map, but each input item can be mapped to 0 or more output items (so func should return a Seq rather than a single item) |

### Aggregation:
  
* Map-Reduce Recap:
  
* Below are few important APIs for performing aggregations:

| Method Name | Description |
| ----------- | ----------- |
| groupByKey([numTasks]) | When called on a dataset of (K, V) pairs, returns a dataset of (K, Iterable<V>) pairs |
| reduceByKey(func, [numTasks]) | When called on a dataset of (K, V) pairs, returns a dataset of (K, V) pairs where the values for each key are aggregated using the given reduce function func, which must be of type (V,V) => V |
| aggregateByKey(zeroValue)(seqOp, combOp, [numTasks]) | When called on a dataset of (K, V) pairs, returns a dataset of (K, U) pairs where the values for each key are aggregated using the given combine functions and a neutral "zero" value. Allows an aggregated value type that is different than the input value type, while avoiding unnecessary allocations |

* Characteristics to be considered while using aggregation to solve specific business use case:

| reduceByKey | aggregateByKey | groupByKey |
| ----------- | ----------- | ----------- |
| Take 1 parameter as function - for seqOp and combOp | Take 2 parameter as function 1st for seqOp and 2nd for combOp | No parameters as function - Generally followed by map or flatMap |
| Uses combiner implicitly | Uses combiner explicitly| Does not use combiner |
| seqOp (or combiner) logic is same as combOp (or reducer) logic | seqOp (or combiner) logic is different from combOp (or reducer) logic | No combiner |
| Performance is great for aggregation | Performance is good for aggregation | Relatively slower for aggregation |
| Applicable for aggregation only | Applicable for aggregation only | Applicable for aggregation, sorting & ranking etc. |
| e.g. sum, min, max etc. | e.g. average | e.g. any aggregation is possible but not preferred due to performance hit |
 
* _Note:_
    * If you are grouping in order to perform an aggregation (such as a sum or average) over each key, using reduceByKey or aggregateByKey will yield much better performance. 
    * By default, the level of parallelism in the output depends on the number of partitions of the parent RDD. You can pass an optional numTasks argument to set a different number of tasks.