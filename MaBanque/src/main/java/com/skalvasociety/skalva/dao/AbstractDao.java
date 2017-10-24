package com.skalvasociety.skalva.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDao<PK extends Serializable, T> implements IDao<Serializable,T> {
private final Class<T> persistentClass;
	
	@SuppressWarnings("unchecked")
    public AbstractDao(){
        this.persistentClass =(Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }
	
	@Autowired
    private SessionFactory sessionFactory;
 
    protected Session getSession(){
        return sessionFactory.getCurrentSession();
    }
    
    protected Criteria createEntityCriteria(){
        return getSession().createCriteria(persistentClass);
    }

    
	@SuppressWarnings("unchecked")
	public T getByKey(Serializable key) {
        return (T) getSession().get(persistentClass, key);
    }
 
    public void save(T entity) {
        getSession().persist(entity);
    }

 
    public void delete(T entity) {
    	getSession().delete(entity);        
    }
    
    @SuppressWarnings("unchecked")
	public List<T> getAll() {
		Criteria criteria = createEntityCriteria();					
        return (List<T>) criteria.list();        
	}
}
