package fr.lesesperluettes.cesi_ton_livre;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

public class SignInActivity extends AppCompatActivity {

    Button btnSignIn;
    TextView username,email,password,confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        username = findViewById(R.id.signup_username);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signin_username);
        confirmPassword = findViewById(R.id.signin_password);

        AndroidNetworking.initialize(getApplicationContext());

        btnSignIn = (Button) findViewById(R.id.signup_register);
        btnSignIn.setOnClickListener(v -> signIn(v));
    }

    private void signIn(View v) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username.getText().toString());
            jsonObject.put("email", email.getText().toString());
            jsonObject.put("password",password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent loginActivity = new Intent(this, LoginActivity.class);

        // send sign in request to API
        PropertyReader reader = new PropertyReader(this);
        String url = reader.getMyProperties("app.properties").getProperty("api_url");
        AndroidNetworking.post(url+"/auth/signup")
                .addJSONObjectBody(jsonObject)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // user registered, redirect to login with popup
                        Snackbar snackbar = Snackbar.make(v, "Signup success ! redirect to login in 5sec",Snackbar.LENGTH_LONG);
                        snackbar.show();

                        new Handler().postDelayed(() -> startActivity(loginActivity),5000);
                    }

                    @Override
                    public void onError(ANError anError) {
                        // error on sign up
                        Snackbar snackbar = Snackbar.make(v, "Signup failed :(",Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                });
    }

}
