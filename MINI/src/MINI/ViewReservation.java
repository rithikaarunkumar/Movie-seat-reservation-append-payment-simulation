package MINI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;


public class ViewReservation extends JFrame{
	

	    JTable table;
	    DefaultTableModel model;

	    public ViewReservation() {
	        setTitle("View Reservations");
	        setSize(500, 300);
	        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

	        model = new DefaultTableModel(new String[]{"Username", "Seat", "Movie"}, 0);
	        table = new JTable(model);
	        add(new JScrollPane(table));

	        loadData();
	    }

	    private void loadData() {
	        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:theatre.db");
	             Statement stmt = conn.createStatement();
	             ResultSet rs = stmt.executeQuery("SELECT * FROM reservations")) {

	            while (rs.next()) {
	                model.addRow(new Object[]{
	                    rs.getString("username"),
	                    rs.getString("seat"),
	                    rs.getString("movie")
	                });
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    
	    }
}


