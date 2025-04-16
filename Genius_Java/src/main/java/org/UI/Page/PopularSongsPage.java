package org.UI.Page;

import org.Model.Account;
import org.Model.Song;
import org.Services.SongManager;
import org.UI.Console.Console;

import java.util.List;

public class PopularSongsPage extends Page {
    private final Account account;

    public PopularSongsPage(Account account) {
        this.account = account;
    }

    @Override
    public void displayPage() {
        while (true) {
            List<Song> popularSongs = SongManager.getPopularSongs(10);

            if (popularSongs.isEmpty()) {
                Console.print("\nNo popular songs found.", Console.Color.RED);
                Console.getInput("\nPress Enter to go back...");
                PageManager.runPage(new HomePageRegistered(account));
                return;
            }

            String[] songList = new String[popularSongs.size() + 1];
            for (int i = 0; i < popularSongs.size(); i++) {
                Song song = popularSongs.get(i);
                songList[i] = song.getName() + " (" + song.getGenre() + ") - By " +
                        song.getArtist().getName() + " (" + song.getViewsCount() + " views)";
            }
            songList[popularSongs.size()] = "Back";

            Console.clear();
            Console.print("Popular Songs:\n", Console.Color.YELLOW);
            String input = Console.getInputBox("Choose a song: ", songList, "Enter the number of the song you want to select: ");

            try {
                int selection = Integer.parseInt(input) - 1;

                if (selection == popularSongs.size()) {
                    PageManager.runPage(new HomePageRegistered(account));
                    return;
                } else if (selection >= 0 && selection < popularSongs.size()) {
                    PageManager.runPage(new SongPageForUser(popularSongs.get(selection), account));
                    return;
                } else {
                    Console.print("Invalid selection. Please choose a valid number.", Console.Color.RED);
                }
            } catch (NumberFormatException e) {
                Console.print("Invalid input. Please enter a number.", Console.Color.RED);
            }
        }
    }
}
