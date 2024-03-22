package week09.JWT_Exercise.unitTests;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import week09.JWT_Exercise.DAO.concrete.HotelDAO;
import week09.JWT_Exercise.DAO.concrete.UserDAO;
import week09.JWT_Exercise.DTO.LoginDTO;
import week09.JWT_Exercise.DTO.UserDTO;
import week09.JWT_Exercise.Main09;
import week09.JWT_Exercise.controller.SecurityController;
import week09.JWT_Exercise.controller.UserC;
import week09.JWT_Exercise.model.Hotel;
import week09.JWT_Exercise.config.hibernate;
import week09.JWT_Exercise.config.gsonFactory;
import week09.JWT_Exercise.model.User;

import static org.hamcrest.Matchers.equalTo;

class RestTest {
    private static final Hotel selectHotel = new Hotel("select", "address select");
    private static final Hotel postHotel = new Hotel("post", "address post");
    private static final Hotel putHotel = new Hotel("put", "address put");
    private static final Hotel deleteHotel = new Hotel("delete", "address delete");
    private static RequestSpecification loggedInSession; //// Created cause hotel functions now require a user
    private final Gson gson = gsonFactory.getGson();
    @BeforeAll
    static void setUpAll() {
        hibernate.isDevState = true;
        RestAssured.baseURI = "http://localhost:8888/api";

        HotelDAO hotels = new HotelDAO();
        selectHotel.addRoom(1, 1.2);
        hotels.create(selectHotel);
        hotels.create(putHotel);
        hotels.create(deleteHotel);
        Main09.start(8888);
        LoginDTO login = new LoginDTO("Test","Test");
        UserDAO userDAO = new UserDAO();
        User user = userDAO.createUser(login.getUsername(), login.getPassword());
        UserDTO userDTO = new UserDTO(user);
        String auth = SecurityController.getInstance().createToken(userDTO);

        loggedInSession = RestAssured
                .given()
                .header("Authorization", "BEARER " + auth);

    }

    @AfterAll
    static void tearDownAll() {
        Main09.stopServer();
    }

    @Test
    @DisplayName("Get All \"/hotel\"")
    void testGetAll() {
        loggedInSession
                .when()
                .get("/hotel")
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Get Specific \"/hotel/{id}\"")
    void testGetSpecific() {
        loggedInSession
                .when()
                .get("/hotel/" + selectHotel.getID()).then()
                .statusCode(200)
                .assertThat().body("name", equalTo("select"));
    }

    @Test
    @DisplayName("Get Specific Hotel's Rooms \"/hotel/{id}/rooms\"")
    void testGetSpecificRoom() {
        loggedInSession
                .when()
                .get("/hotel/" + selectHotel.getID() +"/rooms")
                .then()
                .statusCode(200)
                .assertThat().body("rooms[0].number", equalTo(selectHotel.getRooms().get(0).getNumber()));
    }

    @Test
    @DisplayName("Post Hotel \"/hotel\"")
    void testPostHotel() {
        int number = 2;
        postHotel.addRoom(number,5.1);
        loggedInSession
                .when()
                .body(gson.toJson(postHotel))
                .post("/hotel")
                .then()
                .statusCode(202)
                .assertThat().body("rooms[0].number", equalTo(number));
    }

    @Test
    @DisplayName("Put Hotel \"/hotel/{id}\"")
    void testPutHotel() {
        int number = 1;
        putHotel.addRoom(number,99.2).setAddress("put address updated");
        loggedInSession
                .when()
                .body(gson.toJson(putHotel))
                .put("/hotel/{id}", putHotel.getID())
                .then()
                .statusCode(200)
                .assertThat().body("rooms[0].number", equalTo(number));
    }

    @Test
    @DisplayName("Delete Hotel \"/hotel/{id}\"")
    void testDeleteHotel() {
        loggedInSession
                .when()
                .delete("/hotel/" + deleteHotel.getID())
                .then()
                .statusCode(204);
    }
}