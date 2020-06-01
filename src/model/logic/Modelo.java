package model.logic;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import controller.Controller;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.ResizingArrayBag;
import javafx.beans.binding.ObjectBinding;
import model.data_structures.Comparendo;
import model.data_structures.Estacion;
import model.data_structures.EstacionArco;
import model.data_structures.EstacionVertice;
import model.data_structures.GrafoNoDirigido;
import model.data_structures.Haversine;
import view.View;

public class Modelo {

    private GrafoNoDirigido<Integer, EstacionVertice> graph;

    private ResizingArrayBag<Estacion> estaciones;
    private Estacion mayorEstacion;

    private ResizingArrayBag<Comparendo> comparendos;
    private Comparendo mayorComparendo;

    private EstacionVertice mayorIDVertice;
    private EstacionArco mayorIDArco;

    private Controller controller;

    public final static String rutaEstaciones = "./data/estacionpolicia.geojson";
    private static final String GRANDE = "./data/Comparendos_DEI_2018_Bogot�_D.C.geojson";
    private static final String PEQUENIO = "./data/Comparendos_DEI_2018_Bogot�_D.C_small_50000_sorted.geojson";
    private String archivoActualComparendo;

    private EstacionVertice vert;
    private EstacionArco arc;

    private static View view;

    public Modelo() {
        view = new View();
    }

    public void cargar() throws IOException {
        int aarcos = 0;
        int avertices = 0;

        mayorIDVertice = new EstacionVertice(0, 0, 0);
        mayorIDArco = new EstacionArco(0, 0, 0);

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

                //va sacando el mayor
                if (id > mayorIDVertice.getId()) {
                    mayorIDVertice = vert;
                }

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

                    double startLat = graph.getInfoVertex(Integer.parseInt(partes[0])).getLatitud();
                    double startLong = graph.getInfoVertex(Integer.parseInt(partes[0])).getLongitud();

                    double endLat = graph.getInfoVertex(Integer.parseInt(partes[i])).getLatitud();
                    double endLong = graph.getInfoVertex(Integer.parseInt(partes[i])).getLongitud();

                    double costo = Haversine.distance(startLat, startLong, endLat, endLong);

                    graph.addEdge(Integer.parseInt(partes[0]), Integer.parseInt(partes[i]), costo);
                    if (Integer.parseInt(partes[0]) > mayorIDArco.getInicio()) {
                        mayorIDArco = new EstacionArco(Integer.parseInt(partes[0]), Integer.parseInt(partes[i]), 0);
                    }
                }
                linea = lector.readLine();
            }
            reader.close();
            lector.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void cargarEstaciones() {
        JsonReader reader;
        mayorEstacion = new Estacion(0, "", "", "", "", 0, 0, 0, "");
        estaciones = new ResizingArrayBag<>();

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

                //va sacando el mayor
                if (mayorEstacion.getOBJECTID() < OBJECTID) {
                    mayorEstacion = esta;
                }

                estaciones.add(esta);
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void cargarComparendos() {
        //cambiar esto para cambiar de tamanio de archivos.
        archivoActualComparendo = PEQUENIO;
        comparendos = new ResizingArrayBag<>();
        mayorComparendo = new Comparendo(0, "", "", "", "", "", "", "", "", 0, 0, "");
        try {
            FileInputStream inputStream;
            inputStream = new FileInputStream(archivoActualComparendo);
            InputStreamReader inputStreamreader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamreader);

            Json cargar = new Gson().fromJson(bufferedReader, Json.class);

            for (int i = 0; i < cargar.features.length; i++) {
                Comparendo comp = new Comparendo(cargar.features[i].properties.OBJECTID, cargar.features[i].properties.FECHA_HORA,
                        cargar.features[i].properties.MEDIO_DETECCION, cargar.features[i].properties.CLASE_VEHICULO,
                        cargar.features[i].properties.TIPO_SERVICIO, cargar.features[i].properties.INFRACCION,
                        cargar.features[i].properties.DES_INFRACCION, cargar.features[i].properties.LOCALIDAD,
                        cargar.features[i].properties.MUNICIPIO, cargar.features[i].geometry.coordinates[0],
                        cargar.features[i].geometry.coordinates[1], "OBJECTID");

                comparendos.add(comp);
                //va sacando el mayor OBJECTID
                if (mayorComparendo.OBJECTID < comp.OBJECTID) {
                    mayorComparendo = comp;
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }




    public int req1ParteInicial(double latitud, double longitud) {

        Iterator alrededor = graph.adj(0).iterator();
        EstacionVertice actual = (EstacionVertice) graph.getInfoVertex((Integer) alrededor.next());

        double distancia = Haversine.distance(actual.getLatitud(), actual.getLongitud(), latitud, longitud);
        double diferencia = 1000000000;
        int idMasCercana = -1;

        while (alrededor.hasNext()) {
            actual = (EstacionVertice) graph.getInfoVertex((Integer) alrededor.next());
            double distanciaActual = Haversine.distance(actual.getLatitud(), actual.getLongitud(), latitud, longitud);
            if (distancia - distanciaActual < diferencia) {
                diferencia = distancia - distanciaActual;
                idMasCercana = actual.getId();
            }
            if (graph.adj(actual.getId()).iterator().hasNext()) {
                idMasCercana = req1ParteInicial(latitud, longitud);
            }
        }
        return idMasCercana;
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

    //clases del Json para cargar los comparendos

    private static class Json {
        String type;
        Features[] features;
    }

    private static class Features {
        String type;
        Properties properties;
        Geometry geometry;
    }

    private static class Geometry {
        String type;
        double[] coordinates;
    }

    private static class Properties {
        int OBJECTID;
        String FECHA_HORA;
        String MEDIO_DETECCION;
        String CLASE_VEHICULO;
        String TIPO_SERVICIO;
        String INFRACCION;
        String DES_INFRACCION;
        String LOCALIDAD;
        String MUNICIPIO;
    }


}