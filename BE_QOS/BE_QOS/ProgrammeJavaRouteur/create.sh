echo "CREATION" $1 "vers" $2

tc class add dev eth0 parent 1:1 classid 1:$3 htb rate 64kbit ceil 64kbit
iptables -A PREROUTING -t mangle -s $1 -d $2 -p tcp --dport 5001 -j MARK --set-mark $3
tc filter add dev eth0 parent 1:1 protocol ip prio 1 handle $3 fw flowid 1:$3
iptables -A PREROUTING -t mangle -s $1 -d $2 -p tcp --dport 5001 -j DSCP --set-dscp-class EF #EF => DSCP = 46

echo "FIN CREATION"
