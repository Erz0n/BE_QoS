echo "INITIALISATION"
// Créationde la discipline queuing racine
tc qdisc del dev eth0 root
tc qdisc add dev eth0 root handle 1: htb default 20

// Création des classes filles 
tc class add dev eth0 parent 1: classid 1:1 htb rate $PREMIUM_DR ceil 700kbit // VOIX
tc class add dev eth0 parent 1: classid 1:2 htb rate $BE_DR ceil 57900kbit // DEFAUT
