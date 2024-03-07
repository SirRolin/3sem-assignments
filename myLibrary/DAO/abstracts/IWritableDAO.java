package myLibrary.DAO.abstracts;

public interface IWritableDAO<T, IDType> extends IReadableDAO<T, IDType> {
    void create(T in);

    T update(T obj);

    void delete(T t);
}
