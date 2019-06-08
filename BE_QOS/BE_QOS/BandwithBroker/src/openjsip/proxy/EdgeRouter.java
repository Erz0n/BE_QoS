package openjsip.proxy;
import java.net.*;

public class EdgeRouter {

private InetAddress addressIP;
private String name;

public EdgeRouter(String name, InetAddress addressIP)
{
	this.addressIP=addressIP;
	this.name= name;
}

public String getName()
{
	return this.name;
}

public InetAddress getAddr()
{
	return this.addressIP;
}

public String toString()
{
	return ("##EdgeRouter - " + this.name + "@" + this.addressIP);
}

}
