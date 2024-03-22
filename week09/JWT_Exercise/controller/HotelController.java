package week09.JWT_Exercise.controller;

import io.javalin.apibuilder.EndpointGroup;
import week09.JWT_Exercise.config.gsonFactory;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.Nullable;
import week09.JWT_Exercise.DAO.concrete.HotelDAO;
import week09.JWT_Exercise.model.Hotel;
import com.google.gson.*;

import java.util.List;

import static io.javalin.apibuilder.ApiBuilder.*;

public class HotelController {
    static HotelDAO dao = new HotelDAO();
    static Gson gson = gsonFactory.getGson();

    public static final EndpointGroup routes = () -> {
        path("/hotel", () -> {
            before(SecurityController.authenticate);
            get("", HotelController::getAllHotels, UserC.Role.USER);
            get("/{id}", HotelController::getSpecificHotel, UserC.Role.USER);
            get("/{id}/rooms", HotelController::getSpecificHotelsRooms, UserC.Role.USER);
            post("", HotelController::postHotel, UserC.Role.USER);
            put("/{id}", HotelController::putHotel, UserC.Role.USER);
            delete("/{id}", HotelController::deleteHotel, UserC.Role.USER);
            after(ctx -> {
               ctx.header("path", ctx.fullUrl());
            });
        });
    };


    private static void getAllHotels(Context context) {
        List<Hotel> hotels = dao.getAll();
        if (!hotels.isEmpty()) {
            context.status(200);
            context.contentType("application/json");
            String result;
            result = gson.toJson(hotels);
            context.result(result);
        } else {
            context.status(204); //// No Content
        }
    }

    private static void getSpecificHotel(Context context) {
        Long id = getId(context);
        if (id == null) return;
        Hotel hotel = dao.getById(id);
        if (hotel != null) {
            context.status(200);
            context.contentType("application/json");
            context.result(gson.toJson(hotel));
        } else {
            context.status(204); //// No Content
        }
    }

    private static void getSpecificHotelsRooms(Context context) {
        Long id = getId(context);
        if (id == null) return;
        Hotel hotel = dao.getById(id);
        if (hotel != null) {
            context.status(200);
            context.contentType("application/json");
            context.result(gson.toJson(hotel));
        } else {
            context.status(204); //// No Content
        }
    }

    private static void postHotel(Context ctx) {
        String json = ctx.body();
        Hotel hotelFromJson = gson.fromJson(json, Hotel.class);
        dao.create(hotelFromJson);
        if (hotelFromJson.getID() != null) {
            ctx.status(202);
            ctx.contentType("application/json");
            ctx.result(gson.toJson(hotelFromJson));
        } else {
            ctx.status(400);
        }
    }

    private static void putHotel(Context context) {
        Long id = getId(context);
        if (id == null) return;
        Hotel hotel = dao.getById(id);
        if (hotel == null) {
            context.status(404);
            return;
        }
        String json = context.body();
        Hotel hotelFromJson = gson.fromJson(json, Hotel.class);
        hotelFromJson.setId(hotel.getID());
        dao.update(hotelFromJson);
        context.status(200);
        context.contentType("application/json");
        context.result(gson.toJson(hotelFromJson));
    }

    private static void deleteHotel(Context context) {
        Long id = getId(context);
        if (id == null) return;
        Hotel hotel = dao.getById(id);
        if (hotel != null) {
            dao.delete(hotel);
            context.status(204);
        } else {
            context.status(404);
        }
    }

    @Nullable
    private static Long getId(Context context) {
        String idStr = context.pathParam("id");
        Long id = null;
        try {
            if (idStr == null) return null;
            id = Long.parseLong(idStr);
        } catch (Exception e) {
            e.printStackTrace();
            context.status(400);
            return null;
        }
        return id;
    }
}
