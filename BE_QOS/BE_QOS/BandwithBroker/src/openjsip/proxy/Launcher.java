package openjsip.proxy;

import java.net.InetAddress;

public class Launcher {

	
	public static void main(String[] args) 
	{
		ThreadListener Listener = new ThreadListener();
		try {

			Listener.join();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("THREAD Listener : Lancee ");
	}
}
