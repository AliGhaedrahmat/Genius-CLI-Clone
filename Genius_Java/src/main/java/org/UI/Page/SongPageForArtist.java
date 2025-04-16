package org.UI.Page;

import org.Model.Artist;
import org.Model.Genre;
import org.Model.Song;
import org.Services.AccountManager;
import org.Services.SongManager;
import org.UI.Console.Console;

import java.util.Arrays;
import java.util.List;

public class SongPageForArtist extends Page {

    private final Artist artist;
    private final Song song;
    private final SongManager songManager;

    public SongPageForArtist(Artist artist, Song song) {
        this.artist = artist;
        this.song = song;
        this.songManager = new SongManager();
    }

    private void printHeader() {
        Console.print("\n\n    Song: " + song.getName(), Console.Color.LIGHT_YELLOW);
        Console.print("    Genre: " + song.getGenre(), Console.Color.LIGHT_CYAN);
    }

    private void editLyrics() {
        Console.clear();
        printHeader();
        Console.print("\n\nCurrent Lyrics:\n\n", Console.Color.LIGHT_RED);
        Console.print(song.getLyrics(), Console.Color.WHITE);
        String newLyrics = Console.getInput("\n\nEnter new lyrics: ");
        songManager.setLyrics(song, newLyrics);
        Console.print("\nLyrics updated!\n", Console.Color.GREEN);
        Console.getInput("\nPress Enter to continue...");
        displayPage();
    }

    private void changeGenre() {
        Console.clear();
        printHeader();
        Console.print("\n\nAvailable Genres:\n\n", Console.Color.LIGHT_BLUE);
        Genre[] genres = Genre.values();
        for (int i = 0; i < genres.length; i++) {
            Console.commandLabel(genres[i].toString(), i + 1);
        }

        String input = Console.getInput("\nChoose new genre: ");
        try {
            int index = Integer.parseInt(input) - 1;
            if (index >= 0 && index < genres.length) {
                songManager.setGenre(song, genres[index]);
                Console.print("\nGenre updated to " + genres[index] + "!\n", Console.Color.GREEN);
            } else {
                Console.print("\nInvalid choice!\n", Console.Color.RED);
            }
        } catch (NumberFormatException e) {
            Console.print("\nInvalid input!\n", Console.Color.RED);
        }

        Console.getInput("\nPress Enter to continue...");
        displayPage();
    }

    private void viewStats() {
        Console.clear();
        printHeader();
        Console.print("\n\nSong Stats:\n", Console.Color.YELLOW);
        Console.print("    Likes: " + song.getLikesCount() + "\n", Console.Color.GREEN, false);
        Console.print("    Dislikes: " + song.getDislikesCount() + "\n", Console.Color.RED, false);
        Console.print("    Views: " + song.getViewsCount() + "\n", Console.Color.CYAN, false);
        Console.getInput("\nPress Enter to continue...");
        displayPage();
    }

    private void deleteSong() {
        Console.print("\nAre you sure you want to delete this song? (yes/no): ", Console.Color.LIGHT_RED);
        String input = Console.getInput();
        if (input.equalsIgnoreCase("yes")) {
            songManager.delete(song);
            Console.print("\nSong deleted successfully!\n", Console.Color.RED);
            Console.getInput("\nPress Enter to return...");
            PageManager.runPage(new ArtistPanel(artist));
        } else {
            Console.print("\nCancelled.\n", Console.Color.YELLOW);
            Console.getInput("\nPress Enter to continue...");
            displayPage();
        }
    }

    @Override
    public void displayPage() {
        Console.clear();
        printHeader();

        List<String> commands = Arrays.asList(
                "Edit Lyrics",
                "Change Genre",
                "View Stats",
                "Delete Song",
                "Main Menu" ,
                "Exit"
        );

        String input = Console.getInputBox("\n", commands.toArray(new String[0]), "Choose an option: ");

        switch (input) {
            case "1":
                editLyrics();
                break;
            case "2":
                changeGenre();
                break;
            case "3":
                viewStats();
                break;
            case "4":
                deleteSong();
                break;

            case "5":{
                AccountManager accountManager = new AccountManager();
                PageManager.runPage(new HomePageRegistered(accountManager.getByUUID(artist.getId())));
            }

            case "6" : {
                System.exit(0);
            }
            default:
                displayPage(); // Refresh on invalid input
        }
    }
}
