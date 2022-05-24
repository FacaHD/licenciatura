package com.Fabrica.GestArmazem.business;

import java.util.Collection;
import java.util.Objects;
import java.util.TreeSet;

public class Prateleira {

    private String codPrat;
    private int espacosDisp;
    private Collection<String> prateleirasManhosas;

    public Prateleira(String codPrat, int espacosDisp) {
        this.codPrat = codPrat;
        this.espacosDisp = espacosDisp;
    }



    public void adicionaPalete(String  p){
        this.prateleirasManhosas.add(p);
    }
    public Prateleira(String codPrat, int espacosDisp,Collection<String> prateleirasManhosas) {
        this.codPrat = codPrat;
        this.espacosDisp = espacosDisp;
        if(prateleirasManhosas!=null) {
            this.prateleirasManhosas=prateleirasManhosas;}
    }

    public Collection<String> getPrateleirasManhosas() {
        if(prateleirasManhosas==null) return null;
        return new TreeSet<>(prateleirasManhosas);
    }

    public void setPrateleirasManhosas(Collection<String> prateleirasManhosas) {
        this.prateleirasManhosas = prateleirasManhosas;
    }

    public String getCodPrat() {
        return codPrat;
    }

    public void setCodPrat(String codPrat) {
        this.codPrat = codPrat;
    }

    public int getEspacosDisp() {
        return espacosDisp;
    }

    public void setEspacosDisp(int espacosDisp) {
        this.espacosDisp = espacosDisp;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prateleira that = (Prateleira) o;
        return espacosDisp == that.espacosDisp &&
                Objects.equals(codPrat, that.codPrat) &&
                Objects.equals(prateleirasManhosas, that.prateleirasManhosas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codPrat, espacosDisp, prateleirasManhosas);
    }

    @Override
    public String toString() {
        return "Prateleira{" +
                "codPrat='" + codPrat + '\'' +
                ", espacosDisp=" + espacosDisp +
                ", prateleirasManhosas=" + prateleirasManhosas +
                '}';
    }
}
