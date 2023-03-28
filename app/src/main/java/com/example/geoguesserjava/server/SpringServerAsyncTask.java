package com.example.geoguesserjava.server;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class SpringServerAsyncTask extends AsyncTask<String, Void, String> {
    /**
     * emulator's local host is not 127.0.0.1, IT IS 10.0.2.2
     */
    private static final String BASE_URL = "http://10.0.2.2:8080/api/";

    private String requestType;

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    @Override
    protected String doInBackground(String... params) {
        String methodUrl = params[0];
        String fullUrl = BASE_URL + methodUrl;

        try {
            URL url = new URL(fullUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(requestType);

            if (requestType.equals("POST") || requestType.equals("PUT")) {
                connection.setDoOutput(true);
                OutputStream outputStream = connection.getOutputStream();
                String requestBody = params[2];
                outputStream.write(requestBody.getBytes());
                outputStream.flush();
                outputStream.close();
            }

            if (params.length > 3) {
                String contentType = params[3];
                connection.setRequestProperty("Content-Type", contentType);
            }

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

