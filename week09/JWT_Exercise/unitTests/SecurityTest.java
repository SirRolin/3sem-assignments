package week09.JWT_Exercise.unitTests;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import week09.JWT_Exercise.DAO.concrete.UserDAO;
import week09.JWT_Exercise.DTO.LoginDTO;
import week09.JWT_Exercise.DTO.UserDTO;
import week09.JWT_Exercise.Main09;
import week09.JWT_Exercise.config.gsonFactory;
import week09.JWT_Exercise.config.hibernate;
import week09.JWT_Exercise.controller.SecurityController;
import week09.JWT_Exercise.controller.UserC;
import week09.JWT_Exercise.model.Role;
import week09.JWT_Exercise.model.User;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SecurityTest {
    private final Gson gson = gsonFactory.getGson();
    private static RequestSpecification userSession;
    private static RequestSpecification adminSession;
    private static LoginDTO userLogin;
    @BeforeAll
    static void setUpAll() {
        hibernate.isDevState = true;
        RestAssured.baseURI = "http://localhost:8888/api";

        Main09.start(8888);

        UserDAO userDAO = new UserDAO();

        //// User
        userLogin = new LoginDTO("Test","Test");
        User user = userDAO.createUser(userLogin.getUsername(), userLogin.getPassword());
        UserDTO userDTO = new UserDTO(user);
        String auth = SecurityController.getInstance().createToken(userDTO);

        userSession = RestAssured
                .given()
                .header("Authorization", "BEARER " + auth);

        //// Admin
        LoginDTO adminLogin = new LoginDTO("Test2","Test2");
        User admin = userDAO.createUser(adminLogin.getUsername(), adminLogin.getPassword());
        Role adminRole = userDAO.createRole(UserC.Role.ADMIN.toString());
        admin = userDAO.addUserRole(adminLogin.getUsername(), adminRole.getRole());
        UserDTO adminDTO = new UserDTO(admin);
        String adminAuth = SecurityController.getInstance().createToken(adminDTO);

        adminSession = RestAssured
                .given()
                .header("Authorization", "BEARER " + adminAuth);
    }

    @AfterAll
    static void tearDownAll() {
        Main09.stopServer();
    }

    @Test
    public void registerTest(){
        LoginDTO login = new LoginDTO("registerTest", "TestPassword");
        Response Res = RestAssured
                .given()
                .when()
                .body(gson.toJson(login))
                .post("/auth/register");
        System.out.println(Res.header("path"));
        Res.then().statusCode(200);
    }

    @Test
    public void LoginTest(){
        Response Res = RestAssured
                .given()
                .when()
                .body(gson.toJson(userLogin))
                .post("/auth/login");
        System.out.println(Res.header("path"));
        Res.then()
                .statusCode(200);
    }
    @Test
    public void UserDemoTest(){
        Response Res = userSession
                .when()
                .get("/protected/user_demo");
        Res
                .then()
                .statusCode(200);
    }
    @Test
    public void AdminDemoTest(){
        Response adminRes = adminSession
                .when()
                .get("/protected/admin_demo");
        adminRes
                .then()
                .statusCode(200);

        Response userRes = userSession
                .when()
                .get("/protected/admin_demo");
        adminRes
                .then()
                .statusCode(403);

    }
}
