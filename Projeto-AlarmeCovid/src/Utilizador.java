import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Utilizador {
    private int id;
    private String nome;
    private String pass;
    private int x;
    private int y;
    private boolean doente;
    private boolean logado;
    private boolean isPotencialinfetado;
    private boolean premium;
    private List<Posicao> posicao;
    ReentrantLock lock= new ReentrantLock();
    Condition c = lock.newCondition();

    public Utilizador(){
        this.id=0;
        this.nome="";
        this.pass="";
        this.x=0;
        this.x=0;
        this.doente=false;
        this.logado=false;
        this.isPotencialinfetado=false;
        this.premium=false;
        this.posicao=new ArrayList<>();
    }

    public Utilizador(int id, String nome, String pass, int x, int y, boolean premium) {
        this.id = id;
        this.pass=pass;
        this.nome=nome;
        this.x = x;
        this.y = y;
        this.doente=false;
        this.logado=false;
        this.isPotencialinfetado=false;
        this.premium=premium;
        this.posicao=new ArrayList<>();
    }


    public List<Posicao> getPosicao() {
        return posicao;
    }

    public void addPosicao (int x, int y) {
        Posicao p = new Posicao(x,y);
        this.posicao.add(p);
    }

    public void setPosicao(List<Posicao> posicao) {

        this.posicao = posicao;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getNome() {
        return nome;
    }

    public boolean isPotencialinfetado() {
        return isPotencialinfetado;
    }

    public void setPotencialinfetado(boolean potencialinfetado) {
        isPotencialinfetado = potencialinfetado;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isDoente() {
        return doente;
    }

    public void setDoente(boolean doente) {
        this.doente = doente;
        try {
            this.lock.lock();
            this.c.signalAll();
        } finally {
            this.lock.unlock();
        }
    }

    public boolean isLogado() {
        return logado;
    }

    public void setLogado(boolean logado) {
        this.logado = logado;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public void serialize(DataOutputStream out) throws IOException {
        out.writeInt(this.id);
        out.writeUTF(this.nome);
        out.writeUTF(this.pass);
        out.writeInt(this.x);
        out.writeInt(this.y);
        out.writeBoolean(this.doente);
        out.writeBoolean(this.logado);
        out.writeBoolean(this.isPotencialinfetado);
        out.writeBoolean(this.premium);
        out.flush();
    }

    public static Utilizador deserialize (DataInputStream in) throws IOException{
        int id=in.readInt();
        String nome=in.readUTF();
        String pass=in.readUTF();
        int x=in.readInt();
        int y=in.readInt();
        boolean doente= in.readBoolean();
        boolean logado=in.readBoolean();
        boolean isPotencialInfetado =in.readBoolean();
        boolean premium=in.readBoolean();
        return new Utilizador(id,nome,pass,x,y, premium);
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Utilizador{");
        sb.append("id=").append(id);
        sb.append(", nome='").append(nome).append('\'');
        sb.append(", pass='").append(pass).append('\'');
        sb.append(", x=").append(x);
        sb.append(", y=").append(y);
        sb.append(", doente=").append(doente);
        sb.append(", logado=").append(logado);
        sb.append(", premium=").append(premium);
        sb.append(", posicao=").append(posicao);
        sb.append('}');
        return sb.toString();
    }
}
