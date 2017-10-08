package com.skalvasociety.skalva.dao;

import org.springframework.stereotype.Repository;

import com.skalvasociety.skalva.bean.Categorie;

@Repository("categorieDao")
public class CategorieDao extends AbstractDao<Integer,Categorie> implements ICategorieDao {

}
