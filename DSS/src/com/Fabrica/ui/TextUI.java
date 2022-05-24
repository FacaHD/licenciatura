package com.Fabrica.ui;

import com.Fabrica.FabricaFacade;
import com.Fabrica.GestArmazem.business.Localizacao;
import com.Fabrica.GestArmazem.business.Palete;
import com.Fabrica.IFabricaFacade;
import com.Fabrica.exception.WWrongCredentialsException;

import java.util.Scanner;

public class TextUI{

        // O model tem a 'lógica de negócio'.
        private IFabricaFacade model;

        // Scanner para leitura
        private Scanner scin;
        private paginas pag;
        private UIRequisicao uireq;
        private UIGestores uigest;
        private UIRobots uirobots;
        private UIPrateleiras uiprat;


        /**
         * Construtor.
         *
         * Cria os menus e a camada de negócio.
         */
        public TextUI() {

            this.model = new FabricaFacade();
            scin = new Scanner(System.in);

            uireq=new UIRequisicao();
            uigest=new UIGestores();
            uirobots=new UIRobots();
            uiprat=new UIPrateleiras();
        }

        /**
         * Executa o menu principal e invoca o método correspondente à opção seleccionada.
         */
        public void run() {
            System.out.println("\n" +
                    "   ____           _                    _        ____  _             _        \n" +
                    "  / ___| ___  ___| |_ __ _  ___     __| | ___  / ___|| |_ ___   ___| | _____ \n" +
                    " | |  _ / _ \\/ __| __/ _` |/ _ \\   / _` |/ _ \\ \\___ \\| __/ _ \\ / __| |/ / __|\n" +
                    " | |_| |  __/\\__ \\ || (_| | (_) | | (_| |  __/  ___) | || (_) | (__|   <\\__ \\\n" +
                    "  \\____|\\___||___/\\__\\__,_|\\___/   \\__,_|\\___| |____/ \\__\\___/ \\___|_|\\_\\___/\n" +
                    "                                                                             \n");
            this.menuPrincipal();
            System.out.println("\n" +
                    "  .--.  .---. .----.   .----. .----. .----..-. .-..----.\n" +
                    " / {} \\{_   _}| {_     | {}  }| {}  }| {_  | | | || {_  \n" +
                    "/  /\\  \\ | |  | {__    | {}  }| .-. \\| {__ \\ \\_/ /| {__ \n" +
                    "`-'  `-' `-'  `----'   `----' `-' `-'`----' `---' `----'\n");

        }

        // Métodos auxiliares - Estados da UI

        /**
         * Estado - Menu Principal
         */
        private void menuPrincipal() {
            Menu menu = new Menu(new String[]{
                    "Login Gestor  ",
                    "Leitor QR-Code",
                    "Controlar Robot",
                    "Admin         "
            });

            // Registar pré-condições das transições
       //     menu.setPreCondition(3, ()->this.model.haAlunos() && this.model.haTurmas());

            // Registar os handlers
            menu.setHandler(1, ()-> {
                try {
                    login();
                } catch (WWrongCredentialsException e) {
                    System.out.println("              Palavra-Passe ou Codigo incorreto");
                }
            });
            menu.setHandler(2, this::LeitorQRCode);
            menu.setHandler(3, this::robot);
            menu.setHandler(4, this::admin);
            // Falta handler para opção 2 - "Operações sobre Turmas"

          //  menu.setHandler(4, ()->removerAlunoDeTurma());
          //  menu.setHandler(5, ()->listarAlunosDaTurma());

            menu.run();
        }

    private void LeitorQRCode() {
        Menu menu = new Menu(new String[]{
                "Ler nova Palete"
        });
        menu.setHandler(1, this::adicionarPalete);
        menu.run();
    }

    private void manipularRequisicao() {
        Menu menu = new Menu(new String[]{
                "Adicionar Requisicao",
                "Consultar Requisicao",
                "Listar Requisicao   "
        });

        // Registar os handlers
        menu.setHandler(1, ()->uireq.adicionarRequisicao(model, scin));
        menu.setHandler(2, ()->uireq.consultarRequisicao(model, scin));
        menu.setHandler(3, ()->uireq.listarRequisicao(model, scin));

        menu.run();
    }

    private void admin(){
        Menu menu = new Menu(new String[]{
                "Gerir Gestores   ",
                "Gerir Requisicoes",
                "Gerir Robots     ",
                "Gerir Prateleiras",
                "Gerir Paletes    "
        });

        menu.setHandler(1, this::manipularGestor);
        menu.setHandler(2, this::manipularRequisicao);
        menu.setHandler(3, this::manipularRobots);
        menu.setHandler(4, this::manipularPrateleiras);
        menu.setHandler(5, this::manipularPaletes);

        menu.run();

    }

    private void manipularPaletes() {
        Menu menu = new Menu(new String[]{
                "Adicionar Palete",
                "Consultar Palete",
                "Listar Palete",
                "Remover todas as Paletes em Espera"
        });

        // Registar os handlers
        menu.setHandler(1, this::adicionarPalete);
        menu.setHandler(2, this::consultarPalete);
        menu.setHandler(3, this::listarPalete);
        menu.setHandler(4, this::clearPalete);

        menu.run();
    }

    private void manipularPrateleiras() {
        Menu menu = new Menu(new String[]{
                "Adicionar Prateleira",
                "Consultar Prateleira",
                "Listar Prateleira",
                "Remover todas as Prateleiras"
        });

        // Registar os handlers
        menu.setHandler(1, ()->uiprat.adicionarPrateleira(model, scin));
        menu.setHandler(2, ()->uiprat.consultarPrateleira(model, scin));
        menu.setHandler(3, ()->uiprat.listarPrateleira(model, scin));
        menu.setHandler(4, ()->uiprat.clearPrateleiras(model));
        menu.run();
    }

    private void manipularGestor() {
        Menu menu = new Menu(new String[]{
                "Adicionar Gestor",
                "Consultar Gestor",
                "Listar Gestor",
                "consultar listagem",
                "Remover todos os Gestores",
                "Remover da Listagem"
        });

        // Registar os handlers
        menu.setHandler(1, ()->uigest.adicionarGestor(model, scin));
        menu.setHandler(2, ()->uigest.consultarGestor(model, scin));
        menu.setHandler(3, ()->uigest.listarGestor(model, scin));
        menu.setHandler(4, ()->uigest.consultaLista(model));
        menu.setHandler(5, ()->uigest.clearGestores(model));
        menu.setHandler(6, ()->uigest.clearListagem(model));

        menu.run();
    }

    private void manipularRobots() {
        Menu menu = new Menu(new String[]{
                "Adicionar Robot",
                "Consultar Robot",
                "Listar Robot",
                "Remover todos os Robots"
        });

        // Registar os handlers
        menu.setHandler(1, ()->uirobots.adicionarRobots(model, scin));
        menu.setHandler(2, ()->uirobots.consultarRobots(model, scin));
        menu.setHandler(3, ()->uirobots.listarRobots(model, scin));
        menu.setHandler(4, ()->uirobots.clearRobots(model));
        //menu.setHandler(4,()-> uirobots.updateRobots(model,scin));
        menu.run();
    }

    private void robot() {
            System.out.print("\n              Robot: ");
            String codRobot = scin.nextLine();
            if(model.existeRobot(codRobot)) {
                Menu menu = new Menu(new String[]{
                    "Recolher Palete",
                    "Notificar entrega da Palete"
                });
            // Registar os handlers
            menu.setHandler(1, () -> uirobots.updateRobots(model, scin, codRobot));
            menu.setHandler(2, () -> uiprat.adicionaPalete(model, scin, codRobot));

            menu.run();
        } else System.out.println("\n              Robot '" + codRobot +"' inexistente");
    }


    private void login() throws WWrongCredentialsException {
            System.out.print("\n              Código de Gestor : ");
            String codigo = scin.nextLine();
            boolean preLogged;
            try{
                preLogged=model.getGestor(codigo).isLogado();
                boolean logged = false;
                if(!preLogged) {
                    System.out.print("              Password: ");
                    String password = scin.nextLine();
                    logged = this.model.iniciarSessao(codigo, password);
                }
                // boolean logged = this.model.iniciarSessao(codigo, password);
                if(preLogged || logged) {
                    Menu menu = new Menu(new String[]{
                            "Requisitar listagem de Paletes",
                            "Aceitar chegada de paletes",
                            "Terminar Sessao"

                    });

                    // Registar os handlers
                    menu.setHandler(1, () -> uigest.consultaLista(model));
                    // menu.setHandler(2, ()->uigest.aceitarPaletes);
                    menu.setHandler(3, () -> uigest.terminarSessao(model, codigo));

                    menu.run();
                }
            } catch (NullPointerException e) {
                System.out.println("              Gestor inválido");
            }
        }

    private void listarPalete() {
        try {
            System.out.println(this.model.getPaletes().toString());
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    private void consultarPalete() {
        try {
            System.out.print("              Palete a consultar: ");
            String num = scin.nextLine();
            if (this.model.existePalete(num)) {
                System.out.println(this.model.getPalete(num).toString());
            } else System.out.println("              Esta Palete nao existe!");
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }


    private void adicionarPalete() {
        try {
            String cod = "P100";
            while (this.model.existeNaLocalizacao(cod)){
                cod = "P" + Integer.toString(Integer.parseInt(cod.substring(1)) + 1);
            }
            //System.out.print("\n              Palete número " + cod + " adicionada\n");
            if (!this.model.existeNaLocalizacao(cod)) {
                String robot=this.model.escolheRobot();
                if(robot==null){
                    System.out.println("              Não existem Robots no Armazém");
                    return;
                }
                String prat =this.model.escolhePrateleira();
                if(prat==null) {
                    System.out.println("              Armazém nao pode receber mais paletes");
                    return;
                }
                this.model.adicionaPalete(new Palete(cod, robot, prat));
                this.model.adicionaLista(new Localizacao(cod,"Rececao"));
                System.out.println("\n              Palete número '" + cod + "' registada");
            } else { System.out.println("Palete ja existente"); }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void clearPalete(){
        try{
            model.clearPaletes();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
