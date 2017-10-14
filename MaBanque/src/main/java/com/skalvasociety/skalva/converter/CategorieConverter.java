package com.skalvasociety.skalva.converter;

import javax.faces.application.FacesMessage;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.skalvasociety.skalva.bean.Categorie;
import com.skalvasociety.skalva.service.ICategorieService;

@Service
public class CategorieConverter implements Converter {
	
	@Autowired
	private ICategorieService categorieService; 

	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if(value != null && value.trim().length() > 0) {
			try{				
				if(categorieService == null){						
					return null;
				}else{					
					return categorieService.getByKey(Integer.parseInt(value));
				}				
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid categorie."));
            }
        }
        else {
            return null;
        }
	}

	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((Categorie) object).getId());
        }
        else {
            return null;
        }
	}

}
