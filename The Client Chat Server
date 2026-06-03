import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    private static final String SERVER_ADDRESS = "127.0.0.1"; // Connects to localhost
    private static final int SERVER_PORT = 8080;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            System.out.println("Connected to the chat server!");

            // Streams for reading from and writing to the server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner consoleScanner = new Scanner(System.in);

            // Create a dedicated background thread to listen for incoming messages
            Thread listenerThread = new Thread(() -> {
                try {
                    String incomingMessage;
                    // As long as the server keeps sending messages, print them
                    while ((incomingMessage = in.readLine()) != null) {
                        System.out.println(incomingMessage);
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected from server.");
                }
            });
            // Start the listening thread
            listenerThread.start();

            // The main thread is dedicated to capturing what YOU type and sending it
            while (true) {
                String userMessage = consoleScanner.nextLine();
                
                if (userMessage.equalsIgnoreCase("/quit")) {
                    System.out.println("Exiting chat...");
                    break;
                }
                
                out.println(userMessage);
            }

        } catch (IOException e) {
            System.err.println("Could not connect to the server: " + e.getMessage());
        }
    }
}
