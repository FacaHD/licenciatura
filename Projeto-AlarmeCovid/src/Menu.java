

public class Menu {

    public Menu(){}

    public void MenuUtiliz(){
        System.out.println("__________________________________________");
        System.out.println("1-Quantas pessoas estão numa Localização!");
        System.out.println("2-Mudar a minha localização!");
        System.out.println("3-Quero-me deslocar");
        System.out.println("4-Estou infetado!");
        System.out.println("5-Mapa [Funcionalidade Premium]");
        System.out.println("6-Estive em contacto com doentes COVID-19?");
        System.out.print  ("0-Sair                Opção: ");
    }

    public void MenuLogin(){
        System.out.println("________________/MENU\\_________________");
        System.out.println("L-Iniciar Sessão!");
        System.out.println("R-Registar!");
        System.out.println("H-Ajuda!");
        System.out.print  ("S-Sair                Opção: ");
    }

    public void MenuHelp(){
        System.out.println("\nPlataforma para controlo de contagios de um Mapa 10x10.\n");
        System.out.println("Universidade do Minho");
        System.out.println("Mestrado Integrado em Engenharia Informática");
        System.out.println("3º Ano\n");
        System.out.println("Trabalho realizado por:");
        System.out.println("     Daniel Sousa A89562");
        System.out.println("     José Pedro Veloso A89573");
        System.out.println("     Inês Bastos A89522");
        System.out.println("     João Sá A89550");
    }

    public void MenuOpcoes(int i){
        if(i==1)
        System.out.println("\n" +
                "    _   _                       ___ _____   _____ ___  \n" +
                "   /_\\ | |__ _ _ _ _ __  ___   / __/ _ \\ \\ / /_ _|   \\ \n" +
                "  / _ \\| / _` | '_| '  \\/ -_) | (_| (_) \\ V / | || |) |\n" +
                " /_/ \\_\\_\\__,_|_| |_|_|_\\___|  \\___\\___/ \\_/ |___|___/ \n");
        if(i==2)
        System.out.println("\n" +
                "  ___ _                 ___                        \n" +
                " | __(_)__ _ _  _ ___  / __| ___ __ _ _  _ _ _ ___ \n" +
                " | _|| / _` | || / -_) \\__ \\/ -_) _` | || | '_/ _ \\\n" +
                " |_| |_\\__, |\\_,_\\___| |___/\\___\\__, |\\_,_|_| \\___/\n" +
                "          |_|                   |___/              \n");
    }
}
