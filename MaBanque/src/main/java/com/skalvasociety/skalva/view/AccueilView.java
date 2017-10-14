package com.skalvasociety.skalva.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DateAxis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.skalvasociety.skalva.bean.Operation;
import com.skalvasociety.skalva.service.IOperationService;

@ManagedBean
@ViewScoped
@Controller
@Transactional
public class AccueilView {
	
	@Autowired
    private IOperationService service;
	
	private BarChartModel barTotalModel;
	private List<Operation> operations;
	
	
	public List<Operation> getOperations() {
		return operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}
	
	public BarChartModel getBarTotalModel() {
        return barTotalModel;
    }
	
	@PostConstruct
    public void init() {		
		operations = service.getAllCourant();
		createDateModel();
    }
	
    private void createDateModel() {
    	barTotalModel = new BarChartModel();
        
    	ChartSeries recetteSerie = new ChartSeries();
    	recetteSerie.setLabel("recette"); 
        HashMap<String,Double> recettesMensuels = service.recetteMensuels();
        for (Entry<String, Double> recette :recettesMensuels.entrySet()) {
        	recetteSerie.set(recette.getKey(), recette.getValue());
		}

        barTotalModel.setBarWidth(30);
        
    	ChartSeries depensesSerie = new ChartSeries();
    	depensesSerie.setLabel("Depenses"); 
        HashMap<String,Double> depensesMensuels = service.depensesMensuels();
        for (Entry<String, Double> depense :depensesMensuels.entrySet()) {
        	depensesSerie.set(depense.getKey(), depense.getValue());
		}

        
        barTotalModel.addSeries(recetteSerie);
        barTotalModel.addSeries(depensesSerie);
        barTotalModel.setTitle("Bilan");    
        barTotalModel.setAnimate(true);
        barTotalModel.setLegendPosition("ne");
        barTotalModel.getAxis(AxisType.Y).setLabel("Montant");
        DateAxis axis = new DateAxis("Dates"); 
       
        axis.setMin("2016-12-01");
        axis.setMax("2017-10-30");
        axis.setTickFormat("%b %y");
         
        barTotalModel.getAxes().put(AxisType.X, axis);
    }

}
