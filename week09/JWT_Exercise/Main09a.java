package week09.JWT_Exercise;

import io.javalin.Javalin;
import week09.JWT_Exercise.controller.HotelController;
import week09.JWT_Exercise.controller.RoomController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main09a {
    private static Javalin webApp;
    public static void main(String[] args) {
        start(7070);
    }

    public static void start(Integer port){
        webApp = Javalin.create().start(port);

        //// setups
        HotelController.setup(webApp);
        RoomController.setup(webApp);

        /// Error handling
        webApp.error(404, ctx -> {
            String htmlContent = new String(Files.readAllBytes(Paths.get("week09/JWT_Exercise/html/Not Found.html")));
            ctx.contentType("text/html");
            ctx.html(htmlContent);
        });
        webApp.exception(IllegalStateException.class, (e, ctx) -> {
            e.printStackTrace();
            String htmlContent;
            try {
                htmlContent = new String(Files.readAllBytes(Paths.get("week09/JWT_Exercise/html/Error.html")));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            ctx.contentType("text/html");
            ctx.html(htmlContent);
        });
    }
    public static void stopServer(){
        if(webApp!=null)
            webApp.stop();
    }
}
