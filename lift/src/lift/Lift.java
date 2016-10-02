package lift;


public class Lift extends Thread {

	private int curr_floor;
	private Monitor mon;
	private LiftView lv;
	//private int next_floor;
	private int dir;
	
	public Lift(Monitor m, LiftView v){
		dir = 1;
		mon = m;
		lv = v;
	}
	public void run(){
		while(true){
			mon.moveLift(curr_floor, curr_floor + dir);
			lv.moveLift(curr_floor, curr_floor += dir);
			if(curr_floor == 6 || curr_floor == 0){
				dir *= -1;
			}
		}
	}
}
