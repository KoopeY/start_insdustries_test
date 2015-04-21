package com.example.start_insdustries_test;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by Станислав on 21.04.2015.
 */

@ParseClassName("User")
public class User extends ParseObject {
    public User() {
        // A default constructor is required.
    }

    public String getSurname() {
        return getString("surname");
    }

    public void setSurname(String surname) {
        put("surname", surname);
    }

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        put("name", name);
    }

    public String getPatronymic() {
        return getString("patronymic");
    }

    public void setPatronymic(String patronymic) {
        put("patronymic", patronymic);
    }

    public void setAuthor(ParseUser user) {
        put("User", user);
    }

    public String getPhone() {
        return getString("phone");
    }

    public void setPhone(String phone) {
        put("phone", phone);
    }

    public String getBirthday() {
        return getString("birthday");
    }

    public void setBirthday(String birthday) {
        put("birthday", birthday);
    }

    public ParseFile getPhotoFile() {
        return getParseFile("photo");
    }

    public void setPhotoFile(ParseFile file) {
        put("photo", file);
    }

}
