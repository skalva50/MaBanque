package com.skalvasociety.skalva.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import com.skalvasociety.skalva.bean.Categorie;

@Repository("categorieDao")
public class CategorieDao extends AbstractDao<Integer,Categorie> implements ICategorieDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Categorie> getAll() {		
		Criteria criteria = createEntityCriteria();
		criteria.addOrder(Order.asc("libelle"));		
        return (List<Categorie>) criteria.list(); 
	}
	
}
