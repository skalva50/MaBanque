package com.skalvasociety.skalva.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.skalvasociety.skalva.bean.Operation;

@Repository("operationDao")
public class OperationDao extends AbstractDao<Integer,Operation> implements IOperationDao{
	
	
	
	@SuppressWarnings("unchecked")
	public List<Operation> getAllCourant() {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("numcompte","81932331196"));
		return (List<Operation>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Operation> getAllEpargne() {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("numcompte","82183213361"));
		return (List<Operation>) criteria.list();
	}	

	@Override
	public void save(Operation operation) {
		if(isUnique(operation)){
			super.save(operation);
		}		
	}

	public boolean isUnique(Operation operation) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("dateOperation",operation.getDateOperation()));
		criteria.add(Restrictions.eq("libelle",operation.getLibelle()));
		criteria.add(Restrictions.eq("reference",operation.getReference()));
		criteria.add(Restrictions.eq("montant",operation.getMontant()));
		criteria.add(Restrictions.eq("numcompte",operation.getNumcompte()));
		if( criteria.list() != null && !criteria.list().isEmpty()){
			return false;
		}else{
			return true;
		}
	}
}
