package model.data_structures;

public class EstacionVertice  {

	private double latitud;
	private double longitud;
	private int id;
	
    public EstacionVertice(int pid, double plongitud, double platitud){
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
}