package com.example.yunita.tradiogc;

import android.test.ActivityInstrumentationTestCase2;

public class PhotographsUseCaseTest extends ActivityInstrumentationTestCase2 {

    public PhotographsUseCaseTest() {
        super(com.example.yunita.tradiogc.MainActivity.class);
    }

    public void testAttachItemPhotograph(){
        PhotoList photos = new PhotoList();
        Photo photo = new Photo("grab photo from gallery");
        photos.addPhoto(photo);

        Item item = new Item(1, "GAP Gift Card", "A", "Apparel", "Unused", 1, 1);
        item.attachPhotos(photos);

        assertEquals(item.getPhotos().getSize(), 1);
    }

    public void testViewItemPhotograph(){
        PhotoList photos = new PhotoList();
        Photo photo = new Photo("grab photo from gallery");
        photos.addPhoto(photo);

        Item item = new Item(1, "GAP Gift Card", "A", "Apparel", "Unused", 1, 1);
        item.attachPhotos(photos);

        assertTrue(item.getPhotos().getSize() > 0);
    }

    public void testDeleteItemPhotograph(){
        PhotoList photos = new PhotoList();
        Photo photo = new Photo("grab photo from gallery");
        photos.addPhoto(photo);

        Item item = new Item(1, "GAP Gift Card", "A", "Apparel", "Unused", 1, 1);
        item.attachPhotos(photos);

        item.getPhotos().removePhoto(1); //by id

        assertEquals(item.getPhotos().getSize(), 0);
    }

    public void testEnablePhotographDownload(){
        PhotoList photos = new PhotoList();
        Photo photo = new Photo("grab photo from gallery");
        photos.addPhoto(photo);

        Item item = new Item(1, "GAP Gift Card", "A", "Apparel", "Unused", 1, 1);
        item.attachPhotos(photos);

        photos.disablePhotoDownload(); //enable -> true
        assertTrue(photos.isEnabled());
    }

    public void testDisablePhotographDownload() {
        PhotoList photos = new PhotoList();
        Photo photo = new Photo("grab photo from gallery");
        photos.addPhoto(photo);

        Item item = new Item(1, "GAP Gift Card", "A", "Apparel", "Unused", 1, 1);
        item.attachPhotos(photos);

        photos.disablePhotoDownload(); //disable -> false
        assertFalse(photos.isEnabled());
    }

}
