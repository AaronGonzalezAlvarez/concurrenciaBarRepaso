package concurrenciaBarRazaRepaso;

import java.util.Random;

public class Cliente extends Thread{

	private int id;
	private BarSemaphore bar;
	private String raza;
	private Random rand = new Random();
	
	public Cliente(int id, BarSemaphore bar, String raza) {
		this.id = id;
		this.bar = bar;
		this.raza = raza;
	}
	
	
	public void run() {
		try {
			bar.entrar(id, raza);
			Thread.sleep((1 + rand.nextInt(4)) * 1000);
			bar.salir(id, raza);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
