package MINI;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;




public class CancelReservation extends JFrame {
	
	    public CancelReservation() {
	        setTitle("Cancel Reservation");
	        setSize(400, 200);
	        setLayout(new FlowLayout());
	        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

	        JTextField nameField = new JTextField(15);
	        JButton cancelBtn = new JButton("Cancel Booking");

	        add(new JLabel("Enter your name:"));
	        add(nameField);
	        add(cancelBtn);

	        cancelBtn.addActionListener(e -> {
	            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:theatre.db")) {
	                PreparedStatement ps = conn.prepareStatement("DELETE FROM reservations WHERE username = ?");
	                ps.setString(1, nameField.getText());
	                int rows = ps.executeUpdate();
	                if (rows > 0)
	                    JOptionPane.showMessageDialog(this, "Reservation canceled!");
	                else
	                    JOptionPane.showMessageDialog(this, "No record found.");
	            } catch (Exception ex) {
	                ex.printStackTrace();
	            }
	        });
	    }
	}


