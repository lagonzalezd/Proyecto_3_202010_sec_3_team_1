package model.data_structures;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Comparendo implements Comparable<Comparendo> {
	
	private final static double lat = 4.647586;
	private final static double lon = -74.078122;
	
	
	public String INFRACCION;
	public String DES_INFRACCION;
	public String LOCALIDAD;
	public String MUNICIPIO;
	public String FECHA_HORA;
	public String MEDIO_DETECCION;
	public String CLASE_VEHICULO;
	public String TIPO_SERVICIO;
	public int OBJECTID;
	public double distanciaAlaEstacion;
	private Haversine claseDis;
	
	public String indicadorComparacion;

	
	public double longitud;
	public double latitud;
	
	public Comparendo(int objectID, String fechaHora, String medioDeteccion, String claseVehiculo, String tipoServicio, String infraccion, String desInfraccion, String localidad, String municipio, double latitud, double longitud, String criterio){
		this.OBJECTID = objectID;
		this.FECHA_HORA = fechaHora;
		this.MEDIO_DETECCION = medioDeteccion;
		this.CLASE_VEHICULO = claseVehiculo;
		this.TIPO_SERVICIO = tipoServicio;
		this.INFRACCION = infraccion;
		this.DES_INFRACCION = desInfraccion;
		this.LOCALIDAD = localidad;
		this.MUNICIPIO = municipio;
	
		this.indicadorComparacion = criterio;
		
		this.longitud = longitud;
		this.latitud = latitud;
	
		
		this.distanciaAlaEstacion = claseDis.distance(latitud, longitud, lat, lon);
	}
	
	public void cambiarIndicador(String nuevoCriterio){
		indicadorComparacion = nuevoCriterio;
	}
	
	public Date convertirFechaStringADate(){
		DateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd");
		Date convertido = null;
		try {
			convertido = fechaHora.parse(FECHA_HORA);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return convertido;
	}
	
	@Override
	public int compareTo(Comparendo ob) {
		if(indicadorComparacion.equalsIgnoreCase("OBJECTID")){
			return OBJECTID>ob.OBJECTID ? 1 : OBJECTID<ob.OBJECTID ? -1 : 0;
		}
		else if (indicadorComparacion.equalsIgnoreCase("DISTANCIA")){
			return distanciaAlaEstacion<ob.distanciaAlaEstacion ? 1 : OBJECTID>ob.OBJECTID ? -1 : 0;
		}
		else if (indicadorComparacion.equalsIgnoreCase("FECHA")){
			Date estaFecha = convertirFechaStringADate();
			Date otraFecha = ob.convertirFechaStringADate();
			return estaFecha.compareTo(otraFecha);
		}
		else if (indicadorComparacion.equals("FECHAMIN")){
			Date estaFecha = convertirFechaStringADate();
			Date otraFecha = ob.convertirFechaStringADate();
			return -1 * estaFecha.compareTo(otraFecha);
		}
		return 0;
	}
	
	
	public String toString(){
		
		return OBJECTID + ", " + FECHA_HORA + ", " + INFRACCION
				+ ", " + CLASE_VEHICULO + ", " + TIPO_SERVICIO
				+ ", " + LOCALIDAD+".";
	}
	

}