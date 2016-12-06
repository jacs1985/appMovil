package info.androidhive.firebase.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import info.androidhive.firebase.model.Bread;
import info.androidhive.firebase.model.Dessert;
import info.androidhive.firebase.model.Drink;
import info.androidhive.firebase.model.Food;
import info.androidhive.firebase.model.Meat;
import info.androidhive.firebase.model.Vegetal;

public class DataAccessObject {

    private static DataAccessObject dao;
    private static Context context;
    private static SQLiteDatabase database;
    private static JSONArray presenceList;
    private static JSONArray vegetalsList;
    private static AssetDatabaseOpenHelper databaseAdmin;

    public static DataAccessObject getDao(Context currentContext) throws JSONException {
        if(dao==null){
            dao = new DataAccessObject();
            context = currentContext;
            loadDataBase(context);
            try {
                loadJsonFile();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return dao;
    }

    private static void loadDataBase(Context context){
        databaseAdmin = new AssetDatabaseOpenHelper(context);
    }

    private static String loadJSONFromAsset(String nameJSONFile){
        String json = null;
        try {
            InputStream is = context.getAssets().open(nameJSONFile);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private static void loadJsonFile() throws JSONException {
        String nameJSONFile = "presence.json";
        presenceList = new JSONArray(loadJSONFromAsset(nameJSONFile));
    }
   /* private static JSONArray loadJsonFile2() throws JSONException {
        String nameJSONFile = "vegetals.json";
       vegetalsList = new JSONArray(loadJSONFromAsset(nameJSONFile));
        return vegetalsList;
    }*/
    public String getPresenceById(int id) throws JSONException {
        String result = "Ausente";
        for (int i=0; i<presenceList.length(); i++){
            JSONObject jsonObject = presenceList.getJSONObject(i);
            if(jsonObject.getString("id").equals(Integer.toString(id))){
                if(jsonObject.getBoolean("presence")){
                    result = "Presente";
                }
            }
        }
        return result;
    }

    public Cursor getAllRows(){
        database = databaseAdmin.openDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM employees", null);
        //database.close();
        return cursor;
    }

    public Cursor getRowByName(String name){
        database = databaseAdmin.openDatabase();
        Cursor cursorWhere = database.rawQuery("SELECT * FROM employees WHERE name = ?", new String[]{name}, null);
        database.close();
        return cursorWhere;
    }

    public ArrayList leerJson(Context ctx, String arch){
        String name,cost,type,weight,season,fatIndex,origin,wholemeal,unit,arrivalDate,duration;
        ArrayList<Vegetal> vegetalsList = new ArrayList<>();
        ArrayList<Meat> meatsList = new ArrayList<>();
        ArrayList<Bread> breadsList = new ArrayList<>();
        ArrayList<Drink> drinksList = new ArrayList<>();
        ArrayList<Dessert> dessertsList = new ArrayList<>();
        ArrayList<Food> productsList = new ArrayList<>();
        String strJson = null;
        try {
            InputStream is = ctx.getAssets().open(arch);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            strJson = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            JSONObject  jsonRootObject = new JSONObject(strJson);
            if(arch.equals("vegetals.json")){
                JSONArray jsonArray = jsonRootObject.optJSONArray("vegetals");
                Vegetal[] vegetales = new Vegetal[jsonArray.length()];//*************2
                for(int i=0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    name = jsonObject.optString("name").toString();
                    cost = jsonObject.optString("cost").toString();
                    type = jsonObject.optString("type").toString();
                    weight = jsonObject.optString("weight").toString();
                    season = jsonObject.optString("season").toString();
                    vegetales[i] = new Vegetal(name, Integer.parseInt(cost),Integer.parseInt(weight),type,season);
                    vegetalsList.add(vegetales[i]);
                }
                return vegetalsList;
            }
            if(arch.equals("meats.json")){
                JSONArray jsonArray = jsonRootObject.optJSONArray("meats");
                Meat[] meats = new Meat[jsonArray.length()];//*************2
                for(int i=0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    name = jsonObject.optString("name").toString();
                    cost = jsonObject.optString("cost").toString();
                    type = jsonObject.optString("type").toString();
                    weight = jsonObject.optString("weight").toString();
                    origin = jsonObject.optString("origin").toString();
                    fatIndex = jsonObject.optString("fatIndex").toString();
                    meats[i] = new Meat(name, Integer.parseInt(cost),Integer.parseInt(weight),type,origin,Double.parseDouble(fatIndex));
                    meatsList.add(meats[i]);
                }
                return meatsList;
            }
            if(arch.equals("breads.json")){
                JSONArray jsonArray = jsonRootObject.optJSONArray("breads");
                Bread[] breads = new Bread[jsonArray.length()];//*************2
                for(int i=0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    name = jsonObject.optString("name").toString();
                    cost = jsonObject.optString("cost").toString();
                    type = jsonObject.optString("type").toString();
                    weight = jsonObject.optString("weight").toString();
                    origin = jsonObject.optString("origin").toString();
                    wholemeal = jsonObject.optString("wholemeal").toString();
                    breads[i] = new Bread(name, Integer.parseInt(cost),Integer.parseInt(weight),type,origin,Boolean.valueOf(wholemeal));
                    breadsList.add(breads[i]);
                }
                return breadsList;
            }
            if(arch.equals("drinks.json")){
                JSONArray jsonArray = jsonRootObject.optJSONArray("drinks");
                Drink[] drinks = new Drink[jsonArray.length()];//*************2
                for(int i=0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    name = jsonObject.optString("name").toString();
                    cost = jsonObject.optString("cost").toString();
                    type = jsonObject.optString("type").toString();
                    weight = jsonObject.optString("weight").toString();
                    unit = jsonObject.optString("unit").toString();
                    drinks[i] = new Drink(name, Integer.parseInt(cost),Integer.parseInt(weight),type,unit);
                    drinksList.add(drinks[i]);
                }
                return drinksList;
            }
            if(arch.equals("desserts.json")){
                JSONArray jsonArray = jsonRootObject.optJSONArray("desserts");
                Dessert[] desserts = new Dessert[jsonArray.length()];//*************2
                for(int i=0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    name = jsonObject.optString("name").toString();
                    cost = jsonObject.optString("cost").toString();
                    type = jsonObject.optString("type").toString();
                    weight = jsonObject.optString("weight").toString();
                    arrivalDate = jsonObject.optString("arrivalDate").toString();
                    duration = jsonObject.optString("duration").toString();
                    desserts[i] = new Dessert(name, Integer.parseInt(cost),Integer.parseInt(weight),type,arrivalDate,Integer.parseInt(duration));
                    dessertsList.add(desserts[i]);
                }
                return dessertsList;
            }
            if(arch.equals("products.json")){
                JSONArray jsonArray = jsonRootObject.optJSONArray("products");
                Food[] products = new Food[jsonArray.length()];//*************2
                for(int i=0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    name = jsonObject.optString("name").toString();
                    cost = jsonObject.optString("cost").toString();
                    type = jsonObject.optString("type").toString();
                    weight = jsonObject.optString("weight").toString();
                    products[i] = new Food(name, Integer.parseInt(cost),Integer.parseInt(weight),type);
                    productsList.add(products[i]);
                }
                return productsList;
            }
        } catch (JSONException e) {e.printStackTrace();}
        return productsList;
    }


}
