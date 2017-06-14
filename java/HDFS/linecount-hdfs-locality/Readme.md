## LineCount

Name: LineCount

Contact Person: lucasmsp@dcc.ufmg.br

Platform: COMPSs

Language: Java


### Description

A simple application to count the number of lines in a file stored in HDFS. 

###  Execution instructions

Add a flag `--storage_conf` with a file that contain in the first line the master address of HDFS and in the second line the path of the file in the HDFS.

Usage:


```$ runcompss HDFS.LineCount --storage_conf=./configHDFS.txt```



### Execution Example

```$ runcompss --storage_conf=./configHDFS.txt HDFS.LineCount```


