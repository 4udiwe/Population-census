package com.example.populationcensus;

import java.io.Serializable;

public class User implements Serializable {
    String id, email, firstname, lastname, city, sex;
    int age;

    public User(){

    }
    public User(String id, String email, String firstname, String lastname, int age, String city, String sex) {
        this.id = id;
        this.email = email.replace(".", "(dot)");
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.city = city;
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", age='" + age + '\'' +
                ", city='" + city + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }

    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getEmail() {
        return email.replace("(dot)", ".");
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
