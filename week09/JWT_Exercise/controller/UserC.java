package week09.JWT_Exercise.controller;

import io.javalin.apibuilder.EndpointGroup;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.security.RouteRole;
import jakarta.validation.ValidationException;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import week09.JWT_Exercise.DTO.LoginDTO;
import week09.JWT_Exercise.DTO.TokenDTO;
import week09.JWT_Exercise.config.gsonFactory;
import week09.JWT_Exercise.DAO.concrete.UserDAO;
import week09.JWT_Exercise.DTO.UserDTO;
import week09.JWT_Exercise.model.User;
import week09.JWT_Exercise.utils.myJsonObject;

import java.util.Set;

import static io.javalin.apibuilder.ApiBuilder.*;

public class UserC {
    @Getter
    private static final String jsonContentType = "application/json";
    public enum Role implements RouteRole {
        ANYONE("ANYONE"),
        USER("USER"),
        ADMIN("ADMIN");
        private final String val;
        Role(String s){
            this.val = s;
        }
        @Override
        public String toString() {
            return val;
        }
    }

    public static UserDAO userDAO;

    public static EndpointGroup getRoutes() {
        userDAO = new UserDAO();
        return () -> {
            path("/protected", () -> {
                before(SecurityController.authenticate);
                get("/user_demo",
                        (ctx) -> {
                            myJsonObject jo = new myJsonObject().put("msg", "Hello from Users protected");

                            ctx.header("path", ctx.fullUrl());

                            //// gson way
                            returnJson(ctx, gsonFactory.getGson().toJson(jo));

                            // ctx.json(jo); //// Jackson way
                        },
                        Role.USER);
                get("/admin_demo",
                        (ctx) -> {
                            myJsonObject jo = new myJsonObject().put("msg", "Hello from Users protected");

                            ctx.header("path", ctx.fullUrl());

                            //// gson way
                            returnJson(ctx, gsonFactory.getGson().toJson(jo));

                            // ctx.json(jo); //// Jackson way
                        },
                        Role.ADMIN);
            });
            path("/auth", () -> {
                post("/register", registerUser, Role.ANYONE);
                post("/login", login, Role.ANYONE);
            });
        };
    }
    public static final Handler registerUser = (Context ctx) -> {
        //LoginDTO login = ctx.bodyAsClass(LoginDTO.class); //// Jackson way
        LoginDTO login = gsonFactory.getGson().fromJson(ctx.body(), LoginDTO.class); //// Gson way
        User user = userDAO.createUser(login.getUsername(), login.getPassword());
        if (user == null) {
            throw new ValidationException("User already exists");
        }
        TokenDTO tokenDTO = getTokenDTO(user);
        ctx.header("Authorization", "BEARER " + tokenDTO.getToken());
        ctx.header("username", tokenDTO.getUsername());

        //// gson way
        returnJson(ctx, tokenDTO);

        //ctx.json(dto); //// Jackson way
    };

    public static final Handler login = (Context ctx) -> {
        //LoginDTO userDTO = ctx.bodyAsClass(LoginDTO.class); //// Jackson way
        LoginDTO userDTO = gsonFactory.getGson().fromJson(ctx.body(), LoginDTO.class); //// Gson way
        User user = userDAO.getVerifiedUser(userDTO.getUsername(), userDTO.getPassword());
        if (user == null) {
            throw new ValidationException("password or username is wrong");
        }
        TokenDTO tokenDTO = getTokenDTO(user);
        ctx.header("Authorization", "BEARER " + tokenDTO.getToken());
        ctx.header("username", tokenDTO.getUsername());

        //// gson way
        returnJson(ctx, tokenDTO);

        //ctx.json(tokenDTO); //// Jackson way
    };

    /**
     * This is the same as .json from jackson, but as a custom function.
     */
    private static void returnJson(Context ctx, Object dto) {
        ctx.status(200);
        ctx.contentType(jsonContentType);
        ctx.result(gsonFactory.getGson().toJson(dto));
    }

    @NotNull
    private static TokenDTO getTokenDTO(User user) {
        UserDTO dto = new UserDTO(user);
        SecurityController sc = SecurityController.getInstance();
        String token = sc.createToken(dto);
        TokenDTO tdto = new TokenDTO(dto.getUsername(), token);
        return tdto;
    }
}
