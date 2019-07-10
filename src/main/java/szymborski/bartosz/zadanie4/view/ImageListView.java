/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.zadanie4.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import szymborski.bartosz.zadanie4.service.ImageResourceApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author bartosz.szymborski
 */
@Component
@Scope("view")
public class ImageListView {

    @Autowired
    private ImageResourceApplicationService iras;

    private List<Map.Entry<String, Long>> imageList;

    public List<Map.Entry<String, Long>> getImageList() {
        if (imageList == null) {
            imageList = new ArrayList<>(iras.getContent().entrySet());
        }
        Map<String, Integer> mapPosition = iras.getImagesPosition();
        imageList.sort((e1, e2) -> mapPosition.get(e1.getKey()).compareTo(mapPosition.get(e2.getKey()))); //e1, e2 - zmienne typu listy u góry = Map.Entry, wywołanie getera mapy z pozycjami, 
        //sortowanie na liście za pomoca lambdy dwóch list (mapEntry), pobranie wartości pierwszej listy, gdzie wartością jest klucza pierwszej mapy i porównie do drugiego (analogicznie jak pierwsze)
        return imageList;
    }

    public void setImageList(List<Map.Entry<String, Long>> imageList) {
        this.imageList = imageList;
    }

    public void upAndDownList(String fileName, boolean down) {
        Map<String, Integer> mapPosition = iras.getImagesPosition();//pobranie pozycji 
        int newPosition = mapPosition.get(fileName) + (down ? 1 : - 1);//ustalenie nowej pozycji wraz z warunkiem, że jeśli down to  +1 albo -1
        iras.changeImagePositon(fileName, newPosition); //zaposujemy nową zmienną
        FacesMessage message = new FacesMessage("Position change"); //komunikat o zmianie pozycji
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void infoAboutSaveList() {
        // metoda wywałana na widoku w właściwości actionListener = wtedy metoda wywoła sie po wykonaniu metody ujętej w action
        FacesMessage message = new FacesMessage("Changes save");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}
