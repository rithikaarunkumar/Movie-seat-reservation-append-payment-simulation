package MINI;


public class reservationtask implements Runnable{
	// ReservationTask.java

	    private seat seat;
	    private String user;

	    public reservationtask(seat seat, String user) {
	        this.seat = seat;
	        this.user = user;
	    }

	    @Override
	    public void run() {
	        if (seat.reserve()) {
	            System.out.println(user + " reserved " + seat);
	            databasemanager.saveReservation(user, "Inception", seat.toString(), "Paid");
	        } else {
	            System.out.println("Seat " + seat + " already reserved.");
	        }
	    }
	}



