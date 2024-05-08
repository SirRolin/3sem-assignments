package week13.ReactIII;

import io.javalin.Javalin;
import io.javalin.http.HttpStatus;
import io.javalin.websocket.WsConfig;
import io.javalin.websocket.WsHandlerType;
import lombok.Getter;
import week13.ReactIII.controller.HotelController;
import week13.ReactIII.controller.SecurityController;
import week13.ReactIII.controller.UserC;
import week13.ReactIII.utils.myJsonObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Consumer;

public class Main13 {
    private static Javalin webApp;
    @Getter
    private static boolean serverStarted = false; //// part of debugging a very weird error.
    @Getter
    private static boolean serverFailed = false;
    @Getter
    private static boolean serverStillRunning = false;

    public static void main(String[] args) {
        start(7070);
    }

    public static void start(Integer port) {
        webApp = Javalin.create(javalinConfig -> {
            javalinConfig.http.defaultContentType = "application/json";
            javalinConfig.router.contextPath = "/api";
            javalinConfig.bundledPlugins.enableDevLogging();
            javalinConfig.router.apiBuilder(UserC.getRoutes()); //// function way
        });

        webApp.beforeMatched(ctx -> {
            if (ctx.routeRoles().contains(UserC.Role.ANYONE)) {
                return;
            }
            SecurityController.authenticate.handle(ctx);
        });

        webApp.events(event -> {
            event.serverStarted(() -> {
                serverStarted = true;
                serverFailed = false;
                serverStillRunning = true;
            });
            event.serverStartFailed(() -> {
                serverStarted = true;
                serverFailed = true;
                serverStillRunning = false;
            });
            event.serverStopping(() -> {
                serverStillRunning = false;
            });
        });

        webApp.error(404, ctx -> {
            String htmlContent = new String(Files.readAllBytes(Paths.get("week09/JWT_Exercise/html/Not Found.html")));
            ctx.header("path", ctx.fullUrl());
            ctx.contentType("text/html");
            ctx.html(htmlContent);
        });

        webApp.exception(IllegalStateException.class, (e, ctx) -> {
            System.out.println(e);
            String htmlContent;
            try {
                htmlContent = new String(Files.readAllBytes(Paths.get("week09/JWT_Exercise/html/Error.html")));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            ctx.header("path", ctx.fullUrl());
            ctx.contentType("text/html");
            ctx.html(htmlContent);
        });

        webApp.exception(Exception.class, (e, ctx) -> {
            System.out.println(e);
        });


        webApp.start(port);
    }

    public static void stopServer() {
        if (webApp != null)
            webApp.stop();
    }
}
