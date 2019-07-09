/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.zadanie4.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import org.hibernate.Hibernate;
import org.primefaces.model.ByteArrayContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.ApplicationScope;
import szymborski.bartosz.zadanie4.dao.dao.ImageDao;
import szymborski.bartosz.zadanie4.entity.Image;

/**
 *
 * @author bartosz.szymborski
 */
@Service
@ApplicationScope
public class ImageResourceApplicationService implements InitializingBean {

    public static final String FILE_NAME = "FILE_NAME";

    private final Map<String, byte[]> images = new HashMap<>();
    private final Map<String, Integer[]> checkerMap = new HashMap<>();
    private Map<String, Integer> imagesPosition = new HashMap<>();

    @Autowired
    private ImageDao imageDao;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addPhoto(UploadedFile file) {
        try {
            BufferedImage bufferedImage = null;
            bufferedImage = ImageIO.read(file.getInputstream());//czytanie zawartośći pliku
            if (bufferedImage != null) {
                byte[] bArray = readFileToByteArray(file);
                final int position = images.size() + 1;
                addToMaps(file.getFileName(), bArray, bufferedImage, position);
                Image image = new Image();
                image.setPicture(images.get(file.getFileName()));
                image.setPictureName(file.getFileName());
                image.setPosition(position);
                imageDao.persistImage(image);
                System.out.println("ddałem plik");
            } else {
                System.out.println("nie ma pliku");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void addToMaps(String fileName, byte[] bArray, BufferedImage bufferedImage, Integer position) {
        images.put(fileName, bArray);
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        Integer[] arr = new Integer[2];
        arr[0] = width;
        arr[1] = height;
        checkerMap.put(fileName, arr);
        imagesPosition.put(fileName, position);
        System.out.println(width);
        System.out.println(height);
        System.out.println(checkerMap.toString());
    }

    private void addToMaps(Image img) {
        BufferedImage bufferedImage = null;

        try (InputStream inputStream = new ByteArrayInputStream(img.getPicture())) {
            bufferedImage = ImageIO.read(inputStream);
        } catch (IOException ex) {
            Logger.getLogger(ImageResourceApplicationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        addToMaps(img.getPictureName(), img.getPicture(), bufferedImage, img.getPosition());
    }

    private byte[] readFileToByteArray(UploadedFile file) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            InputStream in = file.getInputstream();
            byte[] buffer = new byte[1024];
            int content;
            while ((content = in.read(buffer)) != -1) {
                os.write(buffer, 0, content);
            }
        } catch (IOException ex) {
            Logger.getLogger(ImageResourceApplicationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return os.toByteArray();

    }

    public Map<String, Long> getContent() {
        return images
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, ent -> (long) ent.getValue().length / 1024));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        imageDao.getImages().forEach(this::addToMaps);

    }

    public StreamedContent streamize(String fileName) {
        return new ByteArrayContent(images.get(fileName));
    }

    public Map<String, Integer[]> getCheckerMap() {
        return checkerMap;
    }

    public Map<String, Integer> getImagesPosition() {
        return imagesPosition;
    }

    public void setImagesPosition(Map<String, Integer> imagesPosition) {
        this.imagesPosition = imagesPosition;
    }

    public void changeImagePositon(String fileName, Integer newPosition) {

        final Optional<String> findAny = imagesPosition.entrySet().stream()
                .filter(n -> n.getValue().equals(newPosition))
                .map(Map.Entry::getKey)
                .findAny();

        if (findAny.isPresent()) {
            final Integer oldPosition = imagesPosition.get(fileName);
            imagesPosition.put(findAny.get(), oldPosition);

        }
        imagesPosition.put(fileName, newPosition);

    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveNewList() {
        Collection<Image> image = imageDao.getImages();
        Hibernate.initialize(image); //Hibernate - pobiera klucz główny encji. Hibernate initialize - dociąga wszystkie pola encji.
        image.forEach(img -> img.setPosition(imagesPosition.get(img.getPictureName()))); // pobieranie każadej encji, ustawianie dla niej pozycji której wartość pobieramy z mapy po nazwie zdjęcia 
        image.forEach(imageDao::updateImages);
    }
}
