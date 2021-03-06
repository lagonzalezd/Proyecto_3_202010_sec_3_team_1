package model.data_structures;
import model.logic.Modelo;

public class Vertice <E>{

	private double latitud;
	private double longitud;
	private int id;
	public final static String ARCOS = "./data/bogota_arcos.txt";
	
    public Vertice(int pid, double plongitud, double platitud){
    	id = pid;
    	latitud = platitud;
    	longitud = plongitud;
    }


	public double getLatitud() {
		return latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public int getId() {
		return id;
	}
	
	public String toString(){
		return "ID: " + id + ", latitud: " + latitud + ", longitud: " + longitud;
	}
}