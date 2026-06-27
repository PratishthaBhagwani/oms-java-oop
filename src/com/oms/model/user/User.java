package com.oms.model.user;

public abstract class User {
    private String userId;
    private String name;
    private String email;
    private String passwordHash;

    public User(String userId, String name, String email){
        this.userId=userId;
        this.name=name;
        this.email=email;
        this.passwordHash = "default123";
    }
   public abstract String getRole();
    public String getUserId(){ return  userId; }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
    public String getPasswordHash() { return passwordHash; }
    @Override
    public String toString(){
        return "["+ getRole()+"] " + name +" (" + email + ") | ID: " + userId;
    }
}
