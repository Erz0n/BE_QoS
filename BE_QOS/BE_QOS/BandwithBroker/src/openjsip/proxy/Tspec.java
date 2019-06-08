package openjsip.proxy;
import java.net.*;
import java.util.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.net.NetworkInterface;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Tspec implements Serializable {

private int average_DR;
private int peak_DR;
private int burst_DR;


public Tspec (int aDR, int pDR, int bDR)
{
	this.average_DR=aDR;
	this.peak_DR=pDR;
	this.burst_DR = bDR;
}

public int getADR()
{
	return this.average_DR;
}

public int getPDR()
{
	return this.peak_DR;
}

public int getBDR()
{
	return this.burst_DR;
}

}
