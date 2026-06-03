import java.io.*;
import java.net.*;
import java.util.Set;
import java.util.concurrent.*;

public class ChatServer {
    private static final int PORT = 8080;
    
    // A thread-safe set to hold the output streams of all connected clients.
    // We use this to broadcast messages to everyone.
    private static Set<PrintWriter> clientWriters = ConcurrentHashMap.newKeySet();

    public static void main(String[] args) {
        System.out.println("Chat Server is starting up...");
        
        // A thread pool reuses threads efficiently. A pool of 20 means 20 concurrent users.
        ExecutorService pool = Executors.newFixedThreadPool(20);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

            // Infinite loop to keep accepting new clients
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected from " + clientSocket.getInetAddress());
                
                // Hand the new connection off to a worker thread
                pool.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }

    // This inner class handles the back-and-forth for a single specific user
    private static class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String username;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                // 'in' reads messages from the client. 'out' sends messages to the client.
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Ask the user for their name as soon as they connect
                out.println("Welcome! Please enter your username:");
                username = in.readLine();
                
                // Register the user's output stream so they can receive broadcasted messages
                clientWriters.add(out);
                broadcastMessage("SERVER: " + username + " has joined the chat!");

                // The main listening loop for this user
                String message;
                while ((message = in.readLine()) != null) {
                    broadcastMessage(username + ": " + message);
                }
            } catch (IOException e) {
                System.out.println("Connection issue with " + username + ": " + e.getMessage());
            } finally {
                // Cleanup block: When a user disconnects, remove them and notify others
                if (out != null) {
                    clientWriters.remove(out);
                }
                if (username != null) {
                    System.out.println(username + " disconnected.");
                    broadcastMessage("SERVER: " + username + " has left the chat.");
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Helper method to iterate through all active users and send them a message
        private void broadcastMessage(String message) {
            for (PrintWriter writer : clientWriters) {
                writer.println(message);
            }
        }
    }
}
