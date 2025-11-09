package MINI;

public class seat {

	    private int row;
	    private int number;
	    private boolean reserved;

	    public seat(int row, int number) {
	        this.row = row;
	        this.number = number;
	        this.reserved = false;
	    }

	    public synchronized boolean reserve() {
	        if (!reserved) {
	            reserved = true;
	            return true;
	        }
	        return false;
	    }

	    public boolean isReserved() { return reserved; }
	    public String toString() { return "Seat " + row + "-" + number; }
	}



