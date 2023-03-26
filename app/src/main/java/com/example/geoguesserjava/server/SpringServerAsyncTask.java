package com.example.geoguesserjava.server;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class SpringServerAsyncTask extends AsyncTask<String, Void, String> {
    /**
     * emulator's local host is not 127.0.0.1, IT IS 10.0.2.2
     */
    private static final String BASE_URL = "http://10.0.2.2:8080/api/";

    @Override
    protected String doInBackground(String... urls) {
        String methodUrl = urls[0];
        String fullUrl = BASE_URL + methodUrl;

        try {
            URL url = new URL(fullUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();
                return response.toString();
            } else {
                return "HTTP error code: " + responseCode;
            }
        } catch (IOException e) {
            return "Error: " + e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        // Update UI with result
    }
}

