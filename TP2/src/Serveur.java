import java.net.*; 
import java.io.*;
import java.util.*;

class ServerClient extends Thread {
	// must listen to a socket
	// must send message to all other clients
	Socket socket = null;
	
	void send_message(String message) throws IOException{
		for(int i = 0; i < Serveur.serverClientList.size(); ++i) // car serverClientList est static !!
		{
			if(Serveur.serverClientList.get(i) != this)
			{
				Serveur.serverClientList.get(i).send_to_client(message);
			};
		}
	}

	void send_to_client(String messageEmis) throws IOException{
		PrintWriter bufferEmission = new PrintWriter(new BufferedWriter(new	OutputStreamWriter(socket.getOutputStream())), true);

		bufferEmission.println(messageEmis);
	}
	

	
	public void run(){
		BufferedReader bufferReception;

		try {
			bufferReception = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	
			String messageRecu;
	        while ((messageRecu = bufferReception.readLine()) != null) 
	             {
	        		System.out.println(messageRecu);
	        	 	send_message(messageRecu);
	             }
	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	
	ServerClient(Socket s) {
		socket  = s;
		System.out.println("new socket opened");
	}
	
}

public class Serveur
{
	static boolean quit = false;
    static int port = 50042;  // port par défaut
    
    public static ArrayList<ServerClient> serverClientList;
    
    static void usage(){
    	message("Usage :\n java Serveur ["+port+"]\n");
    }

    static void message(String msg){
    	System.err.println(msg);
    }

    static void erreur(String msg){
    	message("Erreur: "+msg);
    }

    static void erreur(){
    	erreur(null);
    }

    public static void shutup(){
    	quit = true;
    }
    
    static String date(){
		// ou System.currentTimeMillis()
		Date d= new Date();
		
		return d.toString();
    }

    static String erreur400() {
		String msg=null;
	
		msg += "Toto\n";
		return msg;
    }

    public static void main (String argv[]) {
		ServerSocket socket = null;

		if (argv.length == 1 ){
		    port = Integer.parseInt(argv[0]);
		} else if ( argv.length != 1 )
		    usage();
	
		// Création de la socket
		try {socket = new ServerSocket(port);} catch (IOException e){
			erreur("Impossible d'ouvrir le port "+port+":"+e);
		}

		while( !quit ) { // Attente de connexion
		    Socket s = null;
		    try {s=socket.accept();} catch (IOException e){
		    	ServerClient sc = new ServerClient(s);
		    	sc.start();
		    	serverClientList.add(sc);
		    	erreur("accept "+e);
		    }
		}
	
		try{ socket.close(); } catch (IOException e) { 
				System.err.println("Impossible fermer la socket "+e);
		}
    }
}

