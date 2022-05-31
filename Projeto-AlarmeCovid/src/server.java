import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class server {


    public static void main (String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6789);
        Utilizadores contactList = new Utilizadores();
        Mapa mapa=new Mapa();
        mapa.initMapa();
        while (true) {
            Socket socket = serverSocket.accept();
            Thread worker = new Thread(new ServerWorker(socket, contactList,mapa));
            worker.start();
        }
    }




}