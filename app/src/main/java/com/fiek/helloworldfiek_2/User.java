package com.fiek.helloworldfiek_2;

public class User {
    private int Id;
    private String Name;
    private String Username;
    private String Address;

    public User(int id, String name, String username, String address) {
        Id = id;
        Name = name;
        Username = username;
        Address = address;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
