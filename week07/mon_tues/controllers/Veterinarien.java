package week07.mon_tues.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.http.Context;
import week07.mon_tues.DTO.AppointmentDTO;
import week07.mon_tues.adapters.LocalDateTimeAdapter;

import java.time.LocalDateTime;
import java.util.HashMap;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Veterinarien {
    private static final HashMap<Integer, AppointmentDTO> appointments = new HashMap<>();
    private static int count = 0;
    private static final Gson g = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).setPrettyPrinting().create();

    public static void setup(Javalin webApp) {
        webApp.get("/api/vet/appointments", Veterinarien::getFutureAppointments);
        webApp.get("/api/vet/appointment/{id}", Veterinarien::getAppointmentByIdApi);
        webApp.get("/api/vet/patients/{id}", Veterinarien::getPatientByIdApi);
        webApp.get("/api/vet/medicals/{id}", Veterinarien::getMedicalsByIdApi);
        webApp.get("/api/vet/all", Veterinarien::getAllAppointmentsApi);
        webApp.before("/api/vet/*", ctx -> {
            System.out.println(LocalDateTime.now() + ": API was called on path: " + ctx.path());
        });
        webApp.after("/api/vet/*", ctx -> {
            ctx.contentType("json");
        });
    }

    public static EndpointGroup getEndpoints(){
        return () -> {
            path("/api/vet", () -> {
                get("/api/vet/appointments", Veterinarien::getFutureAppointments);
                get("/api/vet/appointment/{id}", Veterinarien::getAppointmentByIdApi);
                get("/api/vet/patients/{id}", Veterinarien::getPatientByIdApi);
                get("/api/vet/medicals/{id}", Veterinarien::getMedicalsByIdApi);
                get("/api/vet/all", Veterinarien::getAllAppointmentsApi);
                before("/api/vet/*", ctx -> {
                    System.out.println(LocalDateTime.now() + ": API was called on path: " + ctx.path());
                });
                after("/api/vet/*", ctx -> {
                    ctx.contentType("json");
                });
            });
        };
    }

    private static void getFutureAppointmentsApi(Context ctx) {
        getFutureAppointments(ctx);
        if (ctx.attribute("appointments") != null) {
            ctx.result(g.toJson(ctx.attribute("appointments")));
        }
    }

    private static void getFutureAppointments(Context ctx) {
        ctx.attribute("appointments",
                (AppointmentDTO[]) appointments.values().stream()
                        .filter(x -> x.getTime().isAfter(LocalDateTime.now()))
                        .toArray());
    }

    private static void getAllAppointmentsApi(Context ctx) {
        getAllAppointments(ctx);
        if (ctx.attribute("appointments") != null) {
            AppointmentDTO[] arr = ctx.attribute("appointments");
            ctx.result(g.toJson(arr));
        }
    }

    private static void getAllAppointments(Context ctx) {
        ctx.attribute("appointments", appointments.values().toArray(new AppointmentDTO[0]));
    }

    private static void getAppointmentByIdApi(Context ctx) {
        getAppointmentById(ctx);
        if (ctx.statusCode() < 400 && ctx.attribute("appointment") != null) {
            ctx.result(g.toJson(ctx.attribute("appointment")));
        }
    }

    private static void getAppointmentById(Context ctx) {
        String id = ctx.pathParam("id");
        if (!id.isEmpty()) {
            ctx.attribute("appointment", appointments.get(id));
            ctx.status(202);
        } else
            ctx.status(400);
    }

    private static void getPatientByIdApi(Context ctx) {
        getPatientById(ctx);
        if (ctx.statusCode() < 400 && ctx.attribute("appointment") != null) {
            ctx.result(g.toJson(ctx.attribute("appointment")));
        }
    }
    private static void getPatientById(Context ctx) {
        String id = ctx.pathParam("id");
        if (!id.isEmpty()) {
            ctx.attribute("appointment", appointments.get(id));
            ctx.status(202);
        } else
            ctx.status(400);
    }

    private static void getMedicalsByIdApi(Context ctx) {
        getAppointmentById(ctx);
        if (ctx.statusCode() < 400 && ctx.attribute("appointment") != null) {
            ctx.result(g.toJson(((AppointmentDTO) ctx.attribute("appointment")).getMedications()));
        }
    }


}
