package com.example.start_insdustries_test;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/*
 * This fragment manages the data entry for a
 * new Meal object. It lets the user input a 
 * meal name, give it a rating, and take a 
 * photo. If there is already a photo associated
 * with this meal, it will be displayed in the 
 * preview at the bottom, which is a standalone
 * ParseImageView.
 */
public class NewUserFragment extends Fragment {

    private ImageButton photoButton;
    private Button saveButton;
    private Button cancelButton;
    private TextView userName;
    private TextView userSurname;
    private TextView userPatronymic;
    private TextView userPhone;
    private DatePicker userBirthday;
    private ParseImageView mealPreview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public String checkDigit(int number){
        return number<=9?"0"+number:String.valueOf(number);
    }

    String getDateString(DatePicker date){
        String return_value="";

        int   day  = date.getDayOfMonth();
        int   month= date.getMonth() + 1;
        int   year = date.getYear();

        return_value=checkDigit(day)+"-"+checkDigit(month)+"-"+year;

        return return_value;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle SavedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_user, parent, false);

        userName = ((EditText) v.findViewById(R.id.user_name));
        userSurname = ((EditText) v.findViewById(R.id.user_surname));
        userPatronymic=((EditText) v.findViewById(R.id.user_patronymic));
        userPhone=((EditText) v.findViewById(R.id.user_phone));
        userPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        userBirthday=((DatePicker) v.findViewById(R.id.user_birthday));

        photoButton = ((ImageButton) v.findViewById(R.id.photo_button));
        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(userName.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(userSurname.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(userPatronymic.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(userPhone.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(userBirthday.getWindowToken(), 0);
                startCamera();
            }
        });

        saveButton = ((Button) v.findViewById(R.id.save_button));
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                User user = ((MainActivity) getActivity()).getCurrentUser();

                user.setName(userName.getText().toString());
                user.setSurname(userSurname.getText().toString());
                user.setPhone(userPhone.getText().toString());
                user.setPatronymic(userPatronymic.getText().toString());
                user.setBirthday(getDateString(userBirthday));
                user.setAuthor(ParseUser.getCurrentUser());
                // If the user added a photo, that data will be
                // added in the CameraFragment

                // Save the meal and return
                user.saveInBackground(new SaveCallback() {

                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            getActivity().setResult(Activity.RESULT_OK);
                            getActivity().finish();
                        } else {
                            Toast.makeText(
                                    getActivity().getApplicationContext(),
                                    "Error saving: " + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                });

            }
        });

        cancelButton = ((Button) v.findViewById(R.id.cancel_button));
        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        });

        // Until the user has taken a photo, hide the preview
        mealPreview = (ParseImageView) v.findViewById(R.id.user_preview_image);
        mealPreview.setVisibility(View.INVISIBLE);

        return v;
    }

    /*
     * All data entry about a Meal object is managed from the NewMealActivity.
     * When the user wants to add a photo, we'll start up a custom
     * CameraFragment that will let them take the photo and save it to the Meal
     * object owned by the NewMealActivity. Create a new CameraFragment, swap
     * the contents of the fragmentContainer (see activity_new_meal.xml), then
     * add the NewMealFragment to the back stack so we can return to it when the
     * camera is finished.
     */
    public void startCamera() {
        Fragment cameraFragment = new CameraFragment();
        FragmentTransaction transaction = getActivity().getFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.fragmentContainer, cameraFragment);
        transaction.addToBackStack("NewUserFragment");
        transaction.commit();
    }

    /*
     * On resume, check and see if a meal photo has been set from the
     * CameraFragment. If it has, load the image in this fragment and make the
     * preview image visible.
     */
    @Override
    public void onResume() {
        super.onResume();
        ParseFile photoFile = ((MainActivity) getActivity())
                .getCurrentUser().getPhotoFile();
        if (photoFile != null) {
            mealPreview.setParseFile(photoFile);
            mealPreview.loadInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    mealPreview.setVisibility(View.VISIBLE);
                }
            });
        }
    }

}
