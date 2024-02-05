package week02.DTO;

import com.google.gson.*;

import java.time.LocalDate;

public class MovieInstantiationer implements JsonDeserializer<MovieDTO> {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Override
    public MovieDTO deserialize(JsonElement jsonElement, java.lang.reflect.Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        MovieDTO result = gson.fromJson(jsonElement, MovieDTO.class);
        result.finalizeLoading();
        return result;
    }
}
