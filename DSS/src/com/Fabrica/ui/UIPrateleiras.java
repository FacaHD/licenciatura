package com.Fabrica.ui;

import com.Fabrica.GestArmazem.business.Prateleira;
import com.Fabrica.GestRobots.Robot;
import com.Fabrica.IFabricaFacade;

import java.util.Scanner;

public class UIPrateleiras {

    public void listarPrateleira(IFabricaFacade model, Scanner scin) {
        try {
            System.out.println(model.getPrateleira().toString());
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    public void consultarPrateleira(IFabricaFacade model, Scanner scin) {
        try {
            System.out.print("Prateleira a consultar: ");
            String num = scin.nextLine();
            if (model.existePrateleira(num)) {
                System.out.println(model.getPrateleira(num).toString());
            } else {
                System.out.println("Esta prateleira nao existe!");
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    public void adicionarPrateleira(IFabricaFacade model, Scanner scin) {
        try {
            String cod = "PR100";
            while (model.existePrateleira(cod)) {
                cod = "PR" + Integer.toString(Integer.parseInt(cod.substring(2)) + 1);
            }
            System.out.println("Número da nova prateleira: " + cod);
            //String cod = scin.nextLine();
            if (!model.existePrateleira(cod)) {
                System.out.print("Capacidade: ");
                int capacidade = scin.nextInt();
                scin.nextLine();
                model.adicionaPrateleira(new Prateleira(cod, capacidade));
                System.out.println("Prateleira Adicionada");
            } else { System.out.println("Prateleira ja existente"); }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void adicionaPalete(IFabricaFacade model,Scanner scin, String robot) {
        try {
        Robot rob = model.getRobot(robot);
        if(!rob.isOcupado()) {
            System.out.println("\n              Robot sem Palete");
            return;
        }
        String n=rob.getPalete();
        String s=rob.getIdPrateleira();
        /*
        String codRobot = null;
        for(Robot r : model.getRobots()){
            if(r.getPalete().compareTo(n) == 0) {
                codRobot = r.getCodRobot();
                break;
            }
        }*/
        model.adicionaPaleteNaPrateleira(n,s);
        model.entregaRobot(new Robot(robot,false,null));
        System.out.println("\n              Palete '" + n + "' transportada para a Prateleira '" + s+ "'");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void clearPrateleiras(IFabricaFacade model){
        try{
            model.clearPrateleiras();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
