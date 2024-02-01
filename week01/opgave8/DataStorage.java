package week01.opgave8;

import java.io.Serializable;

public interface DataStorage<DataType> {

    String store(DataType data);

    DataType retrieve(String source);
    void delete(String source);
}
