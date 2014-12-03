package com.insa.swim.orchestrator;

public class Listener implements Runnable {
	
	private boolean run;
	
	public Listener() {
		run = false;
	}
	
	public void start() {
		run = true;
	}
	
	public void stop() {
		run = false;
	}

        @Override
	public void run() {
		while(run) {
			
		}
	}

}
