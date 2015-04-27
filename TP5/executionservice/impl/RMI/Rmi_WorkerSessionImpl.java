package executionservice.impl.RMI;

import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;
import java.rmi.server.UnicastRemoteObject;

import executionservice.Task;
import executionservice.WorkerSession;
import executionservice.impl.TaskInfo;

public class Rmi_WorkerSessionImpl extends UnicastRemoteObject implements WorkerSession {
	private final Rmi_ExecutionService exe;
	private final String name;
	private TaskInfo info;
	private boolean closed=false;
	
	public Rmi_WorkerSessionImpl(String name, Rmi_ExecutionService exe) throws RemoteException {
		this.name=name;
		this.exe=exe;
	}

	public void close() {
		this.closed=true;
	}

	public Task getTask() throws InterruptedException {
		if (closed==true) throw new RuntimeException("worker session closed");
		this.info=exe.getAvailableTaskInfo();
		return info.getTask();
	}

	public void setResult(long result) {
		if (closed==true) throw new RuntimeException("worker session closed");
		info.setResult(result);
	}

}
