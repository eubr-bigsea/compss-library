/*
 *  Copyright 2002-2015 Barcelona Supercomputing Center (www.bsc.es)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package files;


import integratedtoolkit.types.annotations.task.Method;
import integratedtoolkit.types.annotations.Parameter;
import integratedtoolkit.types.annotations.parameter.Direction;
import integratedtoolkit.types.annotations.parameter.Type;
public interface KMeansItf {


	@Method(declaringClass = "files.KMeans")
	float[] readPointsFromFile(
			@Parameter(type =  Type.FILE, direction =  Direction.IN) String fileName,
			@Parameter(type =  Type.OBJECT,direction =  Direction.INOUT) float[] points,
			@Parameter(direction =  Direction.IN) int numPoints,
			@Parameter(direction =  Direction.IN) int numDimensions,
			@Parameter(type =  Type.OBJECT,direction =  Direction.INOUT)  float[] cluster,
			@Parameter(direction =  Direction.IN) int K
			);

	@Method(declaringClass = "files.KMeans")
	void initilizeClusters(@Parameter(type =  Type.OBJECT, direction =  Direction.INOUT) float[] cluster,
						   @Parameter(type =  Type.OBJECT, direction =  Direction.IN)    float[] data,
						   @Parameter(direction =  Direction.IN) int startPos
	);

	@Method(declaringClass = "files.KMeans")
	void computeNewLocalClusters(
			@Parameter
					int myK,
			@Parameter
					int numDimensions,
			@Parameter
					float[] points,
			@Parameter
					float[] clusterPoints,
			@Parameter(type =  Type.OBJECT,direction = Direction.INOUT)
					float[] newClusterPoints,
			@Parameter(type =  Type.OBJECT,direction = Direction.INOUT)
					int[] clusterCounts
	);

	@Method(declaringClass = "files.KMeans")
	void accumulate(
			@Parameter(direction = Direction.INOUT)
					float[] onePoints,
			@Parameter
					float[] otherPoints,
			@Parameter(direction = Direction.INOUT)
					int[] oneCounts,
			@Parameter
					int[] otherCounts
	);

}
