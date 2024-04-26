package week02.JSON;

import com.google.gson.*;

import java.io.*;

public class JSONMain {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static void main(String[] args) {
        JsonArray accountPerson;
        try {
            File two = new File("week02/JSON/account.json");
            accountPerson = gson.fromJson(new FileReader(two), JsonArray.class);
            PersonDTO[] persons = PersonDTO.ConvertJSON(accountPerson);
            System.out.println(persons[0].toString());
            File f = new File("week02/JSON/accountSavedFromJava.json");
            FileWriter fw = new FileWriter(f);
            fw.write(gson.toJson(accountPerson));
            fw.flush();
            fw.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
