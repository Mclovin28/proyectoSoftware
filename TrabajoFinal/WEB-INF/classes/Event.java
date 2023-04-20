public class Event {
    private String title;
    private String start;
    private String end;
    private String userId; // Add this line to include the user ID field

    public Event(String title, String start, String end, String userId) { // Update the constructor
        this.title = title;
        this.start = start;
        this.end = end;
        this.userId = userId; // Add this line to set the user ID
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
	public String getUserId() {
        return userId;
    }
}