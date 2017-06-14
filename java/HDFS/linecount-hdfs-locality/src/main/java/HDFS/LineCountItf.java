package HDFS;

import integratedtoolkit.types.annotations.Parameter;
import integratedtoolkit.types.annotations.task.Method;
import integration.Block;



/**
 * Created by lucasmsp on 24/03/17.
 */
public interface LineCountItf {


    @Method(declaringClass ="HDFS.LineCount")
    int[]  LineCount(
            @Parameter(type = Parameter.Type.OBJECT, direction = Parameter.Direction.IN) Block b1
    );

}
