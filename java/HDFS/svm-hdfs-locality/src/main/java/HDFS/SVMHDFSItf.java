package HDFS;


/**
 *
 * Created by Lucas Miguel S Ponce.
 * Student of master degree in Computer's Science
 * UFMG - 1/2017
 * Algorithm:  SVM using data from HDFS
 *
 */

import integratedtoolkit.types.annotations.parameter.Type;
import integratedtoolkit.types.annotations.parameter.Direction;
import integratedtoolkit.types.annotations.Parameter;
import integratedtoolkit.types.annotations.task.Method;
import integration.BlockLocality;

import java.util.ArrayList;

public interface SVMHDFSItf {

    @Method(declaringClass = "HDFS.SVMHDFS")
    Sample loadfileFromHDFS(
            @Parameter(type =  Type.OBJECT, direction =  Direction.IN)
                    BlockLocality blk,
            @Parameter(direction =  Direction.IN)
                    int numDim
    );


    @Method(declaringClass = "HDFS.SVMHDFS")
    double[] calc_CostAndGrad(
            @Parameter(direction =  Direction.IN) int numDim,
            @Parameter(direction =  Direction.IN) int f,
            @Parameter(direction =  Direction.IN) double lambda,
            @Parameter(direction =  Direction.IN) double[] w,
            @Parameter(direction =  Direction.IN) Sample XY,
            @Parameter(direction =  Direction.INOUT) double[] COST

    );


    @Method(declaringClass = "HDFS.SVMHDFS")
    void accumulateCostAndGrad(
            @Parameter(direction =  Direction.INOUT) double[] grad,
            @Parameter(direction =  Direction.IN) double[] grad2,
            @Parameter(direction =  Direction.INOUT) double[] COST,
            @Parameter(direction =  Direction.IN) double[] COST2

    );

    @Method(declaringClass = "HDFS.SVMHDFS")
    Sample predict_chunck(
            @Parameter(type =  Type.OBJECT, direction =  Direction.IN)
                    Sample XY,
            @Parameter(direction =  Direction.IN)
                    double[] w

    );








    @Method(declaringClass = "HDFS.SVMHDFS")
    void updateWeight(
            @Parameter(direction =  Direction.IN)
                    double lr,
            @Parameter(type =  Type.OBJECT, direction =  Direction.IN)
                    double[] grad_p,
            @Parameter(direction =  Direction.INOUT)
                    double[] w
    );

    @Method(declaringClass = "HDFS.SVMHDFS")
    void savePredictionToHDFS(
            @Parameter(type =  Type.OBJECT, direction =  Direction.IN) ArrayList<Integer> result,
            @Parameter(type =  Type.OBJECT, direction =  Direction.IN) String defaultFS,
            @Parameter(type =  Type.STRING, direction =  Direction.IN) String filename
    );




}
