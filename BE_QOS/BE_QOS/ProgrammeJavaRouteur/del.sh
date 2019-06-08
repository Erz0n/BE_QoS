echo "DELETE" $1 "vers" $2
tc class del dev eth0 parent 1:1 classid 1:$3 htb rate 64kbit ceil 64kbit
iptables -D PREROUTING -t mangle -s $1 -d $2 -p tcp --dport 5001 -j MARK --set-mark $3
tc filter del dev eth0 parent 1:1 protocol ip prio 1 handle $3 fw flowid 1:$3
iptables -D PREROUTING -t mangle -s $1 -d $2 -p tcp --dport 5001 -j DSCP --set-dscp-class EF #EF => DSCP = 46
echo "FIN DELETE"