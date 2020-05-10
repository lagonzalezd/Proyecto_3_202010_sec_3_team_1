package model.logic;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import controller.Controller;
import javafx.beans.binding.ObjectBinding;
import model.data_structures.Estacion;
import model.data_structures.EstacionArco;
import model.data_structures.EstacionVertice;
import model.data_structures.GrafoNoDirigido;
import view.View;

public class Modelo {

    private GrafoNoDirigido<Integer, EstacionVertice> graph;

    private Controller controller;

    public final static String rutaEstaciones = "./data/estacionpolicia.geojson";

    private EstacionVertice vert;
    private EstacionArco arc;

    private static View view;

    public Modelo() {
        view = new View();
    }

    public void cargar() throws IOException {
        int aarcos = 0;
        int avertices = 0;
        graph = new GrafoNoDirigido<>(228046);
        String rutaVertices = "./data/bogota_vertices.txt";
        String rutaArcos = "./data/bogota_arcos.txt";
        try {
            FileReader reader = new FileReader(rutaVertices);
            BufferedReader lector = new BufferedReader(reader);
            String linea = lector.readLine();
            while (linea != null) {
                String[] partes = linea.split(",");
                int id = Integer.parseInt(partes[0]);
                double longitud = Double.parseDouble(partes[1]);
                double latitud = Double.parseDouble(partes[2]);
                vert = new EstacionVertice(id, longitud, latitud);
                graph.addVertex(id, vert);
                avertices++;
                linea = lector.readLine();
            }
            lector.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FileReader reader = new FileReader(rutaArcos);
            BufferedReader lector = new BufferedReader(reader);

            String linea = lector.readLine();
            while (linea != null) {
                String[] partes = linea.split(" ");
                for (int i = 1; i < partes.length; i++) {
                    aarcos++;
                    graph.addEdge(Integer.parseInt(partes[0]), Integer.parseInt(partes[i]), 0);
                }
                linea = lector.readLine();
            }
            reader.close();
            lector.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        view.printNumEdgesAndVer(avertices + "", aarcos + "");
    }


    public void cargarEstaciones() {
        ArrayList lista = new ArrayList();

        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader(rutaEstaciones));
            JsonElement elem = JsonParser.parseReader(reader);
            JsonArray e2 = elem.getAsJsonObject().get("features").getAsJsonArray();


            for (JsonElement e : e2) {

                int OBJECTID = e.getAsJsonObject().get("properties").getAsJsonObject().get("OBJECTID").getAsInt();
                String FECHAIN = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOFECHA_INI").getAsString();
                String FECHAFIN = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOFECHA_FIN").getAsString();
                String DESCRIPCION = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPODESCRIP").getAsString();
                String DIR_SITIO = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPODIR_SITIO").getAsString();
                double latitud = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOLATITUD").getAsDouble();
                double longitud = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOLONGITU").getAsDouble();
                int TELEFONO = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOTELEFON").getAsInt();
                String CELEC = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOCELECTR").getAsString();

                Estacion esta = new Estacion(OBJECTID, FECHAIN, FECHAFIN, DESCRIPCION, DIR_SITIO, latitud, longitud, TELEFONO, CELEC);

                lista.add(esta);
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        String mensaje = "OBJECTID, FECHA INICIAL, FECHA FINAL, DESCRIPCION, DIRECCION DE LA ESTACION, LATITUD, LONGITUD, TELEFONO, CORREO";

        view.cargarEstaciones(lista.size(), mensaje);
    }

    public void createJson() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter("./data/informacion.json")) {
            gson.toJson(graph, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        view.mensaje2("./data/informacion.json");
    }

    public void readJson() {
        Gson gson = new Gson();
        String path = "./data/informacion.json";
        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader(path));
            graph = gson.fromJson(reader, EstacionVertice.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }


}