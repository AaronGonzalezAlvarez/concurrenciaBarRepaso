package concurrenciaBarRazaRepaso;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Bar {
	
	private ReentrantLock control;
	private Condition ewok;
	private Condition gorax;
	private int aforo;
	private int total;
	private int colaEwok;
	private int colaGorax;
	
	public Bar() {
		this.control = new ReentrantLock();
		this.ewok = control.newCondition();
		this.gorax = control.newCondition();
		//
		this.aforo = 5;
		this.colaEwok = 0;
		this.colaGorax = 0;
		this.total = 0;
	}
	
	public void entrar(int x, String raza) throws InterruptedException {

		control.lock();
		while (!siNoLLenoIncrementarUno()) {
			if (raza.equals("Ewok")) {
				colaEwok++;
				System.err.println("Soy " + raza + " y estoy a la espera");
				ewok.await();
				colaEwok--;
			} else if (raza.equals("Gorax")) {
				colaGorax++;
				System.err.println("Soy " + raza + " y estoy a la espera");
				gorax.await();
				colaGorax--;
			} else {
				System.out.println("ni uno ni otro");
			}
		}
		
		System.out.println("soy " + x + " de la raza " + raza + " y voy a entra al bar");
		System.err.println("\tEwok: " + colaEwok + " Gorax: " + colaGorax);
		//total++;
		control.unlock();

	}

	public void salir(int x) {
		control.lock();
		System.out.println("cliente " + x + " sale del bar");
		if (colaEwok > 0) {
			ewok.signal();
		} else{
			gorax.signal();
		}
		total--;
		control.unlock();
	}
	
	private boolean siNoLLenoIncrementarUno() {
		if (aforo > total) {
			total++;
			return true;
		}
		return false;
	}

}
