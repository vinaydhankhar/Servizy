package kvnb.hostelservicemanagement;

public class User {

    public String password;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String password) {

        this.password = password;
    }

    public String  getPassword(){
        return this.password;

    }
}
