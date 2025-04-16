package org.UI.Page;

import org.Model.*;
import org.Services.AccountManager;
import org.Services.AlbumManager;
import org.Services.SongManager;
import org.UI.Console.Console;

import java.util.ArrayList;
import java.util.List;

public class SearchPage extends Page {
    private final Account account;

    public SearchPage(Account account) {
        this.account = account;
        this.name = "Search Page";
    }

    private String getSearchQuery() {
        String input = Console.getInput("\nWhat are you looking for?\nEnter any name, phrase, or keyword: ");
        return input.trim().isEmpty() ? getSearchQuery() : input.trim();
    }

    @Override
    public void displayPage() {
        while (true) {
            Console.clear();
            String searchQuery = getSearchQuery().toLowerCase();

            // Search
            SongManager songManager = new SongManager();
            List<Song> matchingSongs = songManager.searchSong(searchQuery);

            AccountManager accountManager = new AccountManager();
            List<Artist> matchingArtists = accountManager.searchArtist(searchQuery);

            AlbumManager albumManager = new AlbumManager();
            List<Album> matchingAlbums = albumManager.searchAlbum(searchQuery);

            Console.clear();
            Console.print("\n--- Search Results ---\n", Console.Color.CYAN);

            List<String> options = new ArrayList<>();
            for (Song song : matchingSongs)
                options.add("Song: " + song.getName() + " - " + song.getArtist().getName());

            for (Artist artist : matchingArtists)
                options.add("Artist: " + artist.getName());

            for (Album album : matchingAlbums) {
                Artist albumArtist = AccountManager.GetArtistByUUID(album.getArtistId());
                options.add("Album: " + album.getName() + " - " + albumArtist.getName());
            }

            if (options.isEmpty()) {
                Console.print("\nNo results found for: " + searchQuery, Console.Color.RED);
                Console.getInput("\nPress Enter to search again...");
                continue;
            }

            options.add("Back");
            String[] choices = options.toArray(new String[0]);

            try {
                int selected = Integer.parseInt(Console.getInputBox("\nSelect an item to view:", choices, "Select: ")) - 1;

                if (selected == choices.length - 1) return; // Back

                if (selected >= 0 && selected < matchingSongs.size()) {
                    PageManager.runPage(new SongPageForUser(matchingSongs.get(selected), account));
                    return;
                } else if (selected < matchingSongs.size() + matchingArtists.size()) {
                    int index = selected - matchingSongs.size();
                    PageManager.runPage(new ArtistPage(matchingArtists.get(index), account));
                    return;
                } else if (selected < matchingSongs.size() + matchingArtists.size() + matchingAlbums.size()) {
                    int index = selected - matchingSongs.size() - matchingArtists.size();
                    PageManager.runPage(new AlbumPage(matchingAlbums.get(index), account));
                    return;
                } else {
                    Console.print("Invalid selection. Try again.", Console.Color.RED);
                }
            } catch (NumberFormatException e) {
                Console.print("Invalid input. Please enter a number.", Console.Color.RED);
            }
        }
    }
}
