package com.example.geoguesserjava.ui.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.example.geoguesserjava.entity.user.LoggedInUser;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * This class contains various string constant values,
 * map with translation of the cities in Bulgarian
 * and method which returns a BitmapDescriptor object for a person icon,
 * which can be used as a marker icon in the Google Maps Android API.
 */
public class Constants {
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

    public static String constructMessageForTheUnknownCityName(String cityName) {
        return String.format("<i><b>Непознатият град е: </b></i> %s<br>", englishNameToBulgarianName.get(cityName));
    }

    public static String constructMessageForThePlayerResults(String playerName, Double kilometers, Double points) {
        String htmlMessage = "<b>%s:</b><br><i><b>Разлика в разтоянието:</b></i> %.2f км.<br><i><b>Спечелени точки: </b></i> %.2f<br>";
        return String.format(htmlMessage, playerName, kilometers, points);
    }

    public static String messageForTheWinnerInTwoPlayersGame(String winnerName) {
        String htmlMessage = "<font color=\"#FF0000\">Победител в играта е: %s</font>";
        return String.format(htmlMessage, winnerName);
    }

    public static String pointsToNextLevelText(Double pointsToNextLevel) {
        String text = "Точки до следващото ниво: %.2f";
        return String.format(text, pointsToNextLevel);
    }

    public static String pointsText(Double points) {
        String text = "Точките ти: %.2f";
        return String.format(text, points);
    }

    public static String levelText(Integer level) {
        String text = "Твоето ниво: %d";
        return String.format(text, level);
    }

    public static String welcomeText(String firstName) {
        String text = "Здравей, %s!";
        return String.format(text, firstName);
    }

    /**
     * Returns a BitmapDescriptor object for a person icon, which can be used as a marker icon
     * in the Google Maps Android API.
     *
     * @param context the context of the calling activity or fragment
     * @return a BitmapDescriptor object representing the person icon
     */
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

    public static Map<String, String> englishNameToBulgarianName = new HashMap<String, String>() {
        {
            put("Sofia", "София");
            put("Plovdiv", "Пловдив");
            put("Varna", "Варна");
            put("Burgas", "Бургас");
            put("Ruse", "Русе");
            put("Stara Zagora", "Стара Загора");
            put("Pleven", "Плевен");
            put("Sliven", "Сливен");
            put("Dobrich", "Добрич");
            put("Shumen", "Шумен");
            put("Haskovo", "Хасково");
            put("Pazardzhik", "Пазарджик");
            put("Yambol", "Ямбол");
            put("Blagoevgrad", "Благоевград");
            put("Veliko Tarnovo", "Велико Търново");
            put("Vidin", "Видин");
            put("Montana", "Монтана");
            put("Gabrovo", "Габрово");
            put("Asenovgrad", "Асеновград");
            put("Kazanlak", "Казанлък");
            put("Kyustendil", "Кюстендил");
            put("Pernik", "Перник");
            put("Dimitrovgrad", "Димитровград");
            put("Svishtov", "Свищов");
            put("Nova Zagora", "Нова Загора");
            put("Targovishte", "Търговище");
            put("Dupnitsa", "Дупница");
            put("Lovech", "Ловеч");
            put("Silistra", "Силистра");
            put("Razgrad", "Разград");
            put("Gorna Oryahovitsa", "Горна Оряховица");
            put("Petrich", "Петрич");
            put("Sandanski", "Сандански");
            put("Samokov", "Самоков");
            put("Sevlievo", "Севлиево");
            put("Lom", "Лом");
            put("Karlovo", "Карлово");
            put("Troyan", "Троян");
            put("Svilengrad", "Свиленград");
            put("Harmanli", "Харманли");
            put("Karnobat", "Карнобат");
            put("Popovo", "Попово");
            put("Gotse Delchev", "Гоце Делчев");
            put("Peshtera", "Пещера");
            put("Chirpan", "Чирпан");
            put("Radomir", "Радомир");
            put("Parvomay", "Първомай");
            put("Panagyurishte", "Панагюрище");
            put("Botevgrad", "Ботевград");
            put("Radnevo", "Раднево");
            put("Byala Slatina", "Бяла Слатина");
            put("Cherven Bryag", "Червен бряг");
            put("Omurtag", "Омуртаг");
            put("Kubrat", "Кубрат");
            put("Rakovski", "Раковски");
            put("Knezha", "Кнежа");
            put("Balchik", "Балчик");
            put("Rakitovo", "Ракитово");
            put("Septemvri", "Септември");
            put("Pomorie", "Поморие");
            put("Belene", "Белене");
            put("Provadia", "Провадия");
            put("Nessebar", "Несебър");
            put("Dryanovo", "Дряново");
            put("Devnya", "Девня");
            put("Lyaskovets", "Лясковец");
        }
    };
}
