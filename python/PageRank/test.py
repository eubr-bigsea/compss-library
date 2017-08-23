#!/usr/bin/python
# -*- coding: utf-8 -*-

__author__ = "Lucas Miguel S Ponce"
__email__  = "lucasmsp@gmail.com"



import sys

from pagerank import *

import time
import numpy as np
import pandas as pd
from pycompss.api.api import compss_wait_on




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



#---------------------------------------------------------------------


if __name__ == "__main__":
    import argparse
    parser = argparse.ArgumentParser(description='PageRank -  PyCOMPSs')
    parser.add_argument('-t', '--TrainSet', required=True, help='path to the training file')
    parser.add_argument('-f', '--Nodes',    type=int,  default=4, required=False, help='Number of nodes')
    arg = vars(parser.parse_args())

    fileTrain = arg['TrainSet']

    numFrag   = arg['Nodes']
    separator = ","


    print """Running PageRank with the following parameters:
    - Nodes: {}
    - Training Set: {}\n
    """.format(numFrag,fileTrain,)


    data = ReadParallelFile(fileTrain, separator, True, 'FROM_VALUES', [''])
    settings = dict()
    settings['inlink']  =  'inlink'
    settings['outlink'] = 'outlink'
    settings['damping_factor'] = 0.85
    settings['maxIters'] = 15
    settings['col1'] = 'Vertice'
    settings['col2'] = 'Rank'

    prank = PageRank()
    data1 = prank.runPageRank(data,settings,numFrag)

    data1 = compss_wait_on(data1)
    print data1

    #  Vertice      Rank
    #0   url_2  1.197456
    #1   url_1  1.144744
    #2   url_3  0.657800
