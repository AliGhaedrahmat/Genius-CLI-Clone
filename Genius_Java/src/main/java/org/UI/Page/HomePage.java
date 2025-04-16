package org.UI.Page;

import org.UI.Console.Console;

public class HomePage extends Page {

    public HomePage() {
        this.name = this.getClass().getName();
    }

    public static void displayMainMenu(){
        String input = Console.getInputBox("" , new String[]{"Sign Up", "Login", "Exit"}, "Enter your choice: ");
        switch (input) {
            case "1": {
                PageManager.runPage(new SignUpPage());
                break;
            }
            case "2": {
                PageManager.runPage(new LogInPage());
                break;
            }
            case "3": {
                System.exit(0);
            }
        }
    }


    @Override
    public void displayPage() {
        displayMainMenu();
    }
}
