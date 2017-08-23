#!/usr/bin/python
# -*- coding: utf-8 -*-

__author__ = "Lucas Miguel S Ponce"
__email__  = "lucasmsp@gmail.com"



import sys

from logisticRegression import *

import time
import numpy as np
import pandas as pd





def FeatureAssemble(df, cols, name):
    """
    Feature Assembler is a transformer that combines a given list of columns
    into a single vector column. It is useful for combining raw features and
    features generated by different feature transformers into a single feature
    vector, in order to train ML models.

    Feature Assembler accepts the following input column types: all numeric
    types, boolean type, and vector type. In each row, the values of the
    input columns will be concatenated into a vector in the specified order.

    :param df: Input DataFrame
    :param cols:  List of columns's name to be assembled
    :param name: Name of the new column

    """
    for i in range(len(df)):
        df[i] = FeatureAssemble_parallel(df[i],cols,name)
    return df

@task(returns=list)
def FeatureAssemble_parallel(df,cols,name):
    df[name] =  df[cols].values.tolist()
    #print df
    return df

#---------------------------------------------------------------------

def ReadParallelFile(filename,separator,header,infer,na_values):
    import os, os.path


    data = []
    DIR = filename+"_folder"


        #get the number of files in this folder
    data =  [ ReadFromFile(DIR+"/"+name,separator,header,infer,na_values)
                for name in os.listdir(DIR) ]

    return data


@task(returns=list, filename = FILE_IN)
def ReadFromFile(filename,separator,header,infer,na_values):
    """
        ReadFromFile:

        Method used to load a data from a file to an np.array.

        :param filename: The name of the file.
        :param separator: The string used to separate values.
        :param list_columns: A list with which columns to read
                            (with 0 being the first).
        :return A np.array with the data loaded.
    """

    if infer =="NO":
        if header:
            df = pd.read_csv(filename,sep=separator,na_values=na_values,dtype='str');
        else:
            df = pd.read_csv(filename,sep=separator,na_values=na_values,header=0,dtype='str');

    elif infer == "FROM_VALUES":
        if header:
            df = pd.read_csv(filename,sep=separator,na_values=na_values);
        else:
            df = pd.read_csv(filename,sep=separator,na_values=na_values,header=0);

    return df

#---------------------------------------------------------------------

@task(filename = FILE_OUT)
def SaveToFile(filename,data,mode,header):
    """
        SaveToFile (CSV):

        Method used to save an array into a file.

        :param filename: The name used in the output.
        :param data: The np.array which you want to save.
        :param mode: append, overwrite, ignore or error

    """
    import os.path


    if mode is 'append':
        mode = 'a'
    elif mode is 'ignore':
        if os.path.exists(filename):
            return None
    elif mode is 'error':
        if os.path.exists(filename):
            return None    # !   TO DO: RAISE SOME ERROR
    else:
        mode = 'w'

    #print data
    if len(data)==0:
        data = pd.DataFrame()
    if header:
        data.to_csv(filename,sep=',',mode=mode, header=True,index=False)
    else:
        data.to_csv(filename,sep=',',mode=mode, header=False,index=False)

    return None

#---------------------------------------------------------------------


if __name__ == "__main__":
    import argparse
    parser = argparse.ArgumentParser(description='Logistic Regression -  PyCOMPSs')
    parser.add_argument('-t', '--TrainSet', required=True, help='path to the training file')
    #parser.add_argument('-v', '--TestSet',  required=True, help='path to the test file')
    parser.add_argument('-f', '--Nodes',    type=int,  default=2, required=False, help='Number of nodes')
    #parser.add_argument('-k', '--K',        type=int,  default=1, required=False, help='Number of nearest neighborhood')
    parser.add_argument('-o', '--output',   required=False, help='path to the output file')
    arg = vars(parser.parse_args())

    fileTrain = arg['TrainSet']
    #fileTest  = arg['TestSet']

    numFrag   = arg['Nodes']
    separator = ","


    print """Running Logistic Regression with the following parameters:
    - Nodes: {}
    - Train Set: {}\n
    """.format(numFrag,fileTrain)

    data = ReadParallelFile(fileTrain, separator, True, 'FROM_VALUES', [''])
    data = FeatureAssemble(data,['Exam1','Exam2'],'feature')

    settings = dict()

    settings['alpha'] = 0.01
    settings['threshold'] = .003
    settings['regularization'] = 0
    settings['iters'] = 10
    settings['label'] = 'Admitted'
    settings['features'] = 'feature'

    ClassificationModel = logisticRegression()
    model = ClassificationModel.fit(data, settings, numFrag)

    settings['new_label'] = 'Y-predicted'
    settings['model'] = model
    data = ClassificationModel.transform(data, settings, numFrag)

    data = compss_wait_on(data)
    data = pd.concat([d for d in data],ignore_index=True)
    print data.to_string(index=False)


    if arg['output']:
        output_file= arg['output']
        tmp = [SaveToFile("%s_%d" % (output_file,d),  data[d], 'overwrite', True) for d in range(numFrag)]