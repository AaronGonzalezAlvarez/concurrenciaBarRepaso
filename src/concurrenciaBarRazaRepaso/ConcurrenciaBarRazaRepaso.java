package concurrenciaBarRazaRepaso;

public class ConcurrenciaBarRazaRepaso {

	public static void main(String[] args) {
		System.out.println("bar");
		Bar bar = new Bar();
		
		int aux =0;
		for(int x=0; x<5;x++) {
			Cliente c = new Cliente(aux, bar, "ewook");
			aux++;
			c.start();
			Cliente cl = new Cliente(aux, bar, "gorax");
			cl.start();
			aux++;
		}

	}

}
