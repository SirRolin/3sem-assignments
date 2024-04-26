package week07.mon_tues;

import io.javalin.Javalin;
import week07.mon_tues.controllers.DogController;
import week07.mon_tues.controllers.Veterinarien;

public class Main07a {
    public static void main(String[] args) {
        io.javalin.Javalin webApp = Javalin.create();

        //// setups
        DogController.setup(webApp);
        Veterinarien.setup(webApp);

        webApp.start(7007); //// the task asked for this port

    }
}
