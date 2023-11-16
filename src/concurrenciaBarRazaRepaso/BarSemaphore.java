package concurrenciaBarRazaRepaso;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BarSemaphore {
	//funciona
	private Semaphore entrar;
	private Semaphore salir;
	private Semaphore ewok;
	private Semaphore gorax;
	private Semaphore prueba;
	private int total;
	private int colaEwok;
	private int colaGorax;
	private int aforo;
	
	public BarSemaphore() {
		this.entrar = new Semaphore(5);
		this.salir = new Semaphore(5);
		this.ewok = new Semaphore(0);
		this.gorax = new Semaphore(0);
		this.prueba = new Semaphore(1);
		//
		this.colaEwok = 0;
		this.colaGorax = 0;
		this.total = 0;
		this.aforo =5;
	}
	
	public void entrar(int x, String raza) throws InterruptedException {

		while (!entrar.tryAcquire()) {
			if (raza.equals("Ewok")) {
				colaEwok++;
				System.out.println("Soy "+ raza + " y estoy a la espera");
				ewok.acquire();
				colaEwok--;
			} else {
				colaGorax++;
				System.out.println("Soy "+ raza + " y estoy a la espera");
				gorax.acquire();
				colaGorax--;
			}
		}
		
		System.out.println("soy " + x + " de la raza " + raza + " y voy a entra al bar\n\tEwok: " + colaEwok +" Gorax: " + colaGorax);
		total++;
		//salir.acquire();

	}

	public void salir(int x) throws InterruptedException {
		System.out.println("cliente " + x + " sale del bar");
		total--;
		if (colaEwok> 0) {
			ewok.release();
		} else {
			gorax.release();
		}
		//salir.release();
		entrar.release();
	}

}