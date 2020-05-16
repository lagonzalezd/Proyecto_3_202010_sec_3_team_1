package controller;

import java.io.IOException;
import java.util.Scanner;

import model.logic.Modelo;
import view.View;

public class Controller {

    /* Instancia del Modelo*/
    private Modelo modelo;

    /* Instancia de la Vista*/
    private View view;

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
                    try {
                    	modelo.cargarComparendos();
                        modelo.cargarEstaciones();
                        modelo.reqCargarEstaciones();
                        modelo.cargar();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    modelo.createJson();
                    break;
                case 3:
                	view.printMessage("Ingrese la latitud");
                	double la = lector.nextDouble();
                	view.printMessage("Ingrese la longitud");
                	double lo = lector.nextDouble();
                	int cercano = modelo.req1ParteInicial(la, lo);
                	view.printMessage("El vertice mas cercano es: " + cercano);
                    break;

                case 4:
                    break;
                default:
                    view.printMessage("--------- \n Opcion Invalida \n---------");
                    fin = true;
                    break;
            }
        }

    }
}
