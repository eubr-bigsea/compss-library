package files;


/**
 *
 * Created by Lucas Miguel S Ponce.
 * Student of master degree in Computer's Science
 * UFMG - 1/2017
 * Algorithm:  SVM
 *
 */


import integratedtoolkit.types.annotations.parameter.Direction;
import integratedtoolkit.types.annotations.parameter.Type;
import integratedtoolkit.types.annotations.Parameter;
import integratedtoolkit.types.annotations.task.Method;

public interface SVMItf {

    @Method(declaringClass = "files.SVM")
    void loadfile_train(@Parameter(type =  Type.OBJECT, direction =  Direction.INOUT) double[][] features,
                        @Parameter(type =  Type.OBJECT, direction =  Direction.INOUT) int[] labels,
                        @Parameter(type =  Type.FILE, direction =  Direction.IN)      String fileName,
                        @Parameter(direction =  Direction.IN)                                  int numTotal
    );

    @Method(declaringClass = "files.SVM")
    void loadfile_test(@Parameter(type =  Type.OBJECT, direction =  Direction.INOUT) double[][] features,
                        @Parameter(type =  Type.FILE, direction =  Direction.IN)      String fileName,
                        @Parameter(direction =  Direction.IN)                                  int numTotal
    );

    @Method(declaringClass = "files.SVM")
    double[] calc_CostAndGrad(
            @Parameter(direction =  Direction.IN) int numDim,
            @Parameter(type =  Type.OBJECT, direction =  Direction.IN) double[][] train_features,
            @Parameter(type =  Type.OBJECT, direction =  Direction.IN) int[] train_labels,
            @Parameter(type =  Type.OBJECT, direction =  Direction.INOUT) double[] COST,
            @Parameter(direction =  Direction.IN) int f,
            @Parameter(direction =  Direction.IN) double lambda,
            @Parameter(type =  Type.OBJECT, direction =  Direction.IN) double[] w
    );


    @Method(declaringClass = "files.SVM")
    void  accumulateCostAndGrad(
            @Parameter(direction =  Direction.INOUT) double[] grad,
            @Parameter(direction =  Direction.IN) double[] grad2,
            @Parameter(direction =  Direction.INOUT) double[] COST,
            @Parameter(direction =  Direction.IN) double[] COST2
    );


    @Method(declaringClass = "files.SVM")
    void updateWeight(
            @Parameter(direction =  Direction.IN) double lr,
            @Parameter(type =  Type.OBJECT, direction =  Direction.IN) double[] grad_p,
            @Parameter(direction =  Direction.INOUT) double[] w
    );


    @Method(declaringClass = "files.SVM")
    int[] predict_chunck(
            @Parameter(type =  Type.OBJECT, direction =  Direction.IN) double[][] testX,
            @Parameter(type =  Type.OBJECT, direction =  Direction.IN) double[] w
    );

    @Method(declaringClass = "files.SVM")
    void savePredictionToFile(
            @Parameter(type =  Type.OBJECT, direction =  Direction.IN) int[] result,
            @Parameter(type =  Type.FILE, direction =  Direction.INOUT) String filename
    );



}
