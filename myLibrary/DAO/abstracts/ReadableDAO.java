package myLibrary.DAO.abstracts;

import jakarta.persistence.EntityManagerFactory;
import lombok.Getter;
import week07.wed_thur.config.hibernate;
import myLibrary.DAO.SuperEntity;

import java.lang.reflect.ParameterizedType;

public abstract class ReadableDAO<T extends SuperEntity<IDType>, IDType> implements IReadableDAO<T, IDType> {

    protected static EntityManagerFactory emf;
    @Getter
    private Class<T> tClass;

    public ReadableDAO(EntityManagerFactory emf){
        ReadableDAO.emf = emf;
        ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
        //noinspection unchecked
        this.tClass = (Class<T>) superclass.getActualTypeArguments()[0];
    }
    public abstract T read(IDType id);
    @Override
    public T read (IDType id, Class<T> tClass) {
        try(var em = emf.createEntityManager()){
            T found = em.find(tClass,id);
            if(found != null){
                return found;
            }
        }
        return null;
    }


}
