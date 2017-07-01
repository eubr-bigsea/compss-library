package files;


import integratedtoolkit.types.annotations.parameter.Direction;
import integratedtoolkit.types.annotations.parameter.Type;
import integratedtoolkit.types.annotations.Parameter;
import integratedtoolkit.types.annotations.task.Method;

import java.util.HashMap;

public interface WordCountItf {

	@Method(declaringClass = "files.WordCount")
	public HashMap<String, Integer> mergeResults(
			@Parameter HashMap<String, Integer> m1,
			@Parameter HashMap<String, Integer> m2
	);

	@Method(declaringClass = "files.WordCount")
	public HashMap<String, Integer> map(
			@Parameter(type = Type.FILE, direction = Direction.IN) String filePath
	);
}
