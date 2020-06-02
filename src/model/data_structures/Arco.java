package model.data_structures;

public class Arco {
	private int inicio;
	private int fin;
	private double costo;
	
	public Arco(int idInicio, int idFinal, double pCosto) {
		inicio = idInicio;
		fin = idFinal;
		costo = pCosto;
	}
	
	public int getInicio() {
		return inicio;
	}
	public int getFin() {
		return fin;
	}
	
	public double getCosto() {
		return costo;
	}
	
	public void setCost(double nCosto) {
		this.costo = nCosto;
	}

	public String toString()
	{

		return "ID de inicio: "+inicio+", ID de fin:"+fin+", costo:"+costo;
	}

}
