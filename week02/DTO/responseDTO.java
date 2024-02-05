package week02.DTO;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class responseDTO implements JsonDeserializer<responseDTO> {
    MovieDTO[] results;
    int page;
    int total_pages;
    int total_results;

    @Override
    public responseDTO deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        for (int i = 0; i < results.length; i++) {
            results[i].release_year = "1";
        }
        return this;
    }
}
