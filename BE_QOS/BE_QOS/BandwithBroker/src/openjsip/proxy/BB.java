package openjsip.proxy;
import java.net.*;
import java.util.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;

public class BB extends Thread {

private boolean noReceivedRequest=true;	
private Reservation receivedRes=null;
private int port = 1300;
//private static NetworkTopology network = null; //A configurer en dur.

public BB(int port, Reservation res) 
{
	this.port = port;
	this.receivedRes = res;
	start();
}

public void run() {


	//############# DEFINITION DE LA TOPOLOGIE DU RESEAU SALLE DU BE QoS
	// Site S1
	Client C_S1 =null;
	Client C_S2 =null;
	Client C_S3 =null;

	EdgeRouter rt_S1=null;
	EdgeRouter rt_S2=null;
	EdgeRouter rt_S3=null;
	
	try
	{
		
		//CLIENTS
		C_S1 = new Client("Client1S1",InetAddress.getByName("192.168.1.1"),2155);
		C_S2 = new Client("Client1S2",InetAddress.getByName("192.168.2.1"),2155);
		C_S3 = new Client("Client1S3",InetAddress.getByName("192.168.3.1"),2155);

		
		// EDGE ROUTER
		rt_S1 = new EdgeRouter("RouterS1",InetAddress.getByName("192.168.1.254"));
		rt_S2 = new EdgeRouter("RouterS2",InetAddress.getByName("192.168.2.254"));
		rt_S3 = new EdgeRouter("RouterS3",InetAddress.getByName("192.168.3.254"));


	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
	
	// SLAs
	SLA sla_s1 = new SLA(100,100,100);
	SLA sla_s2 = new SLA(100,100,100);
	SLA sla_s3 = new SLA(100,100,100);

	
	
	// NetworkLink
	NetworkLink nw_s1 = new NetworkLink(100,0,"LinkS1");
	NetworkLink nw_s2 = new NetworkLink(100,0,"LinkS2");
	NetworkLink nw_s3 = new NetworkLink(100,0,"LinkS3");

	
	// Création sites
	Site s1 = new Site(sla_s1, rt_S1, nw_s1);
	s1.siteAddClient(C_S1);

	Site s2 = new Site(sla_s2, rt_S2, nw_s2);
	s2.siteAddClient(C_S2);
	
	Site s3 = new Site(sla_s3, rt_S3, nw_s3);
	s3.siteAddClient(C_S3);

	
	NetworkTopology network = new NetworkTopology();
	network.addSite(s1);
	network.addSite(s2);
	network.addSite(s3);

	
			System.out.println("BB : Requete recue");
			Site siteA = network.getSiteFromClient(receivedRes.getClientA());
			Site siteB = network.getSiteFromClient(receivedRes.getClientB());
			
			System.out.println(siteA);
			System.out.println(siteB);

			try {
				
				System.out.println(receivedRes.getSIP().getAddr());
				Socket MySocket = new Socket(receivedRes.getSIP().getAddr(),receivedRes.getSIP().getPortR()); //receivedRes.getSIP().getPortR()
				// Creation des sockets permettant de repondre l'acception ou non de la demande
				ObjectOutputStream out = NetworkFunctions.outObj(MySocket);
				ObjectInputStream in = NetworkFunctions.inObj(MySocket);
				
				if (siteA.getSLA().compare(receivedRes.getTspec()) && siteB.getSLA().compare(receivedRes.getTspec()) )   //On verifie que le Tspec correspond aux SLAs des sites
				{
					if (siteA.getNet().available(receivedRes.getTspec().getADR()) &&  siteB.getNet().available(receivedRes.getTspec().getADR()) ) //On verifie que le reseau peut suporter le data rate moyen en A et B
					{
						siteA.getNet().take(receivedRes.getTspec().getADR()); // On prend la ressource sur le site A
						siteB.getNet().take(receivedRes.getTspec().getADR()); // On prend la ressource sur le site B
						
						try {
								receivedRes.ReservationOK();
								out.writeObject(receivedRes);// On envoie l'acceptation
								System.out.println("BB : Requete accepte");

							}
							
							catch(IOException e){
								System.out.println("Erreur a l'envoi de l'acceptation");
							}
							// On configure les routers pour la communication en A et B
							ThreadConfigRouter r1 = new ThreadConfigRouter(siteA.getRouter(),0,receivedRes.getClientA().getAddr(), receivedRes.getClientB().getAddr());
							ThreadConfigRouter r2 = new ThreadConfigRouter(siteB.getRouter(),0,receivedRes.getClientB().getAddr(), receivedRes.getClientA().getAddr());
							try {
								r1.join();
								r2.join(); // A verifier ?
							}
							catch(Exception e)
							{
								e.printStackTrace();
							}
							Reservation res2 = null;
							ObjectOutputStream out1 = NetworkFunctions.outObj(MySocket);
							ObjectInputStream in1 = NetworkFunctions.inObj(MySocket);
							ServerSocket connexionBye = new ServerSocket(this.port);
							System.out.println("Attente de fermeture");
							while(true)
							{
								Socket MySocket2 = connexionBye.accept();
	
	
								try 
								{
									while (res2 == null)
									{
										res2 = (Reservation) in.readObject(); // On sauvegarde la requete dans la variable res.
	
									}
								}
								catch (Exception e)
								{
									e.printStackTrace();
								}
								if (res2.getDEC())
								{
									siteA.getNet().give(receivedRes.getTspec().getADR()); // On libere la ressource sur le site A
									siteB.getNet().give(receivedRes.getTspec().getADR()); // On libere la ressource sur le site B
									
									//effacement des règles routeurs
									new ThreadConfigRouter(siteA.getRouter(),1,receivedRes.getClientA().getAddr(), receivedRes.getClientB().getAddr());
									new ThreadConfigRouter(siteB.getRouter(),1,receivedRes.getClientB().getAddr(), receivedRes.getClientA().getAddr());

									
									this.stop(); // On termine le Thread
								}
							}	
					}
				}
				else
				{
					try {
						receivedRes.ReservationKO();
						out.writeObject(receivedRes); // On envoie le refus
						System.out.println("BB : Requete refusee");
					}
					
					catch(IOException e) {
						System.out.println("Erreur a l'envoi du refus");
					}
			
				}
			}
			catch(IOException e)
			{
				e.printStackTrace();
				System.out.println("Erreur a l'envoi de la reponse");
			}
			
			noReceivedRequest = true;

		}
	
}
