package com.rahi.sqlite_database.POJO_Classes;

public class GetData {

    String name,email,pass,prof;
    int age;

    public GetData(String name, String email, String pass, String prof, int age) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.prof = prof;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
