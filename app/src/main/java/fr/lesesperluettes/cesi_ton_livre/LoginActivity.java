package fr.lesesperluettes.cesi_ton_livre;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    Button btnSignUp;
    TextView signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignUp = (Button) findViewById(R.id.buttonSignUp);
        btnSignUp.setOnClickListener(v -> moveToSearchActivity());
        signIn.setOnClickListener(v -> signInActivity());
    }

    /**
     *
     */
    private void moveToSearchActivity() {
        // check if user is in base
        Intent searchActivity = new Intent(this, UserActivity.class);
        startActivity(searchActivity);
    }

    private void signInActivity(){
        Intent searchActivity = new Intent(this, SignInActivity.class);
        startActivity(searchActivity);
    }

}
