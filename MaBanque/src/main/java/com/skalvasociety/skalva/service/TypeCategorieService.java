package com.skalvasociety.skalva.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skalvasociety.skalva.bean.TypeCategorie;
import com.skalvasociety.skalva.dao.ITypeCategorieDao;

@Service("typeCategorieService")
@Transactional
public class TypeCategorieService implements ITypeCategorieService {
	
	@Autowired 
	private ITypeCategorieDao dao;

	public List<TypeCategorie> getAll() {		
		return dao.getAll();
	}

	public void save(TypeCategorie typeCategorie) {
		dao.save(typeCategorie);		
	}

	public void delete(TypeCategorie typeCategorie) {
		dao.delete(typeCategorie);		
	}

	public TypeCategorie getByKey(int key) {		
		return dao.getByKey(key);
	}

	public TypeCategorie getByLibelle(String libelle) {		
		return dao.getByLibelle(libelle);
	}


}
