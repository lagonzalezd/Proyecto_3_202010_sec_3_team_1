package model.data_structures;


public class Estacion  {


    public int OBJECTID;
    public int PLAN;
    public int CODENT;
    public int COD_PROY;
    public int ANIO_GEO;
    public String FECHA_INI;
    public String FECHA_FIN;
    public String DESCRIPCION;
    public String EST_PROY;
    public String INTERV_ESP;
    public String DIR_SITIO;
    public String COD_SITIO;
    public double latitud;
    public double longitud;
    public String SERVICIO;
    public int TELEFONO;
    public String CELEC;
    public String CONTACTO;
    public String NOMBRE;
    public String IDENTIFICACION;
    public int FECHA_C;


    public Estacion(int OBJECTID, String FECHA_INI, String FECHA_FIN, String DESCRIPCION, String DIR_SITIO, double latitud, double longitud, int TELEFONO, String CELEC){

        this.OBJECTID = OBJECTID;
        this.FECHA_INI = FECHA_INI;
        this.FECHA_FIN = FECHA_FIN;
        this.DESCRIPCION = DESCRIPCION;
        this.DIR_SITIO = DIR_SITIO;
        this.latitud = latitud;
        this.longitud = longitud;
        this.TELEFONO = TELEFONO;
        this.CELEC = CELEC;

    }

    public int getOBJECTID() {
        return OBJECTID;
    }

    public int getPLAN() {
        return PLAN;
    }

    public int getCODENT() {
        return CODENT;
    }

    public int getCOD_PROY() {
        return COD_PROY;
    }

    public int getANIO_GEO() {
        return ANIO_GEO;
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

    public String getEST_PROY() {
        return EST_PROY;
    }

    public String getINTERV_ESP() {
        return INTERV_ESP;
    }

    public String getDIR_SITIO() {
        return DIR_SITIO;
    }

    public String getCOD_SITIO() {
        return COD_SITIO;
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


    public int getTELEFONO() {
        return TELEFONO;
    }

    public String getCELEC() {
        return CELEC;
    }

    public String getCONTACTO() {
        return CONTACTO;
    }


    public String getNOMBRE() {
        return NOMBRE;
    }

    public String getIDENTIFICACION() {
        return IDENTIFICACION;
    }

    public int getFECHA_C() {
        return FECHA_C;
    }
}