package week09.JWT_Exercise.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;
import week09.security_wed_thur.config.gsonFactory;

@Getter
public class myJsonObject {
    private final JsonObject obj = new JsonObject();

    @Override
    public String toString(){
        return gsonFactory.getGson().toJson(obj);
    }
    public myJsonObject put(String field, JsonElement value){
        obj.add(field, value);
        return this;
    }
    public myJsonObject put(String field, String value){
        obj.addProperty(field, value);
        return this;
    }
    public myJsonObject put(String field, Number value){
        obj.addProperty(field, value);
        return this;
    }
    public myJsonObject put(String field, Boolean value){
        obj.addProperty(field, value);
        return this;
    }
    public myJsonObject put(String field, Character value){
        obj.addProperty(field, value);
        return this;
    }
}
