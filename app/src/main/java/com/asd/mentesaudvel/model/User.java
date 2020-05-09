package com.asd.mentesaudvel.model;

public class User {

    private int id;
    private String name, email, gender,password,age;

    public User(int id, String name, String email, String gender, String age, String password) {
        this.id       = id;
        this.name     = name;
        this.email    = email;
        this.gender   = gender;
        this.age      = age;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age){
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
