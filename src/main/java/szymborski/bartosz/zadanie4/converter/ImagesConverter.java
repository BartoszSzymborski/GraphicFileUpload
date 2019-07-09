/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.zadanie4.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author bartosz.szymborski
 */
//@FacesConverter("images")
public class ImagesConverter implements Converter<Object[]> {

    @Override
    public Object[] getAsObject(FacesContext fc, UIComponent uic, String string) {
        return new Object[]{};
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object[] t) {
        
        return t[0] + ", " + t[1] +" KB";
    }

}
