package openjsip.proxy;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;

public class ThreadConfigRouter extends Thread {
	
	private EdgeRouter router;
	private int command; // 0 CREATION - 1 DESTRUCTION
	InetAddress adrsrc;
	InetAddress adrdst;

	public ThreadConfigRouter(EdgeRouter router, int command, InetAddress adrsrc, InetAddress adrdst)
	{
		this.router = router;
		this.command = command;
		this.adrsrc = adrsrc;
		this.adrdst = adrdst;
		start();
	}

	public void run()
	{
		ObjectOutputStream out = null;
		Socket MySocket = null;
		try
		{
			MySocket = new Socket(router.getAddr(),1235);
			out = NetworkFunctions.outObj(MySocket);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		if(command == 0)
		{
			System.out.println(command);
			comBBRT mess = new comBBRT(0,adrsrc, adrdst); 
			try {
				out.writeObject(mess);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(command == 1)
		{
			System.out.println(command);
			comBBRT mess = new comBBRT(1,adrsrc, adrdst); 
			try {
				out.writeObject(mess);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			MySocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
