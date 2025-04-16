package org.Services;

import org.FilePathUtil;
import org.Model.*;
import org.database.Database;
import org.UI.Console.Console;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AlbumManager extends Manager<Album> {

    public AlbumManager() {
        super(new Database<Album>(FilePathUtil.getDatabaseFilePath("albums"), Album.class));
    }

    public static Album getAlbumInput(List<Album> Albums) {
        String[] albumsString = new String[Albums.size()];

        for (int i = 0; i < Albums.size(); i++) {
            Album album = Albums.get(i);
            albumsString[i] = album.getName() + " (By : " + AccountManager.GetArtistByUUID(album.getArtistId()).getName() + ")";
        }

        Console.clear();
        Console.print("Albums:\n", Console.Color.YELLOW);
        String input = Console.getInputBox("Choose an Album: ", albumsString, "Enter the number of the Album you want to select: ");

        return Albums.get(Integer.parseInt(input) - 1);
    }

    public void createAlbum(Album album) {
        add(album);
        Console.print("Album created successfully!", Console.Color.GREEN);
    }

    public void addSongToAlbum(Album album, Song song) {
        if (album.getSongs().contains(song.getId())) {
            Console.print("Song already exists in the album.", Console.Color.YELLOW);
        } else {
            album.getSongs().add(song.getId());
            update(album);
            Console.print("Song added to the album successfully!", Console.Color.GREEN);
        }
    }

    public void removeSongFromAlbum(Album album, Song song) {
        if (album.getSongs().contains(song.getId())) {
            album.getSongs().remove(song.getId());
            update(album);
            Console.print("Song removed from the album.", Console.Color.GREEN);
        } else {
            Console.print("Song not found in the album.", Console.Color.YELLOW);
        }
    }

    public List<Song> getSongs(Album album) {
        List<Song> songs = new ArrayList<>();
        SongManager songManager = new SongManager();

        for (UUID songId : album.getSongs()) {
            Song song = songManager.getByUUID(songId);
            if (song != null) {
                songs.add(song);
            }
        }
        return songs;
    }

    public Album getByUUID(UUID uuid) {
        return super.getByUUID(uuid);
    }

    public Account getArtistAccount(Album album) {
        AccountManager accountManager = new AccountManager();
        return accountManager.getByUUID(album.getArtistId());
    }

    public List<Album> getAllAlbums() {
        return getAll();
    }

    public List<Album> searchAlbum(String keyword) {
        List<Album> allAlbums = getAll();
        List<Album> matchingAlbums = new ArrayList<>();

        for (Album album : allAlbums) {
            if (album.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                    AccountManager.GetArtistByUUID(album.getArtistId()).getName().toLowerCase().contains(keyword.toLowerCase())) {
                matchingAlbums.add(album);
            }
        }

        return matchingAlbums;
    }

    public static List<Album> getAlbumsByArtist(Artist artist) {
        AlbumManager albumManager = new AlbumManager();
        List<Album> allAlbums = albumManager.getAllAlbums();

        List<Album> myAlbums = new ArrayList<>();
        for (Album album : allAlbums) {
            if (album.getArtistId().equals(artist.getId())) {
                myAlbums.add(album);
            }
        }
        return myAlbums;
    }
}
