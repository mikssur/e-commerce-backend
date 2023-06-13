package com.rest_api.fs14backend.auth;

import com.rest_api.fs14backend.user.User;
import com.rest_api.fs14backend.user.UserService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@RestController
@CrossOrigin("https://e-commerce-frontend-livid.vercel.app/")
@RequestMapping("api/v1/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired AuthService authService;

    @PostMapping
    public ResponseEntity<Object> authenticate(@RequestBody AuthRequest authRequest) throws IOException, InterruptedException {
        JSONObject userData = fetchUserData(authRequest.getAccess_token());

        if (userData.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.UNAUTHORIZED);
        }

        User user = userService.findByEmail(userData.getString("email"));

        if (user == null) {
            AuthResponse token = authService.createUser(userData.getString("name"), userData.getString("email"), userData.getString("picture"), "USER");

            return new ResponseEntity<>(token, HttpStatus.OK);
        }

        AuthResponse token = authService.googleAuthenticate(user);

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    private JSONObject fetchUserData(String accessToken) throws IOException, InterruptedException
    {
        String googleUserInfoEndpoint = "https://www.googleapis.com/oauth2/v3/userinfo";

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(googleUserInfoEndpoint))
                .header("Authorization", "Bearer " + accessToken)
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        int responseCode = response.statusCode();

        if (responseCode == 200) {
            String responseBody = response.body();
            JSONObject result = null;
            try {
                result = new JSONObject(responseBody);
            } catch (JSONException err) {
                System.out.println("Error" + err.toString());
            }
            return result;
        }

        return null;
    }
}
