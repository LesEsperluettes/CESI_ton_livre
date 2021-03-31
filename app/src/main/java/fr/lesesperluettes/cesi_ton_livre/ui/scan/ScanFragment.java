package fr.lesesperluettes.cesi_ton_livre.ui.scan;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import fr.lesesperluettes.cesi_ton_livre.R;
import fr.lesesperluettes.cesi_ton_livre.UserActivity;

public class ScanFragment extends Fragment {

    private ScanViewModel scanViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Set fragment theme color
        UserActivity activity = (UserActivity) getActivity();
        int theme = getArguments().getInt("theme");
        activity.setTheme(theme);
        activity.setActivityColor();

        // Inflate ViewModel for the fragment
        scanViewModel = new ViewModelProvider(this).get(ScanViewModel.class);
        View root = inflater.inflate(R.layout.fragment_scan, container, false);

        // Set test text
        final TextView textView = root.findViewById(R.id.text_scan);
        scanViewModel.getText().observe(getViewLifecycleOwner(), s -> textView.setText(s));

        return root;
    }
}