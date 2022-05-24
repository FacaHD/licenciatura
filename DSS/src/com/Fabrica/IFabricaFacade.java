package com.Fabrica;

import com.Fabrica.GestArmazem.business.Gestor;
import com.Fabrica.GestArmazem.business.Localizacao;
import com.Fabrica.GestArmazem.business.Palete;
import com.Fabrica.GestArmazem.business.Prateleira;
import com.Fabrica.GestRequisicoes.Requisicao;
import com.Fabrica.GestRobots.Robot;
import com.Fabrica.exception.WWrongCredentialsException;

import java.util.Collection;
import java.util.List;

public interface IFabricaFacade {

    void adicionaRequisicao(Requisicao Req);
    boolean existeRequisicao(String tid);
    Requisicao getRequisicao(String num);
    Collection<Requisicao> getRequisicoes();
    void clearRequisicoes();

    void adicionaGestor(Gestor gestor);
    boolean existeGestor(String tid);
    Gestor getGestor(String num);
    Collection<Gestor> getGestores();
    void clearGestores();

    void entregaRobot(Robot robot);
    void adicionaRobot(Robot robot);
    Robot getRobot(String num);
    Collection<Robot> getRobots();
    boolean existeRobot(String num);
    String escolheRobot();
    void clearRobots();

    Collection<Prateleira> getPrateleira();
    void adicionaPrateleira(Prateleira p);
    boolean existePrateleira(String tid);
    Prateleira getPrateleira(String num);
    void clearPrateleiras();

    void removePalete (Palete p);
    void adicionaPaleteNaPrateleira(String s,String p);

    Palete getPalete(String pal);
    boolean existePalete(String num);
    Collection<Palete> getPaletes();
    boolean putPalete (Palete p ,String r);
    void clearPaletes();

    void adicionaPalete(Palete palete);
    boolean iniciarSessao(String cod, String password) throws WWrongCredentialsException;

    void adicionaLista(Localizacao t);
    boolean existeNaLocalizacao(String cod);
    Collection<Localizacao> getLista();
    void clearListagem();

    String escolhePrateleira();
    List<Palete> paletesPorRobot(String robot);
    void terminarSessao(String cod);
}
