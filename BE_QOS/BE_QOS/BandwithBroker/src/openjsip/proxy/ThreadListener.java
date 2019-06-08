package openjsip.proxy;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public class ThreadListener extends Thread {

	
public ThreadListener()
{
	start();
}

public void run()
{
	int port = 1300;
	Reservation res = null;
	try {
		ServerSocket connexionSIP = new ServerSocket(1234);  // Num du socket  definir
		Socket MySocket = connexionSIP.accept();
		System.out.println("Connexion TCP etablie entre le SIP et le BB");


		
		while(true) {
			ObjectOutputStream out = NetworkFunctions.outObj(MySocket);
			ObjectInputStream in = NetworkFunctions.inObj(MySocket);
			try {
				while (res == null)
				{
					res = (Reservation) in.readObject(); // On sauvegarde la requ�te dans la variable res.

					System.out.println(res);

				}
				}
				catch (Exception e)
				{
					e.printStackTrace();
					System.out.println("Erreur a la lecture de la reservation");
				}
			
			System.out.println(res);
			System.out.println(res.getClientA().getAddr().toString() + res.getClientB().getAddr().toString());
			
			
			//out.close();
			//in.close();
			 port ++;
             BB BB1 = new BB(port,res);
              
		}
	
	}
	
	catch (IOException e )
	{
		System.out.println("Erreur a l'execution du BB");
	}
	
}
}
