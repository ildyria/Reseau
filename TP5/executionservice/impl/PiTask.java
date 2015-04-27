package executionservice.impl;

import java.io.Serializable;
import java.util.Random;

import executionservice.Task;

public class PiTask implements Task, Serializable{

	private long n;
	private long seed;

	public PiTask(long l, long seed) {
		this.n = l;
		this.seed = seed;
	}

	public long execute() {

		Random prng = new Random(seed);
		long count = 0;

		for(long i = 0; i < n; ++i)
		{
			double x = prng.nextDouble();
			double y = prng.nextDouble();
			if(x*x + y*y <= 1)
			{
				count ++;
			}
		}
		return count;
		// Generate n random points in the unit square and count how many are
		// in the unit circle

		// INSERT CODE HERE

	}

}