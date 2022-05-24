package com.Fabrica.GestRobots;

import com.Fabrica.GestArmazem.business.Palete;
import com.Fabrica.data.RobotDAO;

import java.util.*;

public class GestRobotsFacade implements IGestRobots {

    private Map<String,Robot> robots;

    public GestRobotsFacade() {
        this.robots = RobotDAO.getInstance();
    }

    public Collection<Robot> getRobots() {
        return new ArrayList<>(this.robots.values());
    }

    public void entregaRobotaux(Robot robot){
        this.robots.replace(robot.getCodRobot(),robot);
    }

    public boolean putPalete (String r, Palete p) {
        Robot t = robots.get(r);
        boolean result=false;
        if(!t.isOcupado()) {
            t.setPalete(p.getCodPal());
            t.setIdPrateleira(p.getIdPrateleira());
            t.setOcupado(!t.isOcupado());
            this.robots.put(t.getCodRobot(), t);
            result=true;
        }
        return result;
    }

    public void adicionaRobot(Robot robot) {
        robots.put(robot.getCodRobot(),robot);
    }

    public boolean existeRobot(String tid) {
        return robots.containsKey(tid);
    }

    public Robot getRobot(String num) {
        return this.robots.get(num);
    }

    public Robot escolheRobotStringOcupado(){ return robots.values().stream().findFirst().orElse(null);}

    public Map<String,Robot> getMapRobots(){
        Map<String,Robot> aux= new HashMap<>();
        for(Robot r :robots.values()){
            aux.put(r.getCodRobot(),r);
        }
        return aux;
    }

    public void setOcupado(String robot){
        Robot r=robots.get(robot);
        r.setOcupado(true);
    }

    public void clearRobots(){
        robots.clear();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GestRobotsFacade that = (GestRobotsFacade) o;
        return Objects.equals(robots, that.robots);
    }

    @Override
    public int hashCode() {
        return Objects.hash(robots);
    }

    @Override
    public String toString() {
        return "GestRobotsFacade{" +
                "robots=" + robots +
                '}';
    }



}
