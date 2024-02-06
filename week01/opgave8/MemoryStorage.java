package week01.opgave8;

import java.util.HashMap;
import java.util.Map;

public class MemoryStorage<DataType> implements DataStorage<DataType> {
    private final Map<String, DataType> storedData = new HashMap<>();

    @Override
    public String store(DataType data) {
        String hash = String.valueOf(data.hashCode());
        storedData.put(hash, data);
        return hash;
    }

    @Override
    public DataType retrieve(String source) {
        if(storedData.containsKey(source))
            return storedData.get(source);
        return null;
    }

    @Override
    public void delete(String source) {
        storedData.remove(source);
    }


}
