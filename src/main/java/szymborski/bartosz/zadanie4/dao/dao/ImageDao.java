/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.zadanie4.dao.dao;

import java.util.Collection;
import java.util.List;
import javax.ws.rs.container.Suspended;
import org.hibernate.Hibernate;
import org.hibernate.Query;
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
    public void persistImage(Image image) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(Image.class.getSimpleName(), image); // CRUD - C - zapisywanie do bazy
    }

    @SuppressWarnings("unchecked")
    public Collection<Image> getImages(String pictureName) { //gdy - ? extends - wild card - każda klasa rozszerzająca twoją klase - Image
        Session session = sessionFactory.getCurrentSession();
        pictureName = pictureName.toLowerCase();
        Query query = session.createQuery("SELECT pictureName FROM Image i");
        query.setParameter("pictureName", pictureName);
        final List list = query.list();
        list.forEach(Hibernate::initialize);
        return list;
    }

    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Collection<Image> getImages() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT i FROM Image i ORDER BY i.position ASC"); // CRUD - R - pobieranie listy wszystkich plików uporządkowanych po numerze pozycji
        final List list = query.list();
        list.forEach(Hibernate::initialize);
        return list;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateImages(Image... image) { //varargs - wiele lub jeden artgumentów do tablicy
        if (image != null) { // jeśli nie jest null
            for (Image img : image) { //iteracja dla każdego elementu
                Session session = sessionFactory.getCurrentSession();
                session.update(img);//CRUD - U -  update
            }
        }

    }
}
