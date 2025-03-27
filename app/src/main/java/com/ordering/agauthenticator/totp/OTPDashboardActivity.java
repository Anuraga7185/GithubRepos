package com.ordering.agauthenticator.totp;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ordering.agauthenticator.databinding.DashboardLayoutBinding;

public class OTPDashboardActivity extends AppCompatActivity {
    DashboardLayoutBinding binding;
    String SECRET_KEY = "SECRETKEYFORTOTPGENERATION";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DashboardLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.generateButton.setOnClickListener(view -> refreshOTP());

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                refreshOTP();
                handler.postDelayed(this, 30000);
            }
        };
        handler.postDelayed(runnable, 1);
    }

    private void refreshOTP() {
        long time = System.currentTimeMillis() / 1000;
        String otp = TOTPGenerator.generateTOTP(SECRET_KEY, time);
        Log.d("OTP ACTOVOTY", otp);
        binding.otpTextView.setText(otp);
    }
}
