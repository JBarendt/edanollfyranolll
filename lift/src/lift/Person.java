package lift;

public class Person extends Thread {
	Monitor lm;
	int start;
	int dest;
	
	public Person(Monitor monitor){
		lm = monitor;
	}
	
	private void shuffle(){
		try {
			int delay = 1000*((int)(Math.random()*6.0)); // sleep need milliseconds
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		start = (int) (Math.random()*7);
		dest = (int) (Math.random()*7);
		
		while(start == dest){
			dest = (int) (Math.random()*7);
		}
	}
	public void run(){
		
		shuffle();
		while(true){
			lm.spawn(start,dest);
			shuffle();
		}
	}
}
