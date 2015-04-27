package executionservice;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Allows workers to obtain and execute tasks.
 * 
 */
public interface WorkerSession extends Remote {
	/**
	 * Provides a task that is available for execution. Blocks if there is no available task.
	 * @return the task
	 * @throws InterruptedException 
	 */
	Task getTask() throws InterruptedException, RemoteException;
	/**
	 * Sets the result of executing the task. 
	 * @param result
	 */
	void setResult(long result) throws RemoteException;
	/**
	 * Closes this session.
	 */
	void close() throws RemoteException;
	

}
