package com.Fabrica.ui;

import com.Fabrica.GestArmazem.business.Gestor;
import com.Fabrica.GestArmazem.business.Localizacao;
import com.Fabrica.IFabricaFacade;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;


public class UIGestores {

    public void listarGestor(IFabricaFacade model, Scanner scin) {
        try {
            System.out.println(model.getGestores().toString());
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    Comparator<Localizacao> comparatorOrdemNaturalG = Comparator.comparing(Localizacao::getIdPalete);
    public void consultaLista(IFabricaFacade model) {
        try {
        List<Localizacao> l = new ArrayList<>(model.getLista());
        l.sort(comparatorOrdemNaturalG);
        paginas p=new paginas();
        for (Localizacao j: l) p.inserePaginas(j);
        p.menuPaginaLeitura(p);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }


    public void consultarGestor(IFabricaFacade model, Scanner scin) {
        try {
            System.out.print("Gestor a consultar: ");
            String num = scin.nextLine();
            if (model.existeGestor(num)) {
                System.out.println(model.getGestor(num).toString());
            } else {
                System.out.println("Este gestor nao existe!");
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    public void adicionarGestor(IFabricaFacade model, Scanner scin) {
        try {
            String cod = "G100";
            while (model.existeGestor(cod)){
                cod = "G" + Integer.toString(Integer.parseInt(cod.substring(1)) + 1);
            }
            System.out.println("Número do novo gestor: " + cod);
            //String cod = scin.nextLine();
            if (!model.existeGestor(cod)) {
                System.out.print("email: ");
                String mail = scin.nextLine();
                System.out.print("Palavra-Passe: ");
                String pass = scin.nextLine();
                model.adicionaGestor(new Gestor(cod, mail,pass,false));
                System.out.println("Gestor Adicionado");
            } else { System.out.println("Gestor ja existente"); }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void terminarSessao(IFabricaFacade model, String cod) {
        try{
            model.terminarSessao(cod);
            System.out.println("\n              A sua sessão foi terminada");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void clearGestores(IFabricaFacade model){
        try{
            model.clearGestores();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void clearListagem(IFabricaFacade model){
        try{
            model.clearListagem();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
