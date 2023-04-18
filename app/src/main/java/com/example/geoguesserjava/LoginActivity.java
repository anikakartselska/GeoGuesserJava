package com.example.geoguesserjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.geoguesserjava.entity.user.LoggedInUser;
import com.example.geoguesserjava.server.Services;
import com.example.geoguesserjava.server.UserHttpClient;

public class LoginActivity extends AppCompatActivity {

    private static final UserHttpClient userHttpClient = Services.getUserHttpClient();
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
        EditText editTextUsername = findViewById(R.id.username);
        EditText editTextPassword = findViewById(R.id.password);
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        String usernameAndPassword = username + "###" + password;

        userHttpClient.loginUser(usernameAndPassword);

        if(LoggedInUser.getCurrentUser() == null){
            DialogsService.errorDialog("Невалидено потребителско име или парола",this);
        }else{
            startActivity(intent);
        }
    }

    public void onSignUpClick(View view) {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

}