package fr.lesesperluettes.cesi_ton_livre.ui.popup;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import fr.lesesperluettes.cesi_ton_livre.R;

public class SearchDialogFragment extends DialogFragment {

    String localisation;

    public SearchDialogFragment(String localisation){
        this.localisation = localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();

        View content =  inflater.inflate(R.layout.dialog_search, null);


        builder.setView(content)
                .setNeutralButton("Ok", (dialog, which) -> SearchDialogFragment.this.getDialog().cancel());

        TextView text = (TextView) content.findViewById(R.id.search_popup_section);
        text.setText(this.localisation);

        return builder.create();
    }
}
