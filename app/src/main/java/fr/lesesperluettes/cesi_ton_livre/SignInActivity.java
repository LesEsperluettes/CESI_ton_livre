package fr.lesesperluettes.cesi_ton_livre;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity extends AppCompatActivity {

    Button btnSignIn;
    TextView name, firstname, phone, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignIn = (Button) findViewById(R.id.register);
        btnSignIn.setOnClickListener(v -> signIn());
    }

    private void signIn() {
        // insert into user values
        findViewById(R.id.name);

    }

}
