package org.Model;

import org.Services.AccountManager;
import org.Services.SongManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Artist extends Account {
    private Genre genre;
    private final List<UUID> followers  = new ArrayList<>();
    public Artist(String username, String password, String name, String bio, Genre genre) {
        super(username, password, AccountRole.ARTIST, name, bio);
        this.genre = genre;
    }

    // Getters
    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public List<UUID> getFollowers() {
        return followers;
    }

    public void setFollowers(List<UUID> followers) {
        this.followers.clear();
        this.followers.addAll(followers);

    }

    public void addFollower(UUID uuid) {
        if (!followers.contains(uuid)) {
            followers.add(uuid);
        }
    }

    public void removeFollower(UUID uuid) {
        followers.remove(uuid);
    }

}
