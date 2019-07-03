/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.zadanie4.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.primefaces.context.PrimeFacesContext;
import org.primefaces.model.ByteArrayContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

/**
 *
 * @author bartosz.szymborski
 */
@Service
@ApplicationScope
public class ImageResourceApplicationService implements InitializingBean {

    public static final String FILE_NAME = "FILE_NAME";

    private String fileName;
    private StreamedContent photo;

    private final Map<String, byte[]> images = new HashMap<>();

    public void addPhoto(UploadedFile file) {
        byte[] bArray = readFileToByteArray(file);
        images.put(file.getFileName(), bArray);

    }

    private byte[] readFileToByteArray(UploadedFile file) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            InputStream in = file.getInputstream();
            byte[] buffer = new byte[1024];
            int content;
            while ((content = in.read(buffer)) != -1) {
                os.write(buffer, 0, content);
            }
        } catch (IOException ex) {
            Logger.getLogger(ImageResourceApplicationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return os.toByteArray();

    }

    public Map<String, Long> getContent() {
        return images
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, ent -> (long) ent.getValue().length / 1024));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //// do uzupełnienia przy bazie danych
    }
   
    public StreamedContent streamize(String fileName) {
        return new ByteArrayContent(images.get(fileName));
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
