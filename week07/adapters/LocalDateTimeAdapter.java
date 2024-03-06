package week07.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;

public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
    @Override
    public void write(JsonWriter jsonWriter, LocalDateTime localDateTime) throws IOException {
        if(localDateTime == null){
            jsonWriter.nullValue();
            return;
        }
        String output = String.format("%s:%s:%s-%s/%s-%s", localDateTime.getSecond(), localDateTime.getMonthValue(), localDateTime.getYear(), localDateTime.getDayOfMonth(), localDateTime.getMonthValue(), localDateTime.getYear());
        jsonWriter.value(output);
    }

    @Override
    public LocalDateTime read(JsonReader jsonReader) throws IOException {
        if(jsonReader.peek() == JsonToken.NULL){
            jsonReader.nextNull();
            return null;
        }
        String input = jsonReader.nextString();
        int year = Integer.parseInt(input.split("-")[2]);
        int month = Integer.parseInt(input.split("-")[1].split("/")[1]);
        int day = Integer.parseInt(input.split("-")[1].split("/")[0]);
        int hour = Integer.parseInt(input.split("-")[0].split(":")[2]);
        int minute = Integer.parseInt(input.split("-")[0].split(":")[1]);
        int second = Integer.parseInt(input.split("-")[0].split(":")[0]);

        return LocalDateTime.of(year, month, day, hour, minute, second);
    }
}
