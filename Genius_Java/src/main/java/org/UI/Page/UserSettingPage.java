package org.UI.Page;

import org.Model.Account;
import org.Model.Artist;
import org.Model.Genre;
import org.Model.User;
import org.Services.AccountManager;
import org.UI.Console.Console;

public class UserSettingPage extends Page {
    User user;

    public UserSettingPage(User user) {
        this.user = user;
    }

    public void saveChanges(User user){
        // Save changes
        AccountManager manager = new AccountManager();
        manager.update(user);

        Console.print("\nChanges saved successfully!\n", Console.Color.GREEN);
        Console.getInput("Press Enter to continue...");
    }

    @Override
    public void displayPage() {
        while (true) {
            Console.clear();
            Console.print("User Settings\n\n", Console.Color.LIGHT_BLUE);
            Console.print("Current Info:\n", Console.Color.YELLOW);
            Console.print("Name: " + user.getName() + "\n", Console.Color.WHITE);
            Console.print("Bio: " + user.getBio() + "\n", Console.Color.WHITE);

            String[] options = {
                    "Change Name",
                    "Change Bio",
                    "Go Back",
                    "Exit "
            };

            String input = Console.getInputBox("What would you like to change?", options, "Choose an option:");

            switch (input) {
                case "1":
                    String newName = Console.getInput("Enter new name:");
                    user.setName(newName);
                    saveChanges(user);
                    break;
                case "2":
                    String newBio = Console.getInput("Enter new bio:");
                    user.setBio(newBio);
                    saveChanges(user);
                    break;
                case "3":
                    AccountManager accountManager = new AccountManager();
                    Account account = accountManager.getByUUID(user.getId());
                    PageManager.runPage(new HomePageRegistered(account));

                case "4":
                    System.exit(0);
                    break;
            }


        }
    }
}
