package org.database;

import java.util.ArrayList;
import java.util.List;
import org.Model.Identifiable;

public class Database<T extends Identifiable> {
    private final String databaseFilePath;
    private final Class<T> type;

    public Database(String databaseFilePath, Class<T> type) {
        this.databaseFilePath = databaseFilePath;
        this.type = type;
    }

    // Read all items from the JSON file
    public List<T> getAll() {
        List<T> items = JsonManager.readFromJsonFile(databaseFilePath, type);
        if (items == null) {
            items = new ArrayList<>();
        }
        return items;
    }

    // Add a new item and write to file
    public void add(T item) {
        List<T> items = getAll();
        items.add(item);
        JsonManager.writeToJsonFile(items, databaseFilePath);
    }

    // Update an existing item
    public void update(T item) {
        List<T> items = getAll();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(item.getId())) {
                items.set(i, item);
                break;
            }
        }
        JsonManager.writeToJsonFile(items, databaseFilePath);
    }

    // Delete an item by ID
    public void delete(T item) {
        List<T> items = getAll();
        items.removeIf(i -> i.getId().equals(item.getId()));
        JsonManager.writeToJsonFile(items, databaseFilePath);
    }
}
