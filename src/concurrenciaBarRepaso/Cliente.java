package concurrenciaBarRepaso;

public class Cliente extends Thread{
	
	private int x;
	private BarSynchronized bar;
	
	public Cliente(int x, BarSynchronized bar) {
		this.x = x;
		this.bar = bar;
	}
	
	public void run() {
		try {
			bar.entrar(x);
			Thread.sleep(3*1000);
			bar.salir(x);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
