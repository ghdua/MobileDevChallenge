package com.example.mobiledevchallenge.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;
import com.example.mobiledevchallenge.Firebase.RealtimeManager;
import com.example.mobiledevchallenge.Model.Client;
import com.example.mobiledevchallenge.databinding.ActivityHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        binding.ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmLogout();
            }
        });
        binding.tvCanlendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(HomeActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                binding.tvCanlendar.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
        binding.btnGuardarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String names = binding.etNombresCliente.getText().toString().trim();
                String lastnames = binding.etApellidosCliente.getText().toString().trim();
                String ageStr = binding.etEdadCliente.getText().toString().trim();
                int ageInt = -1;
                if(!ageStr.isEmpty())
                    ageInt = Integer.parseInt(ageStr);
                String birthDate = binding.tvCanlendar.getText().toString().trim();
                if(names.isEmpty())
                    binding.etNombresCliente.setError("Debe ingresar el nombre o nombres del cliente a registrar");
                if(lastnames.isEmpty())
                    binding.etApellidosCliente.setError("Debe ingresar los apellidos del cliente a registrar");
                if(ageStr.isEmpty())
                    binding.etEdadCliente.setError("Debe ingresar la edad del cliente a registrar");
                if(birthDate.isEmpty())
                    binding.tvCanlendar.setError("Debe ingresar le fecha de nacimiento del cliente a registrar");
                if(!names.isEmpty() && !lastnames.isEmpty() && !ageStr.isEmpty() && !birthDate.isEmpty()) {
                    Client client = new Client();
                    client.setNames(names);
                    client.setLastnames(lastnames);
                    client.setAge(ageInt);
                    client.setBirthDate(birthDate);
                    SaveClient(client);
                }
            }

            private void SaveClient(Client pClient) {
                RealtimeManager realtimeManager = new RealtimeManager();
                boolean saved = realtimeManager.InsertCliente(pClient);
                if (saved)
                    Toast.makeText(HomeActivity.this, "Agregado correctamente", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(HomeActivity.this, "No se puede agregar ahora, intente luego", Toast.LENGTH_SHORT).show();
                CleanInputs();
            }
        });
    }

    private void CleanInputs() {
        binding.etNombresCliente.setText("");
        binding.etApellidosCliente.setText("");
        binding.etEdadCliente.setText("");
        binding.tvCanlendar.setText("");
    }

    private void ConfirmLogout() {
        AlertDialog.Builder alert = new AlertDialog.Builder(HomeActivity.this);
        alert.setCancelable(false);
        alert.setMessage("¿Segur@ que desea cerrar sesión?");
        alert.setTitle("Confirmación de salida");
        alert.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    FirebaseAuth.getInstance().signOut();
                    GoToLogin();
                }
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        alert.show();
    }

    private void GoToLogin() {
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}