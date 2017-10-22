package com.skalvasociety.skalva.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.skalvasociety.skalva.bean.TypeCategorie;

@Repository("typeCategorieDao")
public class TypeCategorieDao extends AbstractDao<Integer,TypeCategorie> implements ITypeCategorieDao  {

	public TypeCategorie getByLibelle(String libelle) {		
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("libelle",libelle));
		return (TypeCategorie) criteria.uniqueResult();
	}

}
