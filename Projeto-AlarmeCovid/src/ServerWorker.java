import exception.UserAlreadyRegisteredException;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ServerWorker implements Runnable {
    private Socket socket;
    private Utilizadores utilizadores;
    private Mapa mapa;


    public ServerWorker (Socket socket, Utilizadores utilizadores, Mapa mapa) {
        this.utilizadores = utilizadores;
        this.mapa=mapa;
        this.socket = socket;
    }

    // @TODO
    @Override
    public void run() {
        try {
            DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            while (true) {
                String option = in.readUTF();
                switch (option.toUpperCase()) {
                    case "L":
                        int id = in.readInt();
                        String pass = in.readUTF();
                        boolean b;
                        if ((b = utilizadores.checkLogin(id, pass))) utilizadores.alteraLogado(id);
                        out.writeBoolean(b);
                        out.flush();
                        break;
                    case "R":
                        boolean ok=in.readBoolean();
                        if(ok) {
                            Utilizador newUtilizador = Utilizador.deserialize(in);
                            newUtilizador.addPosicao(newUtilizador.getX(), newUtilizador.getY());
                            boolean sucess = utilizadores.addUtilizador(newUtilizador);
                            if (sucess) {
                                mapa.addCliente(newUtilizador.getId(), newUtilizador.getX(), newUtilizador.getY());
                                mapa.setNMapa(newUtilizador.getX(), newUtilizador.getY(), 1);
                                mapa.setTotalGeralMapa(newUtilizador.getX(), newUtilizador.getY(), 1);
                            }
                            out.writeBoolean(sucess);
                            out.flush();
                        }
                        break;
                    case "1":
                        int id3=in.readInt();
                        boolean r = utilizadores.verifyLogin(id3);
                        boolean doente=utilizadores.verifyDoente(id3);
                        out.writeBoolean(r);
                        out.flush();
                        out.writeBoolean(doente);
                        out.flush();
                        if(r && !doente) {
                           int x = in.readInt();
                           int y = in.readInt();
                           if(x<10 && y<10) {
                               int n1 = mapa.getNMapa(x, y);
                               out.writeInt(n1);
                               out.flush();
                           }
                        }
                        break;
                    case "2":
                        int id1=in.readInt();
                        boolean logg = utilizadores.verifyLogin(id1);
                        boolean d = utilizadores.verifyDoente(id1);
                        out.writeBoolean(logg);
                        out.flush();
                        out.writeBoolean(d);
                        out.flush();
                        if(logg && !d) {
                            int x1 = in.readInt();
                            int y1 = in.readInt();
                            if(x1<10 && y1 <10) {
                                Utilizador utilizador = utilizadores.getUtilizadores().get(id1);
                                utilizador.addPosicao(x1, y1);
                                mapa.atualizaMapa(utilizador.getX(), utilizador.getY(), x1, y1, id1);
                                //System.out.println(utilizador);
                            }
                        }
                        break;

                    case "3":
                            int id2=in.readInt();
                            boolean t = utilizadores.verifyLogin(id2);
                            boolean w = utilizadores.verifyDoente(id2);
                            out.writeBoolean(t);
                            out.flush();
                            out.writeBoolean(w);
                            out.flush();
                            if(t && !w) {
                                int x1 = in.readInt();
                                int y1 = in.readInt();
                                int n = mapa.getNMapa(x1, y1);
                                out.writeInt(n);
                                out.flush();
                                if (n != 0) {
                                    Thread n1 = new Thread(new locVaziaWorker(socket, mapa, x1, y1));
                                    n1.start();
                                }
                            }
                        break;
                    case "4":
                        int id4=in.readInt();
                        boolean y = utilizadores.verifyLogin(id4);
                        boolean u = utilizadores.verifyDoente(id4);
                        out.writeBoolean(y);
                        out.flush();
                        out.writeBoolean(u);
                        out.flush();
                        if(y && !u) {
                            Utilizador ut=utilizadores.getUtilizadores().get(id4);
                            utilizadores.getUtilizadores().get(id4).setDoente(true);
                         /* mapa.setSaudavelMapa(ut.getX(), ut.getY(), -1);
                            mapa.setDoenteMapa(ut.getX(), ut.getY(), 1); */
                            mapa.posInfetadoEsteve(ut,mapa);
                            List<Integer> ids= mapa.detetePotenciaisInfetados(utilizadores.getUtilizadores().get(id4).getPosicao(),id4);
                            utilizadores.potenciaisinfetados(ids);
                            out.writeUTF("Não tem mais acesso ao Servidor! As melhoras " + ut.getNome());
                            out.flush();
                        }
                        break;
                    case "5":
                        int id5 = in.readInt();
                        boolean log = utilizadores.verifyLogin(id5);
                        boolean sick = utilizadores.verifyDoente(id5);
                        boolean premium = utilizadores.verifyPremium(id5);
                        out.writeBoolean(log);
                        out.flush();
                        out.writeBoolean(sick);
                        out.flush();
                        out.writeBoolean(premium);
                        out.flush();
                        if(log && premium && !sick) {
                            Mapa newMapa=mapa;
                            newMapa.serialize(out);
                            out.flush();
                        }
                        break;
                    case "6":
                        int id6 = in.readInt();
                        boolean log1 = utilizadores.verifyLogin(id6);
                        boolean sick1 = utilizadores.verifyDoente(id6);
                        out.writeBoolean(log1);
                        out.flush();
                        out.writeBoolean(sick1);
                        out.flush();
                        if(log1 && !sick1){
                            Thread n1 = new Thread(new potencialInfetadoWorker(socket, utilizadores,id6));
                            n1.start();
                            if(utilizadores.getUtilizadores().get(id6).isPotencialinfetado()) {
                                out.writeUTF("Esteve em contacto com infetados, isole-se!");
                                out.writeUTF("Nao esteve em contacto com infetados!");
                            }
                            else {
                                out.writeUTF("Nao esteve em contacto com infetados!");
                            }
                            out.flush();
                        }
                        break;
                    case "0":
                        break;
                    case "S":
                        throw new EOFException();
                }
            }
        } catch (EOFException e) {
            System.out.println(e.toString());
            System.out.println("Connection closed");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }



}
