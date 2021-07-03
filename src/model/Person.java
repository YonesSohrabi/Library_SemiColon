package model;

public class Person {
    private String firstName;
    private String lastName;
    private String ID;
    private String codeMeli;
    private String password;
    private String userName;

    public Person(){};
    public Person(String ID,String userName, String password, String firstName, String lastName, String codeMeli) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.codeMeli = codeMeli;
        this.ID = ID;
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

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCodeMeli() {
        return CodeMeli;
    }

    public void setCodeMeli(String codeMeli) {
        CodeMeli = codeMeli;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
