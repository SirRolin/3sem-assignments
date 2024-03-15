package week08.security_wed_thur.config;

import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.servlet.http.HttpServletRequest;
import week08.security_wed_thur.utils.myJsonObject;

public class ApplicationConfig {
    private Javalin app;
    private static ApplicationConfig instance;
    private ApplicationConfig(){
    }

    public static ApplicationConfig getInstance(){
        if(instance == null){
            instance = new ApplicationConfig();
        }
        return instance;
    }

    public ApplicationConfig initiateServer(){
        app = Javalin.create(config -> {
            config.http.defaultContentType = "application/json";
            config.router.contextPath = "/api"; // what ever you want your urls starts with
        });

        app.before( ctx -> {
            HttpServletRequest request = ctx.req();
            System.out.println(request);
        });
        return instance;
    }
    public ApplicationConfig setRoute(EndpointGroup route){
        //app.routes(route); //// Doesn't exist
        return instance;
    }

    /*
    Exception Handling:
     */
    public ApplicationConfig setExceptionOverallHandling(){
        app.exception(NumberFormatException.class,(e,ctx) ->{
            myJsonObject node = new myJsonObject().put("Bad request: Not a number!",e.getMessage());
            ctx.status(400).json(node);
        });
        app.exception(NullPointerException.class,(e,ctx) -> {
            myJsonObject node = new myJsonObject().put("Bad request: Not found!",e.getMessage());
            ctx.status(404).json(node);
        });
        app.exception(Exception.class, (e,ctx) ->{
            myJsonObject node = new myJsonObject().put("errorMessage",e.getMessage());
            ctx.status(500).json(node);
        });
        return instance;
    }
    // public ApplicationConfig setException
    public ApplicationConfig startServer(int port){
        app.start(port);
        return instance;
    }
}
