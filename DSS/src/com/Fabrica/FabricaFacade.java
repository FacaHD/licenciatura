package com.Fabrica;

import com.Fabrica.GestArmazem.business.*;
import com.Fabrica.GestRequisicoes.GestRequisicoesFacade;
import com.Fabrica.GestRequisicoes.IGestRequisicoes;
import com.Fabrica.GestRequisicoes.Requisicao;
import com.Fabrica.GestRobots.GestRobotsFacade;
import com.Fabrica.GestRobots.Robot;
import com.Fabrica.GestRobots.IGestRobots;
import com.Fabrica.exception.WWrongCredentialsException;

import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class FabricaFacade implements IFabricaFacade {

    private IGestRobots robots;
    private IGestRequisicoes requisicoes;
    private IGestArmazem armazem;

    public FabricaFacade() {
        this.robots = new GestRobotsFacade();
        this.requisicoes = new GestRequisicoesFacade();
        this.armazem = new ArmazemFacade();
    }

    public FabricaFacade(IGestRobots robots, IGestRequisicoes requisicoes, IGestArmazem armazem) {
        this.robots = robots;
        this.requisicoes = requisicoes;
        this.armazem = armazem;
    }

    // Robot
    public void adicionaRobot(Robot robot) {
        robots.adicionaRobot(robot);
    }
    public Robot getRobot(String num) {
        return robots.getRobot(num);
    }
    public void entregaRobot(Robot robot){ robots.entregaRobotaux(robot);}
    public Collection<Robot> getRobots() {
        return robots.getRobots();
    }
    public boolean existeRobot(String num) {
        return robots.existeRobot(num);
    }
    public void clearRobots(){
        robots.clearRobots();
    }

    // Requisicao
    public void adicionaRequisicao(Requisicao Req) {
        requisicoes.adicionaRequisicao(Req);
    }
    public boolean existeRequisicao(String tid) {
        return requisicoes.existeRequisicao(tid);
    }
    public Requisicao getRequisicao(String num) {
        return requisicoes.getRequisicao(num);
    }
    public Collection<Requisicao> getRequisicoes() {
        return requisicoes.getRequisicoes();
    }
    public void clearRequisicoes(){
        requisicoes.clearRequisicoes();
    }
    //Gestores
    public Collection<Gestor> getGestores() {
        return armazem.getGestores();
    }
    public void adicionaGestor(Gestor Gest) {
        armazem.adicionaGestor(Gest);
    }
    public boolean existeGestor(String tid) {
        return armazem.existeGestor(tid);
    }
    public Gestor getGestor(String num) {
        return armazem.getGestor(num);
    }
    public void terminarSessao(String cod) { armazem.terminarSessao(cod);}
    public boolean iniciarSessao(String cod, String password) throws WWrongCredentialsException {
        return armazem.iniciarSessao(cod, password);
    }

    public void adicionaPaleteNaPrateleira(String s,String p) {
        armazem.adicionaPaleteNaPrateleira(s,p);
    }
    public void clearGestores(){
        armazem.clearGestores();
    }

    //Prateleira
    public Collection<Prateleira> getPrateleira() {
        return armazem.getPrateleiras();
    }
    public void adicionaPrateleira(Prateleira p) {
        armazem.adicionaPrateleira(p);
    }
    public boolean existePrateleira(String tid) {
        return armazem.existePrateleira(tid);
    }
    public Prateleira getPrateleira(String num) {
        return armazem.getPrateleira(num);
    }
    public void clearPrateleiras(){
        armazem.clearPrateleiras();
    }

    //Listagem
    public void adicionaLista(Localizacao t) {
        armazem.adicionaLista(t);
    }
    public Collection<Localizacao> getLista(){
        return armazem.getLocalizacao();
    }
    public boolean existeNaLocalizacao(String cod) { return armazem.existeLista(cod); }
    public void clearListagem(){
        armazem.clearListagem();
    }

    //Palete
    public void adicionaPalete(Palete p) { armazem.adicionaPalete(p); }
    public Collection<Palete> getPaletes() {
        return armazem.getPaletes();
    }
    public boolean existePalete(String tid) {
        return armazem.existePalete(tid);
    }
    public Palete getPalete(String num) {
        return armazem.getPalete(num);
    }
    public void removePalete (Palete p) { armazem.removePalete(p); }


    public void clearPaletes(){
        armazem.clearPaletes();
    }

    public String escolheRobot() {
        String codRobot ;
        Robot robot;
        Map<String,Palete> aux2=armazem.filtragemOcupado();
        if(aux2.size()==0) {
            robot=robots.escolheRobotStringOcupado();
            if(robot!=null) codRobot=robot.getCodRobot();
            else codRobot=null;
        }
        else codRobot = armazem.escolheRobot(aux2,robots.getMapRobots().keySet());
        return codRobot;
    }

    public boolean putPalete (Palete p ,String codRobot){
        armazem.setLocalizacao(p.getCodPal(),codRobot);
        return robots.putPalete(codRobot,p);
    }

    public String escolhePrateleira() {
        return armazem.escolhePrateleira().getCodPrat();
    }
    public List<Palete> paletesPorRobot(String robot){
        return armazem.paletesPorRobot(robot);
    }


}
