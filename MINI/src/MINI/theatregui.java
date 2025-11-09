package MINI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class theatregui extends JFrame {// TheatreGUI.java
	

	    private JComboBox<movie> movieBox;
	    private JButton reserveButton;

	    public theatregui() {
	        setTitle("Movie Reservation System");
	        setSize(400, 200);
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	        setLayout(new FlowLayout());

	        movieBox = new JComboBox<>(new movie[]{
	            new movie("F1", 12.5, 148),
	            new movie("VTV", 14.0, 169),
	            new movie("Lokah",14.0,169),
	            new movie("kantara",14.0,169)
	        });
	        

	        reserveButton = new JButton("Reserve Seat");
	        reserveButton.addActionListener(e -> reserve());

	        add(new JLabel("Select a Movie:"));
	        add(movieBox);
	        add(reserveButton);
	    }

	    private void reserve() {
	        movie movie = (movie) movieBox.getSelectedItem();
	        String username = JOptionPane.showInputDialog("Enter your name:");
	        seat seat = new seat(1, (int)(Math.random() * 10 + 1));

	        new Thread(new reservationtask(seat, username)).start();

	        String response = client.sendReservation(username + " - " + movie.getTitle());
	        JOptionPane.showMessageDialog(this, response);
	    }

	    public static void main(String[] args) {
	        databasemanager.init();
	        SwingUtilities.invokeLater(() -> new theatregui().setVisible(true));
	    }
	}



