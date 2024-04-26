package week07.mon_tues.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.Nullable;
import week07.mon_tues.DTO.DogDTO;

import java.util.HashMap;
import java.util.Set;

public class DogController {

    private static final HashMap<Integer, DogDTO> dogs = new HashMap<>();
    private static final Gson g = new GsonBuilder().setPrettyPrinting().create();

    public static void setup(Javalin webApp){
        webApp.get("/dogs", DogController::getAllDogs);
        webApp.get("/dog/{id}", DogController::getDogById);
        webApp.post("/dog", DogController::createDog);
        webApp.put("/dog/{id}", DogController::updateDog);
        webApp.delete("/dog/{id}", DogController::deleteDog);

        webApp.get("/api/dogs", DogController::getAllDogsApi);
        webApp.get("/api/dog/{id}", DogController::getDogByIdApi);
        webApp.get("/api/dog", DogController::createDogApi);
        webApp.put("/api/dog/{id}", DogController::updateDogApi);
        webApp.delete("/api/dog/{id}", DogController::deleteDogApi);
        webApp.after("/api/dog/*", ctx -> {
            ctx.contentType("json");
        });
    }

    public static void getAllDogs(Context ctx) {
        ctx.attribute("dogs", dogs.values().toArray());
    }

    public static void getAllDogsApi(Context ctx) {
        ctx.result(g.toJson(dogs));
    }

    public static void getDogById(Context ctx) {
        Integer id = getIdFromContext(ctx);
        if (id == null) return;
        DogDTO dog = getDogById(id);
        if (dog != null) {
            ctx.attribute("dog", dog);
            ctx.status(202);
        } else {
            ctx.status(400);
        }
    }

    public static void getDogByIdApi(Context ctx) {
        getDogById(ctx);
        if(ctx.statusCode() == 202 && ctx.attribute("dog") != null){
            ctx.result(g.toJson(ctx.attribute("dog")));
        }

    }

    @Nullable
    private static Integer getIdFromContext(Context ctx) {
        String strId = ctx.formParam("id");
        if (strId == null || strId.isEmpty()) {
            ctx.status(400);
            return null;
        }
        int id;
        try {
            id = Integer.parseInt(strId);
        } catch (Exception e) {
            ctx.status(400);
            return null;
        }
        return id;
    }

    @Nullable
    private static DogDTO getDogById(int id) {
        if (dogs.containsKey(id)) {
            return dogs.get(id);
        }
        return null;
    }

    public static void createDog(Context ctx) {
        try {
            String name = ctx.queryParam("name");
            String breed = ctx.queryParam("breed");
            @SuppressWarnings("DataFlowIssue")
            int gender = Integer.parseInt(ctx.queryParam("gender"));
            @SuppressWarnings("DataFlowIssue")
            int age = Integer.parseInt(ctx.queryParam("age"));
            synchronized (dogs) {
                int id = 0;
                if (!dogs.isEmpty()) {
                    Set<Integer> keys = dogs.keySet();
                    id = keys.stream().max(Integer::compareTo).get() + 1;
                }
                DogDTO dog = new DogDTO(id, name, breed, gender, age);
                dogs.put(id, dog);
                ctx.attribute("dog", dog);
            }
            ctx.status(201);
        } catch (NumberFormatException ignored) {
            ctx.status(400);
        }
    }
    public static void createDogApi(Context ctx){
        createDog(ctx);
        if(ctx.statusCode() == 201 && ctx.attribute("dog") != null){
            DogDTO dog = ctx.attribute("dog");
            String str = g.toJson(dog);
            ctx.result(str);
        }
    }

    public static void updateDog(Context ctx) {
        String idStr = ctx.formParam("id");
        int id;
        try{
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException ignored){
            ctx.status(400);
            return;
        }
        synchronized (dogs) {
            if (dogs.containsKey(id)) {
                String name = ctx.formParam("name");
                String breed = ctx.formParam("breed");
                String genderStr = ctx.formParam("gender");
                int gender = -1;
                try {
                    gender = Integer.parseInt(genderStr);
                } catch (NumberFormatException ignored) {}
                String ageStr = ctx.formParam("age");
                int age = -1;
                try {
                    age = Integer.parseInt(ageStr);
                } catch (NumberFormatException ignored) {}

                DogDTO dog = dogs.get(id);

                if(name != null && !name.isEmpty())
                    dog.setName(name);

                if(breed != null && !breed.isEmpty())
                    dog.setBreed(breed);

                if(gender >= 0)
                    dog.setGender(gender);

                if(age >= 0)
                    dog.setAge(age);


                ctx.status(202);
                ctx.attribute("dog", dog);
            }
        }
    }
    public static void updateDogApi(Context ctx) {
        updateDog(ctx);
        if(ctx.statusCode() == 202 && ctx.attribute("dog") != null){
            ctx.result(g.toJson(ctx.attribute("dog")));
        }
    }

    public static void deleteDog(Context ctx) {
        String idStr = ctx.formParam("id");
        int id;
        try{
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException ignored){
            ctx.status(400);
            return;
        }
        synchronized (dogs){
            dogs.remove(id);
            ctx.status(202);
        }
    }
    public static void deleteDogApi(Context ctx) {
        deleteDog(ctx);
        if(ctx.statusCode() == 202){
            ctx.result("{\n\t\"id\": " + ctx.formParam("id") + ",\n\t\"deleted\":\"true\" \n}");
        }
    }
}
