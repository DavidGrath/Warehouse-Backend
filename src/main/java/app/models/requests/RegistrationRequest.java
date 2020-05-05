package app.models.requests;

import org.springframework.web.multipart.MultipartFile;

public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String eMail;
    private String username;
    private String password;
    private MultipartFile displayPicture;

    public RegistrationRequest(String firstName, String lastName, String eMail, String username, String password, MultipartFile displayPicture) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.username = username;
        this.password = password;
        this.displayPicture = displayPicture;
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

    public MultipartFile getDisplayPicture() {
        return displayPicture;
    }

    public void setDisplayPicture(MultipartFile displayPicture) {
        this.displayPicture = displayPicture;
    }
}