package com.skalvasociety.skalva.service;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.skalvasociety.skalva.bean.Categorie;
import com.skalvasociety.skalva.bean.Operation;
import com.skalvasociety.skalva.bean.TypeCategorie;

public interface IOperationService {
	public List<Operation> getAll();
	public List<Operation> getAllCourant();
	public List<Operation> getAllEpargne();
	public List<Operation> getByMonth(Date date);
	public void save(Operation operation);
	public void delete(Operation operation);
	public Operation getByKey(int key);
	public LinkedHashMap<String,Double> recetteMensuels();
	public LinkedHashMap<String,Double> recetteMensuelsByTypeCategorie(TypeCategorie typeCategorie);
	public LinkedHashMap<String,Double> depensesMensuels();
	public HashMap<String,Double> getCategorieMonth(Categorie categorie);
	public LinkedHashMap<String,Number> getMonthCategorie(Date date);
	public LinkedHashMap<String,Number> getMonthTypeCategorie(Date date);
	public void loadOperation(InputStream inputStream) throws Exception;
	public Double getSoldeCourant(Date dateSolde);
	public LinkedHashMap<String,Double> getEpargneDepenseMensuels();
	public LinkedHashMap<String,Double> getEpargneRecetteMensuels();
	public Double getSoldeEpargne(Date dateSolde);
	public Double getDepensesAnnuels(Date dateFin);
	public Double getRecettesAnnuels(Date dateFin);
}
