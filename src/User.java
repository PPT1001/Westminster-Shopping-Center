import java.io.Serializable;

public class User implements Serializable{
    private String username;
    private String password;
    private int numTimesPurchased = 0;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean UserLogin(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public String getUsername() {
        return username;
    }

    public String setUsername(String username) {
        return this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String setPassword(String password) {
        return this.password = password;
    }

    public int getNumTimesPurchased() {
        return numTimesPurchased;
    }

    public void setNumTimesPurchased(int numTimesPurchased) {
        this.numTimesPurchased = numTimesPurchased;
    }
}
