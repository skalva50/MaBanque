package com.skalvasociety.skalva.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TypeCategorie")
public class TypeCategorie {
	private int id;
	private String libelle;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	@Override
	public boolean equals(Object obj) {
		if (obj == null){
			  return false;
		}		    
		if (!(obj instanceof TypeCategorie)){
			  return false;
		}		    
		return ((TypeCategorie) obj).getId()==this.getId();
	}
}
