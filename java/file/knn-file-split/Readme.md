## K-NN Classifier

Name: KNN

Contact Person: lucasmsp@dcc.ufmg.br

Platform: COMPSs

Language: Java



### Description

K Nearest Neighbors (KNN) is a non-parametric method used for classification and regression. In k-NN classification, an object is classified by a majority vote of its neighbors, with the object being assigned to the class most common among its k nearest neighbors (k is a positive integer, typically small). If k = 1, then the object is simply assigned to the class of that single nearest neighbor.

The optimal choice of the value k is highly data-dependent: in general a larger k suppresses the effects of noise, but makes the classification boundaries less distinct.

The algorithm reads a dataset composed by labels and features (numeric fields), each record by line as the pattern: label,feature1,feature2, ..., featureN




### Execution instructions

Usage:

```$ runcompss knn.Knn   -K <K_value> -f <num_frag> -t <train_datafile> -v <test_datafile> -out <output_path>```



where:

* - K_value: integer number used into the classification;
* - num_frag:  number of nodes used on the execution;
* - train_datafile: Pattern of the filename to train the model;
* - test_datafile: Pattern of the filename to do the classification;
* - output_path:  Path to save the result (optional).


### Execution Example

```$ runcompss 	knn.KNN    -K 1 -f 2  -t train_data.csv -v test_data.csv -out /home/user/output```

In these case, the execution assumes that there are files train\_data.csv\_00 and train\_data.csv\_01 in the current folder. Also assumes that there are files test\_data.csv\_00 and test\_data.csv\_01. The output will be splitted too.

or

```$ runcompss 	knn.KNN    -K 1 -f 4  -t train_data.csv -v test_data.csv```


In these case, the execution assumes that there are files train\_data.csv\_00, train\_data.csv\_01, train\_data.csv\_02 and train\_data.csv\_03 in the current folder. Also assumes that there are files test\_data.csv\_00, test\_data.csv\_01, test\_data.csv\_02 and test\_data.csv\_03.



