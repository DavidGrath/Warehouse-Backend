package app.models.responses;

public class PersonResponse {
    String username;
    String firstName;
    String lastName;
    String eMail;
    String displayPictureUrl;

    public PersonResponse(String username, String firstName, String lastName, String eMail, String displayPictureUrl) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.displayPictureUrl = displayPictureUrl;
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

    public String getDisplayPictureUrl() {
        return displayPictureUrl;
    }

    public void setDisplayPictureUrl(String displayPictureUrl) {
        this.displayPictureUrl = displayPictureUrl;
    }
}
