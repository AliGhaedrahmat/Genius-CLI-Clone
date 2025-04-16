package org.Services;

import org.FilePathUtil;
import org.Model.Account;
import org.Model.Artist;
import org.Model.Genre;
import org.Model.Song;
import org.UI.Console.Console;
import org.database.Database;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SongManager extends Manager<Song> {

    public SongManager() {
        super(new Database<Song>(FilePathUtil.getDatabaseFilePath("songs"), Song.class));
    }

    public static Song getSongById(UUID songId) {
        List<Song> songs = SongManager.getAllSongs();
        for (Song song : songs) {
            if (song.getId().equals(songId)) {
                return song;
            }
        }
        return null;
    }

    public List<Song> searchSong(String keyword) {
        List<Song> songs = new ArrayList<>();
        for (Song song : getAll()) {
            if (song.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                    song.getLyrics().toLowerCase().contains(keyword.toLowerCase())) {
                songs.add(song);
            }
        }
        return songs;
    }

    public static List<Song> getAllSongs() {
        SongManager songManager = new SongManager();
        return songManager.getAll();
    }

    public static List<Song> getSongsByArtist(Artist artist) {
        List<Song> allSongs = getAllSongs();
        List<Song> artistSongs = new ArrayList<>();

        if (allSongs.isEmpty()) {
            Console.print("No songs available.", Console.Color.RED);
            return artistSongs;
        }

        for (Song song : allSongs) {
            if (song.getArtist() != null && song.getArtistId().equals(artist.getId())) {
                artistSongs.add(song);
            }
        }

        if (artistSongs.isEmpty()) {
            Console.print("No songs found for the artist: " + artist.getName(), Console.Color.RED);
        }

        return artistSongs;
    }

    public static Song getSongInput(List<Song> songList) {
        String[] songsString = new String[songList.size()];

        for (int i = 0; i < songList.size(); i++) {
            Song song = songList.get(i);
            songsString[i] = song.getName() + " (" + song.getGenre() + ")";
        }

        Console.clear();
        Console.print("Songs:\n", Console.Color.YELLOW);
        String input = Console.getInputBox("Choose a song: ", songsString, "Enter the number of the song you want to select: ");

        return songList.get(Integer.parseInt(input) - 1);
    }

    public static Song getSongInput(List<Song> songList , boolean showViews) {
        String[] songsString = new String[songList.size()];

        for (int i = 0; i < songList.size(); i++) {
            Song song = songList.get(i);
            songsString[i] = song.getName() + " (" + song.getGenre() + ")";
            if (showViews) {
                songsString[i] += " - views : " + song.getViewsCount();
            }
        }

        Console.clear();
        Console.print("Songs:\n", Console.Color.YELLOW);
        String input = Console.getInputBox("Choose a song: ", songsString, "Enter the number of the song you want to select: ");

        return songList.get(Integer.parseInt(input) - 1);
    }

    public static void setLyrics(Song song, String newLyrics) {
        song.setLyrics(newLyrics);
        new SongManager().update(song);
    }

    public static void setGenre(Song song, Genre newGenre) {
        song.setGenre(newGenre);
        new SongManager().update(song);
    }

    public static List<Song> getPopularSongsByArtist(Artist artist) {
        List<Song> allSongs = getSongsByArtist(artist);
        allSongs.sort((s1, s2) -> Integer.compare(s2.getViewsCount(), s1.getViewsCount()));
        return allSongs.subList(0, Math.min(5, allSongs.size()));
    }

    public static void increaseViewsByOne(Song song) {
        SongManager songManager = new SongManager();
        song.incrementViews();
        songManager.update(song);
    }

    public static void likeSong(Song song , Account account) {
        SongManager songManager = new SongManager();
        song.like(account.getId());
        songManager.update(song);
    }

    public static void dislikeSong(Song song, Account account) {
        SongManager songManager = new SongManager();
        song.dislike(account.getId());
        songManager.update(song);
    }

    public static List<Song> getPopularSongs(int amount) {
        List<Song> allSongs = getAllSongs();
        allSongs.sort((s1, s2) -> Integer.compare(s2.getViewsCount(), s1.getViewsCount()));
        return allSongs.subList(0, Math.min(amount, allSongs.size()));
    }
}
