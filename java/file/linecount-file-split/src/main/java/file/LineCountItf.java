package file;

import integratedtoolkit.types.annotations.task.Method;
import integratedtoolkit.types.annotations.Parameter;
import integratedtoolkit.types.annotations.parameter.Direction;
import integratedtoolkit.types.annotations.parameter.Type;

/**
 * Created by lucasmsp on 24/03/17.
 */
public interface LineCountItf {


    @Method(declaringClass ="file.LineCount")
    int []  LineCount(
            @Parameter(type = Type.FILE, direction = Direction.IN) String filePath
    );

}
