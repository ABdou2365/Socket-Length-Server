package serveurJeu;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class ServeurJeu extends Thread
{
    private int client;
    private int randomNumber;
    private boolean gameFinished = false;
    private String winner;
    public static void main(String[] args) {
        new ServeurJeu().start();
    }

    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(21);
            randomNumber = new Random().nextInt(1000);
            System.out.println("Le serveur est démarrer ...");
            while (true) {
                Socket s = ss.accept();
                ++client;
                new Conversion(s, client).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class Conversion extends Thread{
        private Socket socket;
        private int client;
        public Conversion(Socket socket, int client) {
            this.socket = socket;
            this.client = client;
        }
        public void run() {

            try {
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);

                OutputStream os = socket.getOutputStream();
                PrintWriter pw = new PrintWriter(os,true);
                String ipClient = socket.getRemoteSocketAddress().toString();

                pw.println("Une connexion est établie avec le client " + ipClient + "joueur n= " + client + "et le serveur " + socket.getInetAddress().getHostName());

                while (true) {
                    String s = br.readLine();
                    int secretNumber = Integer.parseInt(s);
                    if(!gameFinished){
                        if(randomNumber<secretNumber){
                            pw.println("The number you inserted is higher than the secret number");
                        } else if(randomNumber>secretNumber){
                            pw.println("The number you inserted is lower than the secret number");
                        } else {
                            pw.println("Congratulation, You have won the game");
                            gameFinished = true;
                            System.out.println("the client with the IP:" + ipClient + " has won the game");
                        }
                    }else {
                        pw.println("the client with the IP:" + ipClient + " has won the game");
                    }
                }
                //commit trick hh
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
