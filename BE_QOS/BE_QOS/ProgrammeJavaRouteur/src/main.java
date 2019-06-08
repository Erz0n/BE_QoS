import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
public class main {

	public static void main(String[] args) {
		
	comBBRT res = null;
	Socket MySocket = null;
	
	//INITIALISATION DES FILES
	JavaRunCommand threadCommand = new JavaRunCommand("init");

	threadCommand.start();	
	try {
		threadCommand.join();

	}
	catch (Exception e)	{
		System.out.println(e.getMessage());
	}
	
	// Création du socket d'écoute
	ServerSocket connexion = null;
	try {
		connexion = new ServerSocket(1235);
	} catch (IOException e1) {
		e1.printStackTrace();
	} 

		
	// On boucle en attendant des nouvelles connexions
	while (true)
	
	{
		res =null;
		try {
			MySocket = connexion.accept();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}

		ObjectInputStream in = NetworkFunctions.inObj(MySocket);
		try {
			while (res == null)
				res = (comBBRT) in.readObject(); 
		}
		catch (Exception e)	{
			System.out.println(e.getMessage());
		}
		
		System.out.println(res);
		/*try {
			MySocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		
		if(res.command==0)
		{
			threadCommand = new JavaRunCommand("ajoutFile", res.ipsrc, res.ipdst);
			threadCommand.start();

			try {
				threadCommand.join();
			}
			catch (Exception e)	{
				System.out.println(e.getMessage());
			}
		}
		
		if(res.command==1)
		{
			threadCommand = new JavaRunCommand("supprimeFile", res.ipsrc, res.ipdst);
			threadCommand.start();
			
			try {
				threadCommand.join();
			}
			catch (Exception e)	{
				System.out.println(e.getMessage());
			}
		}
		
		
		
		
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	}
}


