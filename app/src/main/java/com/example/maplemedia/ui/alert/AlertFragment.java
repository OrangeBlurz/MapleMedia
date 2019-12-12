package com.example.maplemedia.ui.alert;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.maplemedia.R;

/**
 * AlertFragment
 * View responsible for showing the Alert to the user
 */
public class AlertFragment extends Fragment {

    private AlertViewModel alertViewModel;
    private Button alertButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        alertViewModel =
                ViewModelProviders.of(this).get(AlertViewModel.class);
        View root = inflater.inflate(R.layout.fragment_alert, container, false);

        alertButton = root.findViewById(R.id.alert_button);

        inititalizeButtonListeners();
        return root;
    }

    //Button Listeners
    private void inititalizeButtonListeners() {
        alertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle(getString(R.string.alert_dialog_title))
                        .setMessage(getString(R.string.alert_dialog_message))
                        .setPositiveButton(getString(R.string.alert_dialog_button), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });
    }
}