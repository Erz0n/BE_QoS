package openjsip.proxy;
import java.io.*;
import java.net.Socket;

public class NetworkLink {
	private int total_DR;
	private int used_DR;
	private String name;
	
	
public NetworkLink(int DR, int uDR, String name)
	{
		this.total_DR = DR;
		this.used_DR = uDR;
		this.name = name ;
		
	}

	public int get_total_DR()
	{
		return this.total_DR;
	}
	
	public int get_used_DR()
	{
		return this.get_used_DR();
	}
	
	public String get_name()
	{
		return this.name;
	}
	
	public boolean available(int charge)
	{
		if (this.total_DR- this.used_DR > charge)
			return true;
	return false;
	}
	
	public void take(int charge)
	{
		this.used_DR = this.total_DR - charge;
		System.out.println(this.name + "a pris " + charge + " / " + this.total_DR);
	}
	
	public void give(int charge)
	{
		this.total_DR = this.used_DR + charge;
	}
	
	public String toString()
	{
		return ("##Link - " + name + " Used DR : " + used_DR + " - Total DR : " + total_DR);
	}
	
	
}
