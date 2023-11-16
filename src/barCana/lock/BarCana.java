package barCana.lock;

public class BarCana {

	public static void main(String[] args) {
		Bar bar = new Bar(10);
		Camarero camaremo = new Camarero(bar);
		camaremo.start();
		
		for(int x=0; x<10;x++) {
			Cliente c = new Cliente(2,x,bar);
			c.start();
		}

	}

}
