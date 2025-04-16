package org.Model;

public class Admin extends Account{

    public Admin(String username, String password, String name, String bio) {
        super(username, password, AccountRole.ADMIN, name, bio);
    }
}
