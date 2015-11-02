package com.example.yunita.tradiogc;

import android.test.ActivityInstrumentationTestCase2;

public class PhotographsUseCaseTest extends ActivityInstrumentationTestCase2 {

<<<<<<< HEAD
    public PhotographsUseCaseTest() {
        super(com.example.yunita.tradiogc.MainActivity.class);
    }
=======
    public PhotographsUseCaseTest() {super(com.example.yunita.tradiogc.MainActivity.class);}
>>>>>>> master

    public void testAttachItemPhotograph(){
        PhotoList photos = new PhotoList();
        Photo photo = new Photo("grab photo from gallery");
        photos.addPhoto(photo);

        Item item = new Item("GAP Gift Card", 1, 50.00, "Unused", true);
        item.attachPhotos(photos);

        assertEquals(item.getPhotos().getSize(), 1);
    }

    public void testViewItemPhotograph(){
        PhotoList photos = new PhotoList();
        Photo photo = new Photo("grab photo from gallery");
        photos.addPhoto(photo);

        Item item = new Item("GAP Gift Card", 1, 50.00, "Unused", true);
        item.attachPhotos(photos);

        assertTrue(item.getPhotos().getSize() > 0);
    }

    public void testDeleteItemPhotograph(){
        PhotoList photos = new PhotoList();
        Photo photo = new Photo("grab photo from gallery");
        photos.addPhoto(photo);

        Item item = new Item("GAP Gift Card", 1, 50.00, "Unused", true);
        item.attachPhotos(photos);

        item.getPhotos().removePhoto(1); //by id

        assertEquals(item.getPhotos().getSize(), 0);
    }

    public void testEnablePhotographDownload(){
        PhotoList photos = new PhotoList();
        Photo photo = new Photo("grab photo from gallery");
        photos.addPhoto(photo);

        Item item = new Item("GAP Gift Card", 1, 50.00, "Unused", true);
        item.attachPhotos(photos);

        photos.disablePhotoDownload(); //enable -> true
        assertTrue(photos.isEnabled());
    }

    public void testDisablePhotographDownload() {
        PhotoList photos = new PhotoList();
        Photo photo = new Photo("grab photo from gallery");
        photos.addPhoto(photo);

        Item item = new Item("GAP Gift Card", 1, 50.00, "Unused", true);
        item.attachPhotos(photos);

        photos.disablePhotoDownload(); //disable -> false
        assertFalse(photos.isEnabled());
    }

}
