package com.Fabrica.GestArmazem.business;


import com.Fabrica.data.GestorDAO;
import com.Fabrica.data.ListagemDAO;
import com.Fabrica.data.PaleteDAO;
import com.Fabrica.data.PrateleiraDAO;
import com.Fabrica.exception.WWrongCredentialsException;


import java.util.*;
import java.util.stream.Collectors;

public class ArmazemFacade implements IGestArmazem {

    private Map<String,Gestor> gestores;
    private Map<String,Prateleira> prateleiras;
    private Map<String,Palete> paletesEmEspera;
    private Map<String,Localizacao> listagem;

    private Mapa mapa;

    public ArmazemFacade() {
        this.gestores= GestorDAO.getInstance();
        this.prateleiras= PrateleiraDAO.getInstance();
        this.paletesEmEspera= PaleteDAO.getInstance();
        this.listagem= ListagemDAO.getInstance();
        this.mapa=criarMapa();

    }

    public Mapa getMapa() { return mapa; }
    public void setMapa(Mapa mapa) { this.mapa = mapa; }
    public Mapa criarMapa(){
        this.mapa = new Mapa();
        if(prateleiras.size()<=0)
            geraPrateleiras();
        Vertice Rececao = new Vertice("Rececao", null);
        Vertice T1 = new Vertice("T01", null);
        Vertice T2 = new Vertice("T02", null);
        Vertice PR100 = new Vertice("PR100", prateleiras.get("PR100"));
        Vertice PR101 = new Vertice("PR101", prateleiras.get("PR101"));
        Vertice PR102 = new Vertice("PR102", prateleiras.get("PR102"));
        Vertice PR103 = new Vertice("PR103", prateleiras.get("PR103"));
        Vertice PR104 = new Vertice("PR104", prateleiras.get("PR104"));
        Vertice PR105 = new Vertice("PR105", prateleiras.get("PR105"));
        Vertice PR106 = new Vertice("PR106", prateleiras.get("PR106"));
        Vertice PR107 = new Vertice("PR107", prateleiras.get("PR107"));
        Vertice PR108 = new Vertice("PR108", prateleiras.get("PR108"));
        Vertice PR109 = new Vertice("PR109", prateleiras.get("PR109"));

        mapa.addAresta(Rececao, T1, 2);
        mapa.addAresta(T1, T2, 5);
        mapa.addAresta(T1, PR100, 2);
        mapa.addAresta(PR100, PR101, 4);
        mapa.addAresta(PR101, PR102, 4);
        mapa.addAresta(PR102, PR103, 4);
        mapa.addAresta(PR103, PR104, 4);
        mapa.addAresta(T2, PR105, 2);
        mapa.addAresta(PR105, PR106, 4);
        mapa.addAresta(PR106, PR107, 4);
        mapa.addAresta(PR107, PR108, 4);
        mapa.addAresta(PR108, PR109, 4);
        return mapa;
    }

    private void geraPrateleiras(){
        Prateleira PR100 = new Prateleira("PR100",5);
        Prateleira PR101 = new Prateleira("PR101",5);
        Prateleira PR102 = new Prateleira("PR102",5);
        Prateleira PR103 = new Prateleira("PR103",5);
        Prateleira PR104 = new Prateleira("PR104",5);
        Prateleira PR105 = new Prateleira("PR105",5);
        Prateleira PR106 = new Prateleira("PR106",5);
        Prateleira PR107 = new Prateleira("PR107",5);
        Prateleira PR108 = new Prateleira("PR108",5);
        Prateleira PR109 = new Prateleira("PR109",5);

        prateleiras.put(PR100.getCodPrat(),PR100);
        prateleiras.put(PR101.getCodPrat(),PR101);
        prateleiras.put(PR102.getCodPrat(),PR102);
        prateleiras.put(PR103.getCodPrat(),PR103);
        prateleiras.put(PR104.getCodPrat(),PR104);
        prateleiras.put(PR105.getCodPrat(),PR105);
        prateleiras.put(PR106.getCodPrat(),PR106);
        prateleiras.put(PR107.getCodPrat(),PR107);
        prateleiras.put(PR108.getCodPrat(),PR108);
        prateleiras.put(PR109.getCodPrat(),PR109);
    }

    public boolean iniciarSessao(String cod, String password) throws WWrongCredentialsException {
        Gestor gestor = gestores.get(cod);
        boolean loggedIn;
        if (gestor == null)
            throw new WWrongCredentialsException();
        if(loggedIn = gestor.validaCredenciais(cod, password)){
            gestor.setLogado(true);
            gestores.put(gestor.getCodGestor(), gestor);
        }
        if (!loggedIn)
            throw new WWrongCredentialsException();
        return loggedIn;
    }

    public void terminarSessao(String cod) {
        Gestor gestor=gestores.get(cod);
        gestor.setLogado(false);
        gestores.put(gestor.getCodGestor(),gestor);
    }

    //Gestores
    public Collection<Gestor> getGestores() { return new ArrayList<>(this.gestores.values()); }
    public void adicionaGestor(Gestor gestor) { gestores.put(gestor.getCodGestor(),gestor); }
    public boolean existeGestor(String tid) { return gestores.containsKey(tid); }
    public Gestor getGestor(String num) { return this.gestores.get(num); }
    public void setGestores(Map<String, Gestor> gestores) { this.gestores = gestores; }


    public Map<String, Palete> getPaletesEmEspera() { return paletesEmEspera; }
    public void setPaletesEmEspera(Map<String, Palete> paletesEmEspera) { this.paletesEmEspera = paletesEmEspera; }


    //listagem
    public Collection<Localizacao> getLocalizacao() { return new ArrayList<>(this.listagem.values()); }
    public void adicionaLista (Localizacao t) { listagem.put(t.getIdPalete(),t); }
    public boolean existeLista(String cod) { return listagem.containsKey(cod); }
    public Localizacao getLocalizacao(String num) { return this.listagem.get(num); }
    public void setLocalizacao(String codPal,String codRobot){
        Localizacao l = listagem.get(codPal);
        l.setLoca(codRobot);
        listagem.put(l.getIdPalete(),l);
    }

    //Prateleiras
    public void setPrateleiras(Map<String, Prateleira> prateleiras) { this.prateleiras = prateleiras; }
    public void adicionaPrateleira(Prateleira p) { prateleiras.put(p.getCodPrat(),p); }
    public boolean existePrateleira(String tid) { return prateleiras.containsKey(tid);}
    public Prateleira getPrateleira(String num) { return this.prateleiras.get(num); }
    public Collection<Prateleira> getPrateleiras() { return new ArrayList<>(this.prateleiras.values()); }

    public void adicionaPaleteNaPrateleira(String s,String p){
        Prateleira pe = this.prateleiras.get(p);
        if(pe!=null) {
            pe.adicionaPalete(s);
            Localizacao l = listagem.get(s);
            l.setLoca(p);
            listagem.put(l.getIdPalete(), l);
            this.prateleiras.put(p, pe);
        }
    }

    // Paletes
    public Palete getPalete(String num) { return this.paletesEmEspera.get(num); }
    public Collection<Palete> getPaletes() { return new ArrayList<>(this.paletesEmEspera.values()); }
    public boolean existePalete(String tid) { return paletesEmEspera.containsKey(tid); }
    public void adicionaPalete(Palete p) { paletesEmEspera.put(p.getCodPal(),p); }
    public void removePalete (Palete p) { paletesEmEspera.remove(p.getCodPal()); }
    public List<Palete> paletesPorRobot(String robot){
        return paletesEmEspera.values().stream().filter(p-> p.getIdRobot().equals(robot)).collect(Collectors.toList());
    }

    public Map<String,Palete> filtragemOcupado (){ return paletesEmEspera; }

    Comparator<Palete> comparatorOrdemNaturalP  = Comparator.comparing(Palete::getIdRobot);
    Comparator<String> comparatorOrdemNaturalR = Comparator.comparing(String::toString);

    public String escolheRobot (Map<String,Palete> arg,Set<String> robots) {
        int cont = 0;
        int min = 1000;
        List<Palete> a = new ArrayList<>(arg.values());
        List<String> y =new ArrayList<>(robots);
        a.sort(comparatorOrdemNaturalP);
        y.sort(comparatorOrdemNaturalR);
        String res=null;
        for (String t : y) {
            for(Palete b : a)
                if (t.equals(b.getIdRobot())) cont++;
            if (min > cont) {
                min = cont;
                res = t;
                }
            cont=0;
            }
        return res;
    }

    public Prateleira escolhePrateleira() {
        Vertice v = mapa.getVertices().stream().filter(p -> p.nome.equals("Rececao")).findFirst().orElse(null);
        Vertice res=null;
        double count;
        double actual = 100;
        for (Vertice c : mapa.getVertices()) {
            count = mapa.caminhoMaisCurto(v, c);
            mapa.resetNodesVisited();
            if (c.getPrat() != null && c.getPrat().getEspacosDisp() > 0 && count < actual) {
                actual = count;
                res = c;
            }
        }
        if (res != null) {
            res.getPrat().setEspacosDisp(res.getPrat().getEspacosDisp()-1);
            prateleiras.put(res.getPrat().getCodPrat(),res.getPrat());
            return res.getPrat();
        } else
            return null;
    }

    public void clearGestores(){ gestores.clear(); }
    public void clearPaletes(){ paletesEmEspera.clear(); }
    public void clearListagem(){ listagem.clear(); }
    public void clearPrateleiras() { prateleiras.clear(); }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArmazemFacade that = (ArmazemFacade) o;
        return Objects.equals(gestores, that.gestores) &&
                Objects.equals(prateleiras, that.prateleiras) &&
                Objects.equals(paletesEmEspera, that.paletesEmEspera) &&
                Objects.equals(listagem, that.listagem) &&
                Objects.equals(mapa, that.mapa) &&
                Objects.equals(comparatorOrdemNaturalR, that.comparatorOrdemNaturalR);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gestores, prateleiras, paletesEmEspera, listagem, mapa, comparatorOrdemNaturalR);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ArmazemFacade{");
        sb.append("gestores=").append(gestores);
        sb.append(", prateleiras=").append(prateleiras);
        sb.append(", paletesEmEspera=").append(paletesEmEspera);
        sb.append(", listagem=").append(listagem);
        sb.append(", mapa=").append(mapa);
        sb.append('}');
        return sb.toString();
    }
}
