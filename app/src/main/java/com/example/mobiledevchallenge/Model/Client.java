package com.example.mobiledevchallenge.Model;

public class Client {
    private String Names;
    private String Lastnames;
    private int Age;
    private String BirthDate;
    public Client() {
    }

    public String getNames() {
        return Names;
    }

    public void setNames(String names) {
        Names = names;
    }

    public String getLastnames() {
        return Lastnames;
    }

    public void setLastnames(String lastnames) {
        Lastnames = lastnames;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String birthDate) {
        BirthDate = birthDate;
    }
}
