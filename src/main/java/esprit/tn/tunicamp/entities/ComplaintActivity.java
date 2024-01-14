package esprit.tn.tunicamp.entities;

public class ComplaintActivity extends Activity {
    private String status;

    public ComplaintActivity(int id, int creator, String name, String dateTime, String description, int maxPeople, String status) {
        super(id, creator, name, dateTime, description, maxPeople);
        this.status = status;
    }

    // Getter and setter for the status attribute
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
