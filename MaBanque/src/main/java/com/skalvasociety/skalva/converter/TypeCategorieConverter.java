package com.skalvasociety.skalva.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skalvasociety.skalva.bean.Categorie;
import com.skalvasociety.skalva.service.ITypeCategorieService;

@Service("typeCategorieConverter")
public class TypeCategorieConverter implements Converter {
	
	@Autowired
	private ITypeCategorieService typeCategorieService; 
	
	private static Logger logger = Logger.getLogger(TypeCategorieConverter.class); 
	
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if(value != null && value.trim().length() > 0) {
			try{				
				if(typeCategorieService == null){
					return null;
				}else{
					return typeCategorieService.getByKey(Integer.parseInt(value));
				}				
            } catch(NumberFormatException e) {
            	logger.error(e.getMessage(), e.getCause());
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
