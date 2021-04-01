package fr.lesesperluettes.cesi_ton_livre;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity extends AppCompatActivity {

    Button btnSignIn;
    TextView name, firstname, phone, password, confirmPassword, address, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        btnSignIn = (Button) findViewById(R.id.register);
        btnSignIn.setOnClickListener(v -> signIn());
    }

    private void signIn() {
        /*name = findViewById(R.id.name);
        firstname = findViewById(R.id.firstname);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmpassword);
        address = findViewById(R.id.address);
        email = findViewById(R.id.email);*/
        // insert into user values
        Intent loginActivity = new Intent(this, LoginActivity.class);
        startActivity(loginActivity);
    }

}
