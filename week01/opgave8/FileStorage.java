package week01.opgave8;

import java.io.*;

public class FileStorage<DataType extends java.io.Serializable> implements DataStorage<DataType> {
    @Override
    public String store(DataType data) {
        String hash = data.getClass().toString().replace(".", "-") + "-" + data.hashCode();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(hash))) {
            oos.writeObject(data);
            return hash;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DataType retrieve(String source) {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(source))){
            //noinspection unchecked
            return (DataType) ois.readObject();
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String source) {
        File fileToDelete = new File(source);
        boolean deleted = fileToDelete.delete();
    }
}
