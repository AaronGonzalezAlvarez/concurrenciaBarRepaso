package concurrenciaBarRazaRepaso;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Bar {
	
	private ReentrantLock control;
	private Condition ewok;
	private Condition gorax;
	private AtomicInteger aforo;
	private AtomicInteger total;
	private AtomicInteger colaEwok;
	private AtomicInteger colaGorax;
	
	public Bar() {
		this.control = new ReentrantLock();
		this.ewok = control.newCondition();
		this.gorax = control.newCondition();
		//
		this.aforo = new AtomicInteger(5);
		this.colaEwok = new AtomicInteger(0);
		this.colaGorax = new AtomicInteger(0);
		this.total = new AtomicInteger(0);
	}
	
	public void entrar(int x, String raza) throws InterruptedException {

		control.lock();
		while (aforo.get() == total.get()) {
			if (raza.equals("ewook")) {
				colaEwok.incrementAndGet();
				System.err.println("Soy "+ raza + " y estoy a la espera: " + " Ewok: " + colaEwok.get() +" Gorax: " + colaGorax.get());
				ewok.await();
			} else {
				colaGorax.incrementAndGet();
				System.err.println("Soy "+ raza + " y estoy a la espera: " + " Ewok: " + colaEwok.get() +" Gorax: " + colaGorax.get());
				gorax.await();
			}
		}
		
		System.out.println("soy " + x + " de la raza " + raza + " y voy a entra al bar");
		total.incrementAndGet();
		control.unlock();

	}

	public void salir(int x, String raza) {
		control.lock();
		System.out.println("cliente " + x + " y de raza " + raza + " sale del bar" + " Ewok: " + colaEwok.get() +" Gorax: " + colaGorax.get());
		if (colaEwok.get()> 0) {
			colaEwok.decrementAndGet();
			ewok.signal();
		} else {
			colaGorax.decrementAndGet();
			gorax.signal();
		}
		total.decrementAndGet();
		control.unlock();
	}

}
