package edu.skku.cs.final_project;

public class UserModel {
    private String username;
    private String password;

    public UserModel(String initUsername, String initPassword){
        this.username=initUsername;
        this.password=initPassword;
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
}
