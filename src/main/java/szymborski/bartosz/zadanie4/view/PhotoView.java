/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.zadanie4.view;

import szymborski.bartosz.zadanie4.service.ImageResourceApplicationService;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.primefaces.PrimeFaces;
import org.primefaces.context.PrimeFacesContext;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import static szymborski.bartosz.zadanie4.service.ImageResourceApplicationService.FILE_NAME;

/**
 *
 * @author bartosz.szymborski
 */
@Component
@Scope("view")
public class PhotoView {

    public static final String FILE_NAME = "FILE_NAME";
    private String fileName;
    private StreamedContent photo;

    @Autowired
    private ImageResourceApplicationService iras;
    
    @PostConstruct
    public void init() {
        final Map<String, String> requestParameterMap = PrimeFacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap();
        fileName = requestParameterMap.get(FILE_NAME);
        if (fileName == null) {
            return;
        }
        photo = iras.streamize(fileName);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public StreamedContent getPhoto() {
        return photo;
    }

    public void setPhoto(StreamedContent photo) {
        this.photo = photo;
    }
    
}
