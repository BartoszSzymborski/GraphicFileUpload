/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.zadanie4.logic;

import java.util.Arrays;
import java.util.stream.Stream;
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

    public void upload() {
        if (file.getSize() > 0) { //sprawdzanie wielkości uploadowanego pliku
            String fileName = file.getFileName();//pobieranie nazwy uploadowanego pliku
            String[] split = fileName.split("\\.");//dzielnie nazwy uploadowanego pliku na dwa indeksy
            if (split.length >= 1) { //jeśli ma drugi człon
                String typeOfFile = split[split.length-1];//przypadek jak jest więcej niż jedna kropka (znajdzie ostatni element po kropce - który jest rozszerzeniem pliku, a - 1 bo trzeba pamiętać o indeksach)
                if (Stream.of("jpg","png","jpeg").anyMatch(s -> s.equals(typeOfFile))) //sprawdzanie czy drugi człon pliku zgadza się z formatem przekazywanym 
                {
                    iras.addPhoto(file);
                    FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded");
                    FacesContext.getCurrentInstance().addMessage(null, message); //dodwanie zdjęcia plus komuniakt
                    return; //przerwanie działania metody
                }
// http://thequirksofit.blogspot.com/2016/08/fileuploadexception-ut000020-connection.html ustawianie max wielkości przesyłanego pliku w Wildfly
            }
        } 
            FacesMessage mess = new FacesMessage("Please upload picture file. Any other file will not be permited. File also must exist");//to wyświetli się jeśli jakiś warunek u góry się nie spełnił
            FacesContext.getCurrentInstance().addMessage(null, mess);
        

    }
}
