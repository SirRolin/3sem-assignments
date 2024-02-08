package week02.JSON;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class PersonDTO {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private String firstName;
    private String lastName;
    private String birthDate;
    private AddressDTO address;
    private AccountDTO account;
    public static PersonDTO[] getAccountsFromJson(String json){
        return gson.fromJson(json, PersonDTO[].class);
    }
    public static PersonDTO getAccountFromJson(String json){
        return gson.fromJson(json, PersonDTO.class);
    }
    public static PersonDTO[] ConvertJSON(JsonArray json){
        return gson.fromJson(json, PersonDTO[].class);
    }
    public static PersonDTO ConvertJSON(JsonObject json){
        return gson.fromJson(json, PersonDTO.class);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Person: ").append(firstName).append(" ").append(lastName);
        if(birthDate != null)
            sb.append(" - ").append(birthDate);
        if(address != null)
            sb.append("\n\t").append(address.toString());
        if(account != null)
            sb.append("\n\t").append(account.toString());
        return sb.toString();
    }
}
