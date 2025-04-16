package org.UI.Page;

import org.Model.*;
import org.Services.AccountManager;
import org.Services.SongManager;
import org.UI.Console.Console;
import org.database.JsonManager;

import java.util.Date;
import java.util.List;

public class AdminPanel extends Page {
    Admin admin;

    public AdminPanel(Admin admin) {
        this.admin = admin;
    }

    public void unapprovedEditsPage() {
        AccountManager accountManager = new AccountManager();
        List<Edit> unapprovedEdits = AccountManager.getAllUnapprovedEdits();

        if (unapprovedEdits.isEmpty()) {
            Console.print("There are no unapproved edits for your songs.", Console.Color.GREEN);
            Console.getInput("\nPress Enter to return...");
            PageManager.runPage(this);
            return;
        }

        Console.clear();
        Console.print("Unapproved Edits:", Console.Color.CYAN);

        for (int i = 0; i < unapprovedEdits.size(); i++) {
            Edit edit = unapprovedEdits.get(i);
            Song song = SongManager.getSongById(edit.getSongId());

            Console.print("\n\n" + (i + 1) + ". Song: " + song.getName());
            Console.print("\n   Description: " + edit.getDescription());
            Console.print("\n   Release Date: " + edit.getReleaseDate());
            Console.print("\n   Old Lyrics: " + edit.getOldLyrics());
            Console.print("\n   New Lyrics: " + edit.getNewLyrics());

            String action = Console.getInput("Approve (a) / Reject (r) / Skip (s): ");
            switch (action.toLowerCase()) {
                case "a": {
                    edit.setApproved(true);
                    User user = JsonManager.getUserByAccount(accountManager.getByUUID(edit.getAuthorId()));
                    user.updateEdit(edit);
                    accountManager.update(user);
                    Console.print("The edit has been approved.", Console.Color.GREEN);
                    break;
                }
                case "r": {
                    User user = JsonManager.getUserByAccount(accountManager.getByUUID(edit.getAuthorId()));
                    user.deleteEdit(edit);
                    accountManager.update(user);
                    Console.print("The edit has been rejected.", Console.Color.RED);
                    break;
                }
                case "s": {
                    Console.print("Skipped the edit.", Console.Color.YELLOW);
                    break;
                }
                default: {
                    Console.print("Invalid input. Please enter 'a', 'r', or 's'.", Console.Color.RED);
                    break;
                }
            }
        }

        Console.getInput("\nPress Enter to return...");
        PageManager.runPage(this);
    }

    public static void unregisteredArtists() {
        // To be implemented
    }

    @Override
    public void displayPage() {
        Console.clear();
        Console.print("Admin Panel\n", Console.Color.LIGHT_BLUE);
        Console.print("Name : " + admin.getName() + "\n\n", Console.Color.LIGHT_BLUE);

        String[] commands = {
                "Unapproved Edits",
                "Log Out",
                "Exit"
        };

        String input = Console.getInputBox("\n", commands, "Choose an option: ");

        switch (input) {
            case "1": unapprovedEditsPage(); break;
            case "2": PageManager.runPage(new HomePage()); break;
            case "3": System.exit(0); break;
        }
    }
}
