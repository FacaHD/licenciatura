package com.Fabrica.GestRobots;

import com.Fabrica.GestArmazem.business.Palete;

import java.util.Collection;
import java.util.Map;

public interface IGestRobots {

    void entregaRobotaux(Robot robot);
    void adicionaRobot(Robot robot);
    boolean existeRobot(String num);
    boolean putPalete(String r, Palete p);


    Robot getRobot(String num);
    Collection<Robot> getRobots();
    void clearRobots();

    Robot escolheRobotStringOcupado();
    Map<String,Robot> getMapRobots();

    void setOcupado(String robot);

}

