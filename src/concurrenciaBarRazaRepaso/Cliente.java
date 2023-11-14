package concurrenciaBarRazaRepaso;

import java.util.Random;

public class Cliente extends Thread{

	private int id;
	private BarSynchronized bar;
	private String raza;
	private Random rand = new Random();
	
	public Cliente(int id, BarSynchronized bar, String raza) {
		this.id = id;
		this.bar = bar;
		this.raza = raza;
	}
	
	
	public void run() {
		try {
			bar.entrar(id, raza);
			Thread.sleep(5000);
			bar.salir(id);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
