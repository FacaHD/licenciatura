import java.io.*;
import java.net.Socket;

public class asd {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 6789);
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream()));

        boolean connected = true;

        while (connected) {
            String line = null;
            try {
                line = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (line.equals("zero")) {
                connected = false;
            } else System.out.println(line);
        }

        socket.close();
    }
}
