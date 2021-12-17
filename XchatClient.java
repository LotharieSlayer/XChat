import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.Socket;
import java.util.Locale;
import java.util.Scanner;

public class XchatClient {

    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private Scanner sc = new Scanner(System.in, "UTF-8");

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
     * Constructeur de XchatClient, initialise le client et se connecte au serveur.
     * @param serverName
     * @param serverPort
     */
    public XchatClient(String serverName, int serverPort) {
        Locale loc = new Locale("fr", "FR");
        sc.useLocale(loc);

        System.out.println(ANSI_CYAN + "Tentative de connexion..." + ANSI_RESET);
        try {
            clientSocket = new Socket(serverName, serverPort);
            System.out.println(ANSI_GREEN + "Connecté à " + clientSocket + ANSI_RESET);


            System.out.println(ANSI_PURPLE + "____  ____________   ___ ___    ________________");
            System.out.println("\\   \\/  /\\_   ___ \\ /   |   \\  /  _  \\__    ___/");
            System.out.println(" \\     / /    \\  \\//    ~    \\/  /_\\  \\|    |");
            System.out.println(" /     \\ \\     \\___\\    Y    /    |    \\    |");
            System.out.println("/___/\\  \\ \\______  /\\___|_  /\\____|__  /____|");
            System.out.println("      \\_/        \\/       \\/         \\/       Client" + ANSI_RESET);
            System.out.println("\nBienvenue sur XCHAT !\n(.quit pour quitter le chat)\n");

            launch();
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

    /**
     * Lance le début du client : Init des PW et BR, demande de pseudo, démarrage des threads.
     */
    public void launch() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            System.out.println("Entrez votre pseudo : ");
            String pseudo = sc.nextLine();
            System.out.println("XCHAT Bot : Salut " + pseudo + " !");

            Thread send = new Thread(new Runnable() {
                String msg;

                @Override
                public void run() {
                    msg = "";
                    while (!msg.equals(".quit")) {
                        msg = sc.nextLine();
                        out.println(pseudo + " : " + msg);
                        out.flush();
                    }
                    System.out.println(ANSI_RED + "Déconnexion..." + ANSI_RESET);
                    System.exit(0);
                }
            });
            send.start();

            Thread receive = new Thread(new Runnable() {
                String msg;

                @Override
                public void run() {
                    try {
                        msg = in.readLine();
                        while (!msg.equals(".quit")) {
                            System.out.println(msg);
                            msg = in.readLine();
                        }
                        System.out.println(ANSI_RED + "Déconnexion..." + ANSI_RESET);
                        System.exit(0);
                    } catch (IOException e) {
                        System.out.println(ANSI_RED + "Serveur déconnecté" + ANSI_RESET);
                        System.exit(0);
                    }
                }
            });
            receive.start();

        } catch (IOException e) {
            System.out.println(ANSI_RED + "Connexion perdue." + ANSI_RESET);
            System.exit(0);
        }
    }

    /**
     * Vérifie qu'il y a bien 2 args avant de lancer le constructeur et lance le programme.
     * @param args
     */
    public static void main(String[] args) {
        XchatClient client;
        if (args.length != 2)
            System.out.println("Utilisation de ce fichier : java XchatClient hôte port");
        else
            client = new XchatClient(args[0], Integer.parseInt(args[1]));
    }
}