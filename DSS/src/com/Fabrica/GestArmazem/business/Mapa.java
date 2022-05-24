package com.Fabrica.GestArmazem.business;

import java.util.*;

public class Mapa {

    private Set<Vertice> vertices;

    Mapa() {
        vertices = new HashSet<>();
    }

    public Set<Vertice> getVertices() {
        return vertices;
    }

    public void setNodes(Set<Vertice> nodes) {
        this.vertices = nodes;
    }

    public void addNode(Vertice n) {
        vertices.addAll(Arrays.asList(n));
    }

    public void addAresta(Vertice source, Vertice destination, double weight) {
        vertices.add(source);
        vertices.add(destination);
        adicionaArestaAux(source, destination, weight);
        adicionaArestaAux(destination, source, weight);
    }

    private void adicionaArestaAux(Vertice a, Vertice b, double weight) {
        for (Aresta aresta : a.arestas) {
            if (aresta.fonte == a && aresta.destino == b) {
                aresta.custo = weight;
                return;
            }
        }
        a.arestas.add(new Aresta(a, b, weight));
    }


    public void resetNodesVisited() {
        for (Vertice v : vertices) v.unvisit();
    }

    public double caminhoMaisCurto(Vertice inicio, Vertice fim) {
        HashMap<Vertice, Vertice> changedAt = new HashMap<>();
        changedAt.put(inicio, null);

        HashMap<Vertice, Double> caminhoMaisCurto = new HashMap<>();

        for (Vertice v : vertices) {
            if (v == inicio)
                caminhoMaisCurto.put(inicio, 0.0);
            else caminhoMaisCurto.put(v, Double.POSITIVE_INFINITY);
        }

        for (Aresta a : inicio.arestas) {
            caminhoMaisCurto.put(a.destino, a.custo);
            changedAt.put(a.destino, inicio);
        }

        inicio.visit();

        while (true) {
            Vertice vAtual = verticeMaisProxUnvisitado(caminhoMaisCurto);
            if (vAtual == null) return 0;
            if (vAtual == fim) {

                Vertice child = fim;

                String path = fim.nome;
                while (true) {
                    Vertice parent = changedAt.get(child);
                    if (parent == null) break;
                    path = parent.nome + " " + path;
                    child = parent;
                }
                return caminhoMaisCurto.get(fim);
            }
            vAtual.visit();
            for (Aresta a : vAtual.arestas) {
                if (a.destino.isVisitado())
                    continue;

                if (caminhoMaisCurto.get(vAtual) + a .custo < caminhoMaisCurto.get(a.destino)) {
                    caminhoMaisCurto.put(a.destino, caminhoMaisCurto.get(vAtual) + a.custo);
                    changedAt.put(a.destino, vAtual);
                }
            }
        }
    }

    private Vertice verticeMaisProxUnvisitado(HashMap<Vertice, Double> caminhoMaisCurto) {

        double menorDist = Double.POSITIVE_INFINITY;
        Vertice vProx = null;
        for (Vertice v : vertices) {
            if (v.isVisitado())
                continue;

            double DistAtual = caminhoMaisCurto.get(v);
            if (DistAtual == Double.POSITIVE_INFINITY)
                continue;

            if (DistAtual < menorDist) {
                menorDist = DistAtual;
                vProx = v;
            }
        }
        return vProx;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mapa mapa = (Mapa) o;
        return Objects.equals(vertices, mapa.vertices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertices);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Mapa{");
        sb.append("nodes=").append(vertices);
        sb.append('}');
        return sb.toString();
    }
}
