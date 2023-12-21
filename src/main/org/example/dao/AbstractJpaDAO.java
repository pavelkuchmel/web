package main.org.example.dao;

import main.org.example.services.JPAService;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class AbstractJpaDAO<PK extends Serializable, T> implements AutoCloseable{

    private Class<T> clazz;
    protected JPAService jpaService;

    public AbstractJpaDAO(){
        this.clazz = (Class<T>) ((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        this.jpaService = JPAService.getInstance();
    }

    public T findById(PK pk){
        return jpaService.findById(clazz, pk);
    }

    public List<T> findAll(){
        return jpaService.getAll(clazz);
    }

    public void deleteById(PK pk){
        jpaService.deleteById(clazz, pk);
    }

    public void saveOrUpdate(T t){
        jpaService.saveOrUpdate(t);
    }

    public T update(T t){
        return jpaService.update(t);
    }


    public void create(T t){
        jpaService.create(t);
    }

    public void delete(T t){
        jpaService.delete(t);
    }

    public List<T> findAllByCondition(String condition){
        return jpaService.getAll(clazz, condition);
    }

    public T findByCondition(String condition){
        return jpaService.getByCondition(clazz, condition);
    }

    @Override
    public void close() throws Exception {
        jpaService.closeEntityManager();
    }
}
