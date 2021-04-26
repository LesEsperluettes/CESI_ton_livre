package fr.lesesperluettes.cesi_ton_livre.ui.borrow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import fr.lesesperluettes.cesi_ton_livre.Book;
import fr.lesesperluettes.cesi_ton_livre.R;
import fr.lesesperluettes.cesi_ton_livre.UserActivity;
import fr.lesesperluettes.cesi_ton_livre.enums.BookCardType;
import fr.lesesperluettes.cesi_ton_livre.views.BookCardView;

public class BorrowFragment extends Fragment {

    private BorrowViewModel borrowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Set fragment theme color
        UserActivity activity = (UserActivity) getActivity();
        int theme = getArguments().getInt("theme");
        activity.setTheme(theme);
        activity.setActivityColor();

        // Inflate ViewModel for the fragment
        borrowViewModel = new ViewModelProvider(this).get(BorrowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_borrow, container, false);

        // Add book card (temp)
        final LinearLayout v = (LinearLayout) root.findViewById(R.id.borrow_layout);
        BookCardView bookCardView = new BookCardView(getContext(),null, BookCardType.BORROW,new Book());
        bookCardView.setFragmentActivity(getActivity());
        v.addView(bookCardView);

        return root;
    }
}