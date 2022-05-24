package com.Fabrica.ui;

import com.Fabrica.GestArmazem.business.Palete;
import com.Fabrica.GestRobots.Robot;
import com.Fabrica.IFabricaFacade;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class UIRobots {

    public void listarRobots(IFabricaFacade model, Scanner scin) {
        try {
            System.out.println(model.getRobots().toString());
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    public void consultarRobots(IFabricaFacade model, Scanner scin) {
        try {

            System.out.print("              Robot a consultar: ");
            String num = scin.nextLine();
            if (model.existeRobot(num)) {
                System.out.println(model.getRobot(num).toString());
            } else {
                System.out.println("              Este robot nao existe!");
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    Comparator<Palete> comparatorOrdemNaturalP = Comparator.comparing(Palete::getCodPal);
    public void updateRobots (IFabricaFacade model,Scanner scin, String cod) {
        try {
            //Listar prateleiras disponiveis para esse robot
            List<Palete> l =model.paletesPorRobot(cod);
            l.sort(comparatorOrdemNaturalP);
  /*          paginas pe=new paginas();
            for (Palete j: l) {
                String s = j.getCodPal();
                pe.inserePaginas(s);
            }*/
            //pe.menuPaginaLeitura(pe);
          //  System.out.print("              Palete a transportar : ");
       //     String paleteTrans = scin.nextLine();
            if(l.size()==0) {
                System.out.println("\n              Robot não tem Paletes disponiveis.");
                return;
            }
            String paleteTrans = l.get(0).getCodPal();
            Palete p = model.getPalete(paleteTrans);

            if(!l.contains(p)) {
                System.out.println("\n              Palete '" + paleteTrans +"' não disponivel para o Robot '" + cod +"'");
                return;
            }

            boolean result = model.putPalete(p,cod);

            if(result) {
                model.removePalete(p);
                System.out.println("\n              Palete '"+paleteTrans+"' a ser transportada");
            }
            else System.out.println("\n              Não é possivel transportar a Palete: Robot '" + cod + "' completo");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void adicionarRobots(IFabricaFacade model, Scanner scin) {
        try {
            String cod = "R100";
            while (model.existeRobot(cod)){
                cod = "R" + Integer.toString(Integer.parseInt(cod.substring(1)) + 1);
            }
            System.out.println("Número do novo robot: " + cod);
            if (!model.existeRobot(cod)) {
                boolean ocupado=false;
                Robot r=new Robot(cod, ocupado,"");
                model.adicionaRobot(r);
                System.out.println("Robot adicionado");
            } else { System.out.println("Robot ja existente"); }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void clearRobots(IFabricaFacade model){
        try{
            model.clearRobots();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
