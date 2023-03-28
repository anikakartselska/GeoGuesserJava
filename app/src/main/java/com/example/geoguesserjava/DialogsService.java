package com.example.geoguesserjava;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.view.Gravity;
import android.view.ViewGroup;

import androidx.core.text.HtmlCompat;

/**
 * Helper class used for creating Dialogs
 */
public class DialogsService {
    /**
     * Used when throwing an exception so the user can see what is wrong with the application
     * Uses the baseDialog
     *
     * @param errorMessage the message that is shown to the user
     * @param context      provides a way to access the resources and features of the application
     */
    public static void errorDialog(String errorMessage, Context context) {
        AlertDialog dialog = baseDialog("Грешка !!!", "Съобщение: " + errorMessage, "OK", context, null);
        dialog.show();
    }

    /**
     * Dialog that is shown at the bottom at the window.
     *
     * @param title           The title of the dialog.
     * @param message         The message of the dialog.
     * @param buttonText      The text of the positive button.
     * @param context         Context of the activity in which the dialog is displayed.
     * @param onClickListener The listener of the dialog positive button.
     */
    public static void dialogAtTheBottomOfTheWindow(String title, String message, String buttonText, Context context, DialogInterface.OnClickListener onClickListener) {
        AlertDialog dialog = baseDialog(title, message, buttonText, context, onClickListener);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.show();
    }

    /**
     * Dialog with a message and onButtonClickListener.
     *
     * @param title           The title of the dialog.
     * @param message         The message of the dialog.
     * @param buttonText      The text of the positive button.
     * @param context         Context of the activity in which the dialog is displayed.
     * @param onClickListener The listener of the dialog positive button.
     */
    public static void messageDialog(String title, String message, String buttonText, Context context, DialogInterface.OnClickListener onClickListener) {
        baseDialog(title, message, buttonText, context, onClickListener).show();
    }

    /**
     * Base dialog which is used to set the dialog's title,message,button text and onClickListener
     * so the other functions can use it.
     *
     * @param title           The title of the dialog.
     * @param message         The message of the dialog.
     * @param buttonText      The text of the positive button.
     * @param context         Context of the activity in which the dialog is displayed.
     * @param onClickListener The listener of the dialog positive button.
     * @return The ready dialog
     */
    private static AlertDialog baseDialog(String title, String message, String buttonText, Context context, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(HtmlCompat.fromHtml(message, HtmlCompat.FROM_HTML_MODE_LEGACY))
                .setTitle(title)
                .setPositiveButton(buttonText, onClickListener);
        return builder.create();
    }
}
