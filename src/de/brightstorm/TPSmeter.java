package de.brightstorm;

public class TPSmeter implements Runnable {
	long lastPoll = System.currentTimeMillis() - 3000;
	long polls = 0;

	public TPSmeter() {
		
	}
	
	@Override
	public void run() {
		long now = System.currentTimeMillis();
		long timeSpent = (now - lastPoll) / 1000;
		if (timeSpent == 0) timeSpent = 1;
		float tps = g.interval / timeSpent;
		if(tps>20) g.tps= 20;
		else g.tps = tps;
		lastPoll = now;
		polls++;
	}
}