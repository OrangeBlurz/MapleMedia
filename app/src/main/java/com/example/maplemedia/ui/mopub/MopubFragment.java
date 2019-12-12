package com.example.maplemedia.ui.mopub;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.maplemedia.R;
import com.mopub.common.MoPub;
import com.mopub.common.SdkConfiguration;
import com.mopub.common.SdkInitializationListener;
import com.mopub.common.logging.MoPubLog;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;
import com.mopub.mobileads.MoPubView;

/**
 * MoPubFragment
 * View responsible for showing the MoPub banner and interstitial ads
 */
public class MopubFragment extends Fragment {
    public static final String MOPUB_SDK = "b195f8dd8ded45fe847ad89ed1d016da";
    public static final String MOPUB_BANNER_ADUNIT = "b195f8dd8ded45fe847ad89ed1d016da";
    public static final String MOPUB_INTERSTITIAL_ADUNIT = "24534e1901884e398f1253216226017e";
    private static final String TAG = "MopubFragment";

    private MopubViewModel mopubViewModel;
    private MoPubView moPubView;
    private MoPubInterstitial moPubInterstitial;
    private Button showBannerButton;
    private Button loadInterstitialButton;
    private Button showInterstitalButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initalizeMoPubSdk();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mopubViewModel =
                ViewModelProviders.of(this).get(MopubViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mopub, container, false);

        showBannerButton = root.findViewById(R.id.mopub_show_banner);
        loadInterstitialButton = root.findViewById(R.id.mopub_load_interstital);
        showInterstitalButton = root.findViewById(R.id.mopub_show_interstitial);
        moPubView = root.findViewById(R.id.mopub_banner);

        showInterstitalButton.setEnabled(false);

        initializeAdUnits();
        initializeButtonListeners();

        return root;
    }

    private void initalizeMoPubSdk() {
        SdkConfiguration.Builder builder = new SdkConfiguration.Builder(MOPUB_SDK);
        builder.withLogLevel(MoPubLog.LogLevel.DEBUG);
        MoPub.initializeSdk(getActivity(), builder.build(), initSdkListener());
    }

    private SdkInitializationListener initSdkListener() {
        return new SdkInitializationListener() {
            @Override
            public void onInitializationFinished() {
                Log.d(TAG, "onInitializationFinished");
            }
        };
    }

    //Setup Button listeners
    private void initializeButtonListeners() {
        showBannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moPubView.loadAd();
            }
        });

        loadInterstitialButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                moPubInterstitial.load();
            }
        });

        showInterstitalButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (moPubInterstitial.isReady()) {
                    moPubInterstitial.show();
                }
            }
        });
    }

    private void initializeAdUnits() {
        //Banner Ad
        moPubView.setAdUnitId(MOPUB_BANNER_ADUNIT);

        //Interstitial Ad
        moPubInterstitial = new MoPubInterstitial(getActivity(), MOPUB_INTERSTITIAL_ADUNIT);
        moPubInterstitial.setInterstitialAdListener(new MoPubInterstitial.InterstitialAdListener() {
            @Override
            public void onInterstitialLoaded(MoPubInterstitial interstitial) {
                //Enable interstitial button
                showInterstitalButton.setEnabled(true);
                makeToast("Interstitial Loaded");
            }

            @Override
            public void onInterstitialFailed(MoPubInterstitial interstitial, MoPubErrorCode errorCode) {
                makeToast("onInterstitialFailed: " + errorCode);
            }

            @Override
            public void onInterstitialShown(MoPubInterstitial interstitial) {
                makeToast("Interstitial Shown");
            }

            @Override
            public void onInterstitialClicked(MoPubInterstitial interstitial) {
                makeToast("Interstitial Clicked");
            }

            @Override
            public void onInterstitialDismissed(MoPubInterstitial interstitial) {
                makeToast("Interstitial Dismissed");
            }
        });
    }

    //Helper method to clean up creating Toast messages
    private void makeToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}