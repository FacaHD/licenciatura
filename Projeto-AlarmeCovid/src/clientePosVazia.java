import java.io.*;
import java.net.Socket;
import java.util.concurrent.TransferQueue;

public class clientePosVazia implements Runnable {
    private Socket socket;

    public clientePosVazia(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

            System.out.println(in.readUTF());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
