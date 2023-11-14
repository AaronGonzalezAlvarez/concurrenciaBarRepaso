package concurrenciaBarRepaso;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BarSynchronized {
	
	private Object control;
	private int aforo;
	private AtomicInteger prueba = new AtomicInteger(0);
	
	public BarSynchronized( int aforo) {
		this.control = new Object();
		this.aforo = aforo;
	}
	
	public void entrar(int x) throws InterruptedException {

		while (aforo == prueba.get()) {
			synchronized (control) {
				System.err.println("a la espera , cliente " + x);
				control.wait();
			}			
		}
		prueba.incrementAndGet();
		System.out.println("en el bar el cliente : " + x);

	}

	public void salir(int x) {
		synchronized (control) {
			prueba.decrementAndGet();
			control.notify();
			System.out.println("cliente " + x + " sale del bar");
		}
	}
}
