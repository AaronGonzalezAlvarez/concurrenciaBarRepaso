package concurrenciaBarRazaRepaso;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BarSemaphore {
	
	private Semaphore control;
	private Semaphore ewok;
	private Semaphore gorax;
	private AtomicInteger aforo;
	private AtomicInteger total;
	private AtomicInteger colaEwok;
	private AtomicInteger colaGorax;
	
	public BarSemaphore() {
		this.control = new Semaphore(5);
		this.ewok = new Semaphore(0);
		this.gorax = new Semaphore(0);
		//
		this.aforo = new AtomicInteger(5);
		this.colaEwok = new AtomicInteger(0);
		this.colaGorax = new AtomicInteger(0);
		this.total = new AtomicInteger(0);
	}
	
	public void entrar(int x, String raza) throws InterruptedException {

		
		while (!control.tryAcquire()) {
			if (raza.equals("ewook")) {
				colaEwok.incrementAndGet();
				System.err.println("Soy "+ raza + " y estoy a la espera: " + " Ewok: " + colaEwok.get() +" Gorax: " + colaGorax.get());
				ewok.acquire();
			} else {
				colaGorax.incrementAndGet();
				System.err.println("Soy "+ raza + " y estoy a la espera: " + " Ewok: " + colaEwok.get() +" Gorax: " + colaGorax.get());
				gorax.acquire();
			}
		}
		System.out.println("soy " + x + " de la raza " + raza + " y voy a entra al bar");
		total.incrementAndGet();

	}

	public void salir(int x, String raza) throws InterruptedException {
		//System.out.println("cliente " + x + " y de raza " + raza + " sale del bar" + " Ewok: " + colaEwok.get() +" Gorax: " + colaGorax.get());
		//System.out.println("cliente " + x + " y de raza " + raza + " sale del bar");
		if (colaEwok.get()> 0) {
			colaEwok.decrementAndGet();
			ewok.release();
		} else if(colaGorax.get()> 0) {
			colaGorax.decrementAndGet();
			gorax.release();
		}
		total.decrementAndGet();
		control.release();
	}

}
