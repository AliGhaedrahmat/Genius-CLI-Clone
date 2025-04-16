package org.Model;

import java.util.UUID;

public class Account implements Identifiable {
    UUID id;
    String username;
    String password;
    String name;
    String bio;
    AccountRole role;

    public Account(String username , String password , AccountRole role , String name , String bio) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.bio = bio;
    }

    public UUID getId() {
        return id;
    }

    public AccountRole getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }


}
