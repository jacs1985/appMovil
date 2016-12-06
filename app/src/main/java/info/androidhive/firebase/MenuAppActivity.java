package info.androidhive.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ChosenImage;
import com.kbeanie.imagechooser.api.ChosenImages;
import com.kbeanie.imagechooser.api.ImageChooserListener;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import info.androidhive.firebase.dao.DataAccessObject;
import info.androidhive.firebase.facade.DailyInfoFacade;
import info.androidhive.firebase.managers.GalleryManager;
import info.androidhive.firebase.managers.TimerManager;
import info.androidhive.firebase.R;
import info.androidhive.firebase.singletons.RandomComboGenerator;
import info.androidhive.firebase.model.Dessert;
import info.androidhive.firebase.model.Drink;
import info.androidhive.firebase.model.Food;
import info.androidhive.firebase.model.Meat;
import info.androidhive.firebase.model.Bread;
import info.androidhive.firebase.model.Menu;
import info.androidhive.firebase.model.Vegetal;

public class MenuAppActivity extends AppCompatActivity implements View.OnClickListener, ImageChooserListener {

    private Button botonPrueba;
    private Button menuDetails;
    private Button productsDetail;
    private Button galleryButton;
    private Button loadLocalDataButton;
    private Button pizzaButton;
    private Button connectionButton;

    private RelativeLayout menuBackGround;
    private Menu myMenu;

    private ArrayList<Vegetal> vegetalsList = new ArrayList<>();
    private ArrayList<Meat> meatsList = new ArrayList<>();
    private ArrayList<Bread> breadsList = new ArrayList<>();
    private ArrayList<Drink> drinks = new ArrayList<>();
    private ArrayList<Dessert> desserts = new ArrayList<>();
    private ArrayList<Food> products = new ArrayList<>();

    IntentFilter s_intentFilter = new IntentFilter();
    private int connectionCounter = 0;
    private Button dailyInfoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_app);
        s_intentFilter.addAction(Intent.ACTION_TIME_CHANGED);

        botonPrueba = (Button)findViewById(R.id.random_combos_button);
        botonPrueba.setOnClickListener(this);

        menuDetails = (Button)findViewById(R.id.menu_details);
        menuDetails.setOnClickListener(this);

        productsDetail = (Button)findViewById(R.id.products_detail);
        productsDetail.setOnClickListener(this);

        galleryButton = (Button)findViewById(R.id.gallery_button);
        galleryButton.setOnClickListener(this);

        loadLocalDataButton = (Button)findViewById(R.id.load_localdata_button);
        loadLocalDataButton.setOnClickListener(this);

        pizzaButton = (Button)findViewById(R.id.pizza_button);
        pizzaButton.setOnClickListener(this);

        connectionButton = (Button)findViewById(R.id.connection_button);
        connectionButton.setOnClickListener(this);

        dailyInfoButton = (Button)findViewById(R.id.daily_info_button);
        dailyInfoButton.setOnClickListener(this);


        defaultsObjects();

        defaultMenu();

        String packageName = getApplicationContext().getPackageName()+".ACTION";

        (TimerManager.getInstance()).launchClock(getApplicationContext(), packageName, 1);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        (TimerManager.getInstance()).stopClock();
    }

    @Override
    protected void onPause() {
        super.onPause();
        (TimerManager.getInstance()).stopClock();
    }

    @Override
    protected void onStop() {
        super.onStop();
        (TimerManager.getInstance()).stopClock();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && (requestCode == ChooserType.REQUEST_PICK_PICTURE || requestCode == ChooserType.REQUEST_CAPTURE_PICTURE)) {
            try {
                if (data.getData() != null) {
                    Uri uriPicture = data.getData();
                    menuBackGround.setBackground(uriPictureToBitmap(uriPicture));
                }
            } catch (NullPointerException nPE) {
                nPE.getMessage();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.random_combos_button:
                RandomComboGenerator.getInstance().fillMenu(vegetalsList, meatsList, breadsList, drinks, desserts, myMenu);
                Toast.makeText(getBaseContext(),"Combos generados",Toast.LENGTH_LONG).show();
                break;

            case R.id.menu_details:
                Intent menusList = new Intent(MenuAppActivity.this, MenuList.class);
                menusList.putExtra("obj", myMenu);
                startActivity(menusList);
                break;

            case R.id.products_detail:
                Intent productsListIntent = new Intent(MenuAppActivity.this, ProductsList.class);
                Bundle bundleObject = new Bundle();
                bundleObject.putSerializable("products", products);
                productsListIntent.putExtras(bundleObject);
                startActivity(productsListIntent);
                break;

            case R.id.gallery_button:
                GalleryManager.getInstance(this, this).selectImageFromGallery();
                break;

            case R.id.pizza_button:
                Intent pizzaIntent = new Intent(MenuAppActivity.this, PizzaCustom.class);
                Bundle bundleProducts = new Bundle();
                bundleProducts.putSerializable("products", products);
                pizzaIntent.putExtras(bundleProducts);
                startActivity(pizzaIntent);
                break;

            case R.id.connection_button:
                Intent connectionIntent = new Intent(MenuAppActivity.this, UserConnection.class);
                connectionCounter++;
                if(connectionCounter%2==0){
                    connectionIntent.putExtra("connectionBoolean",true);
                }else{
                    connectionIntent.putExtra("connectionBoolean",false);
                }
                startActivity(connectionIntent);
                break;

            case R.id.daily_info_button:
                Intent dailyInfo = new Intent(MenuAppActivity.this, DailyInfoFacade.class);
                dailyInfo.putExtra("obj", myMenu);
                startActivity(dailyInfo);
                break;

        }
    }

    private void defaultsObjects(){
        DataAccessObject jsons = new DataAccessObject();
        vegetalsList    = jsons.leerJson(getApplicationContext(),"vegetals.json");
        meatsList       = jsons.leerJson(getApplicationContext(),"meats.json");
        breadsList      = jsons.leerJson(getApplicationContext(),"breads.json");
        drinks          = jsons.leerJson(getApplicationContext(),"drinks.json");
        desserts        = jsons.leerJson(getApplicationContext(),"desserts.json");
        products        = jsons.leerJson(getApplicationContext(),"products.json");

        for (int i=0; i<vegetalsList.size(); i++){
            products.add(vegetalsList.get(i));
        }

        for (int i=0; i<meatsList.size(); i++){
            products.add(meatsList.get(i));
        }

        for (int i=0; i<breadsList.size(); i++){
            products.add(breadsList.get(i));
        }

        for (int i=0; i<drinks.size(); i++){
            products.add(drinks.get(i));
        }

        for (int i=0; i<desserts.size(); i++){
            products.add(desserts.get(i));
        }
    }


    private void defaultMenu(){
        myMenu = new Menu(1, "Default Menu", "Lorem ipsum Menu");
    }

    private Drawable uriPictureToBitmap(Uri selectedImage){
        Drawable resultDrawable = null;
        try {
            InputStream imageStream = MenuAppActivity.this.getContentResolver().openInputStream(selectedImage);
            Bitmap returnedImageBitmap = BitmapFactory.decodeStream(imageStream);
            resultDrawable = new BitmapDrawable(getResources(), returnedImageBitmap);
            if(returnedImageBitmap.getHeight()>2000){
                int width = Math.round(returnedImageBitmap.getWidth() / 4);
                int height = Math.round(returnedImageBitmap.getHeight() / 4);
                resultDrawable = new BitmapDrawable(Bitmap.createScaledBitmap(returnedImageBitmap, width, height, false));
            }
        } catch (FileNotFoundException| OutOfMemoryError | NullPointerException e) {
            e.printStackTrace();
        }
        return resultDrawable;
    }

    @Override
    public void onImageChosen(ChosenImage chosenImage) {}

    @Override
    public void onError(String s) {}

    @Override
    public void onImagesChosen(ChosenImages chosenImages) {}
}
