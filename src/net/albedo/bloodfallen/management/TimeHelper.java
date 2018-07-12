package net.albedo.bloodfallen.management;


public class TimeHelper {
	private long startTime;

	public TimeHelper() {
		reset();
	}

	
	public void reset() {
		startTime = System.currentTimeMillis();
	}

	
	public long getMSPassed() {
		return System.currentTimeMillis() - startTime;
	}

	
	public boolean hasMSPassed(long ms) {
		return getMSPassed() >= ms;
	}
}
