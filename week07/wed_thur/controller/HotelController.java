package week07.wed_thur.controller;

import com.google.gson.GsonBuilder;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.Nullable;
import week07.wed_thur.DAO.concrete.HotelDAO;
import week07.wed_thur.model.Hotel;
import com.google.gson.*;

import java.util.List;

public class HotelController {
    static HotelDAO dao = new HotelDAO();
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static void setup(Javalin webApp) {
        webApp.get("/hotel", HotelController::getAllHotels);
        webApp.get("/hotel/{id}", HotelController::getSpecificHotel);
        webApp.get("/hotel/{id}/rooms", HotelController::getSpecificHotelsRooms);
        webApp.post("/hotel", HotelController::postHotel);
        webApp.put("/hotel/{id}", HotelController::putHotel);
        webApp.delete("/hotel/{id}", HotelController::deleteHotel);
    }


    private static void getAllHotels(Context context) {
        List<Hotel> hotels =  dao.getAll();
        if(!hotels.isEmpty()) {
            context.status(200);
            context.contentType("json");
            context.result(gson.toJson(hotels));
        } else {
            context.status(204); //// No Content
        }
    }

    private static void getSpecificHotel(Context context) {
        Long id = getId(context);
        if (id == null) return;
        Hotel hotel = dao.getById(id);
        if(hotel != null) {
            context.status(200);
            context.contentType("json");
            context.result(gson.toJson(hotel));
        } else {
            context.status(204); //// No Content
        }
    }
    private static void getSpecificHotelsRooms(Context context) {
        Long id = getId(context);
        if (id == null) return;
        Hotel hotel = dao.getById(id);
        if(hotel != null) {
            context.status(200);
            context.contentType("json");
            context.result(gson.toJson(hotel));
        } else {
            context.status(204); //// No Content
        }
    }

    private static void postHotel(Context ctx) {
        String json = ctx.body();
        Hotel hotelFromJson = gson.fromJson(json, Hotel.class);
        dao.create(hotelFromJson);
        if(hotelFromJson.getID() != null){
            ctx.status(202);
            ctx.contentType("json");
            ctx.result(gson.toJson(hotelFromJson));
        } else {
            ctx.status(400);
        }
    }

    private static void putHotel(Context context) {
        Long id = getId(context);
        if (id == null) return;
        Hotel hotel = dao.getById(id);
        String json = context.body();
        Hotel hotelFromJson = gson.fromJson(json, Hotel.class);
        hotelFromJson.setId(hotel.getID());
        dao.update(hotelFromJson);
        context.status(200);
        context.contentType("json");
        context.result(gson.toJson(hotelFromJson));
    }

    private static void deleteHotel(Context context) {
        Long id = getId(context);
        if (id == null) return;
        Hotel hotel = dao.getById(id);
        if(hotel != null) {
            dao.delete(hotel);
            context.status(200);
        } else {
            context.status(204);
        }
    }

    @Nullable
    private static Long getId(Context context) {
        String idStr = context.pathParam("id");
        Long id = null;
        try{
            if(idStr == null) return null;
            id = Long.parseLong(idStr);
        } catch (Exception e){
            e.printStackTrace();
            context.status(400);
            return null;
        }
        return id;
    }
}
