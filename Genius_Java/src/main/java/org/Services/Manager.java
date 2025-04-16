package org.Services;

import org.Model.Identifiable;
import org.database.Database;
import org.UI.Console.Console;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Manager<T extends Identifiable> {

    private final Database<T> database;

    public Manager(Database<T> database) {
        this.database = database;
    }

    // Add an item to the database
    public void add(T item) {
        try {
            database.add(item);
        } catch (Exception e) {
            Console.print(e.getMessage(), Console.Color.RED);
        }
    }

    // Delete an item from the database
    public void delete(T item) {
        try {
            database.delete(item);
        } catch (Exception e) {
            Console.print(e.getMessage(), Console.Color.RED);
        }
    }

    // Update an item in the database
    public void update(T item) {
        try {
            database.update(item);
        } catch (Exception e) {
            Console.print(e.getMessage(), Console.Color.RED);
        }
    }

    // Get all items from the database
    public List<T> getAll() {
        try {
            return database.getAll();
        } catch (Exception e) {
            Console.print("Error retrieving items: " + e.getMessage(), Console.Color.RED);
            return null;
        }
    }

    public T getByUUID(UUID uuid) {
        List<T> items = getAll();
        for (T item : items) {
            if (item.getId().equals(uuid)) {
                return item;
            }
        }
        return null;
    }
}