package com.skalvasociety.skalva.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="CATEGORIE")
public class Categorie {
	private int id;
	private String libelle;
	private Boolean horsStats;
	private TypeCategorie typeCategorie;
	
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
		if (!(obj instanceof Categorie)){
			  return false;
		}		    
		return ((Categorie) obj).getId()==this.getId();
	}
	
	@Column(name="hors_stats")
	public Boolean getHorsStats() {
		return horsStats;
	}
	public void setHorsStats(Boolean horsStats) {
		this.horsStats = horsStats;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_typecategorie", nullable = true)
	public TypeCategorie getTypeCategorie() {
		return typeCategorie;
	}
	public void setTypeCategorie(TypeCategorie typeCategorie) {
		this.typeCategorie = typeCategorie;
	}
	
	

	
}
