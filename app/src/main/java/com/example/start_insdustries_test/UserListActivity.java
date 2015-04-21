package com.example.start_insdustries_test;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Станислав on 21.04.2015.
 */
public class UserListActivity extends ListActivity {

    private UserAdapter userAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getListView().setClickable(false);
        // Subclass of ParseQueryAdapter
        userAdapter = new UserAdapter(this);

        // Default view is all meals
        setListAdapter(userAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_user_list, menu);
        return true;
    }

    /*
     * Posting meals and refreshing the list will be controlled from the Action
     * Bar.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_refresh: {
                updateUserList();
                break;
            }

            case R.id.action_new: {
                newUser();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateUserList() {
        userAdapter.loadObjects();
        setListAdapter(userAdapter);
    }

    private void showUsers() {
        userAdapter.loadObjects();
        setListAdapter(userAdapter);
    }

    private void newUser() {
        Intent i = new Intent(this, MainActivity.class);
        startActivityForResult(i, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            // If a new post has been added, update
            // the list of posts
            updateUserList();
        }
    }

}
