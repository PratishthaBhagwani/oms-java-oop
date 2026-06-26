package com.oms.model.user;

public abstract class User {
    private String userId;
    private String name;
    private String email;

    public User(String userId, String name, String email){
        this.userId=userId;
        this.name=name;
        this.email=email;
    }
   public abstract String getRole();
    public String getUserId(){ return  userId; }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
    @Override
    public String toString(){
        return "["+ getRole()+"] " + name +" (" + email + ") | ID: " + userId;
    }
}
