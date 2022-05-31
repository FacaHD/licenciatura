import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class adasd {
    public static void main (String[] args) throws IOException {
    ServerSocket ss = new ServerSocket(6789);
    while (true) {
        Socket socket = ss.accept();

        PrintWriter out = new PrintWriter(socket.getOutputStream());

        String lines[] = {"m1", "m2", "m3"};

        for (String line : lines) {
            out.println(line);
            out.flush();
        }
    }
    }
}
