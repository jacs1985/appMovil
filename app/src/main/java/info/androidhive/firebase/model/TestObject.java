package info.androidhive.firebase.model;

import java.io.Serializable;

/**
 * Created by MAX on 30-06-16.
 */
public class TestObject implements Serializable {

    private String lorem;

    public TestObject(){}

    public String getLorem() {
        return lorem;
    }

    public void setLorem(String lorem) {
        this.lorem = lorem;
    }
}
