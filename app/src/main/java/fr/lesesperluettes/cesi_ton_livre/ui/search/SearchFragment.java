package fr.lesesperluettes.cesi_ton_livre.ui.search;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import fr.lesesperluettes.cesi_ton_livre.R;
import fr.lesesperluettes.cesi_ton_livre.UserActivity;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Set fragment theme color
        UserActivity activity = (UserActivity) getActivity();
        int theme = getArguments().getInt("theme");
        activity.setTheme(theme);
        activity.setActivityColor();

        // Inflate ViewModel for the fragment
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        // Set test text
        final TextView textView = root.findViewById(R.id.text_search);
        searchViewModel.getText().observe(getViewLifecycleOwner(), s -> textView.setText(s));

        return root;
    }
}