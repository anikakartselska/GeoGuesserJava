package com.example.geoguesserjava;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.example.geoguesserjava.entity.user.LoggedInUser;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class StringConstants {
    public static final String RESULTS_FROM_GAME_TITLE = "Резултати от играта";

    public static final String GO_TO_USER_PAGE = "Отиди в началната страница";

    public static final String ERROR = "Грешка !!!";

    public static final String MESSAGE = "Съобщение: ";

    public static final String OK = "OK";

    public static final String CITY_NOT_FOUND_ERROR = "Не можем да намерим локацията на този град";

    public static final String TIME_IS_UP = "Времето свърши!";

    public static final String USERNAME = LoggedInUser.getCurrentUser().getUsername();

    public static final String EXIT = "Изход";

    public static final String ARE_YOU_SURE_YOU_WANT_TO_LEAVE = "Сигурен ли си,че искаш да напуснеш?";

    public static final String YES = "Да";

    public static final String GAME_RULES_TITLE = "Правила на играта";

    public static final String BEGIN = "Започни";

    public static final String FRIEND = "Приятел";


    public static final String GAME_RULES = "Имаш 2 минути да разгледаш локацията. След като си стигнал до отговора," +
            " може да отвориш картата и да отбележиш коя според теб е непознатата локация." +
            "Не забравяй,че играта е само в границите на България.";


    public static String constructMessageForThePlayerResults(String playerName, Double kilometers, Double points) {
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

    public static String pointsToNextLevelText(Double pointsToNextLevel) {
        String text = "Точки до следващото ниво: %.2f";
        return String.format(text, pointsToNextLevel);
    }

    public static String pointsText(Double points) {
        String text = "Точките ти: %.2f";
        return String.format(text,points);
    }

    public static String levelText(Integer level) {
        String text = "Твоето ниво: %d";
        return String.format(text,level);
    }

    public static String welcomeText(String firstName) {
        String text = "Добре дошъл, %s!";
        return String.format(text,firstName);
    }
}
