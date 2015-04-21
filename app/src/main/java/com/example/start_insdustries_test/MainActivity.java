package com.example.start_insdustries_test;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.parse.ParseObject;


public class MainActivity extends Activity {

    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ParseObject.registerSubclass(User.class);
        user = new User();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        // Begin with main data entry view,
        // NewUserFragment
        setContentView(R.layout.activity_main);
        FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            fragment = new NewUserFragment();
            manager.beginTransaction().add(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }

    public User getCurrentUser() {
        return user;
    }
}
