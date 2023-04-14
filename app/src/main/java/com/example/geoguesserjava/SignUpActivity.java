package com.example.geoguesserjava;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.geoguesserjava.entity.user.CreateUserDto;
import com.example.geoguesserjava.entity.user.LoggedInUser;
import com.example.geoguesserjava.server.UserHttpClient;

public class SignUpActivity extends AppCompatActivity {

    private static final UserHttpClient userHttpClient = new UserHttpClient();
    EditText editTextUsername, editFirstName, editLastName, editEmailText, editTextPassword;

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

    private CreateUserDto CheckAllFieldsAndGetCreateUserDto() {

        String email = editEmailText.getText().toString();
        String username = editTextUsername.getText().toString();
        String firstName = editFirstName.getText().toString();
        String lastName = editLastName.getText().toString();
        String pass = editTextPassword.getText().toString();

        if (!username.matches("[a-zA-Z0-9_-]{3,15}")) {
            editTextUsername.setError("Потребителското име трябва да е между 3 и 15 знака и може да съдържа само букви, цифри, тирета и подчертавки.");
            return null;
        } else if (userHttpClient.usernameExists(username)) {
            editTextUsername.setError("Потребител с такова потребителско име вече съществува");
            return null;
        }

        if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            editEmailText.setError("Невалиден е-майл адрес");
            return null;
        } else if (userHttpClient.emailExists(email)) {
            editEmailText.setError("Потребител с такъв е-майл вече съществува");
            return null;
        }

        if (!firstName.matches("[a-zA-Z]{3,15}")) {
            editFirstName.setError("Името трябва да е между 3 и 15 знака и може да съдържа само букви.");
            return null;
        }

        if (!lastName.matches("[a-zA-Z]{3,15}")) {
            editLastName.setError("Фамилията трябва да е между 3 и 15 знака и може да съдържа само букви.");
            return null;
        }

        if (!pass.matches("(?!.*#)[a-zA-Z0-9]{5,15}")) {
            editTextPassword.setError("Паролата трябва да е между 5 и 15 знака и не може да съдържа символа '#'.");
            return null;
        }
        return new CreateUserDto(pass, email, firstName, lastName, username);
    }

    public void onGoBackClick(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
