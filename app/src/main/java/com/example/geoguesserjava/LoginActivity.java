package com.example.geoguesserjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /**
     * Creates an Intent object that specifies the StreetViewActivity class
     * as the target activity, and then starts the new activity
     * by calling the startActivity() method
     *
     * @param view represents the Button view that was clicked to trigger the method.
     */

    public void onLoginClick(View view) {
        Intent intent = new Intent(LoginActivity.this, UserScreenActivity.class);
        startActivity(intent);
//        AllUsersActivity myDialog = new AllUsersActivity(this);
//
//        // set any necessary properties of the dialog, such as a title
//        myDialog.setTitle("Потребители");
//        // show the dialog
//        myDialog.show();
    }

    public void onSignUpClick(View view) {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

}