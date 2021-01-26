package com.example.solicitudcomodines;

public class SOLICITUD {

    private String GESTOR;
    private String CLIENTE;
    private String COMODIN;
    private String FECHA;
    private String HORA_ENTRADA;
    private String HORA_SALIDA;
    private String OBSERVACION;
    private String ID_ANDROID;

    public String getCLIENTE() {
        return CLIENTE;
    }

    public String getCOMODIN() {
        return COMODIN;

    }

    public String getFECHA() {
        return FECHA;
    }

    public String getGESTOR() {
        return GESTOR;
    }

    public String getHORA_ENTRADA() {
        return HORA_ENTRADA;
    }

    public String getHORA_SALIDA() {
        return HORA_SALIDA;
    }

    public String getID_ANDROID() {
        return ID_ANDROID;
    }

    public String getOBSERVACION() {
        return OBSERVACION;
    }

    public void setCLIENTE(String CLIENTE) {
        this.CLIENTE = CLIENTE;
    }

    public void setCOMODIN(String COMODIN) {
        this.COMODIN = COMODIN;
    }

    public void setFECHA(String FECHA) {
        this.FECHA = FECHA;
    }

    public void setGESTOR(String GESTOR) {
        this.GESTOR = GESTOR;
    }

    public void setHORA_ENTRADA(String HORA_ENTRADA) {
        this.HORA_ENTRADA = HORA_ENTRADA;
    }

    public void setHORA_SALIDA(String HORA_SALIDA) {
        this.HORA_SALIDA = HORA_SALIDA;
    }

    public void setID_ANDROID(String ID_ANDROID) {
        this.ID_ANDROID = ID_ANDROID;
    }

    public void setOBSERVACION(String OBSERVACION) {
        this.OBSERVACION = OBSERVACION;
    }
}
