package ReactIII.config;

import com.google.gson.Gson;

import ReactIII.model.Role;
import sir.rolin.my_library.json.Json;

public class gsonFactory {
    static {
        //// Users Role Relation
        Json.addTransientField(Role.class, "users");
    }
    public static Gson getGson(){
        return Json.getGson();
    }
}
