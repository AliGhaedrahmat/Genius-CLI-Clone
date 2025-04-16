package org.UI.Page;

import org.Model.Account;
import org.Model.AccountRole;
import org.Model.Artist;
import org.Model.User;
import org.Services.AccountManager;
import org.UI.Console.Console;
import org.database.JsonManager;

public class HomePageRegistered extends Page {

    Account account;

    public HomePageRegistered(Account account) {
        this.account = account;
        this.name = "Main Menu";
    }



    @Override
    public void displayPage() {
        String header = "    Name : " + account.getName() + "\n    Role : " + account.getRole().toString() + "\n";
        Console.print(header , Console.Color.LIGHT_RED , false);

        String[] commands = new String[] {};

        if (account.getRole() == AccountRole.ADMIN){
            PageManager.runPage(new AdminPanel(JsonManager.getAdminByAccount(account)));
        }
        else if (account.getRole() == AccountRole.ARTIST){
            Artist artist = JsonManager.getArtistByAccount(account);
            PageManager.runPage(new ArtistPanel(artist));
        }
        else if (account.getRole() == AccountRole.USER) {
            commands = new String[]{
                    "Search Albums/Songs/Artists",
                    "Popular Songs Right Now",
                    "Following Artists",
                    "Settings",
                    "Log Out",
                    "Exit"
            };
        }
        String input = Console.getInputBox("", commands, "Enter your choice: ");
        switch (input) {
            case "1": {
                PageManager.runPage(new SearchPage(account));
                break;
            }

            case "2": {
                PageManager.runPage(new PopularSongsPage(account));
                break;
            }

            case "3" : {
                PageManager.runPage(new UserFollowingArtists(account));
                break;
            }

            case "4": {
                User user = JsonManager.getUserByAccount(account);
                PageManager.runPage(new UserSettingPage(user));
            }

            case "5" : {
                PageManager.runPage(new HomePage());
            }

            case "6" : {
                System.exit(0);
            }
        }
        displayPage();
    }
}
