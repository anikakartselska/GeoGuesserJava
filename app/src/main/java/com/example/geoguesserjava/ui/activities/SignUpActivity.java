package com.example.geoguesserjava.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.geoguesserjava.ui.utils.Constants;
import com.example.geoguesserjava.ui.utils.DialogsService;
import com.example.geoguesserjava.R;
import com.example.geoguesserjava.entity.user.CreateUserDto;
import com.example.geoguesserjava.entity.user.LoggedInUser;
import com.example.geoguesserjava.server.Services;
import com.example.geoguesserjava.server.UserHttpClient;

public class SignUpActivity extends AppCompatActivity {

    private static final UserHttpClient userHttpClient = Services.getUserHttpClient();
    EditText editTextUsername, editFirstName, editLastName, editEmailText, editTextPassword;

    /**
     * Initializes the activity and sets up the EditText views.
     *
     * @param savedInstanceState the saved state of the activity, or null if there is no saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        editTextUsername = findViewById(R.id.username_signup_txt);
        editFirstName = findViewById(R.id.first_name_txt);
        editLastName = findViewById(R.id.last_name_txt);
        editEmailText = findViewById(R.id.email_txt);
        editTextPassword = findViewById(R.id.type_pass_txt);
    }

    /**
     * Called when the user clicks on the registration button.
     * Checks all the fields and creates a new user if all fields are valid.
     * If the new user is successfully created, starts the UserScreenActivity and displays an error dialog otherwise.
     *
     * @param view the view that was clicked
     */
    public void onRegistrationClick(View view) {
        Intent intent = new Intent(this, UserScreenActivity.class);

        CreateUserDto createUserDto = CheckAllFieldsAndGetCreateUserDto();

        if (createUserDto != null) {
            userHttpClient.createUser(createUserDto);
            if (LoggedInUser.getCurrentUser() == null) {
                DialogsService.errorDialog("Невалиден е-майл или парола", this);
            } else {
                startActivity(intent);
            }
        }
    }

    /**
     * Checks all the fields on the sign-up screen and returns a CreateUserDto object if all fields are valid.
     * If any field is invalid, an error message is displayed and null is returned.
     *
     * @return a CreateUserDto object if all fields are valid, null otherwise
     */
    private CreateUserDto CheckAllFieldsAndGetCreateUserDto() {

        String email = editEmailText.getText().toString();
        String username = editTextUsername.getText().toString();
        String firstName = editFirstName.getText().toString();
        String lastName = editLastName.getText().toString();
        String pass = editTextPassword.getText().toString();

        if (!username.matches(Constants.USERNAME_REGEX)) {
            editTextUsername.setError(Constants.ERROR_MESSAGE_FOR_INVALID_USERNAME);
            return null;
        } else if (userHttpClient.usernameExists(username)) {
            editTextUsername.setError(Constants.ERROR_MESSAGE_FOR_EXISTING_USERNAME);
            return null;
        }

        if (!email.matches(Constants.EMAIL_REGEX)) {
            editEmailText.setError(Constants.ERROR_MESSAGE_FOR_INVALID_EMAIL);
            return null;
        } else if (userHttpClient.emailExists(email)) {
            editEmailText.setError(Constants.ERROR_MESSAGE_FOR_EXISTING_EMAIL);
            return null;
        }

        if (!firstName.matches(Constants.REGEX_FOR_3_TO_15_LETTERS)) {
            editFirstName.setError(Constants.ERROR_FOR_INVALID_NAME);
            return null;
        }

        if (!lastName.matches(Constants.REGEX_FOR_3_TO_15_LETTERS)) {
            editLastName.setError(Constants.ERROR_FOR_INVALID_LAST_NAME);
            return null;
        }

        if (!pass.matches(Constants.PASSWORD_REGEX)) {
            editTextPassword.setError(Constants.ERROR_FOR_INVALID_PASSWORD);
            return null;
        }
        return new CreateUserDto(pass, email, firstName, lastName, username);
    }

    public void onGoBackClick(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
