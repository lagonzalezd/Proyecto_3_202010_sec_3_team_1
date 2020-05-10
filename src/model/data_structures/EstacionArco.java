package model.data_structures;

public class EstacionArco {
	private int inicio;
	private int fin;
	private double costo;
	
	public EstacionArco(int idInicio, int idFinal, double pCosto) {
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
}
