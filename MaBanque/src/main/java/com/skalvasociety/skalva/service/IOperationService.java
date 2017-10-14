package com.skalvasociety.skalva.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.skalvasociety.skalva.bean.Operation;

public interface IOperationService {
	public List<Operation> getAll();
	public List<Operation> getAllCourant();
	public List<Operation> getByMonth(Date date);
	public void save(Operation operation);
	public void delete(Operation operation);
	public Operation getByKey(int key);
	public HashMap<String,Double> recetteMensuels();
	public HashMap<String,Double> depensesMensuels();
}
