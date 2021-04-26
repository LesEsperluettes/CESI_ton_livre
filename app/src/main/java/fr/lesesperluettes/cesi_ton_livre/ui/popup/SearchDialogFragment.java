package fr.lesesperluettes.cesi_ton_livre.ui.popup;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.fragment.app.DialogFragment;

import fr.lesesperluettes.cesi_ton_livre.R;

public class SearchDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_search,null))
                .setNeutralButton("Ok", (dialog, which) -> SearchDialogFragment.this.getDialog().cancel());
        return builder.create();
    }
}
