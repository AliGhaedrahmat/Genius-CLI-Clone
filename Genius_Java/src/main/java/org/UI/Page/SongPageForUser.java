package org.UI.Page;

import org.Model.*;
import org.Services.AccountManager;
import org.Services.SongManager;
import org.UI.Console.Console;
import org.database.JsonManager;

import java.util.*;

public class SongPageForUser extends Page{

    private Song song;
    private Account account;

    public SongPageForUser(Song song , Account account) {
        this.song = song;
        this.account = account;
    }

    public SongPageForUser(Song song) {
        this.song = song;
    }

    public void printSongHeader() {
        String text = "\n\n    ";
        text += "Song Name: " + song.getName() + "\n    ";
        text += "Song Artist: " + song.getArtist().getName() + "\n    ";
        text += "Song Genre: " + song.getGenre().toString() + "\n    ";
        Console.print(text , Console.Color.YELLOW , false);
    }

    public void submitEdit() {
        // Ask the user to provide a description and the new lyrics
        Console.clear();
        printSongHeader();

        String description = Console.getInput("Enter a description for your edit (why are you changing the lyrics?): ");
        String newLyrics = Console.getInput("Enter the new lyrics for the song: ");

        // Create a new Edit object with relevant details
        Edit newEdit = new Edit(account.getId() , song.getId() , new Date() , description , song.getLyrics() ,  newLyrics );

        User user = JsonManager.getUserByAccount(account);

        AccountManager.addEdit(newEdit , user);
        // Notify the user that the edit was submitted
        Console.print("Your edit has been successfully submitted for approval.\n", Console.Color.GREEN);

        // Return to the song page or display a success message
        Continue();
    }



    public void showLyrics() {
        Console.clear();
        printSongHeader();
        Console.print("\n    Song Lyrics : \n\n" , Console.Color.LIGHT_RED);
        Console.print(song.getLyrics() , Console.Color.WHITE);
        String input = Console.getInputBox("" , new String[] {"Submit An Edit" , "Go Back"} , "Enter your choice: ");

        switch (input) {
            case "1": {
                submitEdit();
                break;
            }
            case "2": {
                displayPage();
                break;
            }
        }
    }


    public void viewStats(){
        Console.clear();
        String statsString =
                "\n    Song Name: " + song.getName() + "\n    Artist Name: "
                + song.getArtist().getName() + "\n    Genre: "
                + song.getGenre().toString() + "\n    Views Count: "
                + song.getViewsCount() + "\n    Likes Count: "
                + song.getLikesCount() + "\n    Dislikes Count: "
                + song.getDislikesCount() + "\n    Comments Count: "
                + song.getCommentsCount() + "\n\n";

        Console.print(statsString , Console.Color.YELLOW , false);
        Console.getInput("\nPress Enter to return...");
        displayPage();
    }

    public void likeSong(){
        Console.clear();
        if (song.isLikedBy(account.getId())){
            Console.print("You already liked this song.\n" , Console.Color.YELLOW);
            Continue();
        }
        else {
            SongManager.likeSong(song, account);
            Console.print("You liked this song." , Console.Color.YELLOW);
            Continue();
        }
    }

    public void dislikeSong(){
        Console.clear();
        if (song.isDislikedBy(account.getId())){
            Console.print("You already disliked this song.\n" , Console.Color.YELLOW);
            Continue();
        }
        else {
            SongManager.dislikeSong(song, account);
            Console.print("You disliked this song." , Console.Color.YELLOW);
            Continue();
        }
    }

    public void writeComment() {

        User user = JsonManager.getUserByAccount(account);


        Console.clear();
        printSongHeader();
        String commentText = Console.getInput("\nWrite your comment below:\n> ");
        Comment comment = new Comment(commentText, user.getId(), UUID.randomUUID(), song.getId());

        AccountManager.addComment(comment , user);

        song.incrementComments();

        Console.print("\nYour comment has been added!\n", Console.Color.GREEN);
        Continue();
    }

    public void viewComments(){
        Console.clear();
        printSongHeader();
        Console.print("\nComments:\n", Console.Color.CYAN);

        AccountManager accountManager = new AccountManager();

        boolean hasComments = false;

        for (Comment comment : accountManager.getAllComments()) {
//            Console.print("comment detected \n");
            if (comment.getSongId().equals(song.getId())) {
                hasComments = true;
                User commenter = JsonManager.getUserByAccount(accountManager.getByUUID(comment.getWriterUser()));
//                String username = (commenter != null) ? commenter.getUsername() : "Unknown User";
                assert commenter != null;
                String username = commenter.getUsername();
                Console.print("\n- " + username + " said:", Console.Color.YELLOW);
                Console.print("  " + comment.getText(), Console.Color.WHITE);
                Console.print("  [" + (comment.getDate()) + "]", Console.Color.LIGHT_BLACK);
            }
        }

        if (!hasComments) {
            Console.print("\nNo comments yet on this song.", Console.Color.LIGHT_RED);
        }

        Console.getInput("\n\nPress Enter to return...");
        displayPage();
    }




    @Override
    public void displayPage() {
        Console.clear();
        printSongHeader();

        SongManager.increaseViewsByOne(song);

        List<String> commandsList = new ArrayList<>(Arrays.asList(
                "Show Lyrics",
                "Like Song", "Dislike Song",
                "View Stats",
                "Write a comment", "View Comments",
                "Go To Artists Page",
                "Main Menu", "Exit"
        ));

        if (account != null && account.getRole() == AccountRole.ARTIST
                && song.getArtist().getId().equals(account.getId())) {
            commandsList.add( "Enter Artist Menu For This Song (Only for Artist)");
        }
        String[] commands = commandsList.toArray(new String[0]);

        String input = Console.getInputBox("\n" , commands , "Choose an option: ");

        switch (input) {
            case "1":{
                showLyrics();
                break;
            }
            case "2":{
                likeSong();
                break;
            }

            case "3":{
                dislikeSong();
                break;
            }

            case "4":{
                viewStats();
                break;
            }

            case "5":{
                writeComment();
                break;
            }

            case "6":{
                viewComments();
                break;
            }

            case "7":{
                Artist artist = song.getArtist();
                PageManager.runPage(new ArtistPage(artist , account));
                break;
            }

            case "8":{
                PageManager.runPage(new HomePageRegistered(account));
            }

            case "9" : {
                System.exit(0);
            }

            case "10" : {
                if (account != null && account.getRole() == AccountRole.ARTIST && song.getArtist().getId().equals(account.getId())) {
                    PageManager.runPage(new SongPageForArtist(AccountManager.GetArtistByUUID(account.getId()), song));
                }
                break;
            }
        }
        displayPage();
    }
}
