package com.example.fallen_ai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fallen_ai.databinding.ActivityProfile2Binding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    ActivityProfile2Binding binding;
    FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityProfile2Binding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        getInstances();

        GenderSpinner();

        Calendar calendar = Calendar.getInstance();
        final int year =calendar.get(Calendar.YEAR);
        final int month =calendar.get(Calendar.MONTH);
        final int day =calendar.get(Calendar.DAY_OF_MONTH);

        binding.etDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ProfileActivity2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month= month+1;
                        String date = day+"/"+month+"/"+year;
                        binding.etDOB.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        binding.btnNext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Uid = mAuth.getUid();
                String DOB = binding.etDOB.getText().toString();
                String Gender = binding.genderSpinner.getSelectedItem().toString();

                Map<String, Object> user = new HashMap<>();
                user.put("Date of Birth",DOB);
                user.put("Gender",Gender);

                assert Uid != null;
                firebaseFirestore.collection("Users").document(Uid).set(user, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ProfileActivity2.this, "Uploaded", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfileActivity2.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void getInstances() {
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    private void GenderSpinner() {
        Spinner Gspinner = findViewById(R.id.genderSpinner);
        ArrayAdapter<CharSequence> Gadapter = ArrayAdapter.createFromResource(this,
                R.array.Gender, android.R.layout.simple_spinner_item);
        Gadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Gspinner.setAdapter(Gadapter);
        Gspinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getItemAtPosition(position).equals("Select Gender"))
        {
            Toast.makeText(this, "All fields are Mandatory", Toast.LENGTH_SHORT).show();
        }
        else {
            String Gender = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}