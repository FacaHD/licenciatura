package com.Fabrica.GestArmazem.business;

import com.Fabrica.exception.WWrongCredentialsException;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IGestArmazem {

    boolean iniciarSessao(String cod, String password) throws WWrongCredentialsException;
    void terminarSessao(String cod);

    void adicionaGestor(Gestor requisicao);
    boolean existeGestor(String tid);
    Gestor getGestor(String num);
    Collection<Gestor> getGestores();
    void clearGestores();

    void adicionaPrateleira(Prateleira p);
    boolean existePrateleira(String tid);
    Prateleira getPrateleira(String num);
    Collection<Prateleira> getPrateleiras();

    void adicionaPaleteNaPrateleira(String s,String p);

    Palete getPalete(String num);
    boolean existePalete(String tid);
    Collection<Palete> getPaletes();
    void adicionaPalete(Palete p);
    void removePalete (Palete p);
    void clearPaletes();

    void adicionaLista (Localizacao t);
    boolean existeLista(String cod);
    Collection<Localizacao> getLocalizacao();
    void setLocalizacao(String codPal,String codRobot);
    void clearListagem();

    Prateleira escolhePrateleira();
    List<Palete> paletesPorRobot(String robot);
    void clearPrateleiras();

    Map<String,Palete> filtragemOcupado ();
    String escolheRobot (Map<String,Palete> arg, Set<String> robots);
}
