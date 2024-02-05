package week02.JSON;

import com.google.gson.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class JSONMain {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static void main(String[] args) {
        try {
            File two = new File("week02/JSON/account.json");
            JsonObject accountPerson = gson.fromJson(new FileReader(two), JsonObject.class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
