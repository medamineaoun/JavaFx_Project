package esprit.tn.tunicamp.entities;

public class User {
    private int id;
    private String username;
    private String password;
    private String url;
    private UserType userType;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public User(int id, String username, String password, String url,UserType userType) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.url = url;
        this.userType = userType;
    }
}