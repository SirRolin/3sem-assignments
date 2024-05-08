package ReactIII.DAO.abstracts;

import java.util.List;

public interface IDAO<T, IDType> {
    List<T> getAll();
    T getById(IDType id);
    void create(T in);
    T update(T in);
    void delete(T in);
}
