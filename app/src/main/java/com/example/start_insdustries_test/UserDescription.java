package com.example.start_insdustries_test;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
/**
 * Created by Станислав on 21.04.2015.
 */
public class UserDescription extends Activity {

    String objectId="";

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        objectId = intent.getExtras().getString("objectId");
        setContentView(R.layout.user_detail_information);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
        query.getInBackground(objectId, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    TextView user_fullname = (TextView) findViewById(R.id.user_fullName);
                    user_fullname.setText(object.get("surname").toString() + " " + object.get("name").toString() + " " + object.get("patronymic").toString());
                    TextView user_birthday = (TextView) findViewById(R.id.user_birthday);
                    user_birthday.setText(object.get("birthday").toString());
                    TextView user_phone = (TextView) findViewById(R.id.user_phone);
                    user_phone.setText(object.get("phone").toString());

                    final ImageView user_photo = (ImageView) findViewById(R.id.imageView);
                    ParseFile fileObject = (ParseFile) object.get("photo");
                    if (fileObject == null) {
                        user_photo.setImageBitmap(BitmapFactory.decodeResource(getApplication().getResources(),
                                R.drawable.no_photo));
                    } else{
                        fileObject.getDataInBackground(new GetDataCallback() {
                            public void done(byte[] data, ParseException e) {
                                if (e == null) {
                                    user_photo.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));
                                } else {
                                }
                            }
                        });
                    }
                } else {
                    // something went wrong
                }
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(getApplication(),UserListActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplication().startActivity(i);
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
