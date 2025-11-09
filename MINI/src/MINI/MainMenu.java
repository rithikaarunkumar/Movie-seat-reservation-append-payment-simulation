package MINI;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;


public class MainMenu extends JFrame {
	    public MainMenu() {
	        setTitle("Theatre Reservation System");
	        setSize(400, 300);
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	        setLayout(new GridLayout(4, 1, 10, 10));

	        JButton reserveBtn = new JButton("Reserve Seat");
	        JButton viewBtn = new JButton("View Reservations");
	        JButton cancelBtn = new JButton("Cancel Reservation");
	        JButton exitBtn = new JButton("Exit");

	        reserveBtn.addActionListener(e -> new theatregui().setVisible(true));
	        viewBtn.addActionListener(e -> new ViewReservation().setVisible(true));
	        cancelBtn.addActionListener(e -> new CancelReservation().setVisible(true));
	        exitBtn.addActionListener(e -> System.exit(0));

	        add(reserveBtn);
	        add(viewBtn);
	        add(cancelBtn);
	        add(exitBtn);
	    }

	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> new MainMenu().setVisible(true));
	    }
	}


