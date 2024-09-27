package model;

import java.io.*;

//Handle saving and loading of image
public class PhotoSaveLoad {
    public static void savePhotoModel(String filepath, PhotoModel model) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filepath))) {
            oos.writeObject(model);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PhotoModel loadPhotoModel(String filepath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath))) {
            PhotoModel photoModel = (PhotoModel) ois.readObject();
            return photoModel;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
