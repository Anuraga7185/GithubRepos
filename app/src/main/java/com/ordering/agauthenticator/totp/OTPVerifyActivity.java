package com.ordering.agauthenticator.totp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.ordering.agauthenticator.databinding.ActivityOtpVerifyBinding;

public class OTPVerifyActivity extends AppCompatActivity {
    private ActivityOtpVerifyBinding binding;
    private static final String SECRET_KEY = "SECRETKEYFORTOTPGENERATION";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpVerifyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.verifyButton.setOnClickListener(view -> verifyOTP());
    }

    private void verifyOTP() {
        String enteredOTP = binding.otpInput.getText().toString().trim();

        if (enteredOTP.isEmpty()) {
            Toast.makeText(this, "Please enter OTP", Toast.LENGTH_SHORT).show();
            return;
        }

        long currentTime = System.currentTimeMillis() / 1000;

        String expectedOTP = TOTPGenerator.generateTOTP(SECRET_KEY, currentTime);

        String previousOTP = TOTPGenerator.generateTOTP(SECRET_KEY, currentTime - 30);
        String nextOTP = TOTPGenerator.generateTOTP(SECRET_KEY, currentTime + 30);

        if (enteredOTP.equals(expectedOTP) || enteredOTP.equals(previousOTP) || enteredOTP.equals(nextOTP)) {
            Toast.makeText(this, "OTP Verified Successfully!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Invalid OTP!", Toast.LENGTH_LONG).show();
        }

        Log.d("OTP VERIFICATION", "Entered: " + enteredOTP + ", Expected: " + expectedOTP);
    }
}
