package executionservice.impl;

import java.rmi.RemoteException;

import executionservice.Task;
import executionservice.WorkerRegistration;
import executionservice.WorkerSession;

public class Worker extends Thread {
	final WorkerRegistration wr;
	final String name;

	public Worker(WorkerRegistration wr, String name) {
		this.wr = wr;
		this.name = name;
	}

	public void run() {
		WorkerSession session = null;
		try {
			session = wr.register(name);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// The worker continuously gets and executes tasks
		while(true) {
			try {
				Task t = null;
				t = session.getTask();
				long res = t.execute();
				session.setResult(res);
				System.out.println("[Worker: " + name + "] sent result=" + res);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// session.close();
	}
}
