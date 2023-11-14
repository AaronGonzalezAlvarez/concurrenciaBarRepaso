package concurrenciaBarRepaso;

import java.util.concurrent.Semaphore;

public class Bar {
	
	private Semaphore control;
	private int aforo;
	private int prueba =0;
	
	public Bar( int aforo) {
		this.control = new Semaphore(aforo);
	}
	
	public void entrar (int x) throws InterruptedException {
		Boolean entro = false;
		while(!entro) {
			if(control.tryAcquire()) {
				entro = true;
				System.out.println("entro al bar , cliente " + x);
			}else {
				System.err.println("a la espera , cliente " + x);
				Thread.sleep(1500);
			}
		}
		
	}
	
	public void salir (int x) {
		System.out.println("cliente " + x + " sale del bar");
		control.release();
	}

}
