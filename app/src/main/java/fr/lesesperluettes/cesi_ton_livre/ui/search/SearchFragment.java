package fr.lesesperluettes.cesi_ton_livre.ui.search;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.lesesperluettes.cesi_ton_livre.Book;
import fr.lesesperluettes.cesi_ton_livre.LoginActivity;
import fr.lesesperluettes.cesi_ton_livre.MainActivity;
import fr.lesesperluettes.cesi_ton_livre.PropertyReader;
import fr.lesesperluettes.cesi_ton_livre.R;
import fr.lesesperluettes.cesi_ton_livre.TokenManager;
import fr.lesesperluettes.cesi_ton_livre.UserActivity;
import fr.lesesperluettes.cesi_ton_livre.enums.BookCardType;
import fr.lesesperluettes.cesi_ton_livre.views.BookCardView;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;
    private TokenManager tokenManager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Set fragment theme color
        UserActivity activity = (UserActivity) getActivity();
        int theme = getArguments().getInt("theme");
        activity.setTheme(theme);
        activity.setActivityColor();

        // Inflate ViewModel for the fragment
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        // Add book card (temp)
        final LinearLayout v = (LinearLayout) root.findViewById(R.id.search_card_layout);
        loadBooks(activity,v);

        this.tokenManager = TokenManager.getInstance(activity);

        return root;
    }

    private void addBookCard(LinearLayout layout,Book book){
        BookCardView bookCardView = new BookCardView(getContext(),null, BookCardType.SEARCH,book);
        bookCardView.setFragmentActivity(getActivity());
        layout.addView(bookCardView);
    }

    private void loadBooks(Context context,LinearLayout v){
        SharedPreferences preferences = context.getSharedPreferences("prefs",Context.MODE_PRIVATE);

        // Check if token already exists, check if still valid and redirect if so
        String token = preferences.getString("token","");

        PropertyReader reader = new PropertyReader(context);
        String url = reader.getMyProperties("app.properties").getProperty("api_url");
        AndroidNetworking.get(url+"/books")
                .addHeaders("x-access-token",token)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i<response.length(); i++){
                            JSONObject book = null;
                            Book newBook = new Book();
                            try {
                                book = response.getJSONObject(i);
                                newBook.setISBN(book.getString("ISBN"));
                                newBook.setTitle(book.getString("title"));
                                newBook.setAuthors(book.getString("authors"));
                                newBook.setPublishers(book.getString("publishers"));
                                newBook.setPublishedDate(book.getString("publishedDate"));
                                newBook.setLocalisation(book.getString("localisation"));
                                newBook.setBorrowed(book.getBoolean("available"));
                                newBook.setImage64(book.getString("coverImage"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            addBookCard(v,newBook);
                        }
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