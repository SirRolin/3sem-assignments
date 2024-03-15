package week08.security_wed_thur;

import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;
import week08.security_wed_thur.controller.SecurityController;
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
            //javalinConfig.plugins.enableDevLogging(); //// ?
            javalinConfig.bundledPlugins.enableDevLogging();
            javalinConfig.router.apiBuilder(UserC.getRoutes()); // this works but the app.routes doesn't
        });



        webApp.start(port);
    }
    public static void stopServer(){
        if(webApp!=null)
            webApp.stop();
    }
}
