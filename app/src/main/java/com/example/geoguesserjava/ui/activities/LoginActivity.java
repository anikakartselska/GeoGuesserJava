package com.example.geoguesserjava.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.geoguesserjava.ui.utils.Constants;
import com.example.geoguesserjava.ui.utils.DialogsService;
import com.example.geoguesserjava.R;
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
     * This method is called when the user clicks the "login" button.
     * It retrieves the username and password entered by the user and sends a login request to the server using the userHttpClient instance.
     * If the login is successful, it starts the UserScreenActivity.
     * If the login fails, it displays an error dialog to the user.
     *
     * @param view The View that was clicked to trigger this method.
     */

    public void onLoginClick(View view) {
        Intent intent = new Intent(LoginActivity.this, UserScreenActivity.class);
        EditText editTextUsername = findViewById(R.id.username);
        EditText editTextPassword = findViewById(R.id.password);
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        String usernameAndPassword = username + "###" + password;

        userHttpClient.loginUser(usernameAndPassword);

        if (LoggedInUser.getCurrentUser() == null) {
            DialogsService.errorDialog(Constants.INVALID_USERNAME_OR_PASSWORD, this);
        } else {
            startActivity(intent);
        }
    }

    /**
     * Handles the sign up button click event.
     * Starts a new activity to allow the user to sign up for a new account.
     *
     * @param view The view that was clicked.
     */
    public void onSignUpClick(View view) {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

}