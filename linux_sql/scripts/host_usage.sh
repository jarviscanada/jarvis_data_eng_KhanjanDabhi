#!/bin/bash/
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5
if [ $# -ne  5 ]; then
  echo "Invalid number of arguments passed in the shell script."
  exit 1
fi


vmdata=$(vmstat --unit M)
hostname=$(hostname -f)

memory_free=$(echo "$vmdata" | awk '{print $4}'| tail -1)
cpu_idle=$(echo "$vmdata" | awk '{print $15}'| tail -1)
cpu_kernel=$(echo "$vmdata" | awk '{print $14}'| tail -1)
disk_io=$(vmstat -d| awk '{print $10}'| tail -1)
disk_available=$(df -BM /| awk '{print $2}'| tail -1|tr -dc '0-9')
timestamp=$(vmstat -t | awk '{print $18,$19}'|tail -1)

insert_stmt="INSERT INTO host_usage (timestamp, host_id, memory_free,cpu_idle,cpu_kernel,disk_io,disk_available) VALUES('$timestamp',(SELECT id FROM host_info WHERE hostname='$hostname'), $memory_free, $cpu_idle, $cpu_kernel, $disk_io, $disk_available)"

export PGPASSWORD=$psql_password
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?
