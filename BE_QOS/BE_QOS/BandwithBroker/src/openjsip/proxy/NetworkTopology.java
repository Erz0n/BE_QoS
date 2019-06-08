package openjsip.proxy;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class NetworkTopology {

	private ArrayList<Site> sites;
	
public NetworkTopology() {
	
	this.sites = new ArrayList<Site>();
	
}

public void addSite(Site site)
{
	this.sites.add(site);
}

public Site getSiteFromClient(Client Client)
{
	for (int i=0; i<sites.size(); i++)
	{
		if (sites.get(i).clientIn(Client))
		{
			System.out.println("Client trouvé !");
			return sites.get(i);
		}
		else
		{
			System.out.println("Client introuvable");
		}
	}
	return null;
}
}
