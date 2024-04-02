package com.example.mobiledevchallenge.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.mobiledevchallenge.Tools.Utils;
import com.example.mobiledevchallenge.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        LoadSpinner();
        mAuth = FirebaseAuth.getInstance();
        mAuth.setLanguageCode("es");
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tNumber = binding.etpPhoneNumber.getText().toString().trim();
                int codeSelected = binding.spnCountryCodes.getSelectedItemPosition();
                if (tNumber.isEmpty())
                    binding.etpPhoneNumber.setError("No puede dejar este dato vacío");
                if (codeSelected==0)
                    Toast.makeText(LoginActivity.this, "Debe seleccionar un código de país", Toast.LENGTH_SHORT).show();
                if(codeSelected>0 && !tNumber.isEmpty())
                {
                    binding.pbLoadLogin.setVisibility(View.VISIBLE);
                    binding.btnLogin.setEnabled(false);
                    binding.etpPhoneNumber.setEnabled(false);
                    binding.spnCountryCodes.setEnabled(false);
                    String cod = Utils.LoadCountryCodes().get(codeSelected);
                    int PosPlus = cod.indexOf("+");
                    cod = cod.substring(PosPlus, cod.length());
                    String tPhone = cod + tNumber;
                    PhoneAuthOptions options =
                            PhoneAuthOptions.newBuilder(mAuth)
                                    .setPhoneNumber(tPhone)       // Phone number to verify
                                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                    .setActivity(LoginActivity.this)                 // (optional) Activity for callback binding
                                    .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                                    .build();
                    PhoneAuthProvider.verifyPhoneNumber(options);
                }
            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                //Login
                binding.pbLoadLogin.setVisibility(View.GONE);
                binding.btnLogin.setEnabled(true);
                binding.etpPhoneNumber.setEnabled(true);
                binding.spnCountryCodes.setEnabled(true);
                Login(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                //Login Fail
                binding.pbLoadLogin.setVisibility(View.GONE);
                binding.btnLogin.setEnabled(true);
                binding.etpPhoneNumber.setEnabled(true);
                binding.spnCountryCodes.setEnabled(true);
                String errorMessage = e.getMessage();
                Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                binding.pbLoadLogin.setVisibility(View.GONE);
                binding.btnLogin.setEnabled(true);
                binding.etpPhoneNumber.setEnabled(true);
                binding.spnCountryCodes.setEnabled(true);
                Toast.makeText(LoginActivity.this, "El código de verificación fue enviado...", Toast.LENGTH_SHORT).show();
                OpenDialog(s);
            }
        };
    }

    private void Login(PhoneAuthCredential credential){
        binding.pbLoadLogin.setVisibility(View.VISIBLE);
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    binding.pbLoadLogin.setVisibility(View.GONE);
                    GoToHome();
                }
                else
                {
                    binding.pbLoadLogin.setVisibility(View.GONE);
                    String errorMessage = task.getException().getMessage();
                    Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void OpenDialog(String pS) {
        AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
        final EditText edittext = new EditText(LoginActivity.this);
        alert.setMessage("Ingrese el código enviado por sms, por favor");
        alert.setTitle("Autenticación");
        alert.setView(edittext);
        alert.setCancelable(false);
        alert.setPositiveButton("Validar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String codeVerified = edittext.getText().toString();
                if(!codeVerified.isEmpty()){
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(pS, codeVerified);
                    Login(credential);
                }
                else
                    Toast.makeText(LoginActivity.this, "Ingrese el codigo de verificacion", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                binding.pbLoadLogin.setVisibility(View.GONE);
                binding.btnLogin.setEnabled(true);
                binding.etpPhoneNumber.setEnabled(true);
                binding.spnCountryCodes.setEnabled(true);
                Intent i = new Intent(LoginActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        alert.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null)
            GoToHome();
    }

    private void GoToHome() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void LoadSpinner() {
        Adapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Utils.LoadCountryCodes());
        binding.spnCountryCodes.setAdapter((SpinnerAdapter) adapter);
        ((ArrayAdapter<?>) adapter).notifyDataSetChanged();
    }
}