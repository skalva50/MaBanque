package com.skalvasociety.skalva.view;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.annotation.PostConstruct;

import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.skalvasociety.skalva.bean.Categorie;

import com.skalvasociety.skalva.service.ICategorieService;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
@Controller
@Transactional
public class CategorieView implements Serializable {
	
	@Autowired
    private ICategorieService service;
    
	private List<Categorie> categories;
	
	private Categorie categorie = new Categorie();
	
	private Categorie selectedCategorie;
	
	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	@PostConstruct
    public void init() {		
    	categories = service.getAll();
    }

	public List<Categorie> getCategories() {
		return categories;
	}

	public void setCategories(List<Categorie> categories) {
		this.categories = categories;
	}
	
	public void save(){
		service.save(categorie);
		categorie = new Categorie();
		categories = service.getAll();		
	}
	
	public void delete(){
		if(selectedCategorie != null){
			service.delete(selectedCategorie);
			categories = service.getAll();
		}
		
	}

	public Categorie getSelectedCategorie() {
		return selectedCategorie;
	}

	public void setSelectedCategorie(Categorie selectedCategorie) {
		this.selectedCategorie = selectedCategorie;
	}
	
    public void onRowEdit(RowEditEvent event) {    	   	
    	String libelle = ((Categorie) event.getObject()).getLibelle();    	
    	Boolean horsStats = ((Categorie) event.getObject()).getHorsStats(); 
    	Categorie categorie = service.getByKey(((Categorie) event.getObject()).getId());    	
    	categorie.setLibelle(libelle);
    	categorie.setHorsStats(horsStats);
    }
    
    public void onRowCancel(RowEditEvent event) {    	    	
    	Categorie categorie = service.getByKey(((Categorie) event.getObject()).getId());  
    	service.delete(categorie);
    	categories = service.getAll();
    }

}
