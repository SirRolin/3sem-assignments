package week13.ReactIII.config;

import com.google.gson.Gson;
import myLibrary.json.Json;
import week13.ReactIII.model.Room;
import week13.ReactIII.model.Role;

public class gsonFactory {
    static {
        //Json.addTransientField(Hotel.class, "rooms"); // To avoid cyclical callings. below is alternative. wouldn't get rooms from hotel.
        Json.addTransientField(Room.class, "hotelid"); // To avoid cyclical callings. above is alternative. wouldn't get hotel from rooms.

        //// Users Role Relation
        Json.addTransientField(Role.class, "users");
    }
    public static Gson getGson(){
        return Json.getGson();
    }
}
