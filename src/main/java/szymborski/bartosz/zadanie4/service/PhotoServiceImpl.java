/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.zadanie4.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import szymborski.bartosz.zadanie4.dao.dao.ImageDao;
import szymborski.bartosz.zadanie4.entity.Image;

/**
 *
 * @author bartosz.szymborski
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class PhotoServiceImpl  implements PhotoService{

    @Autowired
    private  ImageDao imageDao;
    
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<Image> getPhotos(String pictureName) {
        List<Image> images = new ArrayList<>(imageDao.getImages(pictureName));
        return images;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<Image> getPhotos() {
        List<Image> images = new ArrayList<>(imageDao.getImages());
        return images;
    }

   
    
}
