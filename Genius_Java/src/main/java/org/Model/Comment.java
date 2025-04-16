package org.Model;

import java.util.Date;
import java.util.UUID;

public class Comment implements Identifiable {
    String commentText;
    // Song commentedSong;
    UUID writerUser;
    Date commentDate;
    UUID id;
    UUID songId;

    public Comment(String commentText, UUID writerUser, UUID id , UUID songId) {
        this.commentText = commentText;
        this.writerUser = writerUser;
        this.commentDate = new Date();
        this.id = id;
        this.songId = songId;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public UUID getSongId() {
        return songId;
    }

    public String getText(){
        return commentText;
    }

    public Date getDate(){
        return commentDate;
    }

    public UUID getWriterUser() {
        return writerUser;
    }
}
