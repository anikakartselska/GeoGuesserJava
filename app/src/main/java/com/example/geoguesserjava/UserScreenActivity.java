package com.example.geoguesserjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.geoguesserjava.entity.user.LoggedInUser;
import com.example.geoguesserjava.entity.user.UpdateUserDto;
import com.example.geoguesserjava.server.UserHttpClient;

import java.io.ByteArrayInputStream;

import com.example.geoguesserjava.server.UserHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UserScreenActivity extends AppCompatActivity {
    private ImageView userPhoto;

    private static final UserHttpClient userHttpClient = new UserHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_screen);
        userPhoto = findViewById(R.id.imageView2);
        TextView pointsToNextLevelText = findViewById(R.id.pointsToNextLevel);
        pointsToNextLevelText.setText(StringConstants.pointsToNextLevelText(1000 - LoggedInUser.getCurrentUser().getPoints()));
        TextView pointsText = findViewById(R.id.userPoints);
        pointsText.setText(StringConstants.pointsText(LoggedInUser.getCurrentUser().getPoints()));
        TextView levelText = findViewById(R.id.userLevel);
        levelText.setText(StringConstants.levelText(LoggedInUser.getCurrentUser().getLevel()));
        TextView welcomeText = findViewById(R.id.welcomeUser);
        welcomeText.setText(StringConstants.welcomeText(LoggedInUser.getCurrentUser().getFirstName()));
        if (LoggedInUser.getCurrentUser().getImage() == null || LoggedInUser.getCurrentUser().getImage().length == 0) {
            userPhoto.setImageResource(R.drawable.baseline_person_24); // set the image resource
        } else {
            Bitmap bitmap = BitmapFactory.decodeByteArray(LoggedInUser.getCurrentUser().getImage(), 0, LoggedInUser.getCurrentUser().getImage().length);
            userPhoto.setImageBitmap(bitmap);
            userPhoto.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }

    }

    /**
     * The method launches an intent to open the photo picker screen of the device,
     * and when the user selects a photo, it returns the selected photo to the calling activity.
     *
     * @param view
     */
    public void selectPhotoClick(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    /**
     * The code you provided is an implementation of the onActivityResult method,
     * which is called when an activity launched with startActivityForResult completes and returns a result.
     * This method handles the result of the photo picker screen launched by the selectPhotoClick
     * method and sets the selected photo as the user's profile photo.
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode  The integer result code returned by the child activity
     *                    through its setResult().
     * @param data        An Intent, which can return result data to the caller
     *                    (various data can be attached to Intent "extras").
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        UserHttpClient userHttpClient = new UserHttpClient();
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            userPhoto.setImageURI(selectedImage);

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] imageBytes = baos.toByteArray();

                ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;

                userHttpClient.updateUser(new UpdateUserDto(1L, 1, 0.00, imageBytes));

                // Now you can save the imageBytes to your database
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * The method displays a message dialog to the user, presenting the game rules and asking them
     * if they want to begin the game, and if the user selects 'BEGIN', the method launches
     * a new activity to start the one player game
     *
     * @param view
     */
    public void onStartGameClick(View view) {
        DialogsService.messageDialog(StringConstants.GAME_RULES_TITLE,
                StringConstants.GAME_RULES, StringConstants.BEGIN, this, (dialog, which) -> {
                    Intent intent = new Intent(UserScreenActivity.this, StreetViewActivity.class);
                    startActivity(intent);
                });
    }

    public void onOpenAllUsers(View view) {
        AllUsersDialog myDialog = new AllUsersDialog(this);
        // set any necessary properties of the dialog, such as a title
        myDialog.setTitle("Играчи");
        // show the dialog
        myDialog.show();
    }


    /**
     * The method displays a message dialog to the user, presenting the game rules and asking them
     * if they want to begin the game, and if the user selects 'BEGIN', the method launches
     * a new activity to start the game for two players
     *
     * @param view
     */

    public void onStartTwoPlayersGameClick(View view) {
        DialogsService.messageDialog(StringConstants.GAME_RULES_TITLE,
                StringConstants.GAME_RULES, StringConstants.BEGIN, this, (dialog, which) -> {
                    Intent intent = new Intent(UserScreenActivity.this, StreetViewActivity.class);
                    intent.putExtra("twoPlayers", true);
                    startActivity(intent);
                });
    }

    /**
     * method that is called when a user clicks on a logout button.
     * The method displays a message dialog to the user, asking them to confirm if they want to
     * leave the application, and if the user selects 'YES', the method launches a new activity to
     * redirect the user to the Login screen.
     *
     * @param view the logout button
     */
    public void onLogoutClick(View view) {
        DialogsService.messageDialog(StringConstants.EXIT, StringConstants.ARE_YOU_SURE_YOU_WANT_TO_LEAVE
                , StringConstants.YES, this, (dialog, which) -> {
                    Intent intent = new Intent(this, LoginActivity.class);
                    userHttpClient.logoutUser();
                    startActivity(intent);
                });
    }
}