




# How to submit in single or multi-node:

	runcompss --classpath=/home/lucasmsp/workspace/BigSea/Compss-examples/ExpensiveComputation/target/CE.jar \
	ExpensiveComputation.ExpensiveComputation


# How to run in multi-node with docker-swarm:

First init a docker-swarm:

	docker swarm init
	

	runcompss-docker  --worker-containers=4 \
					  --swarm-manager='10.193.157.7:2377'\
					  --image-name=compssbase \
					  --context-dir=/var/workspace
					  --classpath=/home/lucasmsp/workspace/BigSea/Compss-examples/ExpensiveComputation/target/CE.jar \
					  -d ExpensiveComputation.ExpensiveComputation



# How to run in Mesos:


tar zcvf CE.tar.gz *

tar vfxz knn.tar.gz