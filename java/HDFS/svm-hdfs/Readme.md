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


```$ runcompss HDFS.SVMHDFS  -c  <c_value> -lf <lf_value>  -thr <threshold> -i <maxIter>  -nd <num_dim> -nt <size_train> -nv <size_test>  -f  <num_frag> -t  <train_datafile> -v <test_datafile> -out <output path>```



where:

*  c_value:  Regularization parameter
*  lf_value:  Learning rate parameter
*  threshold: Tolerance for stopping criterion
*  maxIter: Number max of iterations
*  num_dim: Dimension of each record
*  size_train:  number of record in the train file
*  size_test:   number of record in the test file
*  num_frag:   (optional in version 2) number of nodes used on the execution
*  train_datafile: File's path used to train the model
*  test_datafile:  File's path to classification
*  output_path:  (Optional) Path to save the result

### Execution Examples

#### Example 1: 
	
``` $ runcompss HDFS.SVM   -c 0.001 -lr 0.0001 -thr 0.001 -nd 28 -i 3 -f 4 -t /user/username/train_data.csv -v /user/username/test_data.csv -out /home/user/output```
