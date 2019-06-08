package openjsip.proxy;
import java.net.*;
import java.util.ArrayList;

public class Site {

private ArrayList<Client> clientSite;
private NetworkLink network;
private EdgeRouter routerEdge;
private SLA siteSLA;

	public Site(SLA sla,EdgeRouter router,NetworkLink net)
	{
		this.siteSLA = sla;
		this.routerEdge = router;
		this.network = net;
		this.clientSite= new ArrayList<Client>();
	}
	
	public boolean clientIn(Client client)
	{
		
		for (Client c : this.clientSite)
		{
			if(c.getAddr() == client.getAddr());
			{
				return true;
			}

		}
		
		return false;

	}
	
	public void siteAddClient(Client client)
	{
		this.clientSite.add(client);

	}
	
	public SLA getSLA()
	{
		return this.siteSLA;
	}
	
	public EdgeRouter getRouter()
	{
		return this.routerEdge;
	}
	public NetworkLink getNet()
	{
		return this.network;
	}
	
	public String toString()
	{
		String newLine = System.getProperty("line.separator");
		return(siteSLA.toString() +newLine+ routerEdge.toString() +newLine+ network.toString()+newLine+"##Nb Clients : "+this.clientSite.size());
	}
	
}
