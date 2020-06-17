package echomachine.com.flickrapi_v1.pojo;

public class UserRegister {
    private String username;
    private String email;
    private String phone;

    public UserRegister() {
    }

    public UserRegister(String username, String email, String phone) {
        this.username = username;
        this.email = email;
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
