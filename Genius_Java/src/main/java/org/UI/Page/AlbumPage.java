package org.UI.Page;
import org.Model.*;
import org.Services.AccountManager;
import org.Services.AlbumManager;
import org.Services.SongManager;
import org.UI.Console.Console;
import org.database.JsonManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class AlbumPage extends Page {
    private  Album album;
    private  Account account;
    private  AlbumManager albumManager;

    public AlbumPage(Album album, Account account) {
        this.album = album;
        this.account = account;
        this.albumManager = new AlbumManager();
    }

    public void printAlbumHeader() {
        String text = "\n\n    ";
        text += "Album Name: " + album.getName() + "\n    ";
        text += "Artist: " + albumManager.getArtistAccount(album).getName() + "\n    ";
        text += "Genre: " + album.getGenre().toString() + "\n    ";
        Console.print(text, Console.Color.LIGHT_YELLOW, false);
    }

    public void showSongsInAlbum() {
        Console.clear();
        printAlbumHeader();
        List<Song> songs = albumManager.getSongs(album);
        Console.print("\n    Songs in this album:\n\n", Console.Color.LIGHT_BLUE);

//        List<Song> artistSongs = SongManager.getSongsByArtist(artist);

        if (songs.isEmpty()) {
            Console.print("No songs found for this album.", Console.Color.RED);
            return;
        }

        Song selectedSong = SongManager.getSongInput(songs);
        Console.clear();

        PageManager.runPage(new SongPageForUser(selectedSong , account));
    }

    @Override
    public void displayPage() {
        Console.clear();
        printAlbumHeader();

        List<String> commandsList = new ArrayList<>(Arrays.asList(
                "View Songs",
                "Go To Artist Page",
                "Main Menu",
                "Exit"
        ));

        // Show artist-specific option only if this account is the album's artist
        if (account != null && account.getRole() == AccountRole.ARTIST
                && album.getArtistId().equals(account.getId())) {
            commandsList.add("Enter Artist Menu For This Album (Only for Artist)");
        }

        String[] commands = commandsList.toArray(new String[0]);
        String input = Console.getInputBox("\n", commands, "Choose an option: ");

        switch (input) {
            case "1": {
                showSongsInAlbum();
                break;
            }
            case "2": {
                AlbumManager albumManager = new AlbumManager();
                Account artistAccount = albumManager.getArtistAccount(album);
                Artist artist = JsonManager.getArtistByAccount(artistAccount);
                PageManager.runPage(new ArtistPage(artist , account));
                break;
            }
            case "3": {
                UUID artistId= album.getArtistId();
                AccountManager accountManager = new AccountManager();
                Account artistAccount = accountManager.getByUUID(artistId);
                Artist artist = JsonManager.getArtistByAccount(artistAccount);
                PageManager.runPage(new ArtistPage(artist , account));
                break;
            }
            case "4": {
                System.exit(0);
            }

        }
    }
}
