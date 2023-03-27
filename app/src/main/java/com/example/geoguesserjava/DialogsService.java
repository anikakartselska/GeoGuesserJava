package com.example.geoguesserjava;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.ViewGroup;

/**
 * Helper class used for creating Dialogs
 */
public class DialogsService {
    /**
     * Used when throwing an exception so the user can see what is wrong with the application
     *
     * @param errorMessage the message that is shown to the user
     * @param context      provides a way to access the resources and features of the application
     */
    public static void errorDialog(String errorMessage, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Съобщение: " + errorMessage)
                .setTitle("Грешка !!!")
                .setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Shows a dialog with a message for the distance between two cities.
     *
     * @param distanceBetweenTwoCitiesInKilometers the distance that is displayed
     * @param context                              provides a way to access the resources and features of the application
     * @param onClickListener
     */
    public static void showResultsAfterGuessingTheCityDialog(float distanceBetweenTwoCitiesInKilometers, Context context, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Разликата в разтоянията между двете локации е:");
        builder.setMessage(distanceBetweenTwoCitiesInKilometers + " километра");
        builder.setPositiveButton("Върни се в началната страница", onClickListener);

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.show();
    }

    public static void messageDialog(String title,String message,String buttonText, Context context,  DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setTitle(title)
                .setPositiveButton(buttonText, onClickListener);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
