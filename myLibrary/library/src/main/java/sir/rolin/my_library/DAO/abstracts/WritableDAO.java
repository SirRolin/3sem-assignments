package sir.rolin.my_library.DAO.abstracts;

import jakarta.persistence.EntityManagerFactory;
import sir.rolin.my_library.DAO.SuperEntity;

public abstract class WritableDAO<T extends SuperEntity<IDType>, IDType>
        extends ReadableDAO<T, IDType>
        implements IWritableDAO<T, IDType>{

    protected static EntityManagerFactory emf;

    public WritableDAO(EntityManagerFactory emf){
        super(emf);
        WritableDAO.emf = emf;
    }


    @Override
    public void create(T in){
        if(in != null){
            try(var em = emf.createEntityManager()){
                em.getTransaction().begin();
                em.persist(in);
                em.getTransaction().commit();
            }
        }
    }

    public T update(T obj, IDType id){
        T toReturn = null;
        if(obj != null){
            try(var em = emf.createEntityManager()){
                em.getTransaction().begin();
                Object found = em.find(getGenericType(obj), id);
                if(found != null){
                    toReturn = em.merge(obj);
                    em.getTransaction().commit();
                }
            }
        }
        return toReturn;
    }
    @Override
    public T update(T obj) {
        return update(obj, obj.getID());
    }

    public void delete(Class<T> tClass, IDType id){
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            Object found = em.find(tClass,id);
            if(found != null){
                em.remove(found);
                em.getTransaction().commit();
            }
        }
    }

    @Override
    public void delete(T t){
        delete(getGenericType(t), t.getID());
    }

    @SuppressWarnings("unchecked")
    static <T> Class<T> getGenericType(T t){
        return (Class<T>) t.getClass();
    }


}
