package com.example.geoguesserjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UserScreenActivity extends AppCompatActivity {
    private ImageView userPhoto;
    private Button selectPhotoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_screen);
        userPhoto = findViewById(R.id.imageView2);
        selectPhotoButton = findViewById(R.id.button2);

        selectPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            userPhoto.setImageURI(selectedImage);
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//                byte[] imageBytes = baos.toByteArray();
//
//                // Now you can save the imageBytes to your database
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }

    }

    public void onStartGameClick(View view) {
        DialogsService.messageDialog("Правила на играта",
                "Имаш 3 минути да разгледаш локацията. След като си стигнал до отговора," +
                        " може да отвориш картата и да отбележиш коя според теб е непознатата локация." +
                        "Не забравяй,че играта е само в границите на България.", "Започни", this, (dialog, which) -> {
                    Intent intent = new Intent(UserScreenActivity.this, StreetViewActivity.class);
                    startActivity(intent);
                });
    }
    public void onStartTwoPlayersGameClick(View view) {
        DialogsService.messageDialog("Правила на играта",
                "Имаш 3 минути да разгледаш локацията. След като си стигнал до отговора," +
                        " може да отвориш картата и да отбележиш коя според теб е непознатата локация." +
                        "Не забравяй,че играта е само в границите на България.", "Започни", this, (dialog, which) -> {
                    Intent intent = new Intent(UserScreenActivity.this, StreetViewActivity.class);
                    intent.putExtra("twoPlayers", true);
                    startActivity(intent);
                });
    }
    public void onLogoutClick(View view) {
        DialogsService.messageDialog("Изход",
                "Сигурен ли си,че искаш да напуснеш.", "Да", this, (dialog, which) -> {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                });
    }
}