package week02.Threads;

import com.google.gson.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Exercise6 {
    public static void main(String[] args) {
        List<DTO> fetchList = new ArrayList<>();
        fetchList.add(new dadJokesDTO());
        fetchList.add(new chuckNorrisDTO());
        fetchList.add(new kanyeDTO());
        fetchList.add(new trumpDTO());
        fetchList.add(new spaceXDTO());
        fetchList.add(new pokemonDTO());
        fetchList.add(new boredDTO());
        fetchList.add(new numbersDTO());
        fetchList.add(new dogDTO());
        //// You must dream about the 10th as that either costs money, requires a library of countries, names or more.
        //// just getting 9 of these was a lot of work.
        ScheduledExecutorService scheduledToBeReadded = Executors.newScheduledThreadPool(1);

        Runnable printRandomJoke = () -> {
            Random rng = new Random();
            int indexOfList = rng.nextInt(fetchList.size());
            final DTO dto = fetchList.get(indexOfList);

            //// I don't want the same link to spaceX 3-10 times. So it's limited to once per 50 seconds.
            //// I do this by removing it from the list and creating a new thread which is delayed by 50 seconds, before it adds it back to the list.

            if (dto.removeAfterRun() & fetchList.contains(dto)) {
                synchronized (fetchList) {
                    fetchList.remove(dto);
                }
                scheduledToBeReadded.schedule(() -> {
                    synchronized (fetchList) {
                        fetchList.add(dto);
                    }
                }, 50L, TimeUnit.SECONDS);
            }
            System.out.println(dto.getClass().getSimpleName() + ": " + dto.convertJson(dto.fetchDTO())[0].getJoke());
        };

        ExecutorService exe = Executors.newFixedThreadPool(1);
        for (int i = 0; i < 50; i++) {
            exe.execute(printRandomJoke);
        }
        exe.shutdown();
        try {
            System.out.println("Terminated without problem? " + exe.awaitTermination(10L, TimeUnit.DAYS));
            scheduledToBeReadded.shutdownNow().forEach(Runnable::run);
            scheduledToBeReadded.awaitTermination(10L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Still in fetchList:");
        fetchList.forEach((x) -> System.out.println(x.getClass().getSimpleName()));
    }

    private static abstract class DTO {
        public static Gson gson = new GsonBuilder().setPrettyPrinting().create();

        public abstract DTO convertJson(JsonObject json);

        public abstract DTO[] convertJson(JsonArray json);

        public DTO[] convertJson(String json) {
            JsonElement element = gson.fromJson(json, JsonElement.class);
            if (element.isJsonArray()) {
                return convertJson(element.getAsJsonArray());
            } else if (element.isJsonObject()) {
                DTO[] result = new DTO[1];
                result[0] = convertJson(element.getAsJsonObject());
                return result;
            }
            return null;
        }

        public abstract String getJoke();

        public abstract String fetchDTO();

        //// Due to having done this in another exercise I suppress the warning.
        @SuppressWarnings("DuplicatedCode")
        public String getJsonString(String url, Map<String, String> headersToAdd) {
            Request.Builder reqBuilder = new Request.Builder()
                    .url(url)
                    .get();

            headersToAdd.forEach(reqBuilder::addHeader);

            Request req = reqBuilder.build();
            OkHttpClient client = new OkHttpClient();
            try {
                Response response = client.newCall(req).execute();
                assert response.body() != null;
                return response.body().string();
            } catch (IOException e) {
                System.out.println("You likely don't have internet or otherwise can't get a connection to: " + url);
                throw new RuntimeException(e);
            }
        }

        public boolean removeAfterRun() {
            return false;
        }
    }

    private static class dadJokesDTO extends DTO {
        @Override
        public DTO convertJson(JsonObject json) {
            return DTO.gson.fromJson(json, dadJokesDTO.class);
        }

        @Override
        public DTO[] convertJson(JsonArray json) {
            return DTO.gson.fromJson(json, dadJokesDTO[].class);
        }

        @Override
        public String getJoke() {
            return joke;
        }

        @Override
        public String fetchDTO() {
            return getJsonString("https://icanhazdadjoke.com/", Map.of("Accept", "application/json"));
        }

        public String joke;

    }

    private static class chuckNorrisDTO extends DTO {
        @Override
        public DTO convertJson(JsonObject json) {
            return gson.fromJson(json, chuckNorrisDTO.class);
        }

        @Override
        public DTO[] convertJson(JsonArray json) {
            return DTO.gson.fromJson(json, chuckNorrisDTO[].class);
        }

        @Override
        public String getJoke() {
            return value;
        }

        @Override
        public String fetchDTO() {
            return getJsonString("https://api.chucknorris.io/jokes/random", Map.of("Accept", "application/json"));
        }

        String value;
    }

    private static class kanyeDTO extends DTO {
        @Override
        public DTO convertJson(JsonObject json) {
            return gson.fromJson(json, kanyeDTO.class);
        }

        @Override
        public DTO[] convertJson(JsonArray json) {
            return DTO.gson.fromJson(json, kanyeDTO[].class);
        }

        @Override
        public String getJoke() {
            return quote;
        }

        @Override
        public String fetchDTO() {
            return getJsonString("https://api.kanye.rest/", Map.of("Accept", "application/json"));
        }

        String quote;
    }

    private static class trumpDTO extends DTO {
        @Override
        public DTO convertJson(JsonObject json) {
            return gson.fromJson(json, trumpDTO.class);
        }

        @Override
        public DTO[] convertJson(JsonArray json) {
            return DTO.gson.fromJson(json, trumpDTO[].class);
        }

        @Override
        public String getJoke() {
            return message;
        }

        @Override
        public String fetchDTO() {
            return getJsonString("https://api.whatdoestrumpthink.com/api/v1/quotes/random", Map.of("Accept", "application/json"));
        }

        String message;
    }

    private static class spaceXDTO extends DTO {
        @Override
        public DTO convertJson(JsonObject json) {
            return gson.fromJson(json, spaceXDTO.class);
        }

        @Override
        public DTO[] convertJson(JsonArray json) {
            return DTO.gson.fromJson(json, spaceXDTO[].class);
        }

        @Override
        public String getJoke() {
            return links.get("reddit").getAsJsonObject().get("launch").getAsString();
        }

        @Override
        public String fetchDTO() {
            return getJsonString("https://api.spacexdata.com/v5/launches/latest", Map.of("Accept", "application/json"));
        }

        @Override
        public boolean removeAfterRun() {
            return true;
        }

        JsonObject links;
    }

    private static class pokemonDTO extends DTO {
        @Override
        public DTO convertJson(JsonObject json) {
            return gson.fromJson(json, pokemonDTO.class);
        }

        @Override
        public DTO[] convertJson(JsonArray json) {
            return DTO.gson.fromJson(json, pokemonDTO[].class);
        }

        @Override
        public String getJoke() {
            if (name == null) {
                return results.getAsString();
            }
            StringBuilder sb = new StringBuilder();
            JsonArray ja = abilities.getAsJsonArray();
            for (int i = 0; i < ja.size(); i++) {
                JsonObject abilities = ja.get(i).getAsJsonObject();
                JsonObject ability = abilities.get("ability").getAsJsonObject();
                sb.append("\n\t");
                if (abilities.has("slot"))
                    sb.append(abilities.get("slot").getAsString()).append(": ");
                else
                    sb.append("unknown: ");
                if (ability.has("name"))
                    sb.append(ability.get("name").getAsString()).append(".");
                else
                    sb.append("unknown.");
            }
            return name + " - " + order + " base_xp: " + base_experience + sb;
        }

        @Override
        public String fetchDTO() {
            Random rng = new Random();
            int randomPokemonID = rng.nextInt(1302); //// could get it dynamically, but I don't feel like pulling data from them 3 times per call.
            String firstJString = getJsonString("https://pokeapi.co/api/v2/pokemon/?offset=" + randomPokemonID + "&limit=1", Map.of());
            DTO jsonEle = convertJson(firstJString)[0];
            String second = null;
            if (jsonEle instanceof pokemonDTO pDTO) {
                second = getJsonString("https://pokeapi.co/api/v2/pokemon/" + pDTO.results.getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString()
                        , Map.of());
            }
            return second;
        }

        JsonElement results;
        JsonElement abilities;
        int base_experience;
        String name;
        int order;
    }

    private static class boredDTO extends DTO {
        @Override
        public DTO convertJson(JsonObject json) {
            return DTO.gson.fromJson(json, boredDTO.class);
        }

        @Override
        public DTO[] convertJson(JsonArray json) {
            return DTO.gson.fromJson(json, boredDTO[].class);
        }

        @Override
        public String getJoke() {
            return activity;
        }

        @Override
        public String fetchDTO() {
            return getJsonString("https://www.boredapi.com/api/activity/", Map.of("Accept", "application/json"));
        }

        public String activity;

    }

    private static class numbersDTO extends DTO {
        @Override
        public DTO convertJson(JsonObject json) {
            return DTO.gson.fromJson(json, numbersDTO.class);
        }

        @Override
        public DTO[] convertJson(JsonArray json) {
            return DTO.gson.fromJson(json, numbersDTO[].class);
        }

        @Override
        public String getJoke() {
            return text;
        }

        @Override
        public String fetchDTO() {
            //noinspection HttpUrlsUsage
            return getJsonString("http://numbersapi.com/random?json", Map.of("Accept", "application/json"));
        }

        public String text;

    }

    private static class dogDTO extends DTO {
        @Override
        public DTO convertJson(JsonObject json) {
            return DTO.gson.fromJson(json, dogDTO.class);
        }

        @Override
        public DTO[] convertJson(JsonArray json) {
            return DTO.gson.fromJson(json, dogDTO[].class);
        }

        @Override
        public String getJoke() {
            return message;
        }

        @Override
        public String fetchDTO() {
            return getJsonString("https://dog.ceo/api/breeds/image/random", Map.of("Accept", "application/json"));
        }

        public String message;

    }
}
