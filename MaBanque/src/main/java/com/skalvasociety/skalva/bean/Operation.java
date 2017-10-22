package com.skalvasociety.skalva.bean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.skalvasociety.skalva.converter.DateConverter;

@Entity
@Table(name="OPERATION")
public class Operation {
	private int id;
	private Date dateOperation;
	private String libelle;
	private Double montant;
	private boolean sens;
	private Categorie categorie;
	private String numcompte;
	private String reference;
	
	 @Transient
    private String mois;
    
	 @Transient
    public String getMois() {   
		 return new DateConverter().dateToStringMonth(getDateOperation(), "yyyy-MM-dd");
    }
    
    public void setMois(String mois){
    	this.mois = mois;
    }
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDateOperation() {
		return dateOperation;
	}
	public void setDateOperation(Date dateOperation) {
		this.dateOperation = dateOperation;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public Double getMontant() {
		return montant;
	}
	public void setMontant(Double montant) {
		this.montant = montant;
	}
	/**
	 * Vrai si recette, Faux di depenses
	 * @return
	 */
	public boolean isSens() {
		return sens;
	}
	public void setSens(boolean sens) {
		this.sens = sens;
	}	

	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_categorie", nullable = true)
	public Categorie getCategorie() {
		return categorie;
	}
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public String getNumcompte() {
		return numcompte;
	}

	public void setNumcompte(String numcompte) {
		this.numcompte = numcompte;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
}
