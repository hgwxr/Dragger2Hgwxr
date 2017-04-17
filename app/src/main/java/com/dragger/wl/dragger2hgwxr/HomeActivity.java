package com.dragger.wl.dragger2hgwxr;

import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import javax.inject.Inject;

public class HomeActivity extends AppCompatActivity {

    @Inject
    LocationManager locationManager;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ((DemoApplication) getApplication()).component().inject(this);

        // TODO do something with the injected dependencies here!
        Log.d("HomeActivity", locationManager.toString());
    }
}
