package com.skalvasociety.skalva.view;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.skalvasociety.skalva.service.IOperationService;

@ManagedBean
@ViewScoped
@Controller
@Transactional
public class UploadView {
	@Autowired
    private IOperationService service;
	
    private UploadedFile file;
    
    public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }
     
    public void upload() {
        if(file != null) {
        	try {
				service.loadOperation(file.getInputstream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
}
