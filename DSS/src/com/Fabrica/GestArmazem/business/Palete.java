package com.Fabrica.GestArmazem.business;

import java.util.Objects;

public class Palete {

    private String codPal;
    private String idRobot;
    private String idPrateleira;

    public Palete(String codPal) {

        this.codPal=codPal;

    }

    public Palete(String codPal,String idRobot,String idPrateleira) {
        this.codPal = codPal;

        this.idRobot=idRobot;
        this.idPrateleira=idPrateleira;
    }

    public String getCodPal() {
        return codPal;
    }

    public void setCodPal(String codPal) {
        this.codPal = codPal;
    }


    public String getIdRobot() {
        return idRobot;
    }

    public void setIdRobot(String idRobot) {
        this.idRobot = idRobot;
    }

    public String getIdPrateleira() {
        return idPrateleira;
    }

    public void setIdPrateleira(String idPrateleira) {
        this.idPrateleira = idPrateleira;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Palete palete = (Palete) o;
        return Objects.equals(codPal, palete.codPal) &&
                Objects.equals(idRobot, palete.idRobot) &&
                Objects.equals(idPrateleira, palete.idPrateleira);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codPal, idRobot, idPrateleira);
    }

    @Override
    public String toString() {
        return "Palete{" +
                "codPal='" + codPal + '\'' +
                ", idRobot='" + idRobot + '\'' +
                ", idPrateleira='" + idPrateleira + '\'' +
                '}';
    }
}