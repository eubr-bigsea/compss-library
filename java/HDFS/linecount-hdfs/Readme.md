## LineCount

Name: LineCount

Contact Person: lucasmsp@dcc.ufmg.br

Platform: COMPSs

Language: Java



### Description

A simple application to count how many lines has a file stored in HDFS. 

###  Execution instructions
Usage:


```$ runcompss HDFS.LineCount -f <numfrag> -in  <filename>```


where:

* numfrag: Number of parts of the file;
* filename: path of a file in HDFS.


### Execution Example

```$ runcompss HDFS.LineCount -f 4 -in /user/username/data.csv```






