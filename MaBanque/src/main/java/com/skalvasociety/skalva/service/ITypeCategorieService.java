package com.skalvasociety.skalva.service;

import java.util.List;

import com.skalvasociety.skalva.bean.TypeCategorie;

public interface ITypeCategorieService {
	public List<TypeCategorie> getAll();
	public void save(TypeCategorie typeCategorie);
	public void delete(TypeCategorie typeCategorie);
	public TypeCategorie getByKey(int key);
}
