package week07.wed_thur.DAO.abstracts;

import java.util.List;

public interface IDAO<T, IDType> {
    List<T> getAll();
    T getById(IDType id);
    void create(T in);
    void update(T in);
    void delete(T in);
}
