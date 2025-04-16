package org.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Album implements Identifiable {
    private final UUID id;
    private String name;
    private Genre genre;
    private UUID artistId;
    private List<UUID> songsIds = new ArrayList<>();
    private List<UUID> featuredArtistsIds = new ArrayList<>();
    private int viewsCount = 0;
    private int commentsCount = 0;
    private int likesCount = 0;
    private int dislikesCount = 0;
    private Date releaseDate;

    public Album(String name, Genre genre, Artist artist) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.genre = genre;
        this.artistId = artist.getId();
    }

    // Getters and setters...

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Genre getGenre() {
        return genre;
    }


    public int getViewsCount() {
        return viewsCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public int getDislikesCount() {
        return dislikesCount;
    }

    public List<UUID> getSongs(){
        return songsIds;
    }

    public UUID getArtistId() {
        return artistId;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    // Add methods to manipulate songs, like incrementing views/likes etc.

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", genre=" + genre +
                ", artistId=" + artistId +
                ", songsIds=" + songsIds +
                ", featuredArtistsIds=" + featuredArtistsIds +
                ", viewsCount=" + viewsCount +
                ", commentsCount=" + commentsCount +
                ", likesCount=" + likesCount +
                ", dislikesCount=" + dislikesCount +
                ", releaseDate=" + (releaseDate != null ? releaseDate.toString() : "N/A") +
                '}';
    }
}
