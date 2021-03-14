package com.example.fallen_ai;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fallen_ai.UserModel.Female;
import com.example.fallen_ai.UserModel.Male;
import com.example.fallen_ai.databinding.ActivityProfile2Binding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ProfileActivity2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    ActivityProfile2Binding binding;
    FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;
    LocalDate today = LocalDate.now();
    String date;
    int age;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityProfile2Binding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        getInstances();
        PreferncesSpinner();

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
                        month = month+1;
                        date = day+"/"+month+"/"+year;
                        binding.etDOB.setText(date);
                        LocalDate birthdate = LocalDate.of(year,month,day);
                        age = Period.between(birthdate,today).getYears();
                        binding.setage.setText(""+age);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });


        binding.btnNext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Uid = mAuth.getUid();
                String Email = getIntent().getStringExtra("Email");
                String FirstName = getIntent().getStringExtra("FirstName");
                String LastName = getIntent().getStringExtra("LastName");
                String Gender = getIntent().getStringExtra("Gender");
                String DOB = binding.etDOB.getText().toString();
                String PhoneNumber = binding.etPhone.getText().toString();
                String Preference = binding.preferncesSpinner.getSelectedItem().toString();

                if (Gender.equals("Male")) {
                    Male user = new Male(Email,FirstName,LastName,Gender,DOB,PhoneNumber,Preference,age);
                    assert Uid != null;
                    firebaseFirestore.collection("Users").document("Gender").collection(Gender).document(Uid).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ProfileActivity2.this, "Uploaded through male class", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ProfileActivity2.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else if (Gender.equals("Female")){
                    Female user = new Female(Email,FirstName,LastName,Gender,DOB,PhoneNumber,Preference,age);
                    assert Uid != null;
                    firebaseFirestore.collection("Users").document("Gender").collection(Gender).document(Uid).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ProfileActivity2.this, "Uploaded through female class", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ProfileActivity2.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

//                Map<String, Object> user = new HashMap<>();
//                user.put("Age",age);
//                user.put("Date of Birth",DOB);
//                user.put("Preference",Preference);

//                assert Uid != null;
//                firebaseFirestore.collection("Users").document("Gender").collection(Gender).document(Uid).set(user, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(ProfileActivity2.this, "Uploaded", Toast.LENGTH_SHORT).show();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(ProfileActivity2.this, "Error", Toast.LENGTH_SHORT).show();
//                    }
//                });
                Intent intent = new Intent(ProfileActivity2.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void PreferncesSpinner() {
        Spinner Pspinner = findViewById(R.id.preferncesSpinner);
        ArrayAdapter<CharSequence> Padapter = ArrayAdapter.createFromResource(this,
                R.array.Preferences, android.R.layout.simple_spinner_item);
        Padapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Pspinner.setAdapter(Padapter);
        Pspinner.setOnItemSelectedListener(this);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getItemAtPosition(position).equals("Preferences"))
        {
            Toast.makeText(this, "All fields are Mandatory", Toast.LENGTH_SHORT).show();
        }
        else {
            String Preference = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void getInstances() {
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

}