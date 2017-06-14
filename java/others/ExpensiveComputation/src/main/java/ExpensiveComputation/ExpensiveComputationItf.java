package ExpensiveComputation;

import integratedtoolkit.types.annotations.task.Method;

/**
 * Created by lucasmsp on 27/03/17.
 */
public interface ExpensiveComputationItf {

    @Method(declaringClass ="ExpensiveComputation.ExpensiveComputation")
    double[]  Calc();
}
