package week08.security_wed_thur;

import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
import week08.hotel_mon_tues.config.gsonFactory;
import week08.security_wed_thur.controller.UserC;

public class Main08b {
    private static Javalin webApp;
    public static void main(String[] args) {
        start(7070);
    }

    public static void start(Integer port){
        webApp = Javalin.create(javalinConfig -> {
            javalinConfig.http.defaultContentType = "application/json";
            javalinConfig.router.contextPath = "/api";
            javalinConfig.bundledPlugins.enableDevLogging();
            javalinConfig.router.apiBuilder(UserC.getRoutes());
        });



        webApp.start(port);
    }
    public static void stopServer(){
        if(webApp!=null)
            webApp.stop();
    }
}
