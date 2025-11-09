package MINI;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;



public class Server {
	


	    private static final int PORT = 5000;
	    private static ExecutorService pool = Executors.newFixedThreadPool(5);

	    public static void main(String[] args) throws IOException {
	        ServerSocket listener = new ServerSocket(PORT);
	        System.out.println("Server running on port " + PORT);
	        while (true) {
	            Socket client = listener.accept();
	            pool.execute(() -> handleClient(client));
	        }
	    }

	    private static void handleClient(Socket socket) {
	        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
	            String request = in.readLine();
	            System.out.println("Received: " + request);
	            out.println("Reservation confirmed for " + request);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}

	


