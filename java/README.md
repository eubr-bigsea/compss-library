## COMPSs's Library (Java)

### Maths

- ./others/ExensiveComputation: This is application do a expensive computation in parallel. It's usefull to do comparisons between Java and Python.
- ./file/sparseLU-bsc: The original sparse LU algorithm developed by BSC.

### ML: Clustering
- ./file/kmeans-bsc: The original Kmeans developed by BSC (without Ophidia).
- ./file/kmeans-file: An adapted version of the Kmeans which read a single file in order to do the clusterization.

### ML: Classification

- ./file/knn-file-simple: Performs a KNN algorithm using the single file that contains a train set and other single file to do the classification.
- ./file/knn-file-split: Performs a KNN algorithm using datasets already splitted by the number of cores.
- ./file/svm-file-simple: Performs a SVM algorithm using the single file that contains a train set and other single file to do the classification.
- ./file/svm-file-split: Performs a SVM algorithm using datasets already splitted by the number of cores.


### Others

- ./file/linecount-file-split: A simple program that count the number of file in a dataset. The dataset is already splitted by the number of cores.
- ./file/simple-bsc: A simple program to test the Mesos's integration. 