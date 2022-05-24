package com.Fabrica.GestRobots;

import java.util.Objects;

public class Robot {

    private String codRobot;
    private boolean ocupado;
    private String palete;
    private String idPrateleira;



    public Robot(String codRobot, boolean ocupado,String palete) {
        this.codRobot = codRobot;
        this.ocupado = ocupado;
        this.palete = palete;
    }
    public Robot(String codRobot, boolean ocupado,String palete,String idPrateleira) {
        this.codRobot = codRobot;
        this.ocupado = ocupado;
        this.palete=palete;
        this.idPrateleira=idPrateleira;
    }

    public String getIdPrateleira() {
        return idPrateleira;
    }

    public void setIdPrateleira(String idPrateleira) {
        this.idPrateleira = idPrateleira;
    }


    public String getCodRobot() {
        return codRobot;
    }

    public void setCodRobot(String codRobot) {
        this.codRobot = codRobot;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    public String getPalete() {
        return palete;
    }

    public void setPalete(String palete) {
        this.palete = palete;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Robot robot = (Robot) o;
        return ocupado == robot.ocupado &&
                Objects.equals(codRobot, robot.codRobot) &&
                Objects.equals(palete, robot.palete) &&
                Objects.equals(idPrateleira, robot.idPrateleira);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codRobot, ocupado, palete, idPrateleira);
    }

    @Override
    public String toString() {
        return "Robot{" +
                "codRobot='" + codRobot + '\'' +
                ", ocupado=" + ocupado +
                ", palete='" + palete + '\'' +
                '}';
    }


}


