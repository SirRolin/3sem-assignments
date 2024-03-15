package week08.security_wed_thur.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import io.javalin.security.RouteRole;
import io.restassured.internal.mapping.GsonMapper;
import jakarta.validation.ValidationException;
import week08.hotel_mon_tues.config.gsonFactory;
import week08.security_wed_thur.DAO.UserDAO;
import week08.security_wed_thur.DTO.UserDTO;
import week08.security_wed_thur.model.User;
import week08.security_wed_thur.utils.myJsonObject;

import static io.javalin.apibuilder.ApiBuilder.*;

public class UserC {
    public enum Role implements RouteRole {ANYONE, USER, ADMIN}

    public static UserDAO userDAO = new UserDAO();

    public static EndpointGroup getRoutes() {
        return () -> {
            path("/auth", () -> {
                post("/register", registerUser, Role.ANYONE);
                post("/login", login, Role.ANYONE);
            });
            path("/protected", () -> {
                before(SecurityController.authenticate);
                get("/user_demo",
                        (ctx) -> ctx.json(new myJsonObject().put("msg", "Hello from Users protected")),
                        Role.USER);
            });
        };
    }

    private static final Handler registerUser = (Context ctx) -> {
        UserDTO userDTO = ctx.bodyAsClass(UserDTO.class);
        User user = userDAO.createUser(userDTO.getUsername(), userDTO.getPassword());
        if (user == null) {
            throw new ValidationException("User already exists");
        }
        UserDTO dto = new UserDTO(user);
        ctx.json(dto);
    };

    private static final Handler login = (Context ctx) -> {
        UserDTO userDTO = ctx.bodyAsClass(UserDTO.class);
        User user = userDAO.getVerifiedUser(userDTO.getUsername(), userDTO.getPassword());
        if (user == null) {
            throw new ValidationException("password or username is wrong");
        }
        UserDTO dto = new UserDTO(user);
        ctx.json(dto);
    };
}
