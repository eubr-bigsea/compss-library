package HDFS;


/**
 *
 * Created by Lucas Miguel S Ponce.
 * Student of master degree in Computer's Science
 * UFMG - 1/2017
 * Algorithm:  SVM using data from HDFS
 *
 */


import integratedtoolkit.types.annotations.Parameter;
import integratedtoolkit.types.annotations.task.Method;
import integration.Block;

import java.util.ArrayList;

public interface SVMHDFSItf {

    @Method(declaringClass = "HDFS.SVMHDFS")
    Sample loadfileFromHDFS(
            @Parameter(type = Parameter.Type.OBJECT, direction = Parameter.Direction.IN)   Block blk,
            @Parameter(direction = Parameter.Direction.IN)  int numDim

    );


    @Method(declaringClass = "HDFS.SVMHDFS")
    double[] calc_CostAndGrad(
            @Parameter(direction = Parameter.Direction.IN) int numDim,
            @Parameter(direction = Parameter.Direction.IN) int f,
            @Parameter(direction = Parameter.Direction.IN) double lambda,
            @Parameter(direction = Parameter.Direction.IN) double[] w,
            @Parameter(direction = Parameter.Direction.IN) Sample XY,
            @Parameter(direction = Parameter.Direction.INOUT) double[] COST

    );


    @Method(declaringClass = "HDFS.SVMHDFS")
    void accumulateCostAndGrad(
            @Parameter(direction = Parameter.Direction.INOUT) double[] grad,
            @Parameter(direction = Parameter.Direction.IN) double[] grad2,
            @Parameter(direction = Parameter.Direction.INOUT) double[] COST,
            @Parameter(direction = Parameter.Direction.IN) double[] COST2

    );

    @Method(declaringClass = "HDFS.SVMHDFS")
    Sample predict_chunck(
            @Parameter(type = Parameter.Type.OBJECT, direction = Parameter.Direction.IN)    Sample XY,
            @Parameter(direction = Parameter.Direction.IN)   double[] w

    );



    @Method(declaringClass = "HDFS.SVMHDFS")
    void updateWeight(
            @Parameter(direction = Parameter.Direction.IN)
                    double lr,
            @Parameter(type = Parameter.Type.OBJECT, direction = Parameter.Direction.IN)
                    double[] grad_p,
            @Parameter(direction = Parameter.Direction.INOUT)
                    double[] w
    );

    @Method(declaringClass = "HDFS.SVMHDFS")
    void savePredictionToHDFS(
            @Parameter(type = Parameter.Type.OBJECT, direction = Parameter.Direction.IN) ArrayList<Integer> result,
            @Parameter(type = Parameter.Type.OBJECT, direction = Parameter.Direction.IN) String defaultFS,
            @Parameter(type = Parameter.Type.STRING, direction = Parameter.Direction.IN) String filename
    );




}
