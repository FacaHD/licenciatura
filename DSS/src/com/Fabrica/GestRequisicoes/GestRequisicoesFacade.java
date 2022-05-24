package com.Fabrica.GestRequisicoes;


import com.Fabrica.data.RequisicaoDAO;

import java.util.*;

public class GestRequisicoesFacade implements IGestRequisicoes{

    private Map<String, Requisicao> requisicoes;

    public GestRequisicoesFacade() {
        this.requisicoes = RequisicaoDAO.getInstance();
    }

    public Collection<Requisicao> getRequisicoes() {
        return new ArrayList<>(this.requisicoes.values());
    }

    public void adicionaRequisicao(Requisicao requisicao) {
        requisicoes.put(requisicao.getCodReq(),requisicao);
    }

    public boolean existeRequisicao(String tid) {
        return this.requisicoes.containsKey(tid);
    }

    public Requisicao getRequisicao(String num) {
        return this.requisicoes.get(num);
    }

    public void clearRequisicoes(){
        requisicoes.clear();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GestRequisicoesFacade that = (GestRequisicoesFacade) o;
        return Objects.equals(requisicoes, that.requisicoes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requisicoes);
    }

    @Override
    public String toString() {
        return "GestRequisicoesFacade{" +
                "requisicoes=" + requisicoes +
                '}';
    }

    
}
