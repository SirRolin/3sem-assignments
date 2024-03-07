package week07.wed_thur;

import io.javalin.Javalin;
import week07.wed_thur.controller.HotelController;
import week07.wed_thur.controller.RoomController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main07b {
    public static void main(String[] args) {
        Javalin webApp = Javalin.create().start(7070);

        //// setups
        HotelController.setup(webApp);
        RoomController.setup(webApp);

        /// Error handling
        webApp.error(404, ctx -> {
            String htmlContent = new String(Files.readAllBytes(Paths.get("week07/wed_thur/html/Not Found.html")));
            ctx.contentType("text/html");
            ctx.html(htmlContent);
        });
        webApp.exception(IllegalStateException.class, (e, ctx) -> {
            e.printStackTrace();
            String htmlContent = null;
            try {
                htmlContent = new String(Files.readAllBytes(Paths.get("week07/wed_thur/html/Error.html")));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            ctx.contentType("text/html");
            ctx.html(htmlContent);
        });

    }
}
