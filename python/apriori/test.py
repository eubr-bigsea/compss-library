#!/usr/bin/python
# -*- coding: utf-8 -*-

__author__ = "Lucas Miguel S Ponce"
__email__  = "lucasmsp@gmail.com"



import sys

from apriori import *

import time
import numpy as np
import pandas as pd





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
    if separator == "<new_line>": separator = "\n"

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


def transform(data,settings,numFrag):
    for f in range(numFrag):
        data[f] = transform_p(data[f], settings) 
    return data

@task(returns=list)
def transform_p(data, settings):
    col = settings['column']
    ncol = settings['new_column']
    tmp = []
    for row in data[col].tolist():
        lst = row.split(",")
        tmp.append(filter(None, lst))                         # Remove trailing comma
    data[ncol] = tmp

    #print data
    return data

#---------------------------------------------------------------------


if __name__ == "__main__":
    import argparse
    parser = argparse.ArgumentParser(description='Apriori -  PyCOMPSs')
    parser.add_argument('-t', '--TrainSet', required=True, help='path to the training file')
    parser.add_argument('-f', '--Nodes',    type=int,  default=4, required=False, help='Number of nodes')
    parser.add_argument('-o', '--output',   required=False, help='path to the output file')
    parser.add_argument('-s', '--minSupport',  help='minimum support value', default=0.15, type=float)
    parser.add_argument('-c', '--minConfidence',  help='minimum confidence value', default=0.6,  type=float)
    arg = vars(parser.parse_args())

    fileTrain = arg['TrainSet']

    numFrag   = arg['Nodes']
    separator = "<new_line>"


    print """Running Apriori with the following parameters:
    - Nodes: {}
    - Training Set: {}
    - minConfidence:{}
    - minSupport: {}\n
    """.format(numFrag,fileTrain,arg['minConfidence'],arg['minSupport'])

    data = ReadParallelFile(fileTrain, separator, True, 'FROM_VALUES', [''])
    settings = dict()
    settings['column'] = 'transactions'
    settings['new_column'] = 'transactions'
    data = transform(data, settings, numFrag )
    #data = compss_wait_on(data)
    #print data

    settings['col'] = "transactions"
    settings['confidence'] = arg['minConfidence']
    settings['minSupport'] = arg['minSupport']

    pfreq = Apriori()
    rules, nTotal = pfreq.runApriori(data, settings, numFrag)
    print "Num transactions:", nTotal
    toRetRules = pfreq.generateRules(rules,settings,nTotal)
    toRetRules = compss_wait_on(toRetRules)
    pfreq.printResults(toRetRules)
