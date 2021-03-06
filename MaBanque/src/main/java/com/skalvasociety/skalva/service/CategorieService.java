package com.skalvasociety.skalva.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skalvasociety.skalva.bean.Categorie;
import com.skalvasociety.skalva.dao.ICategorieDao;

@Service("categorieService")
@Transactional
public class CategorieService  implements ICategorieService {
	@Autowired 
	private ICategorieDao dao;
	
	public List<Categorie> getAll() {		
		return dao.getAll();
	}

	public void save(Categorie categorie) {
		dao.save(categorie);
		
	}

	public void delete(Categorie categorie) {
			dao.delete(categorie);	
	}

	public Categorie getByKey(int key) {
		return dao.getByKey(key);
		
	}
	

}
