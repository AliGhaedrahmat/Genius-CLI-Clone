package org.Model;

import java.util.ArrayList;
import java.util.List;

public class User extends Account {
    private List<Comment> comments = new ArrayList<>();
    private List<Edit> edits = new ArrayList<>();

    public User(String username, String password , String name , String bio) {
        super(username , password , AccountRole.USER , name , bio);
    }

    public List<Comment> getComments() {
        if (comments == null) {
            return new ArrayList<>();
        }
        return comments;
    }

    public void addComment(Comment comment) {
        if (this.comments == null) {
            this.comments = new ArrayList<>();
        }
        this.comments.add(comment);
    }

    public List<Edit> getEdits() {
        if (this.edits == null) {
            return new ArrayList<>();
        }
        return this.edits;
    }

    public void addEdit(Edit edit) {
        if (this.edits == null) {
            this.edits = new ArrayList<>();

        }
        this.edits.add(edit);
    }

    public void updateEdit(Edit edit) {
        if (this.edits == null) {
            this.edits = new ArrayList<>();
        }

        for (int i = 0; i < edits.size(); i++) {
            if (edits.get(i).getId().equals(edit.getId())) {
                edits.set(i, edit);
                return;
            }
        }

        this.edits.add(edit);
    }


    public void deleteEdit(Edit edit) {
        if (this.edits == null || edit == null || edit.getId() == null) return;

        edits.removeIf(existingEdit ->
                existingEdit != null &&
                        existingEdit.getId() != null &&
                        existingEdit.getId().equals(edit.getId())
        );
    }

}