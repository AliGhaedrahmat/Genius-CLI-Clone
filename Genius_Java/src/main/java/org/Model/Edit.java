package org.Model;

import org.Services.SongManager;

import java.util.Date;
import java.util.UUID;

public class Edit implements Identifiable {

    private UUID id;
    private UUID authorId;
    private UUID songId;
    private Date releaseDate;
    private boolean approved;

    private String description;
    private String oldLyrics;
    private String newLyrics;

    // Default constructor
    public Edit(UUID authorId, UUID songId, Date releaseDate, String description, String oldLyrics, String newLyrics) {
        this.id = UUID.randomUUID();  // Automatically generate a new UUID
        this.authorId = authorId;
        this.songId = songId;
        this.releaseDate = releaseDate;
        this.approved = false; // Default value
        this.description = description;
        this.oldLyrics = oldLyrics;
        this.newLyrics = newLyrics;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getAuthorId() {
        return authorId;
    }

    public void setAuthorId(UUID authorId) {
        this.authorId = authorId;
    }

    public UUID getSongId() {
        return songId;
    }

    public void setSongId(UUID songId) {
        this.songId = songId;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;

        SongManager songManager = new SongManager();
        Song song = SongManager.getSongById(this.songId);
        song.setLyrics(this.newLyrics);

        songManager.update(song);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOldLyrics() {
        return oldLyrics;
    }

    public void setOldLyrics(String oldLyrics) {
        this.oldLyrics = oldLyrics;
    }

    public String getNewLyrics() {
        return newLyrics;
    }

    public void setNewLyrics(String newLyrics) {
        this.newLyrics = newLyrics;
    }

    // Method to approve the edit (sets approved to true)
    public void approve() {
        this.approved = true;
    }

    // Method to reject the edit (sets approved to false)
    public void reject() {
        this.approved = false;
    }

    // Override toString for easy debugging and logging
    @Override
    public String toString() {
        return "Edit{" +
                "id=" + id +
                ", authorId=" + authorId +
                ", songId=" + songId +
                ", releaseDate=" + releaseDate +
                ", approved=" + approved +
                ", description='" + description + '\'' +
                ", oldLyrics='" + oldLyrics + '\'' +
                ", newLyrics='" + newLyrics + '\'' +
                '}';
    }
}
