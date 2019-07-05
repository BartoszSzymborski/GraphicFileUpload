/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.zadanie4.service;

import java.util.List;
import szymborski.bartosz.zadanie4.entity.Image;

/**
 *
 * @author bartosz.szymborski
 */
public interface PhotoService {
    
    List<Image> getPhotos(String pictureName);
    List<Image> getPhotos();
}
