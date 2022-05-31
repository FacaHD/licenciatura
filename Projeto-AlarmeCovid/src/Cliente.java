
import exception.*;

import javax.security.auth.login.LoginException;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.locks.ReadWriteLock;

public class Cliente {




    public static void main(String[] args) throws IOException, UserAlreadyRegisteredException {
        Socket socket = new Socket("localhost", 6789);
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        Menu menu = new Menu();
        ClienteCovid clienteCovid = new ClienteCovid(out,in,stdin,socket,menu);
        menu.MenuOpcoes(1);
        menu.MenuLogin();
        String userInput;
        while ((userInput = stdin.readLine()) != null) {
            out.writeUTF(userInput);
            out.flush();
            switch (userInput.toUpperCase()) {
                case "L":
                    try {
                        clienteCovid.login();
                    }catch (InvalidUserCredentialsException l){
                        System.out.println("Login nao realizado com sucesso");
                    } catch (UserAcessToServerInvalidException e) {
                        System.out.println("Utilizador nao tem acesso ao servidor");
                    } catch (UserLoginNotValidException e) {
                        System.out.println(e.toString());
                    } catch (UserNotPremiumException e) {
                        System.out.println("Nao é Premium! Subscreva por apenas 5€ mensais.");
                    }
                    menu.MenuLogin();
                    break;
                case "R":
                    try {
                        clienteCovid.registar();
                    } catch (UserAlreadyRegisteredException e) {
                        System.out.println("Utilizador com este Id já está registado!");
                    }
                    menu.MenuLogin();
                    break;
                case "H":
                    menu.MenuHelp();
                    menu.MenuLogin();
                    break;
                case "S":
                    menu.MenuOpcoes(2);
                    socket.close();
                    return;
                default:
                    System.out.println("Opção não disponivel! ");
                    break;
            }
        }
        socket.close();
    }
}

