package barCana.lock;

import java.util.Random;

public class Cliente  extends Thread{
	
	private int numCanas;
	private int c;
	private Bar bar;
	private Random rand;
	
	public Cliente(int numCanas, int c, Bar bar) {
		this.numCanas = numCanas;
		this.c = c;
		this.bar = bar;
		this.rand = new Random();
	}
	
	
	public void run() {
		try {
			Thread.sleep(rand.nextInt(1000,3000));
			bar.tomarCaña(c,numCanas);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
