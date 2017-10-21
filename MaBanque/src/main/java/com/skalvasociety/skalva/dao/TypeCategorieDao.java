package com.skalvasociety.skalva.dao;

import org.springframework.stereotype.Repository;

import com.skalvasociety.skalva.bean.TypeCategorie;

@Repository("typeCategorieDao")
public class TypeCategorieDao extends AbstractDao<Integer,TypeCategorie> implements ITypeCategorieDao  {

}
