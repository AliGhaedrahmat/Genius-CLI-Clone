package org.Services;

import org.FilePathUtil;
import org.Model.*;
import org.UI.Console.Console;
import org.database.Database;
import org.database.JsonManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Collections;
import java.util.Comparator;
public class AccountManager extends Manager<Account> {

    public AccountManager() {
        super(new Database<Account>(FilePathUtil.getDatabaseFilePath("accounts") , Account.class));
    }

    // You can add specialized methods here
    public List<Account> getAccountsByRole(AccountRole role) {
        List<Account> accounts = getAll();
        List<Account> accountsByRole = new ArrayList<>();
        for (Account account : accounts) {
            if (account.getRole() == role) {
                accountsByRole.add(account);
            }
        }
        return accountsByRole;
    }

    public boolean checkAccountExistence(String username) {
        List<Account> accounts = getAll();
        for (Account account : accounts) {
            if (account.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public UUID findUUIDByUsername(String username) {
        List<Account> accounts = getAll();
        for (Account account : accounts) {
            if (account.getUsername().equals(username)) {
                return account.getId();
            }
        }
        return null;
    }


    public String findPasswordByUUID(UUID uuid) {
        List<Account> accounts = getAll();
        for (Account account : accounts) {
            if (account.getId().equals(uuid)) {
                return account.getPassword();
            }
        }
        return null;
    }

    public static Artist GetArtistByUUID(UUID uuid) {
        AccountManager accountManager = new AccountManager();
        return JsonManager.getArtistByAccount(accountManager.getByUUID(uuid));
    }


    public List<Artist> searchArtist(String keyword) {
        List<Account> matchingAccounts = new ArrayList<Account>();
        List<Account> allArtistsAccounts = getAccountsByRole(AccountRole.ARTIST);
        for (Account account : allArtistsAccounts) {
            if (account.getUsername().equals(keyword) || account.getName().equals(keyword)) {
                matchingAccounts.add(account);
            }
        }

        List<Artist> matchingArtists = new ArrayList<>();

        for (Account account : matchingAccounts) {
            Artist artist = JsonManager.getArtistByAccount(account);
            matchingArtists.add(artist);
        }
        return matchingArtists;
    }

    public static void addComment(Comment comment , User user) {
        AccountManager accountManager = new AccountManager();
        user.addComment(comment);
        accountManager.update(user);
    }



    public List<Comment> getAllComments() {
        List<Account> userAccounts = getAccountsByRole(AccountRole.USER);
        List<Comment> comments = new ArrayList<>();

        for (Account account : userAccounts) {
            User user = JsonManager.getUserByAccount(account);
            if (user != null) {
                comments.addAll(user.getComments());
            } else {
                Console.print("\nUser " + user.getUsername() + " is null.\n", Console.Color.RED);
            }
        }

        // Sort the comments by date (descending order, most recent first)
        Collections.sort(comments, new Comparator<Comment>() {
            @Override
            public int compare(Comment c1, Comment c2) {
                return c2.getDate().compareTo(c1.getDate()); // For descending order
            }
        });

        return comments;
    }


    public static void addEdit(Edit edit , User user) {
        AccountManager accountManager = new AccountManager();
        user.addEdit(edit);
        accountManager.update(user);
    }

    public static List<Edit> getAllEdits() {
        AccountManager accountManager = new AccountManager();
        List <Account> userAccounts = accountManager.getAccountsByRole(AccountRole.USER);
        List<Edit> edits = new ArrayList<>();

        for (Account account : userAccounts) {
            User user = JsonManager.getUserByAccount(account);
            if (user != null) {
//                Console.print("\n" + user.getComments().toString() + "\n");
                edits.addAll(user.getEdits());
            }
            else {
                Console.print("\nUser " + user.getUsername() + " is null.\n" , Console.Color.RED);
            }

        }

        return edits;
    }

    public static List<Edit> getAllUnapprovedEdits() {
        List<Edit> allEdits = getAllEdits();
        List<Edit> unapprovedEdits = new ArrayList<>();

        for (Edit edit : allEdits) {
                if (!edit.isApproved()) {
                    unapprovedEdits.add(edit);
                }
        }

        return unapprovedEdits;
    }

    public static Artist getArtistByEdit(Edit edit){
        AccountManager accountManager = new AccountManager();
        Song song = SongManager.getSongById(edit.getSongId());
        return JsonManager.getArtistByAccount(accountManager.getByUUID(song.getArtistId()));
    }

    public static List<Edit> getUnapprovedEditsByArtist(Artist artist) {
        List<Edit> allEdits = getAllEdits();
        List<Edit> unapprovedEdits = new ArrayList<>();

        for (Edit edit : allEdits) {
            if (getArtistByEdit(edit).getId().equals(artist.getId())) {
                if (!edit.isApproved()) {
                    unapprovedEdits.add(edit);
                }
            }
        }

        return unapprovedEdits;
    }

    public static List<UUID> getArtistFollowersUUIDs(Artist artist) {
        return artist.getFollowers();
    }

    public static List<Artist> getUserFollowingArtists(User user) {
        AccountManager accountManager = new AccountManager();
        List<Artist> artists = new ArrayList<>();
        for (Account account : accountManager.getAccountsByRole(AccountRole.ARTIST)) {
            Artist artist = JsonManager.getArtistByAccount(account);
            if (artist != null) {
                artists.add(artist);
            }
        }

        List<Artist> followingArtists = new ArrayList<>();

        for (Artist artist : artists) {
            List<UUID> followers = artist.getFollowers();
            for (UUID follower : followers) {
                if (follower.equals(user.getId())) {
                    followingArtists.add(artist);
                }
            }
        }

        return followingArtists;

    }

    // Additional methods specific to Account management can be added here
}
