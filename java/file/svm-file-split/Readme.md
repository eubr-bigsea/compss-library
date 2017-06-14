## SVM Classifier

Name: Support Vector Machine

Contact Person: lucasmsp@dcc.ufmg.br

Platform: COMPSs

Language: Java


### Description

SVM is a supervised learning model used for binary classification. Given a set of training examples, each marked as belonging to one or the other of two categories, an SVM training algorithm builds a model that assigns new examples to one category or the other, making it a non-probabilistic binary linear classifier. An SVM model is a representation of the examples as points in space, mapped so that the examples of the separate categories are divided by a clear gap that is as wide as possible. New examples are then mapped into that same space and predicted to belong to a category based on which side of the gap they fall.


The algorithm reads a dataset composed by labels (-1.0 or 1.0) and features (numeric fields), each record by line as the pattern: label,feature1,feature2, ..., featureN



###  Execution instructions

Usage:

```$ runcompss files.SVM   -c  <c_value> -lf <lf_value>  -thr <threshold> -i <maxIter>  -nd <num_dim> -nt <size_train> -nv <size_test> -f  <num_frag> -t <train_datafile> -v <test_datafile> -out <output path>```



where:

*  - c_value:  Regularization parameter
* - lf_value:  Learning rate parameter
* - threshold: Tolerance for stopping criterion
* - maxIter: Number max of iterations
* - num_dim: Dimension of each record
* - size_train:  number of record in the train file
* - size_test:   number of record in the test file
* - num_frag:    number of nodes used on the execution
* - train_datafile: Pattern of the filename to train the SVM
* - test_datafile:  Pattern of the filename to the classification
* - output_path:  (Optional) Path to save the result

### Execution Examples

#### Example 1: 
	
```$ runcompss files.SVM    -c 0.001 -lr 0.0001 -thr 0.001 -nd 28 -i 3 -f 4 -t train_data.csv -v test_data.csv```

In this case, the execution assumes that there are files train\_data.csv\_00, train\_data.csv\_01, train\_data.csv\_02 and train\_data.csv\_03 in the current folder. Also assumes that there are files test\_data.csv\_00, test\_data.csv\_01, test\_data.csv\_02 and test\_data.csv\_03.


#### Example 2: 

	runcompss files.SVM    -c 0.001 -lr 0.0001 -thr 0.001 -nd 28 -i 3 -f 2 -t train_data.csv -v test_data.csv -out /home/user/output

In this case, the execution assumes that there are files train\_data.csv\_00, train\_data.csv\_01, test\_data.csv\_00 and test\_data.csv\_01. The result will be wrote two files, one called output\_00 and output\_01 in the folder /home/user.
