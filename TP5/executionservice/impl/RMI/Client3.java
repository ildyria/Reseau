package executionservice.impl.RMI;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import executionservice.impl.RMI.Rmi_ExecutionService;
import executionservice.Task;
import executionservice.WorkerRegistration;
import executionservice.WorkerSession;
import executionservice.impl.*;



public class Client3 {
	static final int NUMTASKS=8;
	static final int NUMWORKERS=5;
	static private List<Integer> ids =new ArrayList<Integer>();

	public static void main (String[] args) {

		
		
		String url;
		
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
		// If the user did not provide as a command-line argument the URL
		// where the server should be registered, let's display an error message
		// and exit the program
		try {
		  url = "rmi://"+ InetAddress.getLocalHost()
		    .getHostAddress() + ":50042/piDistributed";
		} catch (UnknownHostException e) {
		  return;
		}

		Rmi_ExecutionService es = null;
		try {
			es = new Rmi_ExecutionService();
			LocateRegistry.createRegistry(50042);
			Naming.bind(url, es);
			System.out.println("Server started and registered with URL " + url);
		} catch (MalformedURLException e) {
			System.out.println("The following URL is not valid: " + url);
		} catch (AlreadyBoundException e) {
			System.out.println(
				"Another server is already registered with URL " + url);
		} catch (RemoteException e) {
			System.out.println("Error, the server could not be launched: " + e);
		}
//
//		WorkerRegistration wr=(WorkerRegistration)es;
//		for (int i=0; i<NUMWORKERS;i++) {
//			new Worker(wr,"worker"+i).start();
//		}
		
		// Start timing.
		long totalPoints=100;
		long totalCount=0;

		Scanner sc = new Scanner(System.in);
		sc.nextLine();

		System.out.println("GO GO GO !");
		long time = System.currentTimeMillis();
		for (int i=0; i<NUMTASKS;i++) {
			int id=es.submitTask(new PiTask(totalPoints / NUMTASKS,i));
			ids.add(id);
		}
		for (Integer i: ids) {
			long res = 0;
			try {
				res = es.getResult(i);
				totalCount+=res;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			// System.out.println("Result "+i+" is :"+res);
		}

		// Print approximate value of pi.
        System.out.println ("pi = " + 4.0 * totalCount / totalPoints);

		// Stop timing.
        time = System.currentTimeMillis()-time;
        System.out.println (time + " msec");
        }
}