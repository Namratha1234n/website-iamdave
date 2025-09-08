package com.yourname.apitests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class ReqresApiTest {

    private static final String BASE_URL = "https://reqres.in/api";

    
    @Test
    public void testGetUserSuccess() throws Exception {
        URL url = new URL(BASE_URL + "/users/2");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();

        assertEquals(200, responseCode, "Expected response code 200");
    }

    
    @Test
    public void testResponseContainsEmail() throws Exception {
        URL url = new URL(BASE_URL + "/users/2");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject json = new JSONObject(response.toString());
        assertTrue(json.has("data"), "Response should contain 'data'");
        JSONObject data = json.getJSONObject("data");
        assertTrue(data.has("email"), "Data should contain 'email'");
        assertEquals("janet.weaver@reqres.in", data.getString("email"));
    }

   
    @Test
    public void testInvalidUser() throws Exception {
        URL url = new URL(BASE_URL + "/users/99999");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();

        assertEquals(404, responseCode, "Expected 404 for invalid user");
    }
}