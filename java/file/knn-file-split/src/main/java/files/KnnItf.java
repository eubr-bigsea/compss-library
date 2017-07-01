package files;


/**
 *
 * Created by Lucas Miguel S Ponce.
 * Student of master degree in Computer's Science
 * UFMG - 1/2017
 * Algorithm:  k-NN (K Nearst Neighborhood)
 *
 */

import integratedtoolkit.types.annotations.task.Method;
import integratedtoolkit.types.annotations.Parameter;
import integratedtoolkit.types.annotations.parameter.Direction;
import integratedtoolkit.types.annotations.parameter.Type;

public interface KnnItf {

    @Method(declaringClass = "files.Knn")
    int[] ClassifyBlock(
            @Parameter(type = Type.FILE, direction = Direction.IN) String TestFile,
            @Parameter(type = Type.OBJECT, direction = Direction.IN) int[] train_labels,
            @Parameter(type = Type.OBJECT, direction = Direction.IN) double[][] train_features,
            @Parameter(direction = Direction.IN) int K,
            @Parameter(direction = Direction.IN) int sizeTestPerFrag,
            @Parameter(direction = Direction.IN) int numDim
    );

    @Method(declaringClass = "files.Knn")
    void savePredictionToFile(
            @Parameter(type = Type.OBJECT, direction = Direction.IN)
                    int[] result,
            @Parameter(type = Type.FILE, direction = Direction.INOUT)
                    String filename
    );

}
