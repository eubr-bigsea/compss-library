
# WordCount 

Name: WordCount

Contact Person: lucasmsp@dcc.ufmg.br

Platform: COMPSs

Language: Java



##	Usage

Parameters: 

* -in \<filename> : Pattern of the input file
* -f \<numFrag> : Number of files 
* -list : optional, to print the result


### Example of how to submit:


```$ runcompss -g --log_level=info --classpath=target/wordcount-file-split.jar files.WordCount -in ./sample_data/file-example.txt -f 4 -list```

The execution assumes that there are four files called file-example.txt\_00, file-example.txt\_01, file-example.txt\_02 and file-example.txt\_03.
