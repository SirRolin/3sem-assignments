package week13.ReactIII.unitTests;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import week13.ReactIII.DAO.concrete.HotelDAO;
import week13.ReactIII.DAO.concrete.UserDAO;
import week13.ReactIII.DTO.LoginDTO;
import week13.ReactIII.DTO.UserDTO;
import week13.ReactIII.Main09;
import week13.ReactIII.controller.SecurityController;
import week13.ReactIII.controller.UserC;
import week13.ReactIII.model.Hotel;
import week13.ReactIII.config.hibernate;
import week13.ReactIII.config.gsonFactory;
import week13.ReactIII.model.User;

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