package MINI;
import java.io.*;
import java.net.*;

public class client {

	
	    public static String sendReservation(String reservationDetails) {
	        try (Socket socket = new Socket("localhost", 5000);
	             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
	            out.println(reservationDetails);
	            return in.readLine();
	        } catch (IOException e) {
	            e.printStackTrace();
	            return "Error connecting to server.";
	        }
	    }
	}



