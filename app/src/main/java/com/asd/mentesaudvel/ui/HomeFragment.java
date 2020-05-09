package com.asd.mentesaudvel.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.asd.mentesaudvel.R;
import com.bumptech.glide.Glide;
import com.google.zxing.integration.android.IntentIntegrator;


public class HomeFragment extends Fragment {
    Button scanBtn;
    private IntentIntegrator scanner;
    public final int CUSTOMIZED_REQUEST_CODE = 0x0000ffff;
    ImageView imageView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        scanBtn = root.findViewById(R.id.scanBtn);
        imageView = root.findViewById(R.id.imageView);
        scanner=new IntentIntegrator(getActivity());

        Glide.with(this).load(R.drawable.animate).into(imageView);

        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                scanner.initiateScan();

                IntentIntegrator integrator = new IntentIntegrator(getActivity());
//                integrator.setCaptureActivity(AnyOrientationCaptureActivity.class);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                integrator.setPrompt("Scan something");
                integrator.setOrientationLocked(false);
                integrator.setBeepEnabled(false);
                integrator.initiateScan();

            }
        });

        return root;
    }
}
