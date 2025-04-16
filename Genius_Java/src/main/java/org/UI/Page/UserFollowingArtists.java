package org.UI.Page;

import org.Model.Account;
import org.Model.AccountRole;
import org.Model.Artist;
import org.Model.User;
import org.Services.AccountManager;
import org.UI.Console.Console;
import org.database.JsonManager;

import java.util.ArrayList;
import java.util.List;

public class UserFollowingArtists extends Page{

    Account account;
    User user;

    public UserFollowingArtists(Account account){
        this.account = account;
        this.user = JsonManager.getUserByAccount(account);

    }

    @Override
    public void displayPage() {
        List<Artist> followingArtists = AccountManager.getUserFollowingArtists(user);

        if (followingArtists.isEmpty()) {
            Console.print("\nYou are not following any artists yet.", Console.Color.RED);
            Console.getInput("\nPress Enter to go back...");
            return;
        }

        // Prepare a list of options
        List<String> commands = new ArrayList<>();
        for (Artist artist : followingArtists) {
            commands.add(artist.getName());
        }
        commands.add("Back");

        String input = Console.getInputBox("\nFollowing Artists: ", commands.toArray(new String[0]), "Choose an artist to go to their page:");

        if (input.equals("Back")) {
            return; // Go back to the previous page
        }

        // View artist profile
        int selectedIndex = Integer.parseInt(input) - 1;
        if (selectedIndex >= 0 && selectedIndex < followingArtists.size()) {
            Artist selectedArtist = followingArtists.get(selectedIndex);

            PageManager.runPage(new ArtistPage(selectedArtist, account));

        }
    }

}
