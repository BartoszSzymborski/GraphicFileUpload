/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.zadanie4.view;

import szymborski.bartosz.zadanie4.service.ImageResourceApplicationService;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author bartosz.szymborski
 */
@Component
@Scope("request")
public class AvailablePhotosView {

    @Autowired
    private ImageResourceApplicationService iras;

    Map<String, Object> options = new HashMap<>();

    public void openPhoto(String fileName) {
        options.put("draggable", Boolean.TRUE);
        options.put("resizable", Boolean.TRUE);
        options.put("responsive", Boolean.TRUE);
        options.put("contentHeight", iras.getCheckerMap().get(fileName)[1] + 20);
        options.put("contentWidth", iras.getCheckerMap().get(fileName)[0] + 20);
        Map<String, List<String>> params = new HashMap<>();
        params.put(ImageResourceApplicationService.FILE_NAME, Arrays.asList(fileName));
        PrimeFaces.current().dialog().openDynamic("showPhoto", options, params);

    }

}
