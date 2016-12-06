package info.androidhive.firebase.model;


import java.io.Serializable;

public class Bread extends Food implements Serializable {

    private String originCountry;
    private boolean wholemeal;

    public Bread(String name, int costo, int peso, String tipo, String originCountry, boolean wholemeal){
        super(name, costo, peso, tipo);
        this.originCountry = originCountry;
        this.wholemeal = wholemeal;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public boolean isWholemeal() {
        return wholemeal;
    }


}
