package com.example.geoguesserjava;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.geoguesserjava.entity.user.CreateUserDto;
import com.example.geoguesserjava.server.UserHttpClient;

import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    private static final UserHttpClient userHttpClient = new UserHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
    }

    public void onRegistrationClick(View view) {
        Intent intent = new Intent(this, UserScreenActivity.class);

        EditText editTextUsername = findViewById(R.id.username_signup_txt);
        EditText editFirstName = findViewById(R.id.first_name_txt);
        EditText editLastName = findViewById(R.id.last_name_txt);
        EditText editEmailText = findViewById(R.id.email_txt);
        EditText editTextPassword = findViewById(R.id.type_pass_txt);

        String email = editEmailText.getText().toString();
        String username = editTextUsername.getText().toString();
        String firstName = editFirstName.getText().toString();
        String lastName = editLastName.getText().toString();
        String pass = editTextPassword.getText().toString();

        List<String> errorMessages = new ArrayList<>();

        if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            errorMessages.add("Invalid email address");
        }

        if (!username.matches("[a-zA-Z0-9_-]{3,15}")) {
            errorMessages.add("Username must be between 3 and 15 characters and can only contain letters, numbers, hyphens and underscores");
        }

        if (!firstName.matches("[a-zA-Z]{3,15}")) {
            errorMessages.add("First name must be between 3 and 15 characters and can only contain letters");
        }

        if (!lastName.matches("[a-zA-Z]{3,15}")) {
            errorMessages.add("Last name must be between 3 and 15 characters and can only contain letters");
        }

        if (!pass.matches("(?!.*#)[a-zA-Z0-9]{5,15}")) {
            errorMessages.add("Password must be between 5 and 15 characters and cannot contain the '#' character");
        }

        if(errorMessages.size() > 0){
            errorMessages.forEach(System.out::println);
        }else{
            CreateUserDto createUserDto = new CreateUserDto(pass, email, firstName, lastName, username);

            userHttpClient.createUser(createUserDto);

            if(userHttpClient.getLoggedInUser().getCurrentUser() == null){
                System.out.println("Error logging in!");
            }else{
                errorMessages = new ArrayList<>();
                startActivity(intent);
            }
        }

    }

    public void onGoBackClick(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
