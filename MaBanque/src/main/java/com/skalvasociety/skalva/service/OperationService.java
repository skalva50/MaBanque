package com.skalvasociety.skalva.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skalvasociety.skalva.bean.AssOperationCategorie;
import com.skalvasociety.skalva.bean.Categorie;
import com.skalvasociety.skalva.bean.Operation;
import com.skalvasociety.skalva.bean.TypeCategorie;
import com.skalvasociety.skalva.converter.DateConverter;
import com.skalvasociety.skalva.dao.IAssOperationCategorie;
import com.skalvasociety.skalva.dao.IOperationDao;

@Service("operationService")
@Transactional
public class OperationService implements IOperationService {
	@Autowired 
	private IOperationDao dao;
	
	private static Logger logger = Logger.getLogger(OperationService.class); 
	
	@Autowired 
	private IAssOperationCategorie assOperationCategoriedao;
	
	public List<Operation> getAll() {		
		return dao.getAll();
	}
	
	public List<Operation> getAllCourant() {		
		return dao.getAllCourant();
	}
	
	public List<Operation> getAllEpargne() {		
		return dao.getAllEpargne();
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
	
	public LinkedHashMap<String,Double> recetteMensuels(){
		LinkedHashMap<String,Double> recetteMensuels = new LinkedHashMap<String, Double>();
		List<Operation> listeOperations = getAllCourant();
		// Creation liste des dates
		List<Date> listeDates = new LinkedList<Date>();
		for (Operation operation : listeOperations) {
			try {
				Date date = new DateConverter().stringToDate(operation.getMois(), "yyyy-MM-dd");
				if(!listeDates.contains(date)){
					listeDates.add(date);
				}				
			} catch (ParseException e) {
				logger.error(e.getMessage(), e.getCause());
			}
		}
		// Date ordonnée
		Collections.sort(listeDates);
		// Ajout des dates ordonnées dans la linkedhashmap
		for (Date date : listeDates) {
			recetteMensuels.put(new DateConverter().dateToString(date, "yyyy-MM-dd"), 0d);
		}
		
		
		for (Operation operation : listeOperations) {
			if(operation.isSens() && operation.getCategorie() != null && !operation.getCategorie().getHorsStats() ){
				String mois = operation.getMois();
				if(!recetteMensuels.containsKey(mois)){
					recetteMensuels.put(mois, operation.getMontant());
					
				}else{
					recetteMensuels.put(mois, recetteMensuels.get(mois)+operation.getMontant());				
				}
			}			
		}
		return recetteMensuels;		
	}
	
	public LinkedHashMap<String,Double> getEpargneRecetteMensuels(){
		LinkedHashMap<String,Double> epargneRecetteMensuels = new LinkedHashMap<String, Double>();
		List<Operation> listeOperations = getAllEpargne();
		// Creation liste des dates
		List<Date> listeDates = new LinkedList<Date>();
		for (Operation operation : listeOperations) {
			try {
				Date date = new DateConverter().stringToDate(operation.getMois(), "yyyy-MM-dd");
				if(!listeDates.contains(date)){
					listeDates.add(date);
				}				
			} catch (ParseException e) {
				logger.error(e.getMessage(), e.getCause());
			}
		}
		// Date ordonnée
		Collections.sort(listeDates);
		// Ajout des dates ordonnées dans la linkedhashmap
		for (Date date : listeDates) {
			epargneRecetteMensuels.put(new DateConverter().dateToString(date, "yyyy-MM-dd"), 0d);
		}
		
		
		for (Operation operation : listeOperations) {
			if(operation.isSens()){
				String mois = operation.getMois();
				if(!epargneRecetteMensuels.containsKey(mois)){
					epargneRecetteMensuels.put(mois, operation.getMontant());
					
				}else{
					epargneRecetteMensuels.put(mois, epargneRecetteMensuels.get(mois)+operation.getMontant());				
				}
			}			
		}
		return epargneRecetteMensuels;		
	}
	
	public LinkedHashMap<String,Double> getEpargneDepenseMensuels(){
		LinkedHashMap<String,Double> epargneDepenseMensuels = new LinkedHashMap<String, Double>();
		List<Operation> listeOperations = getAllEpargne();
		// Creation liste des dates
		List<Date> listeDates = new LinkedList<Date>();
		for (Operation operation : listeOperations) {
			try {
				Date date = new DateConverter().stringToDate(operation.getMois(), "yyyy-MM-dd");
				if(!listeDates.contains(date)){
					listeDates.add(date);
				}				
			} catch (ParseException e) {
				logger.error(e.getMessage(), e.getCause());
			}
		}
		// Date ordonnée
		Collections.sort(listeDates);
		// Ajout des dates ordonnées dans la linkedhashmap
		for (Date date : listeDates) {
			epargneDepenseMensuels.put(new DateConverter().dateToString(date, "yyyy-MM-dd"), 0d);
		}
		
		
		for (Operation operation : listeOperations) {
			if(!operation.isSens()){
				String mois = operation.getMois();
				if(!epargneDepenseMensuels.containsKey(mois)){
					epargneDepenseMensuels.put(mois, operation.getMontant());
					
				}else{
					epargneDepenseMensuels.put(mois, epargneDepenseMensuels.get(mois)+operation.getMontant());				
				}
			}			
		}
		return epargneDepenseMensuels;		
	}
	
	public LinkedHashMap<String,Double> recetteMensuelsByTypeCategorie(TypeCategorie typeCategorie){
		LinkedHashMap<String,Double> recetteMensuelsByTypeCategorie = new LinkedHashMap<String, Double>();
		List<Operation> listeOperations = getAllCourant();
		// Creation liste des dates
		List<Date> listeDates = new LinkedList<Date>();
		for (Operation operation : listeOperations) {
			try {
				Date date = new DateConverter().stringToDate(operation.getMois(), "yyyy-MM-dd");
				if(!listeDates.contains(date)){
					listeDates.add(date);
				}				
			} catch (ParseException e) {
				logger.error(e.getMessage(), e.getCause());
			}
		}
		// Date ordonnée
		Collections.sort(listeDates);
		// Ajout des dates ordonnées dans la linkedhashmap
		for (Date date : listeDates) {
			recetteMensuelsByTypeCategorie.put(new DateConverter().dateToString(date, "yyyy-MM-dd"), 0d);
		}
		
		
		for (Operation operation : listeOperations) {
			if(!operation.isSens() &&
				operation.getCategorie() != null &&
				!operation.getCategorie().getHorsStats() &&
				operation.getCategorie().getTypeCategorie().equals(typeCategorie))
			{
				String mois = operation.getMois();
				if(!recetteMensuelsByTypeCategorie.containsKey(mois)){
					recetteMensuelsByTypeCategorie.put(mois, operation.getMontant());
					
				}else{
					recetteMensuelsByTypeCategorie.put(mois, recetteMensuelsByTypeCategorie.get(mois)+operation.getMontant());				
				}
			}			
		}
		return recetteMensuelsByTypeCategorie;		
	}
	
	public LinkedHashMap<String,Double> depensesMensuels(){
		LinkedHashMap<String,Double> depensesMensuels = new LinkedHashMap<String, Double>();
		List<Operation> listeOperations = getAllCourant();
		// Creation liste des dates
		List<Date> listeDates = new LinkedList<Date>();
		for (Operation operation : listeOperations) {
			try {
				Date date = new DateConverter().stringToDate(operation.getMois(), "yyyy-MM-dd");
				if(!listeDates.contains(date)){
					listeDates.add(date);
				}				
			} catch (ParseException e) {
				logger.error(e.getMessage(), e.getCause());
			}
		}
		// Date ordonnée
		Collections.sort(listeDates);
		// Ajout des dates ordonnées dans la linkedhashmap
		for (Date date : listeDates) {
			depensesMensuels.put(new DateConverter().dateToString(date, "yyyy-MM-dd"), 0d);
		}		
		
		for (Operation operation : listeOperations) {
			if(!operation.isSens() && operation.getCategorie()!= null && !operation.getCategorie().getHorsStats()){
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

	public HashMap<String, Double> getCategorieMonth(Categorie categorie) {
		HashMap<String,Double> categorieMonth = new HashMap<String, Double>();
		if(categorie == null)
			return categorieMonth;
		List<Operation> liste = getAllCourant();
		for (Operation operation : liste) {
			if(operation.getCategorie() != null && operation.getCategorie().getId() == categorie.getId()){
				String mois = operation.getMois();
				if(!categorieMonth.containsKey(mois)){
					categorieMonth.put(mois, operation.getMontant());				
				}else{
					categorieMonth.put(mois, categorieMonth.get(mois)+operation.getMontant());				
				}
			}			
		}
		return categorieMonth;	
	}

	public void loadOperation(InputStream inputStream) throws Exception {   	
    	
    	BufferedReader in;    	
		List<Operation> listOperation = new LinkedList<Operation>();
		String separator = ";";
		int posNumCompte = 0;
		int posDate = 2;
		int posLibelle =3;
		int posReference = 4;
		int posMontant = 6;	  
		int numLigne = 0;
    	
    	try {
			in = new BufferedReader(new InputStreamReader(inputStream));
			String ligne; 
			// Sauter la ligne de titre:
			ligne=in.readLine();
			while ((ligne=in.readLine()) != null) {
				numLigne ++;	
				if (ligne.split(separator).length != 7){
					Exception e = new Exception("Format ligne "+ numLigne +" du fichier non conforme");
					throw(e);
				}
				Operation operation = new Operation();
				Date dateOperation = null;				
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");					
				dateOperation = simpleDateFormat.parse(ligne.split(separator)[posDate]);				
				operation.setDateOperation(dateOperation);
				operation.setNumcompte(ligne.split(separator)[posNumCompte]);
				operation.setReference(ligne.split(separator)[posReference]);
				operation.setLibelle(ligne.split(separator)[posLibelle]);
				Double montant = Double.parseDouble(ligne.split(separator)[posMontant].replace(',', '.'));				
				operation.setMontant(Math.abs(montant));
				
				if (montant <0){
					operation.setSens(false);					
				}else{
					operation.setSens(true);
				}				
				listOperation.add(operation);
			}
			List<AssOperationCategorie> listAssOperationCategorie = assOperationCategoriedao.getAll();
			for (Operation operation : listOperation)
			{
				// Recherche de l'association de categorie automatique
				for (AssOperationCategorie assOperationCategorie : listAssOperationCategorie) {
					if(operation.getLibelle().contains(assOperationCategorie.getLibelle())){
						operation.setCategorie(assOperationCategorie.getCategorie());
					}
				}
				dao.save(operation);
			}
        	
		} catch (IOException e) {
			logger.error(e.getMessage(), e.getCause());

		} catch (ParseException e) {
			logger.error(e.getMessage(), e.getCause());
		}
		
	}

	public LinkedHashMap<String, Number> getMonthCategorie(Date date) {
		List<Operation> operations = getByMonth(date);
		LinkedHashMap<String,Number> categorieMontant = new LinkedHashMap<String, Number>();
		if(date == null)
			return categorieMontant;		
		for (Operation operation : operations) {
			if(operation.getCategorie() != null && !operation.isSens()  && !operation.getCategorie().getHorsStats()){
				String categorie = operation.getCategorie().getLibelle();
				if(!categorieMontant.containsKey(categorie)){
					categorieMontant.put(categorie, operation.getMontant());				
				}else{
					categorieMontant.put(categorie, categorieMontant.get(categorie).doubleValue()+operation.getMontant());				
				}
			}			
		}	
		return categorieMontant;	
	}

	public LinkedHashMap<String, Number> getMonthTypeCategorie(Date date) {
		List<Operation> operations = getByMonth(date);
		LinkedHashMap<String,Number> categorieMontant = new LinkedHashMap<String, Number>();
		if(date == null)
			return categorieMontant;		
		for (Operation operation : operations) {
			if(operation.getCategorie() != null && !operation.isSens()  && !operation.getCategorie().getHorsStats()){
				String categorie = operation.getCategorie().getTypeCategorie().getLibelle();
				if(!categorieMontant.containsKey(categorie)){
					categorieMontant.put(categorie, operation.getMontant());				
				}else{
					categorieMontant.put(categorie, categorieMontant.get(categorie).floatValue()+operation.getMontant());				
				}
			}			
		}	
		return categorieMontant;
	}
	
	public Double getSoldeCourant(Date dateSolde){		
		Double soldeCourant = 7227.02;
		List<Operation> listeOperations = getAllCourant();
		for (Operation operation : listeOperations) {
			if(operation.getDateOperation().before(dateSolde)){
				if(operation.isSens()){
					soldeCourant = soldeCourant+operation.getMontant();
				}else{
					soldeCourant = soldeCourant-operation.getMontant();
				}
			}			
		}
		soldeCourant = Math.round( soldeCourant * 100.0 ) / 100.0;
		return soldeCourant;
	}
	
	public Double getSoldeEpargne(Date dateSolde){		
		Double soldeEpargne = 0d;
		List<Operation> listeOperations = getAllEpargne();
		for (Operation operation : listeOperations) {
			if(operation.getDateOperation().before(dateSolde)){
				if(operation.isSens()){
					soldeEpargne = soldeEpargne+operation.getMontant();
				}else{
					soldeEpargne = soldeEpargne-operation.getMontant();
				}	
			}				
		}
		soldeEpargne = Math.round( soldeEpargne * 100.0 ) / 100.0;
		return soldeEpargne;
	}
	
	public Double getDepensesAnnuels(Date dateFin){		
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFin);      
        calendar.add(Calendar.YEAR, -1);
        Date dateDebut = calendar.getTime();	       
		List<Operation> listeOperations = getAllCourant();		
		Double depenses = 0d;
		for (Operation operation : listeOperations) {
			if(!operation.isSens() 
					&& operation.getDateOperation().after(dateDebut) 
					&& operation.getDateOperation().before(dateFin)
					&& operation.getCategorie() != null
					&& !operation.getCategorie().getHorsStats()){
				depenses = depenses + operation.getMontant();
			}
		}
		return Math.round( depenses * 100.0 ) / 100.0;		
	}
	
	public Double getRecettesAnnuels(Date dateFin){		
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFin);      
        calendar.add(Calendar.YEAR, -1);
        Date dateDebut = calendar.getTime();	       
		List<Operation> listeOperations = getAllCourant();		
		Double recettes = 0d;
		for (Operation operation : listeOperations) {
			if(operation.isSens() 
					&& operation.getDateOperation().after(dateDebut) 
					&& operation.getDateOperation().before(dateFin)
					&& operation.getCategorie() != null
					&& !operation.getCategorie().getHorsStats()){
				recettes = recettes + operation.getMontant();
			}
		}
		return Math.round( recettes * 100.0 ) / 100.0;		
	}
}
