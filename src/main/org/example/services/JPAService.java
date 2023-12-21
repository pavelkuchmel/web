package main.org.example.services;

import main.org.example.model.*;
import org.hibernate.Session;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Function;


public class JPAService {

    private static EntityManagerFactory entityManagerFactory = null;
    private static JPAService jpaService = null;

    public static JPAService getInstance(){
        if (jpaService == null){
            return jpaService = new JPAService();
        }
        return jpaService;
    }

    static {
        if (entityManagerFactory == null){
            Configuration configuration = new Configuration();
            Properties properties = new Properties();
            properties.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
            properties.put(Environment.URL, "jdbc:mysql://127.0.0.1:3307/users_db");
            properties.put(Environment.USER, "root");
            properties.put(Environment.PASS, "root");
            properties.put(Environment.SHOW_SQL, "true");
            properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            properties.put(Environment.HBM2DDL_AUTO, "update");
            /*properties.put("hibernate.connection.charSet", "utf8");
            properties.put("hibernate.connection.characterEncoding", "utf8");
            properties.put("hibernate.connection.useUnicode", "true");
            properties.put("hibernate.connection.collationConnection", "utf8_general_ci");*/
            configuration.setProperties(properties);
            configuration.addAnnotatedClass(Student.class);
            configuration.addAnnotatedClass(Passport.class);
            configuration.addAnnotatedClass(Office.class);
            configuration.addAnnotatedClass(Employee.class);
            configuration.addAnnotatedClass(Task.class);
            configuration.addAnnotatedClass(Role.class);
            configuration.addAnnotatedClass(User.class);
            ServiceRegistry serviceRegistry =
                    new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            entityManagerFactory = configuration.buildSessionFactory(serviceRegistry);
            //sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
    }

    public static EntityManager getEntityManager(){
        return entityManagerFactory.createEntityManager();
    }

    public <T> T runInTransaction(Function<EntityManager, T> function){
        EntityManager entityManager = getEntityManager();
        EntityTransaction entityTransaction = null;
        T entity = null;
        try{
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entity = function.apply(entityManager);
            entityTransaction.commit();
        }catch (Throwable th){
            th.printStackTrace();
            if (entityTransaction != null && entityTransaction.isActive()){
                entityTransaction.rollback();
            }
        }finally {
            entityManager.close();
        }
        return entity;
    }

    public void runInTransaction(Consumer<EntityManager> function){
        runInTransaction(entityManager -> {
            function.accept(entityManager);
            return null;
        });
    }

    public <T> T run(Function<EntityManager, T> function){
        EntityManager entityManager = getEntityManager();
        try{
            return function.apply(entityManager);
        }finally {
            entityManager.close();
        }
    }

    public <T> void delete(T entity){
        runInTransaction(entityManager -> {
            entityManager.remove(entity);
        });
    }

    public <T>T update(T entity){
        return runInTransaction(entityManager -> {
            return entityManager.merge(entity);
        });
   }

    public <T> void saveOrUpdate(T entity){
        runInTransaction(entityManager -> {
            entityManager.unwrap(Session.class).saveOrUpdate(entity);
        });
    }

    public <T> void create(T entity){
        runInTransaction(entityManager -> {
            entityManager.persist(entity);
        });
    }

    public <T> T findById(Class<T> entityClass, Object pk){
        return run(entityManager -> {
            return entityManager.find(entityClass, pk);
        });
    }

    public <T> void deleteById(Class<T> entityClass, Object pk){
        runInTransaction(entityManager -> {
           T entity = entityManager.find(entityClass, pk);
           if (entity == null){
               throw new EntityNotFoundException("Entity does not exist");
           }
           entityManager.remove(entity);
        });
    }

    public <T> List<T> getAll(Class<T> entityClass, String condition){
        return run( entityManager -> {
            return  entityManager.createQuery("select o from " + entityClass.getSimpleName() + " o " + condition).getResultList();
        });
    }

    public <T>T getByCondition(Class<T> entityClass, String condition){
        return (T)run( entityManager -> {
            return  entityManager.createQuery("select o from " + entityClass.getSimpleName() + " o " + condition).getSingleResult();
        });
    }

    public <T> List<T> getAll(Class<T> entityClass){
        return getAll(entityClass, "");
    }

    public void closeEntityManager(){
        entityManagerFactory.close();
    }

}