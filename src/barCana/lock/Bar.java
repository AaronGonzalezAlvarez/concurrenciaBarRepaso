package barCana.lock;

public class Bar {

	int numBotellines;
	int numBotellinesAux;
	Object o1;
	Object o2;
	boolean cambiar;

	public Bar(int numBotellines) {
		this.numBotellines = numBotellines;
		this.numBotellinesAux = numBotellines;
		this.o1 = new Object();
		this.o2 = new Object();
		this.cambiar = false;
	}

	public void tomarCaña(int c,int botellinesATomar) throws InterruptedException {
		synchronized (o1) {
			while(!verificarBotellinesDisponibles(botellinesATomar)) {
				System.out.println("no hay cerveza suficiente para el cliente : "+c);	
				//cambiar = true;
				synchronized (o2) {
					cambiar = true;
					o2.notify();
				}
				Thread.sleep(500);
				o1.wait();
			}
			servirCaña(botellinesATomar); // Servir una caña
			System.out.println("el cliente "+ c + " ya ha tomado sus botellines, en el barril quedan: " + numBotellines);
		}
	}

	private void servirCaña(int botellinesATomar) {
		for(int x=0; x<botellinesATomar;x++) {
			numBotellines--;
		}
	}

	public void reponer() throws InterruptedException {
		synchronized (o2) {
			while (true) {
				if (cambiar) {
					//synchronized (o1) {
						cambiarBarril(); // Esta acción indica que se repone el barril
						System.out.println("el camarero a cambiado el barril");// anotar que se ha repuesto el barril
						cambiar = false;
						synchronized (o1) {
							o1.notify();
						}
					//}
				} else {
						o2.wait();
						System.out.println("eeeeeeeeeeooooooooooo");
				}
			}
		}
	}

	private void cambiarBarril() {
		this.numBotellines = numBotellinesAux;
		
	}
	
	private boolean verificarBotellinesDisponibles(int numBotellinesATomarCliente) {
		return numBotellines >= numBotellinesATomarCliente;
	}
}
