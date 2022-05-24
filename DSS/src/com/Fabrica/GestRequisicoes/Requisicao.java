package com.Fabrica.GestRequisicoes;

import com.Fabrica.GestArmazem.business.Palete;

import java.util.Collection;
import java.util.Objects;

public class Requisicao {

    private String codReq;
    private Collection<Palete> lstpaletes;

    public Requisicao(String codReq) {
        this.codReq = codReq;
    }

    public Requisicao(String codReq, Collection<Palete> lstpaletes) {
        this.codReq = codReq;
        this.lstpaletes = lstpaletes;
    }

    public String getCodReq() {
        return codReq;
    }

    public void setCodReq(String codReq) {
        this.codReq = codReq;
    }

    public Collection<Palete> getLstpaletes() {
        return lstpaletes;
    }

    public void setLstpaletes(Collection<Palete> lstpaletes) {
        this.lstpaletes = lstpaletes;
    }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Requisicao that = (Requisicao) o;
        return Objects.equals(codReq, that.codReq) &&
                Objects.equals(lstpaletes, that.lstpaletes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codReq, lstpaletes);
    }

    @Override
    public String toString() {
        return "Requisicao{" +
                "codReq='" + codReq + '\'' +
                ", lstpaletes=" + lstpaletes +
                '}';
    }
}
