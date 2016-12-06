package info.androidhive.firebase.managers;

import android.app.Activity;
import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ImageChooserListener;
import com.kbeanie.imagechooser.api.ImageChooserManager;

public class GalleryManager {

    private static GalleryManager galleryManager;
    private static int chooserType;
    private static ImageChooserManager imageChooserManager;
    private static Activity myActivity;

    public static GalleryManager getInstance(Activity activity, ImageChooserListener iML){
        if(galleryManager==null){
            galleryManager = new GalleryManager();
            chooserType = ChooserType.REQUEST_PICK_PICTURE;
            myActivity = activity;
            imageChooserManager = new ImageChooserManager(myActivity, ChooserType.REQUEST_PICK_PICTURE, true);
            imageChooserManager.setImageChooserListener(iML);
        }
        return galleryManager;
    }

    public static void selectImageFromGallery(){
        try {
            imageChooserManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
