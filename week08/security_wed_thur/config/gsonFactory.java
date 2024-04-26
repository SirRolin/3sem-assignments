package week08.security_wed_thur.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import myLibrary.json.Json;
import week08.security_wed_thur.model.Role;

public class gsonFactory {
    static {
        Json.addTransientField(Role.class, "users");
    }
    public static Gson getGson(){
        return Json.getGson();
    }
}
