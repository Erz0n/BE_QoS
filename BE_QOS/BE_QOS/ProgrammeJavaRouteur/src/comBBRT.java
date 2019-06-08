import java.io.Serializable;
import java.net.InetAddress;

public class comBBRT implements Serializable{
	
	int command; //0 - Creation ; 1 - Destruction
	InetAddress ipsrc;
	InetAddress ipdst;
	
	public comBBRT(int command, InetAddress ipsrc, InetAddress ipdst)
	{
		this.command = command;
		this.ipsrc = ipsrc;
		this.ipdst = ipdst;
	}
	
	public String toString()
	{
		return ("Command " + this.command +" de " +this.ipsrc + " vers " + this.ipdst );
	}

}
