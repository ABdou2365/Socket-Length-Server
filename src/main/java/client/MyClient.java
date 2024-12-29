package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class MyClient {

    public static void main(String[] args) throws IOException {

        System.out.println("Client starting...");

        Socket socket = new Socket("localhost",21);

        System.out.println("I'm connected to the server");

        InputStream is = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        OutputStream os = socket.getOutputStream();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter a String: ");

        PrintWriter writer = new PrintWriter(os,true);
        String string = scanner.nextLine();
        writer.println(string);

        System.out.println("String sent.");

        System.out.println(br.readLine());
        System.out.println(br.readLine());


    }
}
