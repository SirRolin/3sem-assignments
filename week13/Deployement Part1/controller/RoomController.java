package week13.ReactIII.controller;

import com.google.gson.Gson;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.Nullable;
import week13.ReactIII.DAO.concrete.RoomDAO;
import week13.ReactIII.config.gsonFactory;
import week13.ReactIII.model.Room;

import java.util.List;

public class RoomController {
    static RoomDAO dao = new RoomDAO();
    static Gson gson = gsonFactory.getGson();
    public static void setup(Javalin webApp) {
        webApp.get("/room", RoomController::getAllRooms);
        webApp.get("/room/{id}", RoomController::getSpecificRoom);
        webApp.post("/room", RoomController::postRoom);
        webApp.put("/room/{id}", RoomController::putRoom);
        webApp.delete("/room/{id}", RoomController::deleteRoom);
    }

    private static void getAllRooms(Context context) {
        List<Room> hotels = dao.getAll();
        if(!hotels.isEmpty()) {
            context.status(200);
            context.contentType("application/json");
            context.result(gson.toJson(hotels));
        } else {
            context.status(204); //// No Content
        }
    }

    private static void getSpecificRoom(Context context) {
        Long id = getId(context);
        if (id == null) return;
        Room room = dao.getById(id);
        if(room != null) {
            context.status(200);
            context.contentType("application/json");
            context.result(gson.toJson(room));
        } else {
            context.status(204); //// No Content
        }
    }

    private static void postRoom(Context context) {
        String json = context.body();
        Room roomFromJson = gson.fromJson(json, Room.class);
        dao.create(roomFromJson);
        if(roomFromJson.getID() != null){
            context.status(202);
            context.contentType("application/json");
            context.result(gson.toJson(roomFromJson));
        } else {
            context.status(400);
        }
    }

    private static void putRoom(Context context) {
        Long id = getId(context);
        if (id == null) return;
        Room room = dao.getById(id);
        String json = context.body();
        Room roomFromJson = gson.fromJson(json, Room.class);
        roomFromJson.setId(room.getID());
        dao.update(roomFromJson);
        context.status(200);
        context.contentType("application/json");
        context.result(gson.toJson(roomFromJson));
    }

    private static void deleteRoom(Context context) {
        Long id = getId(context);
        if (id == null) return;
        Room room = dao.getById(id);
        if(room != null) {
            dao.delete(room);
            context.status(200);
        } else {
            context.status(204);
        }
    }

    @Nullable
    private static Long getId(Context context) {
        String idStr = context.queryParam("id");
        long id;
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
