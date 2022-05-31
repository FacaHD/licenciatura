import java.io.*;
import java.net.Socket;

public class potencialInfetadoWorker implements Runnable {
    private Socket socket;
    private Utilizadores utilizadores;
    private int id;

    public potencialInfetadoWorker (Socket socket, Utilizadores utilizadores,int id) {
        this.socket = socket;
        this.utilizadores=utilizadores;
        this.id=id;
    }

    public void run() {
        try {
            DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            this.utilizadores.verificaInfecao(in,out,id);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}

