package view;

import model.logic.Modelo;

public class View 
{
		public void printMenu()
		{
			System.out.println("1. Cargar todos los datos (comparendos, estaciones, malla vial)");
			System.out.println("2. Persistir los datos en un archivo Json");
			System.out.println("__________________PARTE INCIAL________________");
			System.out.println("3. Vertice mas cercano");
			System.out.println("4. Agregar informacion de los comparendos 2018");
			System.out.println("5. Agregar informacion costo");
			System.out.println("6. Adicionar estaciones");
			System.out.println("__________________Requerimientos Parte A________________");
			System.out.println("7. Requerimiento Parte A-1");
			System.out.println("8. Requerimiento Parte A-2");
			System.out.println("__________________Requerimientos Parte B________________");
			System.out.println("9. Requerimiento Parte B-1");
			System.out.println("10. Requerimiento Parte B-2");
			System.out.println("__________________Requerimientos Parte C________________");
			System.out.println("11. Requerimiento Parte C-1");
			System.out.println("12. Requerimiento Parte C-2");



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
