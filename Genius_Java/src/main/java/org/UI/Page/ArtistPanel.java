package org.UI.Page;

import org.Model.*;
import org.Services.AccountManager;
import org.Services.AlbumManager;
import org.Services.SongManager;
import org.UI.Console.Console;
import org.database.JsonManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArtistPanel extends Page {
    Artist artist;

    public ArtistPanel(Artist artist) {
        this.name = "Artist Panel";
        this.artist = artist;
    }

    private void printSongDetailWhileCreating(String songDetailString) {
        Console.clear();
        Console.print(songDetailString);
    }

    public void createSongPage(Account artist) {
        String songDetailString = "Entering new song information ... \n";
        printSongDetailWhileCreating(songDetailString);
        String songName = Console.getInput("\nEnter Song Name: ");
        songDetailString += "\n Song Name: " + songName;
        printSongDetailWhileCreating(songDetailString);
        Genre songGenre = SignUpPage.getGenre();
        songDetailString += "\n Song Genre: " + songGenre.toString();
        printSongDetailWhileCreating(songDetailString);
        String lyrics = Console.getInput("\nEnter Lyrics: ");
        songDetailString += "\n Lyrics: " + lyrics;
        printSongDetailWhileCreating(songDetailString);

        Song newlyCreatedSong = new Song((Artist) artist , songName, songGenre, lyrics);

        SongManager songManager = new SongManager();
        songManager.add(newlyCreatedSong);

        Console.clear();
        Console.print(songDetailString);
        Console.print("\n\nSong saved successfully .. ");
        Console.getInput("\nPress Enter to continue...\n: ");

        PageManager.runPage(this);
    }

    public void artistSongsPage(Artist artist) {
        List<Song> artistSongs = SongManager.getSongsByArtist(artist);

        if (artistSongs.isEmpty()) {
            Console.print("No songs found for this artist.", Console.Color.RED);
            return;
        }

        Song selectedSong = SongManager.getSongInput(artistSongs);
        Console.clear();
        PageManager.runPage(new SongPageForUser(selectedSong , artist));
    }

    public void createAlbumPage(Artist artist) {
        Console.clear();
        String albumName = Console.getInput("Enter Album Name: ");
        Genre genre = SignUpPage.getGenre();
        Album album = new Album(albumName, genre, artist);

        // Optionally add songs
        List<Song> availableSongs = SongManager.getSongsByArtist(artist);
        if (availableSongs.isEmpty()) {
            Console.print("You don't have any songs to add yet.", Console.Color.YELLOW);
        } else {
            Console.print("Select songs to add to this album:");
            while (true) {
                Song selected = SongManager.getSongInput(availableSongs);
                if (selected == null) break;

                new AlbumManager().addSongToAlbum(album, selected);
                Console.print("\nSong added! Add another? (y/n)");
                String again = Console.getInput(": ");
                if (!again.equalsIgnoreCase("y")) break;
            }
        }

        new AlbumManager().createAlbum(album);
        Console.getInput("\nPress Enter to return...");
        PageManager.runPage(this);
    }

    public void artistAlbumsPage(Artist artist) {
        List <Album> myAlbums = AlbumManager.getAlbumsByArtist(artist);

        if (myAlbums.isEmpty()) {
            Console.print("You don't have any albums yet.", Console.Color.YELLOW);
            return;
        }

        Console.clear();
        Console.print("Your Albums:" , Console.Color.LIGHT_BLUE);
        for (int i = 0; i < myAlbums.size(); i++) {
            Console.print("\n     " + (i + 1) + ". " + myAlbums.get(i).getName());
        }

        int choice = Integer.parseInt(Console.getInput("\n\nSelect an album to view songs (0 to cancel): "));
        if (choice == 0) return;

        Album selected = myAlbums.get(choice - 1);

        PageManager.runPage(new AlbumPage(selected, this.artist));

        Console.getInput("Press Enter to return...");
        PageManager.runPage(this);
    }

    public void unapprovedEditsPage(Artist artist) {
        AccountManager accountManager = new AccountManager();
        // Get all unapproved edits for the artist's songs
        List<Edit> unapprovedEdits = AccountManager.getUnapprovedEditsByArtist(artist);

        // If there are no unapproved edits, inform the artist
        if (unapprovedEdits.isEmpty()) {
            Console.print("There are no unapproved edits for your songs.", Console.Color.GREEN);
            Console.getInput("\nPress Enter to return...");
            PageManager.runPage(this);
            return;
        }

        // Show unapproved edits
        Console.clear();
        Console.print("Unapproved Edits:", Console.Color.CYAN);

        // Display each unapproved edit with options to approve or reject
        for (int i = 0; i < unapprovedEdits.size(); i++) {
            Edit edit = unapprovedEdits.get(i);
            Song song = SongManager.getSongById(edit.getSongId());
            String songName = song.getName();
            String description = edit.getDescription();
            String oldLyrics = edit.getOldLyrics();
            String newLyrics = edit.getNewLyrics();
            Date releaseDate = edit.getReleaseDate();

            Console.print("\n\n" + (i + 1) + ". Song: " + songName);
            Console.print("\n   Description: " + description);
            Console.print("\n   Release Date: " + releaseDate);
            Console.print("\n   Old Lyrics: " + oldLyrics);
            Console.print("\n   New Lyrics: " + newLyrics);

            // Ask if the artist wants to approve or reject this edit
            String action = Console.getInput("Approve (a) / Reject (r) / Skip (s): ");
            switch (action.toLowerCase()) {
                case "a": {
                    // Approve the edit
                    edit.setApproved(true);
                    User user = JsonManager.getUserByAccount(accountManager.getByUUID(edit.getAuthorId()));
                    user.updateEdit(edit);
                    accountManager.update(user);
                    Console.print("The edit has been approved.", Console.Color.GREEN);
                    break;
                }
                case "r": {
                    // Reject the edit (deletes it)

                    User user = JsonManager.getUserByAccount(accountManager.getByUUID(edit.getAuthorId()));
                    user.deleteEdit(edit);
                    accountManager.update(user);
                    Console.print("The edit has been rejected.", Console.Color.RED);
                    break;
                }
                case "s": {
                    // Skip the edit (do nothing)
                    Console.print("Skipped the edit.", Console.Color.YELLOW);
                    break;
                }
                default: {
                    Console.print("Invalid input. Please enter 'a' to approve, 'r' to reject, or 's' to skip.", Console.Color.RED);
                    break;
                }
            }
        }

        // Return to the Artist Panel after processing
        Console.getInput("\nPress Enter to return...");
        PageManager.runPage(this);
    }

    @Override
    public void displayPage() {
        String[] commands = {
                "Create New Song",
                "My Songs",
                "Create New Album",
                "My Albums",
                "Unapproved Edits",
                "Settings",
                "Main Menu",
                "Log out" ,
                "Exit"
        };

        String input = Console.getInputBox("", commands, "Enter your choice: ");
        switch (input) {
            case "1": {
                createSongPage(artist);
                break;
            }
            case "2": {
                artistSongsPage(artist);
                break;
            }
            case "3": {
                createAlbumPage(artist);
                break;
            }
            case "4": {
                artistAlbumsPage(artist);
                break;
            }
            case "5" :{
                unapprovedEditsPage(artist);
                break;
            }

            case "6" : {
                PageManager.runPage(new ArtistSettingPage(artist));
                break;
            }

            case "7":{
                AccountManager accountManager = new AccountManager();
                PageManager.runPage(new HomePageRegistered(accountManager.getByUUID(artist.getId())));
            }

            case "8": {
                PageManager.runPage(new HomePage());
            }

            case "9" : {
                System.exit(0);
            }
            // Handle other cases...
        }
    }
}
