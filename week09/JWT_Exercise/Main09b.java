package week09.JWT_Exercise;

import io.javalin.Javalin;
import week09.JWT_Exercise.controller.UserC;

public class Main09b {
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
