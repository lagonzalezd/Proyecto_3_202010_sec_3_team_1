package view;

import java.awt.BorderLayout;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;

import com.teamdev.jxmaps.swing.MapView;
import com.teamdev.jxmaps.*;
import model.data_structures.DynamicArray;

public class Mapa extends MapView {

    private static Map map;
    private CircleOptions circleOptions;
    private PolylineOptions lineOptions;


    public void generateSimplePath(LatLng[] path) {

        Polyline polyline = new Polyline((com.teamdev.jxmaps.Map) map);
        polyline.setPath(path);
        polyline.setOptions(lineOptions);

    }

    public void generateCircle(LatLng center) {
        Circle circle = new Circle((com.teamdev.jxmaps.Map) map);
        circle.setCenter(center);
        circle.setRadius(20);
        circle.setVisible(true);
        circle.setOptions(circleOptions);
    }

    public Mapa(DynamicArray<LatLng[]> edgeArray) {

        JFrame frame = new JFrame("My First Path");

        Hashtable<String, Integer> vertexMap = new Hashtable<>();

        setOnMapReadyHandler(new MapReadyHandler() {
            @Override
            public void onMapReady(MapStatus status) {
                // Check if the map is loaded correctly
                if (status == MapStatus.MAP_STATUS_OK) {
                    // Getting the associated map object
                    map = getMap();
                    MapOptions mapOptions = new MapOptions();
                    MapTypeControlOptions controlOptions = new MapTypeControlOptions();
                    controlOptions.setPosition(ControlPosition.BOTTOM_LEFT);
                    mapOptions.setMapTypeControlOptions(controlOptions);

                    map.setOptions(mapOptions);
                    map.setCenter(new LatLng(4.6328903475179715, -74.09488677978516));
                    map.setZoom(14);

                }
            }
        });

        try {
            for (int i = 0; i < 5; i++)
                TimeUnit.SECONDS.sleep(1);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        frame.add(this, BorderLayout.CENTER);
        frame.setSize(1500, 1000);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        circleOptions = new CircleOptions();
        circleOptions.setStrokeColor("#FF0000");
        circleOptions.setRadius(30);
        circleOptions.setFillColor("#FF0000");
        circleOptions.setFillOpacity(0.7);

        lineOptions = new PolylineOptions();
        lineOptions.setGeodesic(true);
        lineOptions.setStrokeColor("#FF0000");
        lineOptions.setStrokeOpacity(1);
        lineOptions.setStrokeWeight(2.0);

        for (int i = 0; i < edgeArray.size(); i++) {
            double lat1 = edgeArray.get(i)[0].getLat();
            double long1 = edgeArray.get(i)[1].getLng();

            double lat2 = edgeArray.get(i)[1].getLat();
            double long2 = edgeArray.get(i)[1].getLng();

            if (!vertexMap.containsKey(lat1 + "$" + long1)) {
                vertexMap.put(lat1 + "$" + long1, 0);
                generateCircle(edgeArray.get(i)[0]);
            }
            generateSimplePath(edgeArray.get(i));

            if (!vertexMap.containsKey(lat2 + "$" + long2)) {
                vertexMap.put(lat2 + "$" + long2, 1);
                generateCircle(edgeArray.get(i)[1]);
            }


        }
    }

}
