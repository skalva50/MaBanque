package com.skalvasociety.skalva.service;

import java.util.List;

import com.skalvasociety.skalva.bean.Categorie;

public interface ICategorieService {
	public List<Categorie> getAll();
	public void save(Categorie categorie);
	public void delete(Categorie categorie);
	public Categorie getByKey(int key);
	
}
