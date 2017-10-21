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
import com.skalvasociety.skalva.bean.TypeCategorie;
import com.skalvasociety.skalva.service.ICategorieService;
import com.skalvasociety.skalva.service.ITypeCategorieService;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
@Controller
@Transactional
public class CategorieView implements Serializable {
	
	@Autowired
    private ICategorieService service;	
	@Autowired
	private ITypeCategorieService typeCategorieService;
    
	private List<Categorie> categories;	
	private Categorie categorie = new Categorie();	
	private Categorie selectedCategorie;
	
	private List<TypeCategorie> typeCategories;
	private int typeCategorieSelected;
	
	
	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	@PostConstruct
    public void init() {		
    	categories = service.getAll();
    	setTypeCategories(typeCategorieService.getAll());
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
		init();		
	}
	
	public void delete(){
		if(selectedCategorie != null){
			service.delete(selectedCategorie);			
		}
		init();		
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
  	
    	// Recherche de l'operation selectionné dans la vue
    	Categorie categorieView = null;
    	for (Categorie categorie : categories) {
			if(categorie.getId() == ((Categorie)event.getObject()).getId()){
				categorieView =  categorie;
			}
		}
    	// Recherche de la mêmeoperation selectionné dans la BDD
    	Categorie categorieBDD = service.getByKey(((Categorie)event.getObject()).getId());
    	
    	// Recherche de la categorie selectionnée dans la vue
    	TypeCategorie typecategorie = typeCategorieService.getByKey(typeCategorieSelected);
    	
    	// Mise à jour de l'operation de la vue
    	categorieView.setTypeCategorie(typecategorie);
    	categorieView.setLibelle(libelle);
    	categorieView.setHorsStats(horsStats);
    	// Mise à jour de l'operation de la BDD    	
    	categorieBDD.setTypeCategorie(typecategorie);    
    	categorieBDD.setLibelle(libelle);
    	categorieBDD.setHorsStats(horsStats);
    	
    }
    
    public void onRowCancel(RowEditEvent event) {    	    	
    	Categorie categorie = service.getByKey(((Categorie) event.getObject()).getId());  
    	service.delete(categorie);
    	categories = service.getAll();
    }

	public List<TypeCategorie> getTypeCategories() {
		return typeCategories;
	}

	public void setTypeCategories(List<TypeCategorie> typeCategories) {
		this.typeCategories = typeCategories;
	}

	public int getTypeCategorieSelected() {
		return typeCategorieSelected;
	}

	public void setTypeCategorieSelected(int typeCategorieSelected) {
		this.typeCategorieSelected = typeCategorieSelected;
	}

}
