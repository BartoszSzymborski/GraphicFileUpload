/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.zadanie4.dao.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import szymborski.bartosz.zadanie4.entity.Image;

/**
 *
 * @author bartosz.szymborski
 */
@Repository
public class ImageDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    @Transactional(propagation = Propagation.MANDATORY)
    public void persistImage(Image image){
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(Image.class.getSimpleName(),image);
    }
    
}
