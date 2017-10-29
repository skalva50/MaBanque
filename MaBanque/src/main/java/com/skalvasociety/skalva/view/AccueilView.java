package com.skalvasociety.skalva.view;

import java.text.ParseException;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
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
import com.skalvasociety.skalva.bean.TypeCategorie;
import com.skalvasociety.skalva.converter.DateConverter;
import com.skalvasociety.skalva.service.ICategorieService;
import com.skalvasociety.skalva.service.IOperationService;
import com.skalvasociety.skalva.service.ITypeCategorieService;

@ManagedBean
@ViewScoped
@Controller
@Transactional
public class AccueilView {
	
	private static final Logger logger = Logger.getLogger(AccueilView.class);
	
	@Autowired
    private IOperationService service;
	
	@Autowired
    private ICategorieService categorieService;
	
	@Autowired
    private ITypeCategorieService typeCategorieService;
	
	//Graphe line total
	private LineChartModel lineTotalModel;
	private List<Operation> operations;
	private LineChartSeries recetteSerie;
	
	// Graphe line categories
	private LineChartModel lineCategorieModel;
	private List<Categorie> categories;
	private Categorie categorieSelect;	
	
	// Graphe line epargnes
	private LineChartModel lineEpargneModel;
	
	// Champ
	private Double soldeEpargne;
	private Double soldeCourant;
	private Double depensesAnnuel;
	private Double recettesAnnuel;

	@PostConstruct
    public void init() {		
		operations = service.getAllCourant();
		Date date = new DateConverter().getDateNow();
		soldeEpargne = service.getSoldeEpargne(date);
		soldeCourant = service.getSoldeCourant(date);
		depensesAnnuel = service.getDepensesAnnuels(date);
		recettesAnnuel = service.getRecettesAnnuels(date);
		setCategories(categorieService.getAll());
		createLineCategorieModels();
		createLineTotalModel();
		createlineEpargneModel();
    }

	public void reload(ActionEvent actionEvent) {        
        init();
    }
    
    public void itemSelect(ItemSelectEvent event) {    	
    	
    	// Recuperation de la hashmap d'alimentation du graphe
    	Set<Entry<Object, Number>> mapValues = recetteSerie.getData().entrySet();
    	
    	// Transformation en Array pour recupérer l'index
    	@SuppressWarnings("unchecked")
		Entry<Object,Number>[] test = new Entry[mapValues.size()];
    	mapValues.toArray(test);
    	String sDate = test[event.getItemIndex()].getKey().toString();    	
    	// Conversion de la date string recuperé en Date
    	Date date = null;    	
    	try {
            date = new DateConverter().stringToDate(sDate, "yyyy-MM-dd");
        } catch (ParseException e) {
            logger.error(e.getMessage(), e.getCause());
        }    	

    	// Recuperation du managedBean lié
    	FacesContext context = FacesContext.getCurrentInstance();
        ELContext elContext = context.getELContext();
        MensuelView mensuelView  = (MensuelView) elContext.getELResolver().getValue(elContext, null, "mensuelView");
        // Mise à jour du managed bean
    	mensuelView.setDateSelected(date);
    	mensuelView.init();
    	// Redirection vers la page XHTML
    	FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "mensuel"); 
    }
    
    /**
     * Creer le graphe du bilan total ( recettes , depenses)
     */
    private void createLineTotalModel() {
    	lineTotalModel = new LineChartModel();
        Date dateMin = null;
        Date dateMax = null;
    	
        // Ligne des recettes
        recetteSerie = new LineChartSeries();
    	recetteSerie.setLabel("Recettes");    	
        LinkedHashMap<String,Double> recettesMensuels = service.recetteMensuels();
        Date date;
        for (Entry<String, Double> recette :recettesMensuels.entrySet()) {
        	recetteSerie.set(recette.getKey(), recette.getValue());  
        	// Mise à jour des dates min et max ( pour bornes du graphe)
			try {
				date = new DateConverter().stringToDate(recette.getKey(),"yyyy-MM-dd");  
	        	if(dateMin == null || dateMin.compareTo(date) > 0){
	        		dateMin = date;
	        	}
	        	if(dateMax == null || dateMax.compareTo(date) < 0){
	        		dateMax = date;
	        	}
			} catch (ParseException e) {
				logger.error(e.getMessage(), e.getCause());
			}
		}       
        
        // Ligne des depenses
        LineChartSeries depensesSerie = new LineChartSeries();
    	depensesSerie.setLabel("Depenses"); 
        HashMap<String,Double> depensesMensuels = service.depensesMensuels();        
        for (Entry<String, Double> depense :depensesMensuels.entrySet()) {
        	depensesSerie.set(depense.getKey(), depense.getValue());       	
        	// Mise à jour des dates min et max ( pour bornes du graphe)
			try {
				date =  new DateConverter().stringToDate(depense.getKey(),"yyyy-MM-dd");
	        	if(dateMin == null || dateMin.compareTo(date) > 0){
	        		dateMin = date;
	        	}
	        	if(dateMax == null || dateMax.compareTo(date) < 0){
	        		dateMax = date;
	        	}
			} catch (ParseException e) {
				logger.error(e.getMessage(), e.getCause());
			}
		}
        
        // Ligne des charges fixes
        TypeCategorie typeCategorieChargesFixes = typeCategorieService.getByLibelle("Charges fixes");
        LineChartSeries chargesFixesSerie = new LineChartSeries();
        chargesFixesSerie.setLabel("Charges fixes"); 
        HashMap<String,Double> chargesFixes = service.recetteMensuelsByTypeCategorie(typeCategorieChargesFixes); 
        for (Entry<String, Double> chargesFixe : chargesFixes.entrySet()) {
        	chargesFixesSerie.set(chargesFixe.getKey(), chargesFixe.getValue());
		}
        
        // Ligne des charges fixes
        TypeCategorie typeCategorieLoisirs = typeCategorieService.getByLibelle("Loisirs");
        LineChartSeries loisirsSerie = new LineChartSeries();
        loisirsSerie.setLabel("Loisirs"); 
        HashMap<String,Double> loisirs = service.recetteMensuelsByTypeCategorie(typeCategorieLoisirs); 
        for (Entry<String, Double> loisir : loisirs.entrySet()) {
        	loisirsSerie.set(loisir.getKey(), loisir.getValue());
		}
        

        lineTotalModel.addSeries(recetteSerie);
        lineTotalModel.addSeries(depensesSerie);
        lineTotalModel.addSeries(chargesFixesSerie);
        lineTotalModel.addSeries(loisirsSerie);
        lineTotalModel.setTitle("Bilan");    
        lineTotalModel.setAnimate(true);
        lineTotalModel.setLegendPosition("ne");
        lineTotalModel.setShowPointLabels(true);
        
        
        lineTotalModel.getAxis(AxisType.Y).setLabel("Montant");
        DateAxis axis = new DateAxis("Dates");       
        
        // Conversion des dates min et max en string( necessaire à la hasmpap du graphe)
        String sDateMin = new DateConverter().dateToStringAddMonth(dateMin,-1, "yyyy-MM-dd");       
        String sDateMax = new DateConverter().dateToStringAddMonth(dateMax,2, "yyyy-MM-dd");
        
        axis.setMin(sDateMin);
        axis.setMax(sDateMax);
        axis.setTickFormat("%b %y");
         
        lineTotalModel.getAxes().put(AxisType.X, axis);
    } 
    
    /**
     * Crée le graphe du bilan par categorie 
     */
    private void createLineCategorieModels() {
        
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
    	
    	// Categorie selectoinné par defaut
    	if(categorieSelect == null){ 
    		categorieSelect = categorieService.getByKey(2);
    	}  	
    	categorieSerie.setLabel(categorieSelect.getLibelle());   	 
    	

    	Date date;
        HashMap<String,Double> categorieMonth = service.getCategorieMonth(categorieSelect);
        for (Entry<String, Double> categorieMensuel :categorieMonth.entrySet()) {
        	categorieSerie.set(categorieMensuel.getKey(), categorieMensuel.getValue());  
        	// Mise à jour des dates min et max ( pour bornes du graphe)
			try {
				date = new DateConverter().stringToDate(categorieMensuel.getKey(),"yyyy-MM-dd");
	        	if(dateMin == null || dateMin.compareTo(date) > 0){
	        		dateMin = date;
	        	}
	        	if(dateMax == null || dateMax.compareTo(date) < 0){
	        		dateMax = date;
	        	}
			} catch (ParseException e) {
				logger.error(e.getMessage(), e.getCause());
			}
		}     
        String sDateMin = new DateConverter().dateToStringAddMonth(dateMin,-1, "yyyy-MM-dd");       
        String sDateMax = new DateConverter().dateToStringAddMonth(dateMax,2, "yyyy-MM-dd");
        
        axis.setMin(sDateMin);
        axis.setMax(sDateMax);
        lineCategorieModel.addSeries(categorieSerie);       
    }   
    
    public void createlineEpargneModel() {
    	lineEpargneModel = new LineChartModel();
        Date dateMin = null;
        Date dateMax = null;
        
        lineEpargneModel.setTitle("Epargne");
        lineEpargneModel.setAnimate(true);
        lineEpargneModel.setLegendPosition("ne");
        lineEpargneModel.setShowPointLabels(true);
        
        Axis yAxis = lineEpargneModel.getAxis(AxisType.Y);
        yAxis.setLabel("Montant");
        
        DateAxis axis = new DateAxis("Dates");
        axis.setTickFormat("%b %y");         
        lineEpargneModel.getAxes().put(AxisType.X, axis);
        
    	ChartSeries epargneRecetteSerie = new ChartSeries();  	
    	epargneRecetteSerie.setLabel("Recettes");
    	ChartSeries soldeSerie = new ChartSeries();  	
    	soldeSerie.setLabel("Solde");
    	Date date;
    	Double solde;
        HashMap<String,Double> epargneRecetteMensuels = service.getEpargneRecetteMensuels();
        for (Entry<String, Double> epargneRecetteMensuel :epargneRecetteMensuels.entrySet()) {
        	epargneRecetteSerie.set(epargneRecetteMensuel.getKey(), epargneRecetteMensuel.getValue());  
        	// Mise à jour des dates min et max ( pour bornes du graphe)
        	// Ajout du solde mensuel
			try {
				date = new DateConverter().stringToDate(epargneRecetteMensuel.getKey(),"yyyy-MM-dd");
				solde = service.getSoldeEpargne(date);
				soldeSerie.set(epargneRecetteMensuel.getKey(), solde);
	        	if(dateMin == null || dateMin.compareTo(date) > 0){
	        		dateMin = date;
	        	}
	        	if(dateMax == null || dateMax.compareTo(date) < 0){
	        		dateMax = date;
	        	}
			} catch (ParseException e) {
				logger.error(e.getMessage(), e.getCause());
			}
		}  
        
    	ChartSeries epargneDepensesSerie = new ChartSeries();  	
    	epargneDepensesSerie.setLabel("Depenses");    	
        HashMap<String,Double> epargneDepensesMensuels = service.getEpargneDepenseMensuels();
        for (Entry<String, Double> epargneDepensesMensuel :epargneDepensesMensuels.entrySet()) {
        	epargneDepensesSerie.set(epargneDepensesMensuel.getKey(), epargneDepensesMensuel.getValue());  
        	// Mise à jour des dates min et max ( pour bornes du graphe)
			try {
				date = new DateConverter().stringToDate(epargneDepensesMensuel.getKey(),"yyyy-MM-dd");
	        	if(dateMin == null || dateMin.compareTo(date) > 0){
	        		dateMin = date;
	        	}
	        	if(dateMax == null || dateMax.compareTo(date) < 0){
	        		dateMax = date;
	        	}
			} catch (ParseException e) {
				logger.error(e.getMessage(), e.getCause());
			}
		} 
        
        String sDateMin = new DateConverter().dateToStringAddMonth(dateMin,-1, "yyyy-MM-dd");       
        String sDateMax = new DateConverter().dateToStringAddMonth(dateMax,2, "yyyy-MM-dd");
        
        axis.setMin(sDateMin);
        axis.setMax(sDateMax);
        lineEpargneModel.addSeries(epargneRecetteSerie);   
        lineEpargneModel.addSeries(epargneDepensesSerie);  
        lineEpargneModel.addSeries(soldeSerie);  
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
	
	public LineChartModel getLineEpargneModel() {
		return lineEpargneModel;
	}

	public void setLineEpargneModel(LineChartModel lineEpargneModel) {
		this.lineEpargneModel = lineEpargneModel;
	}

	public Double getSoldeEpargne() {
		return soldeEpargne;
	}

	public void setSoldeEpargne(Double soldeEpargne) {
		this.soldeEpargne = soldeEpargne;
	}

	public Double getSoldeCourant() {
		return soldeCourant;
	}

	public void setSoldeCourant(Double soldeCourant) {
		this.soldeCourant = soldeCourant;
	}

	public Double getDepensesAnnuel() {
		return depensesAnnuel;
	}

	public void setDepensesAnnuel(Double depensesAnnuel) {
		this.depensesAnnuel = depensesAnnuel;
	}

	public Double getRecettesAnnuel() {
		return recettesAnnuel;
	}

	public void setRecettesAnnuel(Double recettesAnnuel) {
		this.recettesAnnuel = recettesAnnuel;
	}
}
