package model.logic;

import java.io.*;
import java.util.Iterator;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import controller.Controller;
import edu.princeton.cs.algs4.ResizingArrayBag;
import model.data_structures.*;
import view.View;

public class Modelo {

    private GrafoNoDirigido<Integer, Vertice> graph;

    private ResizingArrayBag<Estacion> estaciones;
    private Estacion mayorEstacion;

    private Queue<Comparendo> comparendos;
    private Comparendo mayorComparendo;

    private Vertice mayorIDVertice;
    private Arco mayorIDArco;


    private Controller controller;

    public final static String ESTACIONES = "./data/estacionpolicia.geojson";
    private static final String COMPARENDOS = "./data/Comparendos50000.geojson";
    public final static String VERTICES = "./data/bogota_vertices.txt";
    public final static String ARCOS = "./data/bogota_arcos.txt";
    public final static String GRAFO = "./data/grafo.json";

    private Vertice vert;
    private Arco arc;

    private static View view;

    public Modelo() {
        view = new View();
    }

    public void cargarComparendos() {

        comparendos = new Queue<>();
        mayorComparendo = new Comparendo(0, "", "", "", "", "", "", "", "", 0, 0, "");
        try {
            FileInputStream inputStream;
            inputStream = new FileInputStream(COMPARENDOS);
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

                comparendos.enqueue(comp);
                //va sacando el mayor OBJECTID
                if (mayorComparendo.OBJECTID < comp.OBJECTID) {
                    mayorComparendo = comp;
                }

            }
            view.printMessage("Total comparendos: " + comparendos.size());
            view.printMessage("Comparendo con mayor ID: " + mayorComparendo.toString() + "\n");


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void cargarEstaciones() {
        JsonReader reader;
        mayorEstacion = new Estacion(0, "", "", "", "", 0, 0, "", "", 0, "", "");
        estaciones = new ResizingArrayBag<>();

        try {
            reader = new JsonReader(new FileReader(ESTACIONES));
            JsonElement elem = JsonParser.parseReader(reader);
            JsonArray e2 = elem.getAsJsonObject().get("features").getAsJsonArray();


            for (JsonElement e : e2) {


                int OBJECTID = e.getAsJsonObject().get("properties").getAsJsonObject().get("OBJECTID").getAsInt();
                String FECHAINI = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOFECHA_INI").getAsString();
                String FECHAFIN = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOFECHA_FIN").getAsString();
                String DESCRIPCION = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPODESCRIP").getAsString();
                String DIR_SITIO = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPODIR_SITIO").getAsString();
                double latitud = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOLATITUD").getAsDouble();
                double longitud = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOLONGITU").getAsDouble();
                String SERVICIO = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOSERVICIO").getAsString();
                String HORARIO = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOHORARIO").getAsString();
                int TELEFONO = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOTELEFON").getAsInt();
                String IULOCAL = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOIULOCAL").getAsString();
                String CELEC = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOCELECTR").getAsString();

                Estacion esta = new Estacion(OBJECTID, FECHAINI, FECHAFIN, DESCRIPCION, DIR_SITIO, latitud, longitud, SERVICIO, HORARIO, TELEFONO, IULOCAL, CELEC);

                //va sacando el mayor
                if (mayorEstacion.getOBJECTID() < OBJECTID) {
                    mayorEstacion = esta;
                }

                estaciones.add(esta);
            }
            view.printMessage("Total estaciones: " + estaciones.size());
            view.printMessage("Estación con mayor ID: " + mayorEstacion.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cargarVertices() {

        try {
            mayorIDVertice = new Vertice(0, 0, 0);

            graph = new GrafoNoDirigido<>(228046);

            FileReader reader = new FileReader(VERTICES);
            BufferedReader lector = new BufferedReader(reader);
            String linea = lector.readLine();
            while (linea != null) {
                String[] partes = linea.split(",");
                int id = Integer.parseInt(partes[0]);
                double longitud = Double.parseDouble(partes[1]);
                double latitud = Double.parseDouble(partes[2]);
                vert = new Vertice(id, longitud, latitud);
                graph.addVertex(id, vert);
                if (mayorIDVertice.getId() < id) {
                    mayorIDVertice = vert;
                }
                linea = lector.readLine();
            }

            lector.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void cargarGrafo() {

        cargarVertices();

        try {
            JsonReader reader = new JsonReader(new FileReader(GRAFO));
            JsonElement elem = JsonParser.parseReader(reader);
            JsonObject e1 = elem.getAsJsonObject().get("grafo").getAsJsonObject();
            int vertices = e1.get("V").getAsInt();
            int arcos = e1.get("E").getAsInt();

            view.printMessage("Total vertices:" + vertices);
            view.printMessage("Vertice de mayor " + mayorIDVertice);
            view.printMessage("Total arcos:" + arcos);


        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            mayorIDArco = new Arco(0, 0, 0);
            FileReader reader = new FileReader(ARCOS);
            BufferedReader lector = new BufferedReader(reader);

            String linea = lector.readLine();
            linea = lector.readLine();
            linea = lector.readLine();

            while (linea != null) {
                String[] partes = linea.split(" ");
                for (int i = 1; i < partes.length; i++) {

                    Vertice desde = graph.getInfoVertex(Integer.parseInt(partes[0]));
                    Vertice hasta = graph.getInfoVertex(Integer.parseInt(partes[i]));
                    double costo = Haversine.distance(desde.getLatitud(), desde.getLongitud(), hasta.getLatitud(), hasta.getLongitud());

                    graph.addEdge(Integer.parseInt(partes[0]), Integer.parseInt(partes[i]), costo);

                    if (mayorIDArco.getCosto() < costo) {
                        mayorIDArco = new Arco(desde.getId(), hasta.getId(), costo);
                    }

                }
                linea = lector.readLine();
            }
            view.printMessage("Arco de mayor costo: " + mayorIDArco+"\n");
            reader.close();
            lector.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public int req1ParteInicial(double latitud, double longitud) {

        Iterator alrededor = graph.adj(0).iterator();
        Vertice actual = (Vertice) graph.getInfoVertex((Integer) alrededor.next());

        double distancia = Haversine.distance(actual.getLatitud(), actual.getLongitud(), latitud, longitud);
        double diferencia = 1000000000;
        int idMasCercana = -1;

        while (alrededor.hasNext()) {
            actual = (Vertice) graph.getInfoVertex((Integer) alrededor.next());
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

    public int req1(double latitud, double longitud)
    {
        Iterator alrededor = graph.adj(0).iterator();



    }


    public void readJson() {
        Gson gson = new Gson();
        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader(GRAFO));
            graph = gson.fromJson(reader, Vertice.class);
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