package com.Fabrica.GestArmazem.business;

import java.util.LinkedList;

public class Vertice {
    
    String nome;
    Prateleira prat;
    private boolean visitado;
    LinkedList<Aresta> arestas;

    Vertice( String name, Prateleira prat) {
        
        this.nome = name;
        this.prat=prat;
        visitado = false;
        arestas = new LinkedList<>();
    }



    void unvisit() {
        visitado = false;
    }

    void visit(){
        visitado = true;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Prateleira getPrat() {
        return prat;
    }

    public void setPrat(Prateleira prat) {
        this.prat = prat;
    }

    public boolean isVisitado() {
        return visitado;
    }

    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }

    public LinkedList<Aresta> getArestas() {
        return arestas;
    }

    public void setArestas(LinkedList<Aresta> arestas) {
        this.arestas = arestas;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

  
}
