package com.example.geoguesserjava;


import androidx.appcompat.app.AppCompatDialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.geoguesserjava.entity.user.User;


import java.util.List;
import java.util.stream.IntStream;


public class AllUsersDialog extends AppCompatDialog {
    public AllUsersDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);
        List<User> users = BulgariaGuesserApplication.usersCache.get("users");
        Context context = getContext();
        TableLayout tableLayout = findViewById(R.id.all_users_table);
        tableLayout.setBackgroundColor(Color.WHITE);
        IntStream.range(0, users.size() - 1).forEach(i ->
        {
            User user = users.get(i);
            TableRow tableRow = new TableRow(context);

            TextView numberTextView = new TextView(context);
            numberTextView.setText(String.valueOf(i));
            numberTextView.setPadding(8, 8, 8, 8);
            numberTextView.setGravity(Gravity.CENTER);
            numberTextView.setTextSize(20);
            numberTextView.setBackground(context.getResources().getDrawable(R.drawable.cell_boarder));
            TableRow.LayoutParams numberParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 200); // set the height to 100 pixels
            numberTextView.setLayoutParams(numberParams);
            tableRow.addView(numberTextView);

            TextView usernameTextView = new TextView(context);
            usernameTextView.setText(String.valueOf(user.getUsername()));
            usernameTextView.setPadding(8, 8, 8, 8);
            usernameTextView.setGravity(Gravity.CENTER);
            usernameTextView.setTextSize(20);
            usernameTextView.setBackground(context.getResources().getDrawable(R.drawable.cell_boarder));
            TableRow.LayoutParams usernameParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 200); // set the height to 100 pixels
            usernameTextView.setLayoutParams(usernameParams);
            tableRow.addView(usernameTextView);

            TextView levelTextView = new TextView(context);
            levelTextView.setText(String.valueOf(user.getLevel()));
            levelTextView.setPadding(8, 8, 8, 8);
            levelTextView.setGravity(Gravity.CENTER);
            levelTextView.setTextSize(20);
            levelTextView.setBackground(context.getResources().getDrawable(R.drawable.cell_boarder));
            TableRow.LayoutParams levelParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 200); // set the height to 200 pixels
            levelTextView.setLayoutParams(levelParams);
            tableRow.addView(levelTextView);

            TextView pointsTextView = new TextView(context);
            pointsTextView.setText(String.valueOf(user.getPoints()));
            pointsTextView.setPadding(8, 8, 8, 8);
            pointsTextView.setGravity(Gravity.CENTER);
            pointsTextView.setTextSize(20);
            pointsTextView.setBackground(context.getResources().getDrawable(R.drawable.cell_boarder));
            TableRow.LayoutParams pointsParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 200); // set the height to 200 pixels
            pointsTextView.setLayoutParams(pointsParams);
            tableRow.addView(pointsTextView);

            ImageView imageView = new ImageView(context);
            imageView.setPadding(8, 8, 8, 8);
            imageView.setBackground(context.getResources().getDrawable(R.drawable.cell_boarder));
            if (user.getImage() == null || user.getImage().length == 0) {
                imageView.setImageResource(R.drawable.baseline_person_24); // set the image resource
            } else {
                Bitmap bitmap = BitmapFactory.decodeByteArray(user.getImage(), 0, user.getImage().length);
                imageView.setImageBitmap(bitmap);
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            }
            TableRow.LayoutParams imageParams = new TableRow.LayoutParams(200, 200); // set the height to 100 pixels
            imageView.setLayoutParams(imageParams);
            tableRow.addView(imageView);

            tableLayout.addView(tableRow);
        });
    }
}
