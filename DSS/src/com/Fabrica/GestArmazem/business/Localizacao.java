package com.Fabrica.GestArmazem.business;

import java.util.Objects;

public class Localizacao {
    private String idPalete;
    private String loca;

    public Localizacao(String idPalete, String loca) {
        this.idPalete = idPalete;
        this.loca = loca;
    }

    public String getIdPalete() {
        return idPalete;
    }

    public void setIdPalete(String idPalete) {
        this.idPalete = idPalete;
    }

    public String getLoca() {
        return loca;
    }

    public void setLoca(String loca) {
        this.loca = loca;
    }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Localizacao that = (Localizacao) o;
        return Objects.equals(idPalete, that.idPalete) &&
                Objects.equals(loca, that.loca);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPalete, loca);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("");
        sb.append(" Codigo da Palete: '").append(idPalete).append('\'');
        sb.append(" | Localização: ").append(loca);
        return sb.toString();
    }
}
