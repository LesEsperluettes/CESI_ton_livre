package fr.lesesperluettes.cesi_ton_livre;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin, btnCreate;
    TextView username, password;

    TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (TextView) findViewById(R.id.signin_username);
        password = (TextView) findViewById(R.id.signin_password);
        btnLogin = (Button) findViewById(R.id.signin_btnLogin);
        btnCreate = (Button) findViewById(R.id.signin_btnCreate);

        tokenManager = TokenManager.getInstance(this);

        // Check if token already exists, check if still valid and redirect if so
        String token = tokenManager.getToken();
        if(token != null){
            View rootView = findViewById(android.R.id.content);
            checkTokenAndRedirect(token,rootView);
        }

        btnLogin.setOnClickListener(v -> {
            try {
                moveToSearchActivity(v);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btnCreate.setOnClickListener(v -> signInActivity());
    }

    /**
     *
     */
    private void moveToSearchActivity(View v) throws IOException {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username.getText().toString());
            jsonObject.put("password",password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent SearchActivity = new Intent(this, UserActivity.class);

        // Call API to login user
        PropertyReader reader = new PropertyReader(this);
        String url = reader.getMyProperties("app.properties").getProperty("api_url");
        AndroidNetworking.post(url+ "/auth/signin")
                .addJSONObjectBody(jsonObject)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // user signin, save token and redirect
                        try {
                            tokenManager.setToken(response.getString("accessToken"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        startActivity(SearchActivity);
                    }

                    @Override
                    public void onError(ANError anError) {
                        // error on sign up
                        Snackbar snackbar = Snackbar.make(v, "SignIn failed :(",Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                });
    }

    private void checkTokenAndRedirect(String token, View v){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent SearchActivity = new Intent(this, UserActivity.class);

        PropertyReader reader = new PropertyReader(this);
        String url = reader.getMyProperties("app.properties").getProperty("api_url");
        AndroidNetworking.post(url+"/auth/validate")
                .addJSONObjectBody(jsonObject)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        startActivity(SearchActivity);
                    }

                    @Override
                    public void onError(ANError anError) {
                        // error on sign up
                        Snackbar snackbar = Snackbar.make(v, "Token expired, please login",Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                });
    }

    private void signInActivity(){
        Intent searchActivity = new Intent(this, SignInActivity.class);
        startActivity(searchActivity);
    }

}