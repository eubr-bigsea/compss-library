package file;

import integratedtoolkit.types.annotations.Parameter;
import integratedtoolkit.types.annotations.task.Method;

/**
 * Created by lucasmsp on 24/03/17.
 */
public interface LineCountItf {


    @Method(declaringClass ="file.LineCount")
    int []  LineCount(
            @Parameter(type = Parameter.Type.FILE, direction = Parameter.Direction.IN) String filePath
    );

}
