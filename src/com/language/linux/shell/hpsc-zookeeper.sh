#!/bin/bash
cloudhostZookeeperFolder="/cygdrive/c/opt/cloudhost/zookeeper"
host_zoo_cluster=$(grep "^server." zoo.cfg.template | grep c0006025.itcs.hp.com)
echo "host_zoo_cluster is " $host_zoo_cluster

for i in $host_zoo_cluster; do
	
	# get zookeeper server id,we use it to create data and log folders, 
	#we also use it to create myid file and set myid file content to server id
	temp_zookeepername=${i%=*}
	zookeepername=${temp_zookeepername#server.}
	
	# print zookeeper server id
	echo "zookeepername is " ${zookeepername}

	# create locahost availiable cluster zoo.cfg file
	cp zoo.cfg.template zook${zookeepername}.cfg
	echo "create local cluster zoo cfg file is successful"
	echo 'dataDir=/opt/cloudhost/zookeeper/data/zook'${zookeepername} >>${cloudhostZookeeperFolder}/conf/zook${zookeepername}.cfg
    echo 'datalogDir=/opt/cloudhost/zookeeper/datalog/zook'${zookeepername} >>${cloudhostZookeeperFolder}/conf/zook${zookeepername}.cfg

	# mkdir dir locahost availiable cluster data folder	
	test -d ${cloudhostZookeeperFolder}/data/zook${zookeepername} || mkdir ${cloudhostZookeeperFolder}/data/zook${zookeepername}
	echo "create local " zook${zookeepername} "cluster data folder is successful"
	
	# create myid file for each cluster and set content is server id
	touch ${cloudhostZookeeperFolder}/data/zook${zookeepername}/myid
	cat /dev/null>${cloudhostZookeeperFolder}/data/zook${zookeepername}/myid
    echo ${zookeepername} >>${cloudhostZookeeperFolder}/data/zook${zookeepername}/myid
	
	# mkdir dir locahost availiable cluster data folder	
	test -d ${cloudhostZookeeperFolder}/dataLog/zook${zookeepername} || mkdir ${cloudhostZookeeperFolder}/dataLog/zook${zookeepername}
	echo "create local " zook${zookeepername} "cluster data log folder is successful"
	

done
