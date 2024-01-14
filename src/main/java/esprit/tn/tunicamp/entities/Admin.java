package esprit.tn.tunicamp.entities;

public class Admin extends User {
    public Admin(int id, String username, String password, String url, UserType userType) {
        super(id, username, password, url, userType);
    }
}