package model.data_structures;

import javafx.scene.control.Cell;

public class Estacion {


    public int OBJECTID;
    public String FECHA_INI;
    public String FECHA_FIN;
    public String DESCRIPCION;
    public String DIR_SITIO;
    public double latitud;
    public double longitud;
    public String SERVICIO;
    public String HORARIO;
    public int TELEFONO;
    public String IULOCAL;
    public String CELEC;



    public Estacion(int OBJECTID, String FECHA_INI, String FECHA_FIN, String DESCRIPCION, String DIR_SITIO, double latitud, double longitud, String SERVICIO, String HORARIO,
                    int TELEFONO, String IULOCAL, String CELEC) {

        this.OBJECTID = OBJECTID;
        this.FECHA_INI = FECHA_INI;
        this.FECHA_FIN = FECHA_FIN;
        this.DESCRIPCION = DESCRIPCION;
        this.DIR_SITIO = DIR_SITIO;
        this.latitud = latitud;
        this.longitud = longitud;
        this.SERVICIO = SERVICIO;
        this.HORARIO = HORARIO;
        this.TELEFONO = TELEFONO;
        this.IULOCAL = IULOCAL;
        this.CELEC = CELEC;

    }

    public int getOBJECTID() {
        return OBJECTID;
    }

    public String getFECHA_INI() {
        return FECHA_INI;
    }

    public String getFECHA_FIN() {
        return FECHA_FIN;
    }

    public String getDESCRIPCION() {
        return DESCRIPCION;
    }

    public String getDIR_SITIO() {
        return DIR_SITIO;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public String getSERVICIO() {
        return SERVICIO;
    }

    public String getHORARIO() {
        return HORARIO;
    }

    public int getTELEFONO() {
        return TELEFONO;
    }

    public String getIULOCAL() {
        return IULOCAL;
    }

    public String getCELEC() {
        return CELEC;
    }



    public String toString() {
        return "Object ID: " + getOBJECTID()+", DESCRIPCION: "+getDESCRIPCION()+", Dirección: "+getDIR_SITIO()+", Latitud: "
                +getLatitud()+", Longitud: "+getLongitud()+",\nservicio: "
                +getSERVICIO()+",\nHorario: "+getHORARIO()+", Teléfono: "+getTELEFONO()+", IULocal: "+getIULOCAL()+"\n";
    }
}