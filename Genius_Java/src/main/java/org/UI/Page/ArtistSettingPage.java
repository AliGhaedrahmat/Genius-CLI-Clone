package org.UI.Page;

import org.Model.Artist;
import org.Model.Genre;
import org.Services.AccountManager;
import org.UI.Console.Console;

public class ArtistSettingPage extends Page {
    Artist artist;

    public ArtistSettingPage(Artist artist) {
        this.artist = artist;
    }

    public void saveChanges(Artist artist){
        // Save changes
        AccountManager manager = new AccountManager();
        manager.update(artist);

        Console.print("\nChanges saved successfully!\n", Console.Color.GREEN);
        Console.getInput("Press Enter to continue...");
    }

    @Override
    public void displayPage() {
        while (true) {
            Console.clear();
            Console.print("Artist Settings\n\n", Console.Color.LIGHT_BLUE);
            Console.print("Current Info:\n", Console.Color.YELLOW);
            Console.print("Name: " + artist.getName() + "\n", Console.Color.WHITE);
            Console.print("Bio: " + artist.getBio() + "\n", Console.Color.WHITE);
            Console.print("Genre: " + artist.getGenre() + "\n\n", Console.Color.WHITE);

            String[] options = {
                    "Change Name",
                    "Change Bio",
                    "Change Genre" ,
                    "Go Back",
                    "Exit "
            };

            String input = Console.getInputBox("What would you like to change?", options, "Choose an option:");

            switch (input) {
                case "1":
                    String newName = Console.getInput("Enter new name:");
                    artist.setName(newName);
                    saveChanges(artist);
                    break;
                case "2":
                    String newBio = Console.getInput("Enter new bio:");
                    artist.setBio(newBio);
                    saveChanges(artist);
                    break;
                case "3":
                    Console.clear();
                    Genre genre = SignUpPage.getGenre();
                    artist.setGenre(genre);
                    saveChanges(artist);
                    break;
                case "4":
                    PageManager.runPage(new ArtistPanel(artist));

                case "5":
                    System.exit(0);
                    break;
            }


        }
    }
}
