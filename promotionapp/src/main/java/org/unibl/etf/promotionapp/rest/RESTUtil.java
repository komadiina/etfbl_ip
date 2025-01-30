package org.unibl.etf.promotionapp.rest;

import org.json.JSONObject;
import org.unibl.etf.promotionapp.beans.UserBean;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RESTUtil {
    public static final String BASE_URL = "http://localhost:8080/api";

    public static UserBean login(String username, String password)
        throws IOException {
        // POST http://localhost:8080/api/public/auth/client
        // if status == 200 return true
        String body = String.format("{\"username\":\"%s\", \"password\":\"%s\"}", username, password);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .setHeader("Content-Type", "application/json")
                .uri(URI.create(BASE_URL + "/public/auth/login/client"))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject json = new JSONObject(response.body());
            System.out.println(json);
            if (response.statusCode() == 200) {
                return new UserBean(
                        json.getInt("id"),
                        json.getString("username"),
                        json.getString("password"),
                        json.getString("phoneNumber"),
                        json.getString("email"),
                        json.getString("idCardNumber"),
                        json.getString("firstName"),
                        json.getString("lastName"),
                        json.getString("passportID"));
            } else {
                return null;
            }
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            exception.printStackTrace();
            return null;
        }
    }

    public static boolean register(String username, String password, String phoneNumber, String email, String idCardNumber, String firstName, String lastName, String passportID)
        throws IOException {
        // POST http://localhost:8080/api/public/auth/client
        // if status == 200 return true
        String body = String.format(
                "{\"username\":\"%s\", \"password\":\"%s\", \"phoneNumber\":\"%s\", \"email\":\"%s\", \"idCardNumber\":\"%s\", \"firstName\":\"%s\", \"lastName\":\"%s\", \"passportID\":\"%s\"}",
                username, password, phoneNumber, email, idCardNumber, firstName, lastName, passportID);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .setHeader("Content-Type", "application/json")
                .uri(URI.create(BASE_URL + "/public/auth/register/client"))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200;
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            exception.printStackTrace();
            return false;
        }
    }
}
