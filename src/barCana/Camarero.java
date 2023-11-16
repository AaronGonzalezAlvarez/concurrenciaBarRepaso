package barCana;

public class Camarero  extends Thread{
	private Bar bar;
	
	public Camarero(Bar bar2) {
		this.bar = bar2;
	}
	
	
	public void run() {
		try {
			bar.reponer();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}

