import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class XchatServer {

    private ServerSocket serveurSocket;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    public ArrayList<Client> clientList = new ArrayList<Client>();

    // COULEURS
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";


    /**
     * Constructeur de XchatServer, initialise le serveur et vérifie si le port n'est pas déjà utilisé.
     * @param port
     */
    public XchatServer(int port) {
        try {
            System.out.println(ANSI_YELLOW + "____  ____________   ___ ___    ________________");
            System.out.println("\\   \\/  /\\_   ___ \\ /   |   \\  /  _  \\__    ___/");
            System.out.println(" \\     / /    \\  \\//    ~    \\/  /_\\  \\|    |");
            System.out.println(" /     \\ \\     \\___\\    Y    /    |    \\    |");
            System.out.println("/___/\\  \\ \\______  /\\___|_  /\\____|__  /____|");
            System.out.println("      \\_/        \\/       \\/         \\/       Serveur" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "Le serveur prend le port " + port + ANSI_RESET);
            serveurSocket = new ServerSocket(port);
            System.out.println(ANSI_GREEN + "Serveur démarré: " + serveurSocket + ANSI_RESET);
                        
            launch();
        } catch (IOException ioe) {
            System.out.println(ANSI_RED + "Impossible d'utiliser le port " + port + ANSI_RESET);
            System.exit(0);
        }
    }

    /**
     * Accepte les clients entrants, Init des PW et BR, ArrayList des clients,
     * démarre le thread receive qui activera les autres.
     */
    public void launch() {
        try {
            while(!serveurSocket.isClosed()){
                clientSocket = serveurSocket.accept();
                out = new PrintWriter(clientSocket.getOutputStream());
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                Client client = new Client(in, out);
                this.clientList.add(client);
                receive(client);
            }

        } catch (IOException e) {
            System.out.println(ANSI_RED + "Client perdu." + ANSI_RESET);
        }
    }

    /**
     * Méthode qui permet d'envoyer au reste de l'ArrayList le message entrant.
     * @param cli
     * @param msg
     */
    public void backToClients(Client cli, String msg){
        for (Client client : clientList) {
            if(cli == client) continue;
            send(client, msg);
        }
    }

    /**
     * Méthode qui envoie le message pris en paramètre.
     * @param client
     * @param msg
     */
    public void send(Client client, String msg){
        Thread send = new Thread(new Runnable() {
            @Override
            public void run() {
                client.getOut().println(msg);
                client.getOut().flush();
            }
        });
        send.start();
    }

    /**
     * Méthode qui reçois les messages avec un client en paramètre.
     * @param client
     */
    public void receive(Client client) {
        Thread receive = new Thread(new Runnable() {
            String msg;

            @Override
            public void run() {
                try {
                    msg = in.readLine();
                    while (msg != null) {
                        System.out.println(msg);
                        backToClients(client, msg);
                        msg = client.getIn().readLine();
                    }
                    System.out.println(ANSI_RED + "Client déconnecté" + ANSI_RESET);
                    out.close();
                    clientSocket.close();
                    serveurSocket.close();
                } catch (IOException e) {
                    System.out.println(ANSI_RED + "Client déconnecté." + ANSI_RESET);
                }
            }
        });
        receive.start();
    }

    /**
     * Vérifie qu'il y a bien 1 args avant de lancer le constructeur et lance le programme.
     * @param args
     */
    public static void main(String[] args) {
        XchatServer server;
        if (args.length != 1)
            System.out.println("Utilisation de ce fichier : java XchatServer port");
        else
            server = new XchatServer(Integer.parseInt(args[0]));
    }
}