package week09.JWT_Exercise.config;

import com.google.gson.Gson;
import myLibrary.json.Json;
import week09.JWT_Exercise.model.Room;
import week09.JWT_Exercise.model.Role;

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
