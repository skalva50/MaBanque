package com.skalvasociety.skalva.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.skalvasociety.skalva.bean.Categorie;
import com.skalvasociety.skalva.bean.Operation;
import com.skalvasociety.skalva.service.ICategorieService;
import com.skalvasociety.skalva.service.IOperationService;

@ManagedBean
@ViewScoped
@Controller
@Transactional
public class AccueilView {
	
	@Autowired
    private IOperationService service;
	
	@Autowired
    private ICategorieService categorieService;
	
	//Graphe line total
	private LineChartModel lineTotalModel;
	private List<Operation> operations;
	
	// Graphe line categories
	private LineChartModel lineCategorieModel;
	private List<Categorie> categories;
	private Categorie categorieSelect;
	
	@PostConstruct
    public void init() {		
		operations = service.getAllCourant();
		setCategories(categorieService.getAll());
		createLineModels();
		createLineTotalModel();
    }
	
    public void reload(ActionEvent actionEvent) {        
        init();
    }
    
    public void itemSelect(ItemSelectEvent event) {
    	System.out.println("itemselect!!!");
    	FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "mensuel");    	
    }
    
    private void createLineTotalModel() {
    	lineTotalModel = new LineChartModel();
        Date dateMin = null;
        Date dateMax = null;
    	
        LineChartSeries recetteSerie = new LineChartSeries();
    	recetteSerie.setLabel("Recettes");    	
        HashMap<String,Double> recettesMensuels = service.recetteMensuels();
        Date date;
        for (Entry<String, Double> recette :recettesMensuels.entrySet()) {
        	recetteSerie.set(recette.getKey(), recette.getValue());
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-dd");    	
			try {
				date = formatter.parse(recette.getKey());
	        	if(dateMin == null || dateMin.compareTo(date) > 0){
	        		dateMin = date;
	        	}
	        	if(dateMax == null || dateMax.compareTo(date) < 0){
	        		dateMax = date;
	        	}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

       
        
        LineChartSeries depensesSerie = new LineChartSeries();
    	depensesSerie.setLabel("Depenses"); 
        HashMap<String,Double> depensesMensuels = service.depensesMensuels();        
        for (Entry<String, Double> depense :depensesMensuels.entrySet()) {
        	depensesSerie.set(depense.getKey(), depense.getValue());        	
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-dd");      	
			try {
				date = formatter.parse(depense.getKey());
	        	if(dateMin == null || dateMin.compareTo(date) > 0){
	        		dateMin = date;
	        	}
	        	if(dateMax == null || dateMax.compareTo(date) < 0){
	        		dateMax = date;
	        	}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

        lineTotalModel.addSeries(recetteSerie);
        lineTotalModel.addSeries(depensesSerie);
        lineTotalModel.setTitle("Bilan");    
        lineTotalModel.setAnimate(true);
        lineTotalModel.setLegendPosition("ne");
        lineTotalModel.setShowPointLabels(true);
        
        
        lineTotalModel.getAxis(AxisType.Y).setLabel("Montant");
        DateAxis axis = new DateAxis("Dates");
        
        Calendar calendarMin = Calendar.getInstance();
        calendarMin.setTime(dateMin);        
        int month = calendarMin.get(Calendar.MONTH);
        int year = calendarMin.get(Calendar.YEAR);
        String sDateMin = year+"-"+month+"-01"; 
        
        
        Calendar calendarMax = Calendar.getInstance();        
        calendarMax.setTime(dateMax);
        calendarMax.add(Calendar.MONTH, 2);
        month = calendarMax.get(Calendar.MONTH)+1;
        year = calendarMax.get(Calendar.YEAR);
        String sDateMax = year+"-"+month+"-01"; 
        
        axis.setMin(sDateMin);
        axis.setMax(sDateMax);
        axis.setTickFormat("%b %y");
         
        lineTotalModel.getAxes().put(AxisType.X, axis);
    } 

    private void createLineModels() {
        
    	lineCategorieModel = new LineChartModel();
        Date dateMin = null;
        Date dateMax = null;
        
        lineCategorieModel.setTitle("Categorie");
        lineCategorieModel.setAnimate(true);
        lineCategorieModel.setLegendPosition("ne");
        lineCategorieModel.setShowPointLabels(true);
        
        Axis yAxis = lineCategorieModel.getAxis(AxisType.Y);
        yAxis.setLabel("Montant");
        
        DateAxis axis = new DateAxis("Dates");
        axis.setTickFormat("%b %y");         
        lineCategorieModel.getAxes().put(AxisType.X, axis);
        
    	ChartSeries categorieSerie = new ChartSeries();
    	if(categorieSelect == null){ 
    		categorieSelect = categorieService.getByKey(2);
    	}  	
    	categorieSerie.setLabel(categorieSelect.getLibelle());   	 
    	

    	Date date;
        HashMap<String,Double> categorieMonth = service.getCategorieMonth(categorieSelect);
        for (Entry<String, Double> categorieMensuel :categorieMonth.entrySet()) {
        	categorieSerie.set(categorieMensuel.getKey(), categorieMensuel.getValue());
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-dd");      	
			try {
				date = formatter.parse(categorieMensuel.getKey());
	        	if(dateMin == null || dateMin.compareTo(date) > 0){
	        		dateMin = date;
	        	}
	        	if(dateMax == null || dateMax.compareTo(date) < 0){
	        		dateMax = date;
	        	}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}      
        Calendar calendarMin = Calendar.getInstance();
        calendarMin.setTime(dateMin);        
        int month = calendarMin.get(Calendar.MONTH);
        int year = calendarMin.get(Calendar.YEAR);
        String sDateMin = year+"-"+month+"-01"; 
        
        
        Calendar calendarMax = Calendar.getInstance();        
        calendarMax.setTime(dateMax);
        calendarMax.add(Calendar.MONTH, 2);
        month = calendarMax.get(Calendar.MONTH)+1;
        year = calendarMax.get(Calendar.YEAR);
        String sDateMax = year+"-"+month+"-01"; 
        
        axis.setMin(sDateMin);
        axis.setMax(sDateMax);
        lineCategorieModel.addSeries(categorieSerie);       
    }   

    
    public void onCategorieChange(){
    	
    	lineCategorieModel.clear();
    	ChartSeries categorieSerie = new ChartSeries();
    	categorieSerie.setLabel(categorieSelect.getLibelle());   	
    	
        HashMap<String,Double> categorieMonth = service.getCategorieMonth(categorieSelect);
        for (Entry<String, Double> categorieMensuel :categorieMonth.entrySet()) {
        	categorieSerie.set(categorieMensuel.getKey(), categorieMensuel.getValue());
		}      
    	lineCategorieModel.addSeries(categorieSerie);
    }  
    

	public LineChartModel getLineCategorieModel() {
		return lineCategorieModel;
	}
	

	public List<Operation> getOperations() {
		return operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

	public List<Categorie> getCategories() {
		return categories;
	}

	public void setCategories(List<Categorie> categories) {
		this.categories = categories;
	}

	public Categorie getCategorieSelect() {
		return categorieSelect;
	}

	public void setCategorieSelect(Categorie categorieSelect) {		
		this.categorieSelect = categorieSelect;
	}

	public LineChartModel getLineTotalModel() {
		return lineTotalModel;
	}

	public void setLineTotalModel(LineChartModel lineTotalModel) {
		this.lineTotalModel = lineTotalModel;
	}	
}
