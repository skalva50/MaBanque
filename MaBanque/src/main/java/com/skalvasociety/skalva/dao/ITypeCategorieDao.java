package com.skalvasociety.skalva.dao;

import java.io.Serializable;

import com.skalvasociety.skalva.bean.TypeCategorie;


public interface ITypeCategorieDao extends IDao<Serializable,TypeCategorie> {
	public TypeCategorie getByLibelle (String libelle);
}
