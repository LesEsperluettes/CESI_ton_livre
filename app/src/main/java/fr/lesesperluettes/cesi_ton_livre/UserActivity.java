package fr.lesesperluettes.cesi_ton_livre;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserActivity extends AppCompatActivity {

    private TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(R.id.navigation_search,R.id.navigation_scan,R.id.navigation_borrow)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController,appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        this.tokenManager = TokenManager.getInstance(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.header_nav_menu,menu);
        return true;
    }

    /**
     * Toolbar navigation
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_action_logout){
            // Remove token from preferences and redirect to login
            Intent LoginActivity = new Intent(this, LoginActivity.class);

            tokenManager.removeToken();

            startActivity(LoginActivity);

            return true;
        }else if(item.getItemId() == R.id.menu_action_profile){

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Sets colors of navigation using the current theme (set in the fragment)
     */
    public void setActivityColor(){
        // Get primary color from current theme
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        @ColorInt int color = typedValue.data;

        // Init states and color for navigation
        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_selected},  // unchecked
                new int[]{android.R.attr.state_selected},   // checked
                new int[]{}                                // default
        };

        int[] colors = new int[]{
                Color.parseColor("#747474"),
                color,
                color,
        };

        ColorDrawable colorDrawable = new ColorDrawable(color);
        ColorStateList colorStateList = new ColorStateList(states,colors);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Apply colors
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        getWindow().setStatusBarColor(color);
        navView.setItemTextColor(colorStateList);
        navView.setItemIconTintList(colorStateList);
    }


}