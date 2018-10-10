package kvnb.hostelservicemanagement;

public class RegisterUser {
private String name;

    private String mail;
    private String phno;
    private String rollno;
    private String roomno;
    private String hno;
    public RegisterUser(){

    }
    public RegisterUser(String name,String mail,String phno,String rollno,String roomno,String hno){
        this.name=name;


        this.mail=mail;
        this.phno=phno;
        this.rollno=rollno;
        this.roomno=roomno;
        this.hno=hno;
    }
    public String getName(){
        return this.name;
    }
    public String getMail(){
        return this.mail;
    }
    public String getPhno(){
        return this.phno;
    }
    public String getRollno(){
        return this.rollno;
    }
    public String getRoomno(){
        return this.roomno;
    }
    public String getHno(){
        return this.hno;
    }


}
