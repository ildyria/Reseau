package executionservice.impl.RMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

import executionservice.ExecutionService;
import executionservice.Task;
import executionservice.WorkerRegistration;
import executionservice.WorkerSession;
import executionservice.impl.TaskInfo;

public class Rmi_ExecutionService extends UnicastRemoteObject  implements ExecutionService, WorkerRegistration {

	private int nextId = 0;
	private Map<Integer, TaskInfo> tasks = new HashMap<Integer, TaskInfo>();
	private int availableTasks = 0;

	public synchronized int submitTask(Task task) {
		TaskInfo taskInfo = new TaskInfo(task);
		tasks.put(nextId, taskInfo);
		availableTasks++;
		notifyAll();
		return nextId++;
	}

	
	public long getResult(int taskid) throws InterruptedException {

		TaskInfo taskInfo;
		synchronized (this) {
			taskInfo = tasks.get(taskid);
		}
		long res=taskInfo.getResult();
		synchronized (this) {
			tasks.remove(taskid);
		}
		return res;
	}


	public WorkerSession register(String wname) {
		WorkerSession ws = null;
		try {
			ws = new Rmi_WorkerSessionImpl(wname, this);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ws;
	}

	synchronized TaskInfo getAvailableTaskInfo() throws InterruptedException {

		while (availableTasks == 0)
			wait();
		
		for (TaskInfo ti : tasks.values()) {
			if (ti.getAvailable()) {
				ti.setAvailable(false);
				availableTasks--;
				return ti;
			}
		}
		assert false;
		return null;
	}
	
	protected Rmi_ExecutionService() throws RemoteException {
	    super(50001);
		// TODO Auto-generated constructor stub
	}
}
