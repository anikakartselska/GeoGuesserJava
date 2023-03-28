package com.example.geoguesserjava;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class StringConstants {
    public static String resultsFromGameTitle = "Резултати от играта";

    public static String goToUserPage = "Отиди в началната страница";


    public static String constructMessageForThePlayerResults(String playerName, float kilometers, float points) {
        String htmlMessage = "<b>%s:</b><br><i><b>Разлика в разтоянието:</b></i> %.2f км.<br><i><b>Спечелени точки: </b></i> %.2f<br>";
        return String.format(htmlMessage, playerName, kilometers, points);
    }

    public static String messageForTheWinnerInTwoPlayersGame(String winnerName) {
        String htmlMessage = "<font color=\"#FF0000\">Победител в играта е: %s</font>";
        return String.format(htmlMessage, winnerName);
    }

    public static BitmapDescriptor getPersonIcon(Context context) {
        // Get the resource id of the baseline_person_24.xml file
        int resourceId = context.getResources().getIdentifier("baseline_person_24", "drawable", context.getPackageName());

// Convert the vector drawable to a bitmap
        Drawable vectorDrawable = context.getResources().getDrawable(resourceId);
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);

// Create a BitmapDescriptor object from the bitmap with the desired tint
        return BitmapDescriptorFactory.fromBitmap(bitmap);


    }

}
