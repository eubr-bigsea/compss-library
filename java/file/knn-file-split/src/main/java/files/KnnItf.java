package files;


/**
 *
 * Created by Lucas Miguel S Ponce.
 * Student of master degree in Computer's Science
 * UFMG - 1/2017
 * Algorithm:  k-NN (K Nearst Neighborhood)
 *
 */


import integratedtoolkit.types.annotations.Parameter;
import integratedtoolkit.types.annotations.task.Method;


public interface KnnItf {

    @Method(declaringClass = "files.Knn")
    int[] ClassifyBlock(
            @Parameter(type = Parameter.Type.FILE, direction = Parameter.Direction.IN) String TestFile,
            @Parameter(type = Parameter.Type.OBJECT, direction = Parameter.Direction.IN) int[] train_labels,
            @Parameter(type = Parameter.Type.OBJECT, direction = Parameter.Direction.IN) double[][] train_features,
            @Parameter(direction = Parameter.Direction.IN) int K,
            @Parameter(direction = Parameter.Direction.IN) int sizeTestPerFrag,
            @Parameter(direction = Parameter.Direction.IN) int numDim
    );

    @Method(declaringClass = "files.Knn")
    void savePredictionToFile(
            @Parameter(type = Parameter.Type.OBJECT, direction = Parameter.Direction.IN)
                    int[] result,
            @Parameter(type = Parameter.Type.FILE, direction = Parameter.Direction.INOUT)
                    String filename
    );

}
