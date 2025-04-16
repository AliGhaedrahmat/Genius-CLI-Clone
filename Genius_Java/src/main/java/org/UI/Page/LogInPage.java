package org.UI.Page;

import org.Model.Account;
import org.Model.User;
import org.Services.AccountManager;
import org.Services.Manager;
import org.UI.Console.Console;

import java.util.UUID;

public class LogInPage extends Page {

    public LogInPage() {
        this.name = this.getClass().getName();
    }

    void getPassword(AccountManager accountManager , String username) {
        String password = Console.getInput("Enter your password: ");

        Account account = null;
        UUID uuid = accountManager.findUUIDByUsername(username);
        String correctPassword = accountManager.findPasswordByUUID(uuid);
        if (password.equals(correctPassword)) {
            account = accountManager.getByUUID(uuid);
        }
        else {
            Console.print("Wrong Password!\n");
            getPassword(accountManager , username);
        }

        PageManager.runPage(new HomePageRegistered(account));
    }


    @Override
    public void displayPage() {
        String username = Console.getInput("Enter your username: ");

        AccountManager accountManager = new AccountManager();

        boolean accountExists = accountManager.checkAccountExistence(username);

        if (accountExists) {
            getPassword(accountManager , username);
        }
        else {
            Console.print("Account doesn't exist! Sign up first!\n");
            HomePage.displayMainMenu();

        }
    }
}