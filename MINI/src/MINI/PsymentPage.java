package MINI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class PsymentPage extends JFrame {



	    private JTextField nameField, cardField, cvvField;
	    private JLabel amountLabel;
	    private String username, seat, movie;
	    private double price;

	    public PsymentPage(String username, String seat, String movie, double price) {
	        this.username = username;
	        this.seat = seat;
	        this.movie = movie;
	        this.price = price;

	        setTitle("Payment Page");
	        setSize(400, 300);
	        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	        setLayout(new GridLayout(6, 2, 10, 10));

	        add(new JLabel("Movie:"));
	        add(new JLabel(movie));

	        add(new JLabel("Seat:"));
	        add(new JLabel(seat));

	        add(new JLabel("Amount (₹):"));
	        amountLabel = new JLabel(String.valueOf(price));
	        add(amountLabel);

	        add(new JLabel("Name on Card:"));
	        nameField = new JTextField();
	        add(nameField);

	        add(new JLabel("Card Number:"));
	        cardField = new JTextField();
	        add(cardField);

	        add(new JLabel("CVV:"));
	        cvvField = new JTextField();
	        add(cvvField);

	        JButton payButton = new JButton("Pay Now");
	        add(new JLabel("")); // empty placeholder
	        add(payButton);

	        payButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                processPayment();
	            }
	        });
	    }

	    private void processPayment() {
	        String name = nameField.getText().trim();
	        String card = cardField.getText().trim();
	        String cvv = cvvField.getText().trim();

	        if (name.isEmpty() || card.isEmpty() || cvv.isEmpty()) {
	            JOptionPane.showMessageDialog(this, "All fields are required!");
	            return;
	        }

	        if (card.length() != 16 || !card.matches("\\d+")) {
	            JOptionPane.showMessageDialog(this, "Invalid card number!");
	            return;
	        }

	        if (cvv.length() != 3 || !cvv.matches("\\d+")) {
	            JOptionPane.showMessageDialog(this, "Invalid CVV!");
	            return;
	        }

	        JOptionPane.showMessageDialog(this, "Payment Successful!\nReservation Confirmed.");

	        // Save reservation to database
	        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:theatre.db")) {
	            String sql = "INSERT INTO reservations (username, seat, movie) VALUES (?, ?, ?)";
	            PreparedStatement ps = conn.prepareStatement(sql);
	            ps.setString(1, username);
	            ps.setString(2, seat);
	            ps.setString(3, movie);
	            ps.executeUpdate();
	            ps.close();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }

	        dispose(); // Close payment window
	    }

	    // Test the page alone
	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(() ->
	            new PsymentPage("rithika", "Seat 1-6", "Inception", 250.0).setVisible(true));
	    }
	}


