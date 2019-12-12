package com.example.maplemedia.ui.web;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.maplemedia.R;

/**
 * WebFragment
 * View responsible for launching a web intent
 */
public class WebFragment extends Fragment {

    private WebViewModel webViewModel;
    private Button launchWebIntent;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        webViewModel =
                ViewModelProviders.of(this).get(WebViewModel.class);
        View root = inflater.inflate(R.layout.fragment_web, container, false);

        launchWebIntent = root.findViewById(R.id.web_launch_maplemedia);

        intializeButtonListeners();

        return root;
    }

    //Register button listeners
    private void intializeButtonListeners() {
        launchWebIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(getString(R.string.web_intent_url)));
                startActivity(intent);
            }
        });
    }
}