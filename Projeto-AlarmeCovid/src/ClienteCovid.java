import exception.*;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

public class ClienteCovid {

    private DataOutputStream out;
    private DataInputStream in;
    private BufferedReader stdin;
    private Socket socket;
    private Menu menu;

    public ClienteCovid(DataOutputStream out, DataInputStream in, BufferedReader stdin, Socket socket, Menu menu) {
        this.out = out;
        this.in = in;
        this.stdin = stdin;
        this.socket = socket;
        this.menu = menu;
    }

    public static Utilizador parseLine(String userInput) {
        String[] tokens = userInput.split(" ");
        return new Utilizador(
                Integer.parseInt(tokens[0]),
                tokens[1],
                tokens[2],
                Integer.parseInt(tokens[3]),
                Integer.parseInt(tokens[4]),
                Boolean.parseBoolean(tokens[5]));
    }

    public boolean checkInput(String userInput){
        String[] tokens = userInput.split(" ");
        return tokens.length == 6 && tokens[0].matches("-?\\d+") &&
                tokens[3].matches("-?\\d+") &&
                tokens[4].matches("-?\\d+");
    }

    public void login() throws IOException, InvalidUserCredentialsException, UserLoginNotValidException, UserAcessToServerInvalidException, UserNotPremiumException {
        System.out.print("Insira o seu Id: ");
        int id = Integer.parseInt(stdin.readLine());
        out.writeInt(id);
        out.flush();
        System.out.print("Insira a sua Palavra-Passe: ");
        String dois = stdin.readLine();
        out.writeUTF(dois);
        out.flush();
        boolean ok = in.readBoolean();
        if (ok) {
            System.out.println("Login com Sucesso");
            inicializar(id);
        } else throw new InvalidUserCredentialsException();
    }

    public void registar() throws IOException, UserAlreadyRegisteredException {
        System.out.println("                        Posicao <10 Premium?");
        System.out.println("[Numero] [Nome] [Password] [x y] [true/false]");
        String userInput = stdin.readLine();
        boolean b=checkInput(userInput);
        out.writeBoolean(b);
        out.flush();
        if (b) {
            Utilizador newCliente = parseLine(userInput);
            newCliente.serialize(out);
            out.flush();
            boolean sucess = in.readBoolean();
            if (sucess) System.out.println("\nUtilizador registado com sucesso");
            else throw new UserAlreadyRegisteredException();
        } else System.out.println("Utilizador introduzido de forma errada!\nIntroduza segundo as regras!");
    }

    public void inicializar(int id) throws IOException, UserAcessToServerInvalidException, UserLoginNotValidException, UserNotPremiumException {
        menu.MenuUtiliz();
        String userInput;
        boolean run = true;
        while (run) {
            if ((userInput = stdin.readLine()) != null) {
                out.writeUTF(userInput);
                out.flush();
                switch (userInput) {
                    case "1":
                        nrLocalizacao(id);
                        menu.MenuUtiliz();
                        break;
                    case "2":
                        alteraLocalizacao(id);
                        menu.MenuUtiliz();
                        break;
                    case "3":
                        alteraLocalizacaoVazia(id);
                        menu.MenuUtiliz();
                        break;
                    case "4":
                        declararInfecao(id);
                        menu.MenuUtiliz();
                        break;
                    case "5":
                        printMapaPremium(id);
                        menu.MenuUtiliz();
                        break;
                    case "6":
                        estouInfetado(id);
                        menu.MenuUtiliz();
                        break;
                    case "0":
                        run=false;
                        break;
                }
            }
        }
    }

    public void nrLocalizacao(int id) throws IOException, UserLoginNotValidException, UserAcessToServerInvalidException {
        out.writeInt(id);
        out.flush();
        boolean login = in.readBoolean();
        boolean doente=in.readBoolean();
        if (login && !doente) {
            System.out.print("Insira o x: ");
            int x = Integer.parseInt(stdin.readLine());
            out.writeInt(x);
            out.flush();
            System.out.print("Insira o y: ");
            int y = Integer.parseInt(stdin.readLine());
            out.writeInt(y);
            out.flush();
            if(x<10 && y<10) {
                System.out.println("Existe " + in.readInt() + " utilizador nesta posicao!");
            } else System.out.println("Insira posições válidas, que sejam menores que 10");
        } else{
            if(!login) throw new UserLoginNotValidException();
            else throw new UserAcessToServerInvalidException();}
    }

    public void alteraLocalizacao(int id) throws IOException, UserLoginNotValidException, UserAcessToServerInvalidException {
        out.writeInt(id);
        out.flush();
        boolean login = in.readBoolean();
        boolean doente=in.readBoolean();
        if (login && !doente) {
            System.out.print("Insira o novo x: ");
            int x = Integer.parseInt(stdin.readLine());
            out.writeInt(x);
            out.flush();
            System.out.print("Insira o novo y: ");
            int y = Integer.parseInt(stdin.readLine());
            out.writeInt(y);
            out.flush();
            if(x<10 && y<10)
                System.out.println("Alterado para a Localizacao: " + x + " " + y);
            else
                System.out.println("Insira posições válidas, que sejam menores que 10");
        } else{
            if(!login) throw new UserLoginNotValidException();
            else throw new UserAcessToServerInvalidException();}
    }

    public void alteraLocalizacaoVazia(int id) throws IOException, UserLoginNotValidException, UserAcessToServerInvalidException {
        ReentrantLock lock =new ReentrantLock();
        out.writeInt(id);
        out.flush();
        boolean login = in.readBoolean();
        boolean doente = in.readBoolean();
        if (login && !doente) {
            System.out.print("insira o x para onde se quer movimentar: ");
            int x = Integer.parseInt(stdin.readLine());
            out.writeInt(x);
            out.flush();
            System.out.print("insira o y para onde se quer movimentar: ");
            int y = Integer.parseInt(stdin.readLine());
            out.writeInt(y);
            out.flush();
            if (in.readInt() != 0) {
                lock.lock();
                try{
                    System.out.println("A verificar a localizacao...");
                    Thread n2 = new Thread((new clientePosVazia(socket)));
                    n2.start();}
                finally {
                    lock.unlock();
                }
            } else {
                System.out.println("A localização para onde deseja ir está livre");
            }
        } else {
            if (!login) throw new UserLoginNotValidException();
            else throw new UserAcessToServerInvalidException();
        }
    }

    public void estouInfetado(int id) throws IOException {
        out.writeInt(id);
        out.flush();
        boolean login = in.readBoolean();
        boolean doente = in.readBoolean();
        if(login && !doente) {
            System.out.println(in.readUTF());
        }
    }

    public void declararInfecao(int id) throws IOException, UserLoginNotValidException, UserAcessToServerInvalidException {
        out.writeInt(id);
        out.flush();
        boolean login  = in.readBoolean();
        boolean doente =in.readBoolean();
        if (login && !doente) {
            System.out.println(in.readUTF());
        } else{
            if (!login) throw new UserLoginNotValidException();
            else throw new UserAcessToServerInvalidException();}
    }

    public void printMapaPremium(int id) throws IOException, UserLoginNotValidException, UserAcessToServerInvalidException, UserNotPremiumException {
        out.writeInt(id);
        out.flush();
        boolean login  = in.readBoolean();
        boolean doente =in.readBoolean();
        boolean premium =in.readBoolean();
        if (login && !doente && premium) {
            Mapa newMapa = Mapa.deserialize(in);
            System.out.println("   |  0  |  1  |  2  |  3  |  4  |  5  |  6  |  7  |  8  |  9  |");
            for (int i = 0; i < 10; i++) {
                System.out.print(i + "  | ");
                for (int j = 0; j < 10; j++) {
                    System.out.print(newMapa.getTotalGeralMapa(i, j) + "," + newMapa.getDoentesMapa(i, j) + " | ");
                }
                System.out.print("\n");
            }
        } else{
        if (!login) throw new UserLoginNotValidException();
        else if(!premium) throw new UserNotPremiumException();
        else throw new UserAcessToServerInvalidException();
        }
    }
}
