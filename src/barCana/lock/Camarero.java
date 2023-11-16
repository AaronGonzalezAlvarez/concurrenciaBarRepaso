package barCana.lock;

public class Camarero  extends Thread{
	private Bar bar;
	
	public Camarero(Bar bar) {
		this.bar = bar;
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
