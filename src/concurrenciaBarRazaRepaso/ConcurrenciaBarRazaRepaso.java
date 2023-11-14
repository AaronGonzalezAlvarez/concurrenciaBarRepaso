package concurrenciaBarRazaRepaso;

public class ConcurrenciaBarRazaRepaso {

	public static void main(String[] args) {
		System.out.println("bar");
		//Bar bar = new Bar();
		BarSynchronized bar = new BarSynchronized();
		//BarSemaphore bar = new BarSemaphore();
		
		int aux =0;
		/*for(int x=0; x<5;x++) {
			Cliente c = new Cliente(aux, bar, "Ewok");
			aux++;
			c.start();
			Cliente cl = new Cliente(aux, bar, "Gorax");
			cl.start();
			aux++;
		}*/
		for (int i = 0; i < 20; i++) {
			Cliente c = (new Cliente(aux, bar, "Gorax"));
			c.start();
			aux++;
		}
		for (int i = 0; i < 10; i++) {
			Cliente c = (new Cliente(aux, bar, "Ewok"));
			c.start();
			aux++;
		} 

	}

}
