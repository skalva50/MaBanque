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


}
