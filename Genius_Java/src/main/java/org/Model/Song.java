package org.Model;
import org.Services.AccountManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Song implements Identifiable {
    private final UUID id;
//    private Artist artist;
    private UUID artistId;
    private String name;
    private Genre genre;
    private String lyrics;
    private int viewsCount = 0;
    private List<UUID> likedAccounts = new ArrayList<>();
    private List<UUID> dislikedAccounts = new ArrayList<>();
    private int commentsCount = 0;
    private UUID albumId;

    public Song(Artist artist, String name, Genre genre, String lyrics) {
        this.id = UUID.randomUUID();
        this.artistId = artist.getId();
        this.name = name;
        this.genre = genre;
        this.lyrics = lyrics;
    }

    public UUID getId() {
        return id;
    }

    public Artist getArtist() {
        return AccountManager.GetArtistByUUID(artistId);
    }

    public UUID getArtistId() {
        return artistId;
    }

    public void setArtist(Artist artist) {
        this.artistId = artist.getId();
    }

    public void setArtist(UUID artistId) {
        this.artistId = artistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public void incrementViews() {
        this.viewsCount++;
    }

    public List<UUID> getLikedAccounts() {
        return likedAccounts;
    }

    public List<UUID> getDislikedAccounts() {
        return dislikedAccounts;
    }

    public int getLikesCount() {
        return likedAccounts.size();
    }

    public int getDislikesCount() {
        return dislikedAccounts.size();
    }

    public void like(UUID accountId) {
        if (!likedAccounts.contains(accountId)) {
            likedAccounts.add(accountId);
            dislikedAccounts.remove(accountId); // remove from dislikes if exists
        }
    }

    public void dislike(UUID accountId) {
        if (!dislikedAccounts.contains(accountId)) {
            dislikedAccounts.add(accountId);
            likedAccounts.remove(accountId); // remove from likes if exists
        }
    }

    public boolean isLikedBy(UUID accountId) {
        return likedAccounts.contains(accountId);
    }

    public boolean isDislikedBy(UUID accountId) {
        return dislikedAccounts.contains(accountId);
    }

    public void removeReaction(UUID accountId) {
        likedAccounts.remove(accountId);
        dislikedAccounts.remove(accountId);
    }



    public int getCommentsCount() {
        return commentsCount;
    }

    public void incrementComments() {
        this.commentsCount++;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", genre=" + genre +
                ", artist='" + getArtist().getName() + '\'' +
                ", lyrics='" + lyrics + '\'' +
                ", views=" + viewsCount +
                ", likes=" + getLikesCount() +
                ", dislikes=" + getDislikesCount() +
                ", comments=" + commentsCount +
                '}';
    }
}
