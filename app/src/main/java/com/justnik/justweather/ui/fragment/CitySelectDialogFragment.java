package com.justnik.justweather.ui.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.justnik.justweather.R;
import com.justnik.justweather.util.SearchDialogClickListener;

public class CitySelectDialogFragment extends DialogFragment {

    private SearchDialogClickListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        FragmentActivity activity = requireActivity();

        View view =View.inflate(activity,R.layout.dialog_layout,null);
        EditText et = view.findViewById(R.id.etCity);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder
                .setTitle(activity.getResources().getString(R.string.search_city))
                .setView(view)
                .setPositiveButton(R.string.search,(dialog, which) -> listener.onDialogPositiveClick(et.getText().toString()))
                .setNegativeButton(R.string.cancel,(dialog, which) -> listener.onDialogNegativeClick());

        return builder.create();
    }

    public void setListener(SearchDialogClickListener listener){
        this.listener=listener;
    }


}
