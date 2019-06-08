import java.io.*;
import java.lang.*;
import java.net.InetAddress;

public class JavaRunCommand extends Thread {
	
	String command = null;
	InetAddress ipsrc = null;
	InetAddress ipdst = null;
	
	public JavaRunCommand(String command)
	{
		this.command = command;
	}
	
	public JavaRunCommand(String command, InetAddress ipsrc, InetAddress ipdst)
	{
		this.command = command;
		this.ipsrc = ipsrc;
		this.ipdst = ipdst;
	}

    public void run() {

        String s = null;

        try {
            
	    // run the Unix "ps -ef" command
            // using the Runtime exec method:
        	Process p = null;        	
        	if(this.command=="init")
        	{
        		String[] cmd = { "sh", "./init.sh", "test_arg"};
        		p = Runtime.getRuntime().exec(cmd);
        		try {
					p.waitFor();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}	
      	
        	}	
        	else if(this.command=="ajoutFile")
        	{
        		String[] cmd = { "sh", "./create.sh", ipsrc.toString().substring(1), ipdst.toString().substring(1)};
        		p = Runtime.getRuntime().exec(cmd);
        		try {
					p.waitFor();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}	        	
        	}	
        	else if(this.command=="supprimeFile")
        	{
        		String[] cmd = { "sh", "./del.sh", ipsrc.toString().substring(1), ipdst.toString().substring(1)};      
        		p = Runtime.getRuntime().exec(cmd);
        		try {
					p.waitFor();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}	
        	}

            BufferedReader stdInput = new BufferedReader(new 
                 InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new 
                 InputStreamReader(p.getErrorStream()));

            // read the output from the command
            System.out.println("OUTPUT :");
            while ((s = stdInput.readLine()) != null) {
                System.out.println("\t" + s);
            }
            
            // read any errors from the attempted command
            System.out.println("ERROR :\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println("\t" + s);
            }
            
            stdInput.close();
            stdError.close();

            //System.exit(0);
        }
        catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);
        }
    }
}