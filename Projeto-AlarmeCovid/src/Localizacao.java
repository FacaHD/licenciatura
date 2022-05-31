import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class Localizacao {
    int doentes;
    int total;
    int totalGeral;
    List<Integer> utilizadores;
    ReentrantLock lock=new ReentrantLock();

    public Localizacao() {
        this.doentes =0;
        this.total=0;
        this.totalGeral=0;
        this.utilizadores=new ArrayList<>();
    }

    public Localizacao(int doentes, int totalGeral){
        this.doentes =doentes;
        this.totalGeral=totalGeral;
        this.utilizadores=new ArrayList<>();
    }

    public Localizacao(List<Integer> clientes,int doentes, int total, int totalGeral) {
        this.doentes =doentes;
        this.total =total;
        this.totalGeral = totalGeral;
        this.utilizadores = clientes;
    }

    public List<Integer> getUtilizadores() {
        return utilizadores;
    }

    public void setUtilizadores(List<Integer> utilizadores) {
        this.utilizadores = utilizadores;
    }


    public int getDoentes() {
        return doentes;
    }

    public void setDoentes(int doentes) {
        this.doentes = doentes;
    }

    public int getTotal() {
        lock.lock();
        try {
            return total;
        }finally {
            lock.unlock();
        }
    }

    public void setTotal(int total) {
        lock.lock();
        try {
            this.total = total;
        }finally {
            lock.unlock();
        }

    }

    public int getTotalGeral() {
        return totalGeral;
    }

    public void setTotalGeral(int totalGeral) {
        this.totalGeral = totalGeral;
    }

    public void serialize(DataOutputStream out) throws IOException {
        out.writeInt(this.totalGeral);
        out.writeInt(this.doentes);
        out.flush();
    }

    public static Localizacao deserialize (DataInputStream in) throws IOException {
        int totalGeral = in.readInt();
        int doentes  = in.readInt();
        return new Localizacao(doentes,totalGeral);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Localizacao{");
        sb.append("doentes=").append(doentes);
        sb.append(", total=").append(total);
        sb.append(", totalGeral=").append(totalGeral);
        sb.append(", utilizadores=").append(utilizadores);
        sb.append('}');
        return sb.toString();
    }
}
