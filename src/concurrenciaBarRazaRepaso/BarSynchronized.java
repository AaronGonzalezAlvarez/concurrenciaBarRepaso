package concurrenciaBarRazaRepaso;

public class BarSynchronized {
	//funciona
	private Object o1;
	private Object ewok;
	private Object gorax;
	private int aforo;
	private int total;
	private int colaEwok;
	private int colaGorax;

	public BarSynchronized() {
		this.o1 = new Object();
		this.ewok = new Object();
		this.gorax = new Object();
		//
		this.aforo = 5;
		this.colaEwok = 0;
		this.colaGorax = 0;
		this.total = 0;
		//
	}

	public void entrar(int x, String raza) throws InterruptedException {
		while (!siNoLLenoIncrementarUno()) {
			if (raza.equals("Ewok")) {
				synchronized (ewok) {
					colaEwok++;
					System.out.println("Soy " + x + " y de la raza " + raza + " y estoy a la espera");
					ewok.wait();
					colaEwok--;
				}
			} else if (raza.equals("Gorax")) {
				synchronized (gorax) {
					colaGorax++;
					System.out.println("Soy " + x + " y de la raza " + raza + " y estoy a la espera");
					gorax.wait();
					colaGorax--;
				}

			}
		}
		System.out.println("soy " + x + " de la raza " + raza + " y voy a entra al bar\n\tEwok: " + colaEwok + " Gorax: " + colaGorax);
	}

	public void salir(int x) {
		System.out.println("cliente " + x + " sale del bar");
		total--;
		if (colaEwok > 0) {
			synchronized (ewok) {
				ewok.notify();
			}
		} else {
			synchronized (gorax) {
				gorax.notify();
			}
		}
	}

	private boolean siNoLLenoIncrementarUno() {
		if (aforo > total) {
			total++;
			return true;
		}
		return false;
	}

}
