package concurrenciaBarRepaso;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BarLock {
	
	private ReentrantLock control;
	private Condition espera;
	private int aforo;
	private int clienteActual;
	private AtomicInteger prueba = new AtomicInteger(0);
	
	public BarLock( int aforo) {
		this.control = new ReentrantLock();
		this.espera = control.newCondition();
		this.aforo = aforo;
		this.clienteActual = 0;
	}
	
	public void entrar(int x) throws InterruptedException {

		while (aforo == prueba.get()) {
			control.lock();
			System.err.println("a la espera , cliente " + x);
			espera.await();
			control.unlock();
		}
		prueba.incrementAndGet();
		System.out.println("en el bar el cliente : " + x);

	}

	public void salir(int x) {
		control.lock();
		prueba.decrementAndGet();
		espera.signal();
		System.out.println("cliente " + x + " sale del bar");
		control.unlock();
	}

}
