package com.example.geoguesserjava.ui.activities;


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

import com.example.geoguesserjava.R;
import com.example.geoguesserjava.entity.user.LoggedInUser;
import com.example.geoguesserjava.entity.user.User;
import com.example.geoguesserjava.server.Services;
import com.example.geoguesserjava.server.UserHttpClient;


import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;


public class AllUsersDialog extends AppCompatDialog {
    private final UserHttpClient userHttpClient = Services.getUserHttpClient();

    public AllUsersDialog(Context context) {
        super(context);
    }

    /**
     * Called when the activity is starting. This method sets the layout of the activity to "activity_all_users",
     * retrieves a list of all users sorted by points and level from the userHttpClient, creates a table using the retrieved users data, and displays
     * the table on the screen.
     *
     * @param savedInstanceState a Bundle object containing the activity's previously saved state, or null if there
     *                           is no saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);
        List<User> users = userHttpClient.getAllUsers();
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
            createTextCell(context, String.format("%.2f", user.getPoints()), tableRow, cellStyle);

            createImageCell(context, user, tableRow, cellStyle);

            tableLayout.addView(tableRow);
        });
    }

    /**
     * Creates an image cell with the user's image
     * (if available else sets default image from the resources) and adds it to the provided TableRow.
     *
     * @param context   The context in which the cell will be created.
     * @param user      The user for which to display the image (if available).
     * @param tableRow  The TableRow to which the new cell will be added.
     * @param cellStyle The Drawable to use as the background for the cell.
     */
    private void createImageCell(Context context, User user, TableRow tableRow, Drawable cellStyle) {
        // Create a new ImageView and set its padding and background.
        ImageView imageView = new ImageView(context);
        imageView.setPadding(8, 8, 8, 8);
        imageView.setBackground(cellStyle);

        // If the user's image is not available, set the image resource to a default image.
        if (user.getImage() == null || user.getImage().length == 0) {
            imageView.setImageResource(R.drawable.baseline_person_24);
        }
        // If the user's image is available, decode it and set it as the image resource for the ImageView.
        else {
            Bitmap bitmap = BitmapFactory.decodeByteArray(user.getImage(), 0, user.getImage().length);
            imageView.setImageBitmap(bitmap);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }

        // Set the layout parameters for the ImageView.
        TableRow.LayoutParams imageParams = new TableRow.LayoutParams(
                200, // Set the width to 200 pixels.
                200); // Set the height to 200 pixels.
        imageView.setLayoutParams(imageParams);

        // Add the ImageView to the provided TableRow.
        tableRow.addView(imageView);
    }


    /**
     * Creates a text cell with the given text value and adds it to the provided TableRow.
     *
     * @param context   The context in which the cell will be created.
     * @param textValue The text value to display in the cell.
     * @param tableRow  The TableRow to which the new cell will be added.
     * @param cellStyle The Drawable to use as the background for the cell.
     */
    private void createTextCell(Context context, String textValue, TableRow tableRow, Drawable cellStyle) {
        // Create a new TextView and set its text, padding, gravity, and text size.
        TextView numberTextView = new TextView(context);
        numberTextView.setText(textValue);
        numberTextView.setPadding(8, 8, 8, 8);
        numberTextView.setGravity(Gravity.CENTER);
        numberTextView.setTextSize(20);

        // Set the background of the TextView to the provided Drawable.
        numberTextView.setBackground(cellStyle);

        // Set the layout parameters for the TextView.
        TableRow.LayoutParams numberParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT, // Set the width to wrap content.
                200); // Set the height to 200 pixels.
        numberTextView.setLayoutParams(numberParams);

        // Add the TextView to the provided TableRow.
        tableRow.addView(numberTextView);
    }
}
