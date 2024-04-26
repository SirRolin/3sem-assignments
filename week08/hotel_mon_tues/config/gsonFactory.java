package week08.hotel_mon_tues.config;

import com.google.gson.Gson;
import myLibrary.json.Json;
import week08.hotel_mon_tues.model.Hotel;
import week08.hotel_mon_tues.model.Room;

public class gsonFactory {
    static {
        //Json.addTransientField(Hotel.class, "rooms"); // To avoid cyclical callings. below is alternative. wouldn't get rooms from hotel.
        Json.addTransientField(Room.class, "hotelid"); // To avoid cyclical callings. above is alternative. wouldn't get hotel from rooms.
    }
    public static Gson getGson(){
        return Json.getGson();
    }
}
