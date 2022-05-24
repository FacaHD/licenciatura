package com.Fabrica.GestArmazem.business;

import java.util.Objects;

public class Aresta {

    Vertice fonte;
    Vertice destino;
    double custo;

    Aresta(Vertice s, Vertice d, double w) {
        fonte = s;
        destino = d;
        custo = w;
    }

    public Vertice getFonte() {
        return fonte;
    }

    public void setFonte(Vertice fonte) {
        this.fonte = fonte;
    }

    public Vertice getDestino() {
        return destino;
    }

    public void setDestino(Vertice destino) {
        this.destino = destino;
    }

    public double getCusto() {
        return custo;
    }

    public void setCusto(double custo) {
        this.custo = custo;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aresta aresta = (Aresta) o;
        return Double.compare(aresta.custo, custo) == 0 &&
                Objects.equals(fonte, aresta.fonte) &&
                Objects.equals(destino, aresta.destino);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fonte, destino, custo);
    }
}
