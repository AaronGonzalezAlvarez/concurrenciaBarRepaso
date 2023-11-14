package concurrenciaBarRazaRepaso;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BarSynchronized {

	private Object entrada;
	private Object salida;
	private Object ewok;
	private Object gorax;
	private AtomicInteger aforo;
	private AtomicInteger total;
	private AtomicInteger colaEwok;
	private AtomicInteger colaGorax;

	public BarSynchronized() {
		this.entrada = new Object();
		this.salida = new Object();
		this.ewok = new Object();
		this.gorax = new Object();
		//
		this.aforo = new AtomicInteger(5);
		this.colaEwok = new AtomicInteger(0);
		this.colaGorax = new AtomicInteger(0);
		this.total = new AtomicInteger(0);
	}

	public void entrar(int x, String raza) throws InterruptedException {
		synchronized (entrada) {
			while (!siNoLLenoIncrementarUno()) {
				if (raza.equals("Ewok")) {
					colaEwok.incrementAndGet();
					System.err.println("Soy " + x + " y de la raza " + raza + " y estoy a la espera");
					synchronized (ewok) {
						ewok.wait();
						colaEwok.decrementAndGet();
					}
				} else if (raza.equals("Gorax")) {
					colaGorax.incrementAndGet();
					System.err.println("Soy " + x + " y de la raza " + raza + " y estoy a la espera");
					synchronized (gorax) {
						gorax.wait();
						colaGorax.decrementAndGet();
					}
				}else {
					System.out.println("ni uno ni otro");
				}
			}
		}
		System.out.println("soy " + x + " de la raza " + raza + " y voy a entra al bar");
		System.err.println("\tEwok: " + colaEwok + " Gorax: " + colaGorax);
	}
	

	public void salir(int x) {
	    synchronized (salida) {
	        System.out.println("cliente " + x + " sale del bar");
	        if (colaEwok.get() > 0) {
	            synchronized (ewok) {
	                ewok.notifyAll();
	            }
	        } else if (colaGorax.get() > 0) {  // Corregido aquí
	            synchronized (gorax) {
	                gorax.notifyAll();
	            }
	        }
	        synchronized (entrada) {
		        total.decrementAndGet();
			}
	    }
	}

	private boolean siNoLLenoIncrementarUno() {
		if (aforo.get() > total.get()) {
			total.incrementAndGet();
			return true;
		}
		return false;
	}

}
