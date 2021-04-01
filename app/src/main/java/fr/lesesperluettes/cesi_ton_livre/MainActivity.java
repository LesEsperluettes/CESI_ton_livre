package fr.lesesperluettes.cesi_ton_livre;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import fr.lesesperluettes.cesi_ton_livre.api.OpenLibraryApi;

public class MainActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(v -> moveToSearchActivity());

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)  == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 100);
        }

        OpenLibraryApi api = new OpenLibraryApi();
        api.getBook("9782871291756",book -> {
            Log.d("debug",book.getTitle());
        });
    }

    private void moveToSearchActivity(){
        Intent searchActivity = new Intent(this, UserActivity.class);
        startActivity(searchActivity);
    }
}