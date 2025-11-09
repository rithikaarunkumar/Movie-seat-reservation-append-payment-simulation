package MINI;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.*;
import java.util.concurrent.*;

public class Movietheatrefinal extends JFrame  {
	

	// ---- Model ----
	enum Status { AVAILABLE, RESERVED }
	class Seat {
	    int row, col; Status status = Status.AVAILABLE;
	    Seat(int r, int c){ row=r; col=c; }
	    String id(){ return (char)('A'+row)+""+(col+1); }
	}

	// ---- Generic DAO ----
	interface DAO<T>{ void save(T t) throws Exception; }

	// ---- Main App ----

	    java.util.List<Seat> seats = new ArrayList<>();
	    ExecutorService payPool = Executors.newFixedThreadPool(3);
	    Connection conn;
	    JLabel info = new JLabel("Select a seat to book.");
	    JLabel stats = new JLabel();

	    Movietheatrefinal() throws Exception {
	        // DB setup
	        conn = DriverManager.getConnection("jdbc:sqlite:theater.db");
	        conn.createStatement().execute(
	            "CREATE TABLE IF NOT EXISTS bookings(seat TEXT, name TEXT, card TEXT, amount REAL, time TEXT)"
	        );

	        // 4x5 seats
	        for(int r=0;r<4;r++) for(int c=0;c<5;c++) seats.add(new Seat(r,c));

	        // GUI setup
	        setTitle("🎬 Movie Theater Reservation System");
	        setLayout(new BorderLayout());
	        JPanel grid = new JPanel(new GridLayout(4,5,5,5));
	        for(Seat s: seats){
	            JButton b = new JButton(s.id());
	            b.addActionListener(e->bookSeat(s,b));
	            grid.add(b);
	        }
	        add(grid, BorderLayout.CENTER);
	        JPanel bottom = new JPanel(new GridLayout(2,1));
	        bottom.add(info); bottom.add(stats);
	        add(bottom, BorderLayout.SOUTH);

	        updateStats();
	        setSize(500,400);
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	        setVisible(true);
	    }

	    void bookSeat(Seat s, JButton b){
	        if(s.status==Status.RESERVED){ JOptionPane.showMessageDialog(this,"Seat already booked!"); return; }

	        // Payment form
	        JTextField name=new JTextField(), card=new JTextField(), amount=new JTextField("150");
	        Object[] msg={"Name:",name,"Card Number:",card,"Amount:",amount};
	        int opt=JOptionPane.showConfirmDialog(this,msg,"Payment for "+s.id(),JOptionPane.OK_CANCEL_OPTION);
	        if(opt!=JOptionPane.OK_OPTION)return;

	        double payAmt = Double.parseDouble(amount.getText());
	        s.status=Status.RESERVED; b.setBackground(Color.RED);
	        info.setText("Processing payment for "+s.id()+"...");

	        // async payment
	        Future<?> f = payPool.submit(()->{
				try {
					simulatePayment();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
	        new Thread(()->{
	            try{
	                f.get(); new BookingDAO(conn).save(s,name.getText(),card.getText(),payAmt);
	                SwingUtilities.invokeLater(()->{
	                    info.setText("Seat "+s.id()+" booked successfully!");
	                    JOptionPane.showMessageDialog(this,"Booking confirmed for "+s.id());
	                    updateStats();
	                });
	            }catch(Exception ex){
	                s.status=Status.AVAILABLE; b.setBackground(null);
	                SwingUtilities.invokeLater(()->{
	                    info.setText("Payment failed for "+s.id());
	                    JOptionPane.showMessageDialog(this,"Payment failed! Try again.");
	                    updateStats();
	                });
	            }
	        }).start();
	    }

	    void simulatePayment() throws Exception {
	        Thread.sleep(1000+(int)(Math.random()*2000));
	        if(Math.random()<0.2) throw new Exception("Transaction Declined");
	    }

	    void updateStats(){
	        long booked=seats.stream().filter(s->s.status==Status.RESERVED).count();
	        stats.setText("Available: "+(seats.size()-booked)+" | Booked: "+booked);
	    }

	    // ---- DAO Implementation ----
	    static class BookingDAO implements DAO<Seat>{
	        Connection c; BookingDAO(Connection c){ this.c=c; }
	        public void save(Seat s) throws Exception { } // not used directly
	        void save(Seat s,String n,String cr,double amt)throws Exception{
	            PreparedStatement ps=c.prepareStatement(
	                "INSERT INTO bookings VALUES(?,?,?,?,datetime('now'))");
	            ps.setString(1,s.id());
	            ps.setString(2,n); ps.setString(3,cr); ps.setDouble(4,amt);
	            ps.executeUpdate();
	        }
	    }

	    // ---- Main ----
	    public static void main(String[] args)throws Exception{
	        Class.forName("org.sqlite.JDBC");
	        SwingUtilities.invokeLater(()->{
	            try{ new Movietheatrefinal(); }catch(Exception e){ e.printStackTrace(); }
	        });
	    }
	}


