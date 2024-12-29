package serveurMT;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMT extends Thread
{
    private int client;
    public static void main(String[] args) {
        new ServerMT().start();
    }

    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(21);
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

                pw.println("Une connexion est établie avec le client " + client + " et le serveur " + socket.getInetAddress().getHostName());

                while (true) {
                    String s = br.readLine();
                    String response = "The length of the text you sent is " + s.length();
                    pw.println(response);
                }
                //commit trick
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
