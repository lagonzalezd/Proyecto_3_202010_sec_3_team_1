package view;

import model.logic.Modelo;

public class View 
{
		public void printMenu()
		{
			System.out.println("1. Cargar Datos");
			System.out.println("2. Persistir los datos en un archivo Json");
			System.out.println("3. Leer archivo Json");
			System.out.println("4. Cargar estaciones");
		}
		
		public void printNumEdgesAndVer(String edges, String ver){
			System.out.println("El numero de arcos es: " + edges);
			System.out.println("El numero de vertices es: " + ver+"\n");
		}

		public void mensaje2(String path)
		{
			System.out.println("Los datos se guardaron en: "+path+"\n");
		}
		
		public void cargarComparendos(int numeroDeComparendos){
			System.out.println("Se cargaron " + numeroDeComparendos + "comparendos");
		}

		public void cargarEstaciones(int numEst)
		{
			System.out.println("Se cargaron "+numEst+" estaciones");
		}
		public void printMessage(String mensaje) {

			System.out.println(mensaje);
		}
		
		
}
