package com.example.start_insdustries_test;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

/**
 * Created by Станислав on 21.04.2015.
 */
public class UserAdapter extends ParseQueryAdapter<User> {

    Context context;

    public UserAdapter(Context context) {
        super(context, new ParseQueryAdapter.QueryFactory<User>() {
            public ParseQuery<User> create() {
                ParseQuery query = new ParseQuery("User");
                return query;
            }
        });

        this.context=context;
    }

    @Override
    public View getItemView(final User user, View v, ViewGroup parent) {

        if (v == null) {
            v = View.inflate(getContext(), R.layout.item_list_favorites, null);
        }

        super.getItemView(user, v, parent);

        ParseImageView userImage = (ParseImageView) v.findViewById(R.id.icon);
        ParseFile photoFile = user.getParseFile("photo");
        if (photoFile != null) {
            userImage.setParseFile(photoFile);
            userImage.loadInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    // nothing to do
                }
            });
        }
        else{
            userImage.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.no_photo));
        }

        TextView titleTextView = (TextView) v.findViewById(R.id.text1);
        titleTextView.setText(user.getName());

        v.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        showUserInformation(user);
                    }
                }
        );

        return v;
    }


    void showUserInformation(User user){
        Intent i = new Intent(getContext(), UserDescription.class);
        i.putExtra("objectId",String.valueOf(user.getObjectId()));
        getContext().startActivity(i);
    }

}
