package org.database;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.FilePathUtil;
import org.Model.*;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;
import java.util.ArrayList;

public class JsonManager {

    private static final Gson gson;

    static {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Account.class, new AccountDeserializer());
        gson = builder.create();
    }

    // Write list to JSON file
    public static <T> void writeToJsonFile(List<T> data, String filePath) {
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read list from JSON file
    public static <T> List<T> readFromJsonFile(String filePath, Class<T> type) {
        try (Reader reader = new FileReader(filePath)) {
            Type listType = TypeToken.getParameterized(List.class, type).getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Read all accounts
    public static List<Account> readAllAccounts() {
        String filePath = FilePathUtil.getDatabaseFilePath("accounts");
        try (Reader reader = new FileReader(filePath)) {
            Type listType = new TypeToken<List<Account>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Get User from Account
    public static User getUserByAccount(Account account) {
        for (Account acc : readAllAccounts()) {
            if (acc.getRole() == AccountRole.USER && acc.getId().equals(account.getId())) {
                return (User) acc;
            }
        }
        return null;
    }

    // Get Artist from Account
    public static Artist getArtistByAccount(Account account) {
        for (Account acc : readAllAccounts()) {
            if (acc.getRole() == AccountRole.ARTIST && acc.getId().equals(account.getId())) {
                return (Artist) acc;
            }
        }
        return null;
    }

    // Get Admin from Account
    public static Admin getAdminByAccount(Account account) {
        for (Account acc : readAllAccounts()) {
            if (acc.getRole() == AccountRole.ADMIN && acc.getId().equals(account.getId())) {
                return (Admin) acc;
            }
        }
        return null;
    }

    // Custom deserializer for Account subtypes
    private static class AccountDeserializer implements JsonDeserializer<Account> {
        @Override
        public Account deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            AccountRole role = AccountRole.valueOf(jsonObject.get("role").getAsString());

            switch (role) {
                case ARTIST:
                    return context.deserialize(jsonObject, Artist.class);
                case USER:
                    return context.deserialize(jsonObject, User.class);
                case ADMIN:
                    return context.deserialize(jsonObject, Admin.class);
                default:
                    return context.deserialize(jsonObject, Account.class);
            }
        }
    }
}
