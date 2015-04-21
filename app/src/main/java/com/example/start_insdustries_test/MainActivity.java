package com.example.start_insdustries_test;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;
import android.view.WindowManager;


public class MainActivity extends ActionBarActivity {

    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        user = new User();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        // Begin with main data entry view,
        // NewUserFragment
        setContentView(R.layout.activity_main);
        /*FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            fragment = new NewUserFragment();
            manager.beginTransaction().add(R.id.fragmentContainer, fragment)
                    .commit();
        }*/
    }

    public User getCurrentUser() {
        return user;
    }
}
