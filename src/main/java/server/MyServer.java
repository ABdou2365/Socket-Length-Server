package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(99);

        System.out.println("I'm listening on a port 99 ....");

        Socket s = ss.accept();

        System.out.println("Connected to the client " + s.getRemoteSocketAddress());

        InputStream is = s.getInputStream();
        OutputStream os = s.getOutputStream();

        System.out.println("I'm waiting for the client to send a bit");

        int nbr = is.read();

        System.out.println("I got the number " + nbr);

        int req = nbr * 2;

        System.out.println("The response was sent to the client");

        os.write(req);

        System.out.println("The connection is closed");

        os.close();


    }
}