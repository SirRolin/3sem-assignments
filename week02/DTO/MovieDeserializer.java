package week02.DTO;

import com.google.gson.*;

public class MovieDeserializer implements JsonDeserializer<Movie[]> {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Override
    public Movie[] deserialize(JsonElement jsonElement, java.lang.reflect.Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Movie[] result = gson.fromJson(jsonElement, Movie[].class);
        for (Movie movieDTO : result) {
            movieDTO.finalizeLoading();
        }
        return result;
    }
}
