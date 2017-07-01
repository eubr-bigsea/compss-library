package hdfs;

import integratedtoolkit.types.annotations.task.Method;
import integratedtoolkit.types.annotations.Parameter;
import integratedtoolkit.types.annotations.parameter.Direction;
import integratedtoolkit.types.annotations.parameter.Type;


import integration.Block;

/**
 *
 * Created by Lucas Miguel S Ponce.
 * Student of master degree in Computer's Science
 * UFMG - 1/2017
 * Algorithm:  k-NN (K Nearst Neighborhood) using data of HDFS
 *
 */

public interface KnnItf {


    @Method(declaringClass = "hdfs.Knn")
    int[] ClassifyBlock(
            @Parameter(type = Type.OBJECT, direction = Direction.IN) int[] train_labels,
            @Parameter(type = Type.OBJECT, direction = Direction.IN) double[][] train_features,
            @Parameter(type = Type.OBJECT, direction = Direction.IN) Block b1,
            @Parameter(direction = Direction.IN) int K,
            @Parameter(direction = Direction.IN) int numDim
    );

    @Method(declaringClass = "hdfs.Knn")
    void savePredictionToHDFS(
            @Parameter(type = Type.OBJECT, direction = Direction.IN) int[] result,
            @Parameter(type = Type.OBJECT, direction = Direction.IN) String defaultFS,
            @Parameter(type = Type.OBJECT, direction = Direction.IN) String filename
    );

}
