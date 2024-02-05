package week02.DTO;

import com.google.gson.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class DTOMain{
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(MovieDTO.class, new MovieInstantiationer()).create();
    public static void main(String[] args){
        OkHttpClient client = new OkHttpClient();

        Request request = FindaMovieWithKeyword("The Ministry of ungentlemanly Warfare");

        try {
            Response response = client.newCall(request).execute();
            assert response.body() != null;
            String json = response.body().string();
            System.out.println(json);
            responseDTO obj = gson.fromJson(json, responseDTO.class);
            System.out.println(obj.results[0]);
            //System.out.println(((JsonArray) obj.get("results")).size());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Request FindaMovieWithKeyword(String keyword) {
        return new Request.Builder()
                .url("https://api.themoviedb.org/3/search/movie?query=" + keyword + "&include_adult=false&language=en-US&page=1")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkOTQ2MGVkYzlkYjMyNDYzYjFjZjg2YzRhNTE2NDgxOCIsInN1YiI6IjY1YzBjYzhjYmYwOWQxMDE4NGE3OGFlYyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.7oXOM3iuoSy0yjc_oTiE4hGpR74_1PmAZu0G57QRrKo")
                .build();
    }
}
