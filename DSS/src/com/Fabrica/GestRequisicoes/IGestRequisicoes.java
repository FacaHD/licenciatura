package com.Fabrica.GestRequisicoes;

import java.util.Collection;

public interface IGestRequisicoes {
    public void adicionaRequisicao(Requisicao requisicao);
    boolean existeRequisicao(String tid);
    Requisicao getRequisicao(String num);
    Collection<Requisicao> getRequisicoes();

    void clearRequisicoes();
}
