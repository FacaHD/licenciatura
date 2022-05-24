package com.Fabrica.ui;

import com.Fabrica.GestRequisicoes.Requisicao;
import com.Fabrica.IFabricaFacade;

import java.util.Scanner;

public class UIRequisicao {


    public void adicionarRequisicao(IFabricaFacade model, Scanner scin) {
        try {
            String cod = "R100";
            while (model.existeRequisicao(cod)) {
                cod = "R" + Integer.toString(Integer.parseInt(cod.substring(1)) + 1);
            }
            System.out.println("Número da nova requisicao: " + cod);
            if (!model.existeRequisicao(cod)) {
                model.adicionaRequisicao(new Requisicao(cod));
                System.out.println("Requisicao adicionada");
            } else {
                System.out.println("Requisicao ja existente");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void trataRemoverRequisicao() {

    }

    public void listarRequisicao(IFabricaFacade model, Scanner scin) {
        try {
            System.out.println(model.getRequisicoes().toString());
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }


    public void consultarRequisicao(IFabricaFacade model, Scanner scin) {
        try {
            System.out.print("Requisição a consultar: ");
            String num = scin.nextLine();
            if (model.existeRequisicao(num)) {
                System.out.println(model.getRequisicao(num).toString());
            } else {
                System.out.println("Essa requisicão nao existe!");
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    public void clearRequisicoes(IFabricaFacade model){
        try{
            model.clearRequisicoes();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
