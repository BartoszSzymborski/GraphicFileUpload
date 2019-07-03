/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.zadanie4.view;

import szymborski.bartosz.zadanie4.service.ImageResourceApplicationService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author bartosz.szymborski
 */
@Component
@Scope("view")
public class ImagListView {

    @Autowired
    private ImageResourceApplicationService iras;

    private Map<String, Long> imageList;

    public Map<String,Long> getList() {
        if (imageList == null) {
            return iras.getContent();
        }
        return imageList;
    }
    

    public void setList(Map<String,Long> list) {
        this.imageList = imageList;
    }

}
