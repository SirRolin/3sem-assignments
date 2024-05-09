package sir.rolin.my_library.DAO.abstracts;

public interface IReadableDAO<T, IDType> {
    T read (IDType id);
    T read (IDType id, Class<T> tClass);
}
