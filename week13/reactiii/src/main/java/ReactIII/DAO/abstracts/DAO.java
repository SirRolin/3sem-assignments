package ReactIII.DAO.abstracts;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import sir.rolin.my_library.DAO.SuperEntity;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import ReactIII.config.hibernate;

public abstract class DAO<T extends SuperEntity<IDType>, IDType> implements IDAO<T, IDType> {
    protected static EntityManagerFactory emf;
    private Class<T> tClass;
    @SuppressWarnings("unchecked")
    public DAO(){
        if(emf == null || !emf.isOpen()){
            emf = hibernate.getEntityManagerFactoryConfig();
        }
        ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
        //noinspection unchecked
        this.tClass = (Class<T>) superclass.getActualTypeArguments()[0];
    }
    @Override
    public List<T> getAll() {
        try(EntityManager em = emf.createEntityManager()){
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(tClass);
            Root<T> rootEntry = cq.from(tClass);
            CriteriaQuery<T> all = cq.select(rootEntry);

            TypedQuery<T> allQuery = em.createQuery(all);
            return allQuery.getResultList();
        }
    }

    @Override
    public T getById(IDType id) {
        try(var em = emf.createEntityManager()){
            T found = em.find(tClass,id);
            if(found != null){
                return found;
            }
        }
        return null;
    }

    @Override
    public void create(T in) {
        if(in != null){
            try(var em = emf.createEntityManager()){
                em.getTransaction().begin();
                em.persist(in);
                em.getTransaction().commit();
            }
        }
    }

    @Override
    public T update(T in) {
        IDType id = in.getID();
        if(in != null){
            try(var em = emf.createEntityManager()){
                Object found = em.find(tClass, id);
                if(found != null){
                    em.getTransaction().begin();
                    T toReturn = em.merge(in);
                    em.getTransaction().commit();
                    return toReturn;
                }
            }
        }
        return in;
    }

    @Override
    public void delete(T in) {
        IDType id = in.getID();
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            Object found = em.find(tClass, id);
            if(found != null){
                em.remove(found);
                em.getTransaction().commit();
            }
        }
    }
}
