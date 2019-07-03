/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.zadanie4.logic;


import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import szymborski.bartosz.zadanie4.service.ImageResourceApplicationService;

/**
 *
 * @author bartosz.szymborski
 */
@Component
@Scope("view")
public class FileUploadView {
    
    @Autowired
    private ImageResourceApplicationService iras;
    
    private UploadedFile file;
    
    public UploadedFile getFile() {
        return file;
    }
   

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void upload(){
        if(file!=null){
            iras.addPhoto(file);
            FacesMessage message = new FacesMessage("Succesful",file.getFileName() + " is uploaded");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    
}
