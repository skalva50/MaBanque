package com.skalvasociety.skalva.view;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.chart.PieChartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.skalvasociety.skalva.bean.Categorie;
import com.skalvasociety.skalva.bean.Operation;
import com.skalvasociety.skalva.service.ICategorieService;
import com.skalvasociety.skalva.service.IOperationService;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
@Controller
@Transactional
public class MensuelView implements Serializable {
	
	@Autowired
    private IOperationService service;
	
	@Autowired
    private ICategorieService categorieService;
    
	private List<Operation> operations;
	private List<Categorie> categories;
	
	// Selection des combobox
	private Date dateSelected = new Date();
	private int categorieSelect;	
	
	// Pie
	private PieChartModel pieModelDetails;
	private PieChartModel pieModelTotal;
	
	// Champ
	private Double total;
	private Double solde;
		
	@PostConstruct
    public void init() {
		categories = categorieService.getAll();
		solde = service.getSoldeCourant(getDateSelected());
		filterOperationByDate();
		createPieModelDetails();
		createPieModelTotal();
    }	
	
	private void filterOperationByDate() {
		List<Operation> listeOperationFiltered = service.getByMonth(dateSelected);
        setOperations(listeOperationFiltered);      
	}
	
    public void reload(ActionEvent actionEvent) {
        init();
    }
	
	public void onDateSelect(SelectEvent event) {
		dateSelected = (Date) event.getObject();
        init();                
	}
	
    public void onRowEdit(RowEditEvent event) {
    	// Recherche de l'operation selectionné dans la vue
    	Operation operationView = null;
    	for (Operation operation : operations) {
			if(operation.getId() == ((Operation)event.getObject()).getId()){
				operationView =  operation;
			}
		}
    	// Recherche de la mêmeoperation selectionné dans la BDD
    	Operation operationBDD = service.getByKey(((Operation)event.getObject()).getId());
    	
    	// Recherche de la categorie selectionnée dans la vue
    	Categorie categorie = categorieService.getByKey(categorieSelect);
    	
    	// Mise à jour de l'operation de la vue
    	operationView.setCategorie(categorie);
    	// Mise à jour de l'operation de la BDD    	
    	operationBDD.setCategorie(categorie);    	
    }  
    
    private void createPieModelDetails() {
    	pieModelDetails = new PieChartModel();
    	HashMap<String,Double> categorieMontant = service.getMonthCategorie(dateSelected);
    	setTotal(0d);
    	for (Entry<String, Double> categorie : categorieMontant.entrySet()) {
    		pieModelDetails.set(categorie.getKey(), categorie.getValue());			
    		setTotal(getTotal()+categorie.getValue());
		}
    	// Mise à jour du total mensuel
    	setTotal(Math.round( getTotal() * 100.0 ) / 100.0);
    	
    	pieModelDetails.setTitle("Repartition details dépenses");
    	pieModelDetails.setLegendPosition("e");
    }
    
    private void createPieModelTotal() {
    	pieModelTotal = new PieChartModel();
    	HashMap<String,Double> categorieMontant = service.getMonthTypeCategorie(dateSelected);    	
    	for (Entry<String, Double> categorie : categorieMontant.entrySet()) {
    		pieModelTotal.set(categorie.getKey(), categorie.getValue());		    		
		}    	
    	pieModelTotal.setTitle("Repartition dépenses");
    	pieModelTotal.setLegendPosition("e");
    }  
    
	public List<Operation> getOperations() {
		return operations;
	}	
	
	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

	public Date getDateSelected() {
		return dateSelected;
	}

	public void setDateSelected(Date dateSelected) {
		this.dateSelected = dateSelected;
	}
	
	public List<Categorie> getCategories(){
		return categories;		
	}

	public void setCategories(List<Categorie> categories) {
		this.categories = categories;
	}

	public int getCategorieSelect() {
		return categorieSelect;
	}

	public void setCategorieSelect(int categorieSelect) {
		this.categorieSelect = categorieSelect;
	}

	public PieChartModel getPieModelDetails() {
		return pieModelDetails;
	}
	
	public PieChartModel getPieModelTotal() {
		return pieModelTotal;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Double getSolde() {
		return solde;
	}

	public void setSolde(Double solde) {
		this.solde = solde;
	}
}
 