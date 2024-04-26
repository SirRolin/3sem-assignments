package week02.DTO;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MovieControllerTest {

    private static final MovieController movieCon = new MovieController();

    @org.junit.jupiter.api.Test
    void getByKeyword() {
        List<String> list = Arrays.asList(
        "The Shawshank Redemption",
        "The Godfather",
        "The Dark Knight",
        "The Godfather: Part II",
        "The Lord of the Rings: The Return of the King",
        "Pulp Fiction",
        "12 Angry Men",
        "The Good, the Bad and the Ugly",
        "Forrest Gump",
        "Fight Club",
        "Inception");
        Movie[][] movies = new Movie[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            movies[i] = movieCon.getByKeyword(list.get(i)).results;
        }
        assertTrue(Arrays.stream(movies).allMatch((movies1 -> movies1.length > 0)));
    }


    @Test
    void getByRating() {
        assertTrue(movieCon.getByRating(8.5F).results.length > 0);
    }

    @Test
    void getSortedByReleaseDate() {
        assertTrue(movieCon.getSortedByReleaseDate().results.length > 0);
    }
}