package com.skalvasociety.skalva.view;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.annotation.PostConstruct;
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

}
