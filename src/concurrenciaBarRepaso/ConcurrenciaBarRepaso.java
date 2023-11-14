package concurrenciaBarRepaso;

public class ConcurrenciaBarRepaso {

	public static void main(String[] args) {
		//Bar bar = new Bar(1);
		//BarLock bar = new BarLock(5);
		BarSynchronized bar = new BarSynchronized(9);
		for(int x=0; x<10;x++) {
			Cliente c = new Cliente(x,bar);
			c.start();
		}

	}

}
