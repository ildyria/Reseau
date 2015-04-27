package executionservice;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 
 * Manages worker registration.
 *
 */
public interface WorkerRegistration extends Remote {
	/**
	 * Registers a worker with given name and returns a session object to be used by the worker. 
	 * 
	 * @param wname
	 * @return session object
	 */
WorkerSession register(String wname) throws RemoteException;
}
