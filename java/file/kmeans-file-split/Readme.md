## Kmeans

Name: Kmeans

Contact Person: lucasmsp@dcc.ufmg.br

Platform: COMPSs

Language: Java

Adaptation from BSC's algorithm


### Description

K-means clustering is a method of cluster analysis that aims to partition ''n'' points into ''k'' clusters in which each point belongs to the cluster with the nearest mean. It follows an iterative refinement strategy to find the centers of natural clusters in the data.

When executed with COMPSs, K-means first splits a input dataset in a number of fragments received as parameter, each fragment being created by an initialization task.

After the initialization, the algorithm goes through a set of iterations. In every iteration, a computation task is created for each fragment; then, there is a reduction phase where the results of each computation are accumulated two at a time by merge tasks; finally, at the end of the iteration the main program post-processes the merged result, generating the current clusters that will be used in the next iteration. Consequently, if ''F'' is the total number of fragments, K-means generates ''F'' computation tasks and ''F-1'' merge tasks per iteration.



###  Execution instructions

Usage:

```$ runcompss files.KMeans -c <num_clusters> -i <maxIter>  -d <num_dim> -f  <num_frag> -t <train_datafile> -n <size_train>```



where:

* - num_clusters: Number of K clusters
* - maxIter: Number max of iterations
* - num_dim: Dimension of each record
* - size_train: number of record in the train file
* - num_frag:   number of nodes used on the execution
* - train_datafile: Pattern of the filename to train the model

### Execution Example

```$ runcompss files.KMeans -c 2 -f 4 -d 28 -i 5 -n 1200 train_data.csv```

In this case, the execution assumes that there are files train\_data.csv\_00, train\_data.csv\_01, train\_data.csv\_02 and train\_data.csv\_03 in the current folder. Also assumes that there are files test\_data.csv\_00, test\_data.csv\_01, test\_data.csv\_02 and test\_data.csv\_03.



