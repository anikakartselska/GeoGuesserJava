package com.example.geoguesserjava;


import androidx.appcompat.app.AppCompatDialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.geoguesserjava.entity.user.LoggedInUser;
import com.example.geoguesserjava.entity.user.User;
import com.example.geoguesserjava.entity.user.UsersCache;
import com.example.geoguesserjava.server.Services;
import com.example.geoguesserjava.server.UserHttpClient;


import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;


public class AllUsersDialog extends AppCompatDialog {
    private UserHttpClient userHttpClient = Services.getUserHttpClient();

    public AllUsersDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);
        //       List<User> users = userHttpClient.getAllUsers();
        List<User> users = UsersCache.usersCache.get("users");
        Context context = getContext();
        TableLayout tableLayout = findViewById(R.id.all_users_table);
        assert tableLayout != null;
        tableLayout.setBackgroundColor(Color.WHITE);
        IntStream.range(0, users.size()).forEach(i ->
        {
            User user = users.get(i);
            TableRow tableRow = new TableRow(context);

            Drawable cellStyle = Objects.equals(user.getId(), LoggedInUser.getCurrentUser().getId()) ?
                    context.getResources().getDrawable(R.drawable.current_user_cells_style)
                    : context.getResources().getDrawable(R.drawable.cell_boarder);

            createTextCell(context, String.valueOf(i + 1), tableRow, cellStyle);
            createTextCell(context, String.valueOf(user.getUsername()), tableRow, cellStyle);
            createTextCell(context, String.valueOf(user.getLevel()), tableRow, cellStyle);
            createTextCell(context, String.format("%.2f",user.getPoints()), tableRow, cellStyle);

            createImageCell(context, user, tableRow, cellStyle);

            tableLayout.addView(tableRow);
        });
    }

    private void createImageCell(Context context, User user, TableRow tableRow, Drawable cellStyle) {
        ImageView imageView = new ImageView(context);
        imageView.setPadding(8, 8, 8, 8);
        imageView.setBackground(cellStyle);
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
    }

    private void createTextCell(Context context, String textValue, TableRow tableRow, Drawable cellStyle) {
        TextView numberTextView = new TextView(context);
        numberTextView.setText(textValue);
        numberTextView.setPadding(8, 8, 8, 8);
        numberTextView.setGravity(Gravity.CENTER);
        numberTextView.setTextSize(20);
        numberTextView.setBackground(cellStyle);
        TableRow.LayoutParams numberParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, 200); // set the height to 100 pixels
        numberTextView.setLayoutParams(numberParams);
        tableRow.addView(numberTextView);
    }
}
