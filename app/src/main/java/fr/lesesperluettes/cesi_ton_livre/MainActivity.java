package fr.lesesperluettes.cesi_ton_livre;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(v -> moveToSearchActivity());
    }

    private void moveToSearchActivity(){
        Intent searchActivity = new Intent(this,SearchActivity.class);
        startActivity(searchActivity);
    }
}