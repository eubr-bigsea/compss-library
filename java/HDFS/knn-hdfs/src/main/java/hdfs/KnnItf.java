package hdfs;

import integratedtoolkit.types.annotations.Parameter;
import integratedtoolkit.types.annotations.task.Method;
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
            @Parameter(type = Parameter.Type.OBJECT, direction = Parameter.Direction.IN) int[] train_labels,
            @Parameter(type = Parameter.Type.OBJECT, direction = Parameter.Direction.IN) double[][] train_features,
            @Parameter(type = Parameter.Type.OBJECT, direction = Parameter.Direction.IN) Block b1,
            @Parameter(direction = Parameter.Direction.IN) int K,
            @Parameter(direction = Parameter.Direction.IN) int numDim
    );

    @Method(declaringClass = "hdfs.Knn")
    void savePredictionToHDFS(
            @Parameter(type = Parameter.Type.OBJECT, direction = Parameter.Direction.IN) int[] result,
            @Parameter(type = Parameter.Type.OBJECT, direction = Parameter.Direction.IN) String defaultFS,
            @Parameter(type = Parameter.Type.OBJECT, direction = Parameter.Direction.IN) String filename
    );

}
