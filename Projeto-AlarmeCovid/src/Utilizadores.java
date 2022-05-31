import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Utilizadores {
    private Map<Integer, Utilizador> utilizadores;
    ReentrantLock lock= new ReentrantLock();
    Condition c = lock.newCondition();


    public Utilizadores() {
        utilizadores = new HashMap<>();
    }


    public Map<Integer, Utilizador> getUtilizadores() {
        return utilizadores;
    }

    public void setUtilizadores(Map<Integer, Utilizador> utilizadores) {
        this.utilizadores = utilizadores;
    }


    public boolean existeUtilizador(int id) {
        return utilizadores.containsKey(id);
    }

    public Utilizador getUtilizador(int id) {
        return this.utilizadores.get(id);
    }

    public boolean addUtilizador (Utilizador utilizador) {
        boolean sucess=false;
        if(!existeUtilizador(utilizador.getId())) {
            utilizadores.put(utilizador.getId(), utilizador);
            sucess=true;
        }
        return sucess;
    }

    public void potenciaisinfetados(List<Integer> ids) {
        for (int id6 : ids) {
            utilizadores.get(id6).setPotencialinfetado(true);
        }
        try {
            lock.lock();
            c.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void verificaInfecao (DataInputStream in, DataOutputStream out, int id) throws InterruptedException, IOException {
        this.lock.lock();
        try {
            while (!this.utilizadores.get(id).isPotencialinfetado()) this.c.await();
        } finally {
            lock.unlock();
        }
    }
    public Boolean checkLogin(int id,String pass) {
        Utilizador aux = utilizadores.get(id);
        if(aux!=null){
            return pass.equals(aux.getPass());
        }
        return false;
    }

    public void alteraLogado(int id){
        utilizadores.get(id).setLogado(true);
    }

    public boolean verifyLogin (int id) {
        Utilizador utilizador = utilizadores.get(id);
        return utilizador.isLogado();
    }
    public boolean verifyDoente (int id) {
        Utilizador utilizador = utilizadores.get(id);
        return utilizador.isDoente();
    }
    public boolean verifyPremium (int id) {
        Utilizador utilizador = utilizadores.get(id);
        return utilizador.isPremium();
    }
}