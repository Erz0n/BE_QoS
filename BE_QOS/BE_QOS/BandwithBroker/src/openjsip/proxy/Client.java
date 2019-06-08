package openjsip.proxy;
import java.net.*;
import java.util.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.net.NetworkInterface;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class Client implements Serializable{

private String name;
private InetAddress addressIP;
private int portR;

public Client(String name, InetAddress addressIP, int portR)
{
	this.name=name;
	this.addressIP=addressIP;
	this.portR = portR;
}

public String getName()
{
	return this.name;
}

public InetAddress getAddr()
{
	return this.addressIP;
}

public int getPortR()
{
	return this.portR;
}

public String toString()
{
	return("##Client - " + this.name + " @" + this.addressIP + ":" + this.portR);
}
}

