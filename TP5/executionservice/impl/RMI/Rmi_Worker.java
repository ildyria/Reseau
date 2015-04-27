package executionservice.impl.RMI;

import java.net.MalformedURLException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.io.*;  

import executionservice.Task;
import executionservice.WorkerRegistration;
import executionservice.WorkerSession;

/**
 * This class describes the client object that handles the
 * communications with the server
 */

public class Rmi_Worker extends Thread {
	final WorkerRegistration wr;
	final String name;

	public Rmi_Worker(WorkerRegistration wr, String name) throws RemoteException {
		this.wr = wr;
		this.name = name;
	    this.start();
	 }
	
	public void run() {
		WorkerSession session = null;
		try {
			session = wr.register(name);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(true)
		{
			try {
				Task t;
				t = session.getTask();
				long res = t.execute();
				session.setResult(res);
				System.out.println("[Worker: " + name + "] sent result=" + res);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

  public static void main(String[] args) {
	System.setSecurityManager(new RMISecurityManager());

	String url;
    String server_name;
    String user;
    String adresseClient;
    String ad_server;

    // Checks the arguments passed on the command line and prints out
    // a help message if there are not enough arguments (2)
    if (args.length < 2) {
      System.out.println("Usage: client <server URL> <username>");
      return;
    } else {
      server_name = args[0];
      user = args[1];
    }
    try {
      ad_server = InetAddress.getByName(server_name).getHostAddress();
    } catch (UnknownHostException e) {
      return;
    }

    url = "rmi://"+ad_server+":50042/piDistributed";
    // Tries to connect to the server
    try {
      // Lookups up the server object using the rmiregistry
    	WorkerRegistration server = (WorkerRegistration) Naming.lookup(url);
      if (server != null) {
	System.out.println("Server found at URL " + url);

	// Server is found, let's create the client object
	Rmi_Worker theClient = new Rmi_Worker(server, user);
      } else {
	System.out.println("No server found at URL " + url);
      }
    } catch (MalformedURLException e) {
      System.out.println("URL is not a valid one: " + url);
    } catch (NotBoundException e) {
      System.out.println("No server bound with this URL: " + url);
    } catch (RemoteException e) {
      System.out.println("Error, client cannot find server: " + e);
    }

    return;
  }
}
