package serveurChat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServeurChat extends Thread
{
    private int client;
    List<Conversion> clients = new ArrayList<>();
    public static void main(String[] args) {
        new ServeurChat().start();
    }

    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(21);
            System.out.println("Le serveur est démarrer ...");
            while (true) {
                Socket s = ss.accept();
                ++client;
                Conversion conversion = new Conversion(s,client);
                clients.add(conversion);
                conversion.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class Conversion extends Thread{
        protected Socket socket;
        protected int client;
        public Conversion(Socket socket, int client) {
            this.socket = socket;
            this.client = client;
        }

        public void broadcast(String message, Socket socket, int numberOfClient) throws IOException {
            for (Conversion c : clients) {
                PrintWriter printWriter = new PrintWriter(c.socket.getOutputStream(),true);
                if (!(c.socket.getInetAddress() == socket.getInetAddress())){
                    if (clients.indexOf(c)==numberOfClient){
                        printWriter.println(socket.getInetAddress()+" : "+message);
                    }
                }
            }
        }

        public void run() {

            try {

                // Input params 'Listening for any message will be sent
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);

                //Creating an output params
                OutputStream os = socket.getOutputStream();
                PrintWriter pw = new PrintWriter(os,true);

                // Informing the server that a new client is connected
                pw.println("Une connexion est établie avec le client " + client + " et le serveur " + socket.getInetAddress().getHostName());


                while (true){
                    String req = br.readLine();
                    if (req.contains("=>")){
                        String[] reqSplited = req.split("=>");
                        if(reqSplited.length == 2){
                            String message = reqSplited[1];
                            int numberOfClient = Integer.parseInt(reqSplited[0]);
                            broadcast(message,socket,numberOfClient);
                        }
                    }

                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
