package org.UI.Page;

import org.Model.Account;
import org.Model.AccountRole;
import org.Model.Album;
import org.Model.Artist;
import org.Model.Song;
import org.Services.AccountManager;
import org.Services.AlbumManager;
import org.Services.SongManager;
import org.UI.Console.Console;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArtistPage extends Page {
    private Artist artist;
    private Account account;

    public ArtistPage(Artist artist, Account account) {
        this.artist = artist;
        this.account = account;
    }

    public void printArtistHeader() {
        Console.print("\n\n", Console.Color.WHITE);
        Console.print("Artist Name: " + artist.getName() + "\n", Console.Color.YELLOW, false);
    }

    public void viewSongs() {
        List<Song> artistSongs = SongManager.getSongsByArtist(artist);
        if (artistSongs.isEmpty()) {
            Console.getInput("\n\nPress Enter to go back...");
            displayPage();
            return;
        }

        Song selectedSong = SongManager.getSongInput(artistSongs);
        if (account != null && account.getId().equals(artist.getId()) && account.getRole() == AccountRole.ARTIST) {
            new SongPageForArtist(artist, selectedSong).displayPage();
        } else {
            new SongPageForUser(selectedSong, account).displayPage();
        }
    }

    public void viewAlbums() {
        List<Album> artistAlbums = AlbumManager.getAlbumsByArtist(artist);
        if (artistAlbums.isEmpty()) {
            Console.getInput("\n\nNo albums found. Press Enter to go back...");
            displayPage();
            return;
        }

        Album selectedAlbum = AlbumManager.getAlbumInput(artistAlbums);
        new AlbumPage(selectedAlbum, account).displayPage();
    }

    public void viewPopularSongs() {
        List<Song> popularSongs = SongManager.getPopularSongsByArtist(artist);
        if (popularSongs.isEmpty()) {
            Console.print("\nNo popular songs found for the artist.\n", Console.Color.RED);
            Console.getInput("\nPress Enter to go back...");
            displayPage();
            return;
        }

        Song selectedSong = SongManager.getSongInput(popularSongs , true);
        if (account != null && account.getId().equals(artist.getId()) && account.getRole() == AccountRole.ARTIST) {
            new SongPageForArtist(artist, selectedSong).displayPage();
        } else {
            new SongPageForUser(selectedSong, account).displayPage();
        }
    }

    public void followPage() {
        if (account == null) {
            Console.print("\nYou must be logged in to follow an artist.", Console.Color.RED);
            Console.getInput("\nPress Enter to go back...");
            displayPage();
            return;
        }

        if (account.getId().equals(artist.getId())) {
            Console.print("\nYou cannot follow yourself.", Console.Color.RED);
            Console.getInput("\nPress Enter to go back...");
            displayPage();
            return;
        }

        boolean isFollowing = artist.getFollowers().contains(account.getId());

        if (isFollowing) {
            String choice = Console.getInputBox("\nYou are currently following this artist.", new String[]{"Unfollow", "Back"}, "Choose an option:");
            if (choice.equals("1")) {
                artist.removeFollower(account.getId());
                Console.print("\nYou have unfollowed the artist.", Console.Color.YELLOW);
            }
        } else {
            String choice = Console.getInputBox("\nYou are not following this artist.", new String[]{"Follow", "Back"}, "Choose an option:");
            if (choice.equals("1")) {
                artist.addFollower(account.getId());
                Console.print("\nYou are now following the artist!", Console.Color.GREEN);
            }
        }

        AccountManager accountManager = new AccountManager();
        accountManager.update(artist);
        Console.getInput("\nPress Enter to go back...");
        displayPage();
    }


    @Override
    public void displayPage() {
        Console.clear();
        printArtistHeader();

        List<String> commands = new ArrayList<>(Arrays.asList(
                "View Songs",
                "View Albums",
                "Popular Songs",
                "Follow / Unfollow This Artist",
                "Main Menu",
                "Exit"
        ));

        String input = Console.getInputBox("\n", commands.toArray(new String[0]), "Choose an option: ");

        switch (input) {
            case "1": {
                viewSongs();
                break;
            }
            case "2": {
                viewAlbums();
                break;
            }
            case "3": {  // Popular Songs Option
                viewPopularSongs();
                break;
            }
            case "4": {
                followPage();
                break;
            }

            case "5":{
                AccountManager accountManager = new AccountManager();
                PageManager.runPage(new HomePageRegistered(account));
            }

            case "6" : {
                System.exit(0);
            }
        }
    }
}
