package com.skalvasociety.skalva.dao;

import org.springframework.stereotype.Repository;

import com.skalvasociety.skalva.bean.AssOperationCategorie;

@Repository("assOperationCategorieDao")
public class AssOperationCategorieDao extends AbstractDao<Integer,AssOperationCategorie> implements IAssOperationCategorie{

}

