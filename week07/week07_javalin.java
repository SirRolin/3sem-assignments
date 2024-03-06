package week07;

import io.javalin.Javalin;
import week07.controllers.DogController;
import week07.controllers.Veterinarien;

public class week07_javalin {
    public static void main(String[] args) {
        io.javalin.Javalin webApp = Javalin.create();

        //// setups
        DogController.setup(webApp);
        Veterinarien.setup(webApp);

        webApp.start(7007); //// the task asked for this port

    }
}
