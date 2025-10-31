package com.prevence.javachatapp.GUI;

import com.prevence.javachatapp.client.ChatClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatClientGUI extends JFrame {
    private JTextArea messageArea;
    private JTextField textField;
    private JButton exitButton;
    private ChatClient client;

    public ChatClientGUI() {
        super ("Application de Chat");
        setSize(400,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        String name = JOptionPane.showInputDialog(this, "Votre pseudo : ", "Nom entré", JOptionPane.PLAIN_MESSAGE);
        this.setTitle("Application de Chat - " + name);
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        add(new JScrollPane(messageArea), BorderLayout.CENTER);
        // Zone de texte
        textField = new JTextField();
        textField.addActionListener(e -> {
            String message = "[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] " + name + ": " + textField.getText();
            client.sendMessage(message);
            textField.setText("");
        });
        // Bouton de sortie du chat
        exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            // Message de départ
            String departMessage = name + " a quitté le chat.";
            client.sendMessage(departMessage);
            try{
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
            System.exit(0);
        });

        JPanel bottomPanel = new JPanel(new BorderLayout());

        bottomPanel.add(textField, BorderLayout.CENTER);
        bottomPanel.add(exitButton, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        // Initialisation et début du ChatClient
        try {
            this.client = new ChatClient("127.0.0.1", 5000, this::onMessageReceived);
            client.startClient();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur de connexion au serveur", "Erreur de connexion", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void onMessageReceived(String message) {
        SwingUtilities.invokeLater(() -> messageArea.append(message + "/n"));
    }

    public static void main (String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ChatClientGUI().setVisible(true);
        });
    }
}
