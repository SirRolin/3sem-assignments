package week09.JWT_Exercise.controller;

import io.javalin.apibuilder.EndpointGroup;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.security.RouteRole;
import jakarta.validation.ValidationException;
import week09.JWT_Exercise.config.gsonFactory;
import week09.JWT_Exercise.DAO.concrete.UserDAO;
import week09.JWT_Exercise.DTO.UserDTO;
import week09.JWT_Exercise.model.User;
import week09.JWT_Exercise.utils.myJsonObject;

import static io.javalin.apibuilder.ApiBuilder.*;

public class UserC {
    private static final String jsonContentType = "application/json";
    public enum Role implements RouteRole {ANYONE, USER, ADMIN}

    public static UserDAO userDAO = new UserDAO();

    public static EndpointGroup getRoutes() {
        return () -> {
            path("/auth", () -> {
                post("/register", registerUser);
                post("/login", login);
            });
            path("/protected", () -> {
                before(SecurityController.authenticate);
                get("/user_demo",
                        (ctx) -> {
                            myJsonObject jo = new myJsonObject().put("msg", "Hello from Users protected");

                            //// start of gson way
                            ctx.status(200);
                            ctx.contentType(jsonContentType);
                            ctx.result(gsonFactory.getGson().toJson(jo));
                            //// end of gson way

                            // ctx.json(jo); //// Jackson way
                        },
                        Role.USER);
            });
        };
    }

    public static final Handler registerUser = (Context ctx) -> {
        //UserDTO userDTO = ctx.bodyAsClass(UserDTO.class); //// Jackson way
        UserDTO userDTO = gsonFactory.getGson().fromJson(ctx.body(), UserDTO.class); //// Gson way
        User user = userDAO.createUser(userDTO.getUsername(), userDTO.getPassword());
        if (user == null) {
            throw new ValidationException("User already exists");
        } else {
            for(String r: userDTO.getRoles()){
                userDAO.addUserRole(user.getUsername(), r);
            }
        }
        UserDTO dto = new UserDTO(user);

        //// start of gson way
        ctx.status(200);
        ctx.contentType(jsonContentType);
        ctx.result(gsonFactory.getGson().toJson(dto));
        //// end of gson way

        //ctx.json(dto); //// Jackson way
    };

    public static final Handler login = (Context ctx) -> {
        //UserDTO userDTO = ctx.bodyAsClass(UserDTO.class); //// Jackson way
        UserDTO userDTO = gsonFactory.getGson().fromJson(ctx.body(), UserDTO.class); //// Gson way
        User user = userDAO.getVerifiedUser(userDTO.getUsername(), userDTO.getPassword());
        if (user == null) {
            throw new ValidationException("password or username is wrong");
        }
        UserDTO dto = new UserDTO(user);
        //// start of gson way
        ctx.status(200);
        ctx.contentType(jsonContentType);
        ctx.result(gsonFactory.getGson().toJson(dto));
        //// end of gson way
        //ctx.json(dto); //// Jackson way
    };
}
