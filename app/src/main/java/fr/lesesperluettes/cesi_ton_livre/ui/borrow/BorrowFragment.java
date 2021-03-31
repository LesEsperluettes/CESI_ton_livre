package fr.lesesperluettes.cesi_ton_livre.ui.borrow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import fr.lesesperluettes.cesi_ton_livre.R;

public class BorrowFragment extends Fragment {

    private BorrowViewModel borrowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        borrowViewModel = new ViewModelProvider(this).get(BorrowViewModel.class);

        View root = inflater.inflate(R.layout.fragment_borrow, container, false);

        final TextView textView = root.findViewById(R.id.text_borrow);

        borrowViewModel.getText().observe(getViewLifecycleOwner(), s -> textView.setText(s));
        return root;
    }
}