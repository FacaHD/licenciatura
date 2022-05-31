import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Mapa {
    private Localizacao[][] mapa;
    ReentrantLock lock= new ReentrantLock();
    Condition c = lock.newCondition();

    public Mapa() {
        this.mapa=new Localizacao[10][10];
    }


    public Localizacao [][] initMapa () {
        for (int i = 0; i<10; i++) {
            for(int j = 0; j<10; j++) {
                mapa[i][j] =new Localizacao();
            }
        }
        return mapa;
    }

    public Mapa(Localizacao[][] mapa) {
        this.mapa = mapa;
    }

    public int getNMapa (int x,int y){
        int r;
        lock.lock();
        try{
            r=this.mapa[x][y].getTotal();
        }finally {
            lock.unlock();
        }
        return r;
    }

    public int getDoentesMapa (int x,int y){
        int r;
        lock.lock();
        try{
            r=this.mapa[x][y].getDoentes();
        }finally {
            lock.unlock();
        }
        return r;
    }



    public void addCliente(int id,int x,int y) {
        this.mapa[x][y].getUtilizadores().add(id);
    }

    public void setNMapa (int x,int y,int i){
        lock.lock();
        try {
            this.mapa[x][y].setTotal(i + this.getNMapa(x, y));
        }
        finally {
            lock.unlock();
        }
    }

    public void setTotalGeralMapa (int x,int y,int i){
        lock.lock();
        try {
            this.mapa[x][y].setTotalGeral(this.mapa[x][y].getTotalGeral()+i);
        }
        finally {
            lock.unlock();
        }
    }

    public int getTotalGeralMapa (int x,int y){
        int r;
        lock.lock();
        try{
            r=this.mapa[x][y].getTotalGeral();
        }finally {
            lock.unlock();
        }
        return r;
    }
    public void setDoenteMapa (int x,int y,int i){
        lock.lock();
        try {
            this.mapa[x][y].setDoentes(this.mapa[x][y].getDoentes()+i);
        }
        finally {
            lock.unlock();
        }
    }
    public void verificaVazia (int x , int y, DataInputStream in, DataOutputStream out) throws InterruptedException {
        this.lock.lock();
        try {

            while (this.mapa[x][y].getTotal()!=0) this.c.await();
            out.writeUTF("""
                                        
                    /------------------------------------------------------------------------------\\
                    |Peço desculpa pela demora, a posição para onde queria ir esta finalmente livre!|
                    \\------------------------------------------------------------------------------/""");
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.lock.unlock();
        }
    }

    public List<Integer> detetePotenciaisInfetados (List<Posicao> p,int id) throws InterruptedException {
        List<Integer> aux;
        List<Integer> aux1 =new ArrayList<>();
        lock.lock();
        try {
            {
                for (Posicao t : p) {
                    aux = this.mapa[t.getX()][t.getY()].getUtilizadores();
                    for (int a : aux) {
                        if (a!=id)
                            aux1.add(a);
                    }
                }
            }
        } finally {
            this.lock.unlock();
        }
        return aux1;
    }




    public void atualizaMapa (int x,int y,int x1,int y1,int id) throws InterruptedException {
        lock.lock();
        try {
            this.mapa[x][y].setTotal(this.mapa[x][y].getTotal() - 1);

        }finally {
            lock.unlock();
        }
        lock.lock();
        try {
            this.mapa[x1][y1].setTotalGeral(this.mapa[x1][y1].getTotalGeral() + 1);
            this.mapa[x1][y1].setTotal(this.mapa[x1][y1].getTotal() + 1);

        }finally {
            lock.unlock();
        }

     /*   if(this.mapa[x][y].getUtilizadores().size()>1)
            this.mapa[x][y].getUtilizadores().remove(id);*/
        this.mapa[x1][y1].getUtilizadores().add(id);

        try {
            this.lock.lock();
            this.c.signalAll();
        } finally {
            this.lock.unlock();
        }
    }

    public void posInfetadoEsteve(Utilizador utiliz, Mapa mapa){
        List<Posicao> pos = utiliz.getPosicao();
        for(Posicao p:pos){
            mapa.setDoenteMapa(p.x,p.y,1);
        }
    }

    public void serialize(DataOutputStream out) throws IOException {
        for (int i = 0; i<10; i++) {
            for(int j = 0; j<10; j++) {
                mapa[i][j].serialize(out);
            }
        }
    }

    public static Mapa deserialize (DataInputStream in) throws IOException{
        Localizacao[][] mapa = new Localizacao[10][10];
        for (int i = 0; i<10; i++) {
            for (int j = 0; j < 10; j++) {
                mapa[i][j]=Localizacao.deserialize(in);
            }
        }
        return new Mapa(mapa);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Mapa{");
        sb.append("mapa=").append(Arrays.toString(mapa));
        sb.append('}');
        return sb.toString();
    }
}

