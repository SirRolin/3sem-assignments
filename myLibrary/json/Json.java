package myLibrary.json;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Json {
    private static final HashMap<String, Set<String>> transientFields = new HashMap<>();
    private static final HashSet<String> transientClasses = new HashSet<>();
    public static void addTransientField(Class<?> clazz, String variableName){
        if(!transientFields.containsKey(clazz.getSimpleName())){
            transientFields.put(clazz.getSimpleName(), new HashSet<>());
        }
        Set set = transientFields.get(clazz.getSimpleName());
        set.add(variableName);
    }
    public static void addTransientClass(Class<?> clazz){
        transientClasses.add(clazz.getSimpleName());
    }
    public static Gson getGson(){
        return new GsonBuilder().setPrettyPrinting().addSerializationExclusionStrategy(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                if(transientFields.containsKey(f.getDeclaringClass().getSimpleName())){
                    Set set = transientFields.get(f.getDeclaringClass().getSimpleName());
                    return set.contains(f.getName());
                }
                return false;
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return transientClasses.contains(clazz.getSimpleName());
            }
        }).create();
    }
}
