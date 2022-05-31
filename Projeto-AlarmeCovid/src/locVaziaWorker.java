import java.io.*;
import java.net.Socket;

public class locVaziaWorker implements Runnable {
    private Socket socket;
    private Mapa mapa;
    int x;
    int y;


    public locVaziaWorker (Socket socket, Mapa mapa, int x , int y) {
        this.mapa=mapa;
        this.socket = socket;
        this.x=x;
        this.y=y;

    }

    public void run() {
        try {
            DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            this.mapa.verificaVazia(x,y,in,out);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
