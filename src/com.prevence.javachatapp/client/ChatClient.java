package com.prevence.javachatapp.client;

import java.io.*;
import java.net.*;

public class ChatClient {
    private Socket socket = null;
    private BufferedReader inputConsole = null;
    private  PrintWriter out = null;
    private BufferedReader in = null;

    public ChatClient(String address, int port) {
        try {
            socket = new Socket(address, port);
            System.out.println("Connexion au serveur du chat");

            inputConsole = new BufferedReader(new InputStreamReader(System.in));
            out = new PrintWriter(socket.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String line = "";
            while (!line.equals("exit")) {
                line = inputConsole.readLine();
                out.println(line);
                System.out.println(in.readLine());
            }
            socket.close();
            inputConsole.close();
            out.close();
        } catch (UnknownHostException u) {
            System.out.println("How unknown : " + u.getMessage());
        } catch (IOException i) {
            System.out.println("Exception inattendu : " + i.getMessage());
        }
    }

    public static void main(String[] args) {
        // Appelle le constructeur qui gère la communication
        ChatClient client = new ChatClient("127.0.0.1", 5000); // Connect to server on port 5000
    }
}