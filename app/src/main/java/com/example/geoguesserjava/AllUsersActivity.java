package com.example.geoguesserjava;


import androidx.appcompat.app.AppCompatDialog;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.geoguesserjava.entity.user.User;
import com.example.geoguesserjava.server.UserHttpClient;

import java.util.Arrays;
import java.util.List;


public class AllUsersActivity extends AppCompatDialog {
    public AllUsersActivity(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);
        UserHttpClient userHttpClient = new UserHttpClient();
        List<User> users = userHttpClient.getAllUsers();
        Context context = getContext();
        TableLayout tableLayout = findViewById(R.id.all_users_table);
        tableLayout.setBackgroundColor(Color.WHITE);
        for (User user : users) {
            TableRow tableRow = new TableRow(context);

            TextView usernameTextView = new TextView(context);
            usernameTextView.setText(String.valueOf(user.getUsername()));
            usernameTextView.setPadding(8, 8, 8, 8);
            usernameTextView.setBackground(context.getResources().getDrawable(R.drawable.cell_boarder));
            TableRow.LayoutParams usernameParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 110); // set the height to 100 pixels
            usernameTextView.setLayoutParams(usernameParams);
            tableRow.addView(usernameTextView);

            TextView levelTextView = new TextView(context);
            levelTextView.setText(String.valueOf(user.getLevel()));
            levelTextView.setPadding(8, 8, 8, 8);
            levelTextView.setBackground(context.getResources().getDrawable(R.drawable.cell_boarder));
            TableRow.LayoutParams levelParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 100); // set the height to 100 pixels
            levelTextView.setLayoutParams(levelParams);
            tableRow.addView(levelTextView);

            TextView pointsTextView = new TextView(context);
            pointsTextView.setText(String.valueOf(user.getPoints()));
            pointsTextView.setPadding(8, 8, 8, 8);
            pointsTextView.setBackground(context.getResources().getDrawable(R.drawable.cell_boarder));
            TableRow.LayoutParams pointsParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 100); // set the height to 100 pixels
            pointsTextView.setLayoutParams(pointsParams);
            tableRow.addView(pointsTextView);

            ImageView imageView = new ImageView(context);
            imageView.setPadding(8, 8, 8, 8);
            imageView.setBackground(context.getResources().getDrawable(R.drawable.cell_boarder));
            imageView.setImageResource(R.drawable.baseline_person_24); // set the image resource
            TableRow.LayoutParams imageParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 100); // set the height to 100 pixels
            imageView.setLayoutParams(imageParams);
            tableRow.addView(imageView);

            tableLayout.addView(tableRow);
        }
    }
}
