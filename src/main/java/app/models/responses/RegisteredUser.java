package app.models.responses;

public class RegisteredUser {
    private String username;
    private String firstName;
    private String lastName;
    private String eMail;
    private String token;
    private String dp;

    public RegisteredUser(String username, String firstName, String lastName, String eMail, String token, String dp) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.token = token;
        this.dp = dp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }
}
