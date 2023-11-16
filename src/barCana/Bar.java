package barCana;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Bar {

	int numBotellines;
	int numBotellinesAux;
	ReentrantLock lock;
	Condition avisarCarero;
	Condition esperar;
	
	boolean cambiar;

	public Bar(int numBotellines) {
		this.numBotellines = numBotellines;
		this.numBotellinesAux = numBotellines;
		this.lock = new ReentrantLock();
		this.avisarCarero = lock.newCondition();
		this.esperar = lock.newCondition();
		this.cambiar = false;
	}

	public void tomarCaña(int c, int botellinesATomar) throws InterruptedException {
		lock.lock();
		while (!verificarBotellinesDisponibles(botellinesATomar)) {
			System.out.println("no hay cerveza suficiente para el cliente : " + c);
			cambiar = true;
			avisarCarero.signal();
			esperar.await();
		}
		servirCaña(botellinesATomar); // Servir una caña
		System.out.println("el cliente " + c + " ya ha tomado sus botellines, en el barril quedan: " + numBotellines);
		lock.unlock();
	}

	private void servirCaña(int botellinesATomar) {
		for(int x=0; x<botellinesATomar;x++) {
			numBotellines--;
		}
	}

	public void reponer() throws InterruptedException {
		while (true) {
			lock.lock();
			if (cambiar) {
				cambiarBarril(); // Esta acción indica que se repone el barril
				System.out.println("el camarero a cambiado el barril");// anotar que se ha repuesto el barril
				cambiar = false;
				esperar.signalAll();
			} else {
				avisarCarero.await();
				System.out.println("eeeeeeeeeeooooooooooo");
			}
			lock.unlock();
		}
	}

	private void cambiarBarril() {
		this.numBotellines = numBotellinesAux;
		
	}
	
	private boolean verificarBotellinesDisponibles(int numBotellinesATomarCliente) {
		return numBotellines >= numBotellinesATomarCliente;
	}
}
