package week13.ReactIII.unitTests;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import week13.ReactIII.DAO.concrete.UserDAO;
import week13.ReactIII.DTO.LoginDTO;
import week13.ReactIII.DTO.UserDTO;
import week13.ReactIII.Main09;
import week13.ReactIII.config.gsonFactory;
import week13.ReactIII.config.hibernate;
import week13.ReactIII.controller.SecurityController;
import week13.ReactIII.controller.UserC;
import week13.ReactIII.model.Role;
import week13.ReactIII.model.User;

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
