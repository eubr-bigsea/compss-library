#!/usr/bin/python
# -*- coding: utf-8 -*-

#
# Developed by Lucas Miguel Ponce
# Mestrando em Ciências da Computação - UFMG
# <lucasmsp@gmail.com>
#



import sys

from svm import *
import pandas as pd


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

    print data
    if len(data)==0:
        data = pd.DataFrame()
    if header:
        data.to_csv(filename,sep=',',mode=mode, header=True,index=False)
    else:
        data.to_csv(filename,sep=',',mode=mode, header=False,index=False)

    return None


if __name__ == "__main__":
    import argparse
    parser = argparse.ArgumentParser(description='SVM - PyCOMPSs')
    parser.add_argument('-t', '--TrainSet', required=True, help='path to the train file')
    parser.add_argument('-v', '--TestSet',  required=True, help='path to the test file')
    parser.add_argument('-f', '--Nodes',        type=int,    default=2, required=False, help='Number of nodes')
    parser.add_argument('-C', '--lambda',       type=float,  default=0.001, required=False, help='Regularization parameter')
    parser.add_argument('-it', '--MaxIters',    type=int,    default=10, required=False, help='Number max of iterations')
    parser.add_argument('-lr', '--lr',          type=float,  default=0.01, required=False, help='Learning rate parameter')
    parser.add_argument('-thr', '--threshold',  type=float,  default=0.01, required=False, help='Tolerance for stopping criterion')
    parser.add_argument('-o', '--output',   required=False, help='path to the output file')
    arg = vars(parser.parse_args())


    fileTrain   = arg['TrainSet']
    fileTest    = arg['TestSet']
    numFrag     = arg['Nodes']

    settings = {}
    settings['coef_lambda']     = arg['lambda']
    settings['coef_lr']         = arg['lr']
    settings['coef_threshold']  = arg['threshold']
    settings['coef_maxIters']   = arg['MaxIters']


    separator = ","

    data0 = ReadParallelFile(fileTrain, separator, True, 'FROM_VALUES', [''])
    data1 = ReadParallelFile(fileTest, separator, True, 'FROM_VALUES', [''])

    settings['labels'] = [u'label']
    settings['features'] = [u'x', u'y']
    ClassificationModel = SVM()
    model = ClassificationModel.fit(data0, settings, numFrag)
    settings['model'] = model

    result = ClassificationModel.transform(data1, settings, numFrag)

    if arg['output']:
        output_file= arg['output']
        tmp = [SaveToFile("%s_%d" % (output_file,d),  result[d], 'overwrite', True) for d in range(numFrag)]
