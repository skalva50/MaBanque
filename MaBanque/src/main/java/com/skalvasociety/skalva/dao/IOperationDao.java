package com.skalvasociety.skalva.dao;

import java.io.Serializable;
import java.util.List;

import com.skalvasociety.skalva.bean.Operation;


public interface IOperationDao extends IDao<Serializable,Operation> {

	public List<Operation> getAllCourant();
	public List<Operation> getAllEpargne();
	public boolean isUnique(Operation operation);

}
