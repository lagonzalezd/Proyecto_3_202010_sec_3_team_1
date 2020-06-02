package controller;

import java.util.Scanner;

import model.logic.Modelo;
import view.View;

public class Controller {

    /* Instancia del Modelo*/
    private Modelo modelo;

    /* Instancia de la Vista*/
    private View view;

    public static double MIN_LONGITUD = -74.094723;

    public static double MIN_LATITUD = 4.597714;

    public static double MAX_LONGITUD = -74.062707;

    public static double MAX_LATITUD = 4.621360;

    public Controller() {
        view = new View();
        modelo = new Modelo();
    }


    public void run() {
        Scanner lector = new Scanner(System.in);
        boolean fin = false;

        while (!fin) {
            view.printMenu();

            int option = lector.nextInt();
            switch (option) {
                case 1:
                    modelo.cargarComparendos();
                    modelo.cargarEstaciones();
                    modelo.cargarVertices();
                    modelo.cargarGrafo();
                    break;
                case 2:
                    //modelo.createJson();
                    break;
                case 3:
                    view.printMessage("Ingrese la latitud");
                    double la = lector.nextDouble();
                    view.printMessage("Ingrese la longitud");
                    double lo = lector.nextDouble();
                    int cercano = modelo.req1Inicial(la, lo);
                    view.printMessage("El vertice mas cercano es: " + cercano + "\n");
                    break;

                case 4:
                    modelo.mapas();
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    view.printMessage("Ingrese la latitud inicial");
                    double latIni = Double.parseDouble(lector.next());
                    view.printMessage("Ingrese la longitud inicial");
                    double longIni = Double.parseDouble(lector.next());
                    view.printMessage("Ingrese la latitud final");
                    double latFin = Double.parseDouble(lector.next());
                    view.printMessage("Ingrese la longitud final");
                    double longFin = Double.parseDouble(lector.next());

                    if (latIni < MIN_LATITUD || latIni > MAX_LATITUD || longIni < MIN_LONGITUD || longIni > MAX_LONGITUD) {
                        view.printMessage("Las coordenadas iniciales estan fuera del rango.");
                        break;
                    }

                    if (latFin < MIN_LATITUD || latFin > MAX_LATITUD || longFin < MIN_LONGITUD || longFin > MAX_LONGITUD) {
                        view.printMessage("Las coordenadas finales estan fuera del rango.");
                        break;
                    }

                    modelo.requerimiento1B(latIni, longIni, latFin, longFin);
                    break;
                case 10:
                    break;
                case 11:
                    break;
                case 12:
                    break;

                default:
                    view.printMessage("--------- \n Opcion Invalida \n---------");
                    fin = true;
                    break;
            }
        }

    }
}
