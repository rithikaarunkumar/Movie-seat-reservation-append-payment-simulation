package MINI;

public class movie {
	
	
	    private String title;
	    private double price;
	    private int durationMinutes;

	    public movie(String title, double price, int durationMinutes) {
	        this.title = title;
	        this.price = price;
	        this.durationMinutes = durationMinutes;
	    }

	    public String getTitle() { return title; }
	    public double getPrice() { return price; }

	    @Override
	    public String toString() {
	        return title + " - $" + price;
	    }
	}

	


