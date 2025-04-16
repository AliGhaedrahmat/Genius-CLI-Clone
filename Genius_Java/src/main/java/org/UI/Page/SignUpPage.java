package org.UI.Page;

import org.Model.*;
import org.Services.AccountManager;
import org.UI.Console.Console;

import java.util.ArrayList;
import java.util.List;

public class SignUpPage extends Page {
    SignUpPage() {
        this.name = "Sign Up";
    }

    AccountRole getRole() {
        String input = Console.getInputBox("" , new String[] {"User" , "Artist"} , "Enter Role number: ");
        return switch (input) {
            case "1" -> AccountRole.USER;
            case "2" -> AccountRole.ARTIST;
            case null, default -> null;
        };
    }

    public static Genre getGenre() {
        List<String> genreNames = new ArrayList<>();
        for (Genre genre : Genre.values()) {
            genreNames.add(genre.name());
        }

        String[] genreArray = genreNames.toArray(new String[0]);
        String input = Console.getInputBox("Select a genre:", genreArray, "Enter your genre number: ");

        try {
            int index = Integer.parseInt(input) - 1;
            if (index >= 0 && index < Genre.values().length) {
                return Genre.values()[index];
            }
        } catch (NumberFormatException e) {
            Console.print("Invalid input. Please enter a number.", Console.Color.RED);
        }

        return null;
    }


    public void getUsername(AccountRole role) {
        String username = Console.getInput("Enter your username: ");

        AccountManager accountManager = new AccountManager();
        boolean accountExists = accountManager.checkAccountExistence(username);

        if (!accountExists) {
            String password = Console.getInput("Enter your password: ");

            Account account = null;

            if (role == AccountRole.USER) {
                account = new User(username , password , username , "");

            }
            else if (role == AccountRole.ARTIST) {
                Genre genre = getGenre();
                account = new Artist(username , password , username , "" , genre);
            }
            else{
                Console.print("Invalid input. Please enter a valid role.");
            }

            if (account != null) {
                accountManager.add(account);
                PageManager.runPage(new HomePageRegistered(account));
            }

        }
        else {
            Console.print("Account already exists!\n");
            displayPage();
        }
    }

    @Override
    public void displayPage() {

        AccountRole role = getRole();

        getUsername(role);
    }
}
