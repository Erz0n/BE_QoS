package openjsip.proxy;

import java.net.*;
import java.util.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.net.NetworkInterface;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

// Classe RESERVE qui permet de recevoir les demandes de reservation du SIP
public class Reservation implements Serializable {

//private String URI_A;
//private String URI_B;

private Client clientA;
private Client clientB;
private Client SIP;
private String STATE;

private Tspec tspecAB;

	public Reservation(Client clientA,Client clientB, Client SIP, Tspec spec)
	{

		this.clientA = clientA;
		this.clientB = clientB;
		this.SIP = SIP;
		this.STATE = "REQ";
		this.tspecAB = spec;
	}
	
	public Reservation(Client clientA,Client clientB)
	{

		this.clientA = clientA;
		this.clientB = clientB;
		this.STATE = "REQ";
	}
	
	public Client getClientB()
	{
		return clientB;
	}
	
	public Client getClientA()
	{
		return clientA;
	}	
	
	public Tspec getTspec()
	{
		return this.tspecAB;
	}
	
	public Client getSIP()
	{
		return this.SIP;
	}
	
	public void ReservationOK()
	{
		this.STATE="OK";
	}
	
	public void ReservationKO()
	{
		this.STATE="KO";
	}
	
	public boolean getOK()
	{
		if (this.STATE.equals("KO"))
			return true;
		return false;
	}
	public void ReservationDEC()
	{
		this.STATE="DEC";
	}
	
	public boolean getDEC()
	{
		if (this.STATE.equals("DEC"))
			return true;
		return false;
	}
}
