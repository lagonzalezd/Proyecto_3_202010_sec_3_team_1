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
                    modelo.cargarComparendos();
                    modelo.cargarEstaciones();
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
                	int cercano = modelo.req1ParteInicial(la, lo);
                	view.printMessage("El vertice mas cercano es: " + cercano);
                    break;

                case 4:
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
