package concurrenciaBarRazaRepaso;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BarSynchronized {
	
	private Object control;
	private Object ewok;
	private Object gorax;
	private AtomicInteger aforo;
	private AtomicInteger total;
	private AtomicInteger colaEwok;
	private AtomicInteger colaGorax;
	
	public BarSynchronized() {
		this.control = new Object();
		this.ewok = new Object();
		this.gorax = new Object();
		//
		this.aforo = new AtomicInteger(5);
		this.colaEwok = new AtomicInteger(0);
		this.colaGorax = new AtomicInteger(0);
		this.total = new AtomicInteger(0);
	}

	public void entrar(int x, String raza) throws InterruptedException {
			while (aforo.get() == total.get()) {
				if (raza.equals("ewook")) {
					colaEwok.incrementAndGet();
					System.err.println("Soy " + raza + " y estoy a la espera: " + " Ewok: " + colaEwok.get()
							+ " Gorax: " + colaGorax.get());
					synchronized (ewok) {
						ewok.wait();
					}
				} else {
					colaGorax.incrementAndGet();
					System.err.println("Soy " + raza + " y estoy a la espera: " + " Ewok: " + colaEwok.get()
							+ " Gorax: " + colaGorax.get());
					synchronized (gorax) {
						gorax.wait();
					}
				}
			}

			System.out.println("soy " + x + " de la raza " + raza + " y voy a entra al bar");
			total.incrementAndGet();
	}

	public void salir(int x, String raza) {
		synchronized (control) {
			System.out.println("cliente " + x + " y de raza " + raza + " sale del bar" + " Ewok: " + colaEwok.get()
					+ " Gorax: " + colaGorax.get());
			if (colaEwok.get() > 0) {
				colaEwok.decrementAndGet();
				synchronized (gorax) {
					gorax.notifyAll();
				}
			} else if (colaGorax.get() > 0) {
				colaGorax.decrementAndGet();
				synchronized (gorax) {
					gorax.notifyAll();
				}
			}
			total.decrementAndGet();
		}
	}

}
