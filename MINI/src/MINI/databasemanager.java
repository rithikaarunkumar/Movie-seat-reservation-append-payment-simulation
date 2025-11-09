package MINI;
import java.sql.*;

public class databasemanager {
	// DatabaseManager.java



	    private static final String URL = "jdbc:sqlite:theatre.db";

	    public static Connection connect() throws SQLException {
	        return DriverManager.getConnection(URL);
	    }

	    public static void init() {
	        try (Connection conn = connect();
	             Statement stmt = conn.createStatement()) {
	            stmt.execute("""
	                CREATE TABLE IF NOT EXISTS reservations (
	                    id INTEGER PRIMARY KEY AUTOINCREMENT,
	                    username TEXT,
	                    movie TEXT,
	                    seat TEXT,
	                    payment_status TEXT
	                )
	            """);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    public static void saveReservation(String user, String movie, String seat, String status) {
	        String sql = "INSERT INTO reservations(username, movie, seat, payment_status) VALUES (?, ?, ?, ?)";
	        try (Connection conn = connect();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	            ps.setString(1, user);
	            ps.setString(2, movie);
	            ps.setString(3, seat);
	            ps.setString(4, status);
	            ps.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

	


