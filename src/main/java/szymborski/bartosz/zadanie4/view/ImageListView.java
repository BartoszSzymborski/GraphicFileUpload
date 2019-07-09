/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.zadanie4.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import szymborski.bartosz.zadanie4.service.ImageResourceApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import szymborski.bartosz.zadanie4.entity.Image;

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

    public void upAndDownList(String fileName, boolean up) {
        Map<String, Integer> mapPosition = iras.getImagesPosition();
        int newPosition = mapPosition.get(fileName) + (up ? 1 : - 1);
        iras.changeImagePositon(fileName, newPosition);
    }
    

}
