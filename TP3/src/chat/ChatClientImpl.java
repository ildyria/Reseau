package chat;

import java.net.MalformedURLException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.io.*;  



/**
 * This class describes the client object that handles the
 * communications with the server
 */

public class ChatClientImpl extends Thread {
  /**
   * The name of the user who sends messages using this client
   */

  protected String user;

  protected boolean alive;

  /**
   * Remote reference to the server
   */

  protected ChatServer theServer;


  /**
   * We create and initialise an object for user <code>user</code> in order
   * to speak to the server which can be found at the URL passed as a second
   * parameter
   */

  public ChatClientImpl(String user, ChatServer server)
    throws RemoteException {
    this.user = user;
    this.theServer = server;
    this.alive = true;

    this.start();
    this.sendMessage();
    System.out.println("Bye bye !");
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

    url = "rmi://"+ad_server+":50042/simpleChatServer";
    // Tries to connect to the server
    try {
      // Lookups up the server object using the rmiregistry
      ChatServer server = (ChatServer) Naming.lookup(url);
      if (server != null) {
	System.out.println("Server found at URL " + url);

	// Server is found, let's create the client object
	ChatClientImpl theClient = new ChatClientImpl(user, server);
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

  /**
   *   Get all the messages from the server and asks the
   *   user interface to display them
   *   nb_msg nombre de messages deja consultes 
   *          Permet de déterminer quels sont les nouveaux messages
   *   retourne le nombre de messages disponibles sur le serveur
   */
  protected int displayAllMessages(int nb_msg) {
	  String messages[] = null;
	  try{
		   messages = theServer.getAllMessages();
	  }
	  catch(Exception e){
		  System.err.println("EXCEPTIONNNNNNNN !!!!! :mqsooidfbqmsodfbmqs");
	  }
	  
	  for(int k = nb_msg; k < messages.length; k++)
	  {
		  System.out.println(messages[k]);
		  nb_msg++;
	  }
	  return nb_msg;
  }

  /**
   *   Adds a new message to the server
   */

  protected void sendMessage()  {
    String line;
    BufferedReader clavier = new BufferedReader(new  InputStreamReader(System.in));
    boolean toSend = true;
    while (alive) {
    	toSend = true;
      try {
			System.out.println("Tape une ligne ");
			line = clavier.readLine();
			if(line.equals("/exit")){alive = false;};
			if(line.length() >= 4 && line.substring(0,4).equals("/me ")){
				line = this.user + " " + line.substring(4);
			}else if(line.length() >= 5 && line.substring(0,5).equals("/nick"))
			{
				if(line.length() > 6)
				{
					this.user = line.substring(6);
					System.out.println("Hello " + user);
				}
				else
				{
					System.out.println("/nick need a parameter NOOB !");
				}
				toSend = false;
			}
			else
			{
				line = "[" + this.user + "] " + line;
			}
			if(toSend){
				// Remote call
				this.theServer.addNewMessage(line);
			}
      } catch (RemoteException e) {
    	  System.out.println("Problem while sending a message to the server: " + e);
      } catch (IOException ex) {
    	  System.err.println ("IO Problem "+ex);
      }
    }
  }

  public void run() {
    int nombre_messages = 0;
    while(alive) {
	// M�morisation du nombre de message deja consult�s
      nombre_messages = displayAllMessages(nombre_messages);
      try {
	Thread.sleep(300);
      } catch (InterruptedException e) {
	System.err.println("Ploblem while sleep");
      }
    }
  }
}
