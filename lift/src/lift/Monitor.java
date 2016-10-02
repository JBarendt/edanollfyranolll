package lift;

public class Monitor {

	int here; // If here!=next, here (floor number) tells from which floor // the lift is moving and next to which floor it is moving.
	int next; // If here==next, the lift is standing still on the floor // given by here.
	int[] waitEntry;// The number of persons waiting to enter the lift at the // various floors.
	int[] waitExit; // The number of persons (inside the lift) waiting to leave // the lift at the various floors.
	int load; // The number of people currently occupying the lift.
	LiftView lv;
	Lift lift;
	Person person;
	
	public Monitor(LiftView lift_view){
		waitEntry = new int[7];
		waitExit = new int[7];
		lv = lift_view;
		lift = new Lift(this, lv);
		lift.start();
		for(int i = 0; i<20;i++){
			new Person(this).start();
		}
	}
	private synchronized boolean allAlone(){
		boolean alone = true;
		for(int i = 0; i < 7; i++){
			if(waitEntry[i] != 0){
				alone = false;
			}
		}
		if(load != 0)
			alone = false;
		return (alone); 
	}
	public synchronized void moveLift(int from, int to) {
		here = from;
		next = from;
		notifyAll();
		while(allAlone() || waitExit[from] > 0 || (waitEntry[from] > 0 && load < 4)){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		next = to;
		notifyAll();
	}
	
	public synchronized void spawn(int floor, int dest){
		lv.drawLevel(floor, ++waitEntry[floor]);
		notifyAll();
		while(here != next ||here != floor || load > 3){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		waitExit[dest]++;
		lv.drawLevel(here, --waitEntry[here]);
		lv.drawLift(here, ++load);
		notifyAll();
		while(here != dest){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		waitExit[here]--;
		lv.drawLift(here, --load);
		notifyAll();	
	}
}
