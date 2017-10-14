package com.skalvasociety.skalva.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skalvasociety.skalva.bean.Operation;
import com.skalvasociety.skalva.dao.IOperationDao;

@Service("operationService")
@Transactional
public class OperationService implements IOperationService {
	@Autowired 
	private IOperationDao dao;
	
	public List<Operation> getAll() {		
		return dao.getAll();
	}
	
	public List<Operation> getAllCourant() {		
		return dao.getAllCourant();
	}

	public void save(Operation operation) {
		dao.save(operation);

	}

	public void delete(Operation operation) {
		dao.delete(operation);

	}

	public Operation getByKey(int key) {		
		return dao.getByKey(key);
	}
	
	public HashMap<String,Double> recetteMensuels(){
		HashMap<String,Double> recetteMensuels = new HashMap<String, Double>();
		List<Operation> liste = getAllCourant();
		for (Operation operation : liste) {
			if(operation.isSens()){
				String mois = operation.getMois();
				if(!recetteMensuels.containsKey(mois)){
					recetteMensuels.put(mois, operation.getMontant());
					
				}else{
					recetteMensuels.put(mois, recetteMensuels.get(mois)+operation.getMontant());				
				}
				if(mois.equals("2017-9-01")){
		        	System.out.print(operation.getMontant() + " |id: ");
		        	System.out.println(operation.getId());	        	
		        	
				}
			}			
		}
		return recetteMensuels;		
	}
	
	public HashMap<String,Double> depensesMensuels(){
		HashMap<String,Double> depensesMensuels = new HashMap<String, Double>();
		List<Operation> liste = getAllCourant();
		for (Operation operation : liste) {
			if(!operation.isSens()){
				String mois = operation.getMois();
				if(!depensesMensuels.containsKey(mois)){
					depensesMensuels.put(mois, operation.getMontant());				
				}else{
					depensesMensuels.put(mois, depensesMensuels.get(mois)+operation.getMontant());				
				}
			}			
		}
		return depensesMensuels;		
	}

	public List<Operation> getByMonth(Date date) {
		Calendar calendarDate = Calendar.getInstance();
        calendarDate.setTime(date);
        int month = calendarDate.get(Calendar.MONTH)+1;
        int year = calendarDate.get(Calendar.YEAR);        
        
        List<Operation> listeOperation = getAllCourant();
        List<Operation> listeOperationFiltered = new LinkedList<Operation>();
        for (Operation operation : listeOperation) {
        	Calendar calendarOperation = Calendar.getInstance();
        	calendarOperation.setTime(operation.getDateOperation());
        	if(month == calendarOperation.get(Calendar.MONTH)+1 && year == calendarOperation.get(Calendar.YEAR)){
        		listeOperationFiltered.add(operation);
        	}        	
		}
        return listeOperationFiltered;
	}
}
