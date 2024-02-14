package week03.unicorn.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import week03.unicorn.HibernateConfig;
import week03.unicorn.Unicorn;
import java.util.List;

public class UnicornDAO {
    private final EntityManagerFactory myEMF = HibernateConfig.getEntityManagerFactoryConfig();
    public Unicorn save(Unicorn unicorn){
        EntityManager em = myEMF.createEntityManager();
        em.getTransaction().begin();
        em.persist(unicorn);
        em.getTransaction().commit();
        em.close();
        return unicorn;
    }
    public Unicorn update(Unicorn unicorn){
        EntityManager em = myEMF.createEntityManager();
        em.getTransaction().begin();
        Unicorn serverUnicorn = em.merge(unicorn);
        em.getTransaction().commit();
        em.close();
        return serverUnicorn;
    }
    @SuppressWarnings("unused")
    public boolean delete(int id){
        EntityManager em = myEMF.createEntityManager();
        em.getTransaction().begin();
        Unicorn serverUnicorn = findByID(id);
        if(serverUnicorn != null){
            em.remove(serverUnicorn);
        }
        em.getTransaction().commit();
        em.close();
        return serverUnicorn != null;
    }
    public Unicorn findByID(int id){
        EntityManager em = myEMF.createEntityManager();
        em.getTransaction().begin();
        Unicorn serverUnicorn = em.find(Unicorn.class, id);
        em.close();
        return serverUnicorn;
    }
    public List<Unicorn> findAll(){
        EntityManager em = myEMF.createEntityManager();
        em.getTransaction().begin();
        @SuppressWarnings("unchecked")
        List<Unicorn> found = (List<Unicorn>) em.createQuery("select distinct c from Unicorn c").getResultList();

        em.getTransaction().commit();
        em.close();
        return found;
    }
    public void close(){
        myEMF.close();
    }
}
