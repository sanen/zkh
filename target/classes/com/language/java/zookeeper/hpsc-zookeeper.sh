#!/bin/bash

PWD=`pwd`

case $0 in
/* )    PRGDIR=$0;;
./* )   PRGDIR=$PWD/${0##./};;
* )     PRGDIR=$PWD/$0;;
esac
PRGDIR=${PRGDIR%/*}

ZOO_HOME=`cd "$PRGDIR/.." >/dev/null; pwd`

ZOO_BINDIR=$ZOO_HOME/bin
ZOO_CFGDIR=$ZOO_HOME/conf
ZOO_DATADIR=$ZOO_HOME/data
ZOO_DATALOGDIR=$ZOO_HOME/dataLog

# check first parameter whether input, if not we print help info to user
if [ ! -n "$1" ] ;then
	set - help
fi

function usage() {
	cat - 1>&2 <<EOF
Usage: 
	$0 start-all|stop-all|restart-all|status-all|check-all
or
	$0 start|stop|restart|status|check NAME
or
	$0 setup|help
Options:
	setup		- Setup all instances configuration and folders only (included in other options as well)
	start-all	- Start all zookeeper instances hosted in current host
	start NAME 	- Start the specified zookeeper instance, like "start zook1"
	stop-all	- Stop all zookeeper instances running in current host
	stop NAME 	- Stop the specified zookeeper instance, like "stop zook1"
	restart-all	- Restart all zookeeper instances hosted in current host one by one
	restart NAME 	- Restart the specified zookeeper instance, like "restart zook1"
	status-all	- Print all zookeeper instances running in current host
	status NAME 	- Print the specified zookeeper instance, like "status zook1"
	check-all	- Check all zookeeper instances running in current host, and bring it back if down
	check NAME 	- Check the specified zookeeper instance, and bring it back if down, like "status zook1"
	help		- Print command help
EOF
}

function prevalidate_setup() {
	if [ ! -d "$ZOO_BINDIR" ]; then
		echo No zookeeper bin folder exist: $ZOO_BINDIR 1>&2
		exit 1
	fi
	if [ ! -d "$ZOO_CFGDIR" ]; then
		echo No zookeeper configuration folder exist: $ZOO_CFGDIR 1>&2
		exit 1
	fi
	if [ ! -d "$ZOO_DATADIR" ]; then
		echo No zookeeper data folder exist: $ZOO_DATADIR 1>&2
		exit 1
	fi
	if [ ! -d "$ZOO_DATALOGDIR" ]; then
		echo No zookeeper data log folder exist: $ZOO_DATALOGDIR 1>&2
		exit 1
	fi
	if [ ! -f "$ZOO_CFGDIR/zoo.cfg.template" ]; then
		echo No zookeeper configuration template file exist: $ZOO_CFGDIR/zoo.cfg.template 1>&2
		exit 1
	fi
}

##
# arguments: zoo_id client_port
##
function setup_single() {
	prevalidate_setup
	zoo_id=$1
	client_port=$2
	zoo_cfg=$ZOO_CFGDIR/zook${zoo_id}.cfg

	# find partition group
	zoo_current_partition=`cat $ZOO_CFGDIR/zoo.cfg.template | sed -n "s/^partition\.[^=]*=\(.*\b$zoo_id\b.*\)/\1/p" | sed "s/:/\\\\\|/g"`
	zoo_partition_total=`grep -c -e "^partition\.[^=]" $ZOO_CFGDIR/zoo.cfg.template`
	zoo_current_partition_count=`echo $zoo_current_partition | wc -w`

	# check if in duplicated partitions
	if [ "$zoo_current_partition_count" -gt 1 ]; then
		echo ERROR: Duplicate server id in partition groups 1>&2
		cat $ZOO_CFGDIR/zoo.cfg.template | sed -n "s/^partition\.[^=]*=\(.*\b$zoo_id\b.*\)/\0/p" 1>&2
		exit 1
	fi

	# check if not in partition
	if [ "$zoo_current_partition_count" -eq 0 -a "$zoo_partition_total" -ne 0 ]; then
		echo ERROR: Server id in $zoo_id is not in any partition groups 1>&2
		exit 1
	fi

	# create data and log folders
	test -d $ZOO_DATADIR/zook${zoo_id} || mkdir $ZOO_DATADIR/zook${zoo_id}
	test -d $ZOO_DATALOGDIR/zook${zoo_id} || mkdir $ZOO_DATALOGDIR/zook${zoo_id}

	# create zoo.cfg file by filtering different partition
	if [ "$zoo_current_partition_count" -eq 0 ]; then
		cp $ZOO_CFGDIR/zoo.cfg.template ${zoo_cfg}.tmp
	else
		sed "/^server\.\($zoo_current_partition\)=/ b EOL;s/^server\.\(.*\)=/## Remove zook\1 by scripts for different partition\n# \0/;:EOL" $ZOO_CFGDIR/zoo.cfg.template > ${zoo_cfg}.tmp
	fi

	# write newline and clientPort parameter to configuraiton file
	# add dataDir and datalogDir attriubtes to configuration file
	cat - >> ${zoo_cfg}.tmp <<-EOF
	# Auto configured by shell
	clientPort=${client_port}
	dataDir=$ZOO_DATADIR/zook${zoo_id}
	datalogDir=$ZOO_DATALOGDIR/zook${zoo_id}
	EOF

	# check if new created is same as previous one
	if [ -f $zoo_cfg ]; then
		diff ${zoo_cfg}.tmp $zoo_cfg 2>&1 >/dev/null
		if [ $? -ne 0 ]; then
			# configuration is changed
			mv $zoo_cfg ${zoo_cfg}.prev
			mv ${zoo_cfg}.tmp $zoo_cfg
			echo Updated configuration for zook${zoo_id}: $zoo_cfg
		else
			rm ${zoo_cfg}.tmp
		fi
	else
		mv ${zoo_cfg}.tmp $zoo_cfg
		echo Create new configuration for zook${zoo_id}: $zoo_cfg
	fi

	# create myid file and set myid file content to server id
	echo ${zoo_id}> $ZOO_DATADIR/zook${zoo_id}/myid
}

function eval_client_port() {
	zoo_id=$1
	client_port=`sed -n "s/^clientPort\.${zoo_id}=\(.*\)$/\1/p" $ZOO_CFGDIR/zoo.cfg.template`
	if [ $? -ne 0 ]; then
		echo ERROR: No "clientPort.${zoo_id}" configured in $ZOO_CFGDIR/zoo.cfg.template 1>&2
		exit 1
	fi
}

function setup_all() {
	for zoo_id in $zoo_instances; do
		eval_client_port $zoo_id
		setup_single $zoo_id $client_port
	done
}

function setup_instance() {
	zoo_name=$1
	zoo_id=${zoo_name##zook}
	validate_name $zoo_name
	eval_client_port $zoo_id
	setup_single $zoo_id $client_port
}

function start_instance() {
	zoo_name=$1
	echo "Starting zookeeper instance ${zoo_name}..."
	$ZOO_BINDIR/zkServer.sh start ${zoo_name}.cfg 2>&1 | sed 's/^/> /'
}

function stop_instance() {
	zoo_name=$1
	echo "Stopping zookeeper instance ${zoo_name}..."
	$ZOO_BINDIR/zkServer.sh stop ${zoo_name}.cfg 2>&1 | sed 's/^/> /'
}

function status_instance() {
	zoo_name=$1
	zoo_id=${zoo_name##zook}
	eval_client_port $zoo_id
	netstat -an | grep LISTEN | grep $client_port 2>&1 1>/dev/null
	status=$?
	if [ $status -eq 0 ]; then
		echo "The zookeeper instance ${zoo_name} is running on port $client_port."
	else
		echo "The zookeeper instance ${zoo_name} is not running or failed to connect to port $client_port"
	fi
	return $status
}

function validate_name() {
	if [ ! -n "$1" ] ; then
		echo ERROR: Need zookeeper instance name, like 'zook1', or 'zoo' for default. 1>&2
		exit 1
	fi
}

current_host=`hostname`
zoo_instances=`sed -n "s/^server\.\([0-9]*\)=${current_host}.*/\1/p" $ZOO_CFGDIR/zoo.cfg.template`

# Main options
case $1 in
setup)
	setup_all
;;
start)
	zoo_name=$2
	validate_name $zoo_name
	setup_instance $zoo_name
	start_instance $zoo_name
;;
start-all)
	echo "Starting all zookeeper instances..."
	setup_all
	for zoo_id in $zoo_instances; do	
		zoo_name=zook${zoo_id}
		start_instance $zoo_name
	done
;;
stop)
	zoo_name=$2
	validate_name $zoo_name
	stop_instance $zoo_name
;;
stop-all)
	echo "Stopping all zookeeper instances..."
	for zoo_cfg in zook*.cfg; do
		zoo_id=${zoo_cfg##zook}
		zoo_id=${zoo_id%%.cfg}
		zoo_name=zook${zoo_id}
		stop_instance $zoo_name
	done
;;
restart)
	# restart one zookeeer ignore it whether down and running by pointing zookeeper config file
	zoo_name=$2
	validate_name $zoo_name
	stop_instance $zoo_name
	sleep 3
	setup_instance $zoo_name
	start_instance $zoo_name
;;
restart-all)
	# restart all one by one
	for zoo_id in $zoo_instances; do	
		zoo_name=zook${zoo_id}
		stop_instance $zoo_name
		sleep 3
		setup_instance $zoo_name
		start_instance $zoo_name
	done
;;
status)
	# check zookeeper server status
	zoo_name=$2
	validate_name $zoo_name
	status_instance $zoo_name
;;
status-all)
	# check all status	
	for zoo_id in $zoo_instances; do
		zoo_name=zook${zoo_id}
		status_instance $zoo_name
	done
;;
check)
	# check server if it is down, and restart this server if detect it is down
	zoo_name=$2
	validate_name $zoo_name
	status_instance $zoo_name
	if [ $? -ne 0 ]; then
		stop_instance $zoo_name
		sleep 3
		setup_instance $zoo_name
		start_instance $zoo_name
	fi
;;
check-all)
	# check servers if they are down, and restart one by one if detect down
	for zoo_id in $zoo_instances; do
		zoo_name=zook${zoo_id}
		status_instance $zoo_name
		if [ $? -ne 0 ]; then
			stop_instance $zoo_name
			sleep 3
			setup_instance $zoo_name
			start_instance $zoo_name
		fi
	done
;;
help|*)
	usage
;;
esac
