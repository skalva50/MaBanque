package com.skalvasociety.skalva.dao;

import java.io.Serializable;
import java.util.List;

public interface IDao<PK extends Serializable,T> {
	public void save(T entite);
	public void delete(T entity);
	public T getByKey(PK key);
	public List<T> getAll();
}
