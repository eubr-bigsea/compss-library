
# PyCOMPSs - MachineLearning -  K-NN"


Remember that you might need to compile the library functions_knn.pyx first:

```$ OPT="-O3 -ffast-math" python setup.py build_ext -i```


## Example of usage:
	
```
$ runcompss --log_level=info -g --summary --lang=python	\
			/home/lucasmsp/workspace/BigSea/compss-library/python/knn/test.py \
			-f 4 -k 1 	\
			-t /home/lucasmsp/workspace/BigSea/compss-library/python/knn/input1.csv \
			-v /home/lucasmsp/workspace/BigSea/compss-library/python/knn/input2.csv
```


