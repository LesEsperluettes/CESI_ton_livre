package fr.lesesperluettes.cesi_ton_livre;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {
    private static TokenManager Instance;
    private Context context;
    private SharedPreferences preferences;

    public TokenManager(Context context){
        this.context = context;
        this.preferences = context.getSharedPreferences("prefs",Context.MODE_PRIVATE);
    }

    public static TokenManager getInstance(Context context){
        if(Instance == null){
            Instance = new TokenManager(context);
        }
        return Instance;
    }

    public void setToken(String token){
        this.preferences.edit()
                .putString("token",token)
                .apply();
    }

    public String getToken(){
        return this.preferences.getString("token",null);
    }

    public void removeToken(){
        this.preferences.edit().remove("token").apply();
    }

}
