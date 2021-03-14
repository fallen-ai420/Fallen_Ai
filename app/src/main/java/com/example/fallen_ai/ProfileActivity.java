package com.example.fallen_ai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.ims.feature.MmTelFeature;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fallen_ai.UserModel.Female;
import com.example.fallen_ai.UserModel.Male;
import com.example.fallen_ai.databinding.ActivityProfileBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    ActivityProfileBinding binding;
    FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        GoogleGso();
        getInstances();
        GenderSpinner();

        String Email = getIntent().getStringExtra("Email");
        binding.etEmail.setText(Email);

//        binding.btnNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String Uid = mAuth.getUid();
//                String FirstName = binding.etFirstName.getText().toString();
//                String LastName = binding.etLastName.getText().toString();
//                String Email = binding.etEmail.getText().toString();
//                String Gender = binding.genderSpinner.getSelectedItem().toString();
//
//                if (Gender.equals("Male")) {
//                    Male user = new Male(FirstName,LastName,Email,Gender);
//                    assert Uid != null;
//                    firebaseFirestore.collection("Users").document("Gender").collection(Gender).document(Uid).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Toast.makeText(ProfileActivity.this, "Uploaded through male class", Toast.LENGTH_SHORT).show();
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(ProfileActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//                else if (Gender.equals("Female")){
//                    Female user = new Female(FirstName,LastName,Email,Gender);
//                    assert Uid != null;
//                    firebaseFirestore.collection("Users").document("Gender").collection(Gender).document(Uid).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Toast.makeText(ProfileActivity.this, "Uploaded through female class", Toast.LENGTH_SHORT).show();
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(ProfileActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//
////                Map<String, Object> user = new HashMap<>();
////                user.put("First Name",FirstName);
////                user.put("Last Name",LastName);
////                user.put("Email",Email);
////                user.put("Gender",Gender);
//
//                Intent intent = new Intent(ProfileActivity.this,ProfileActivity2.class);
//                intent.putExtra("Gender",Gender);
//                startActivity(intent);
//            }
//        });
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = binding.etEmail.getText().toString();
                String FirstName = binding.etFirstName.getText().toString();
                String LastName = binding.etLastName.getText().toString();
                String Gender = binding.genderSpinner.getSelectedItem().toString();

                Intent intent = new Intent(ProfileActivity.this,ProfileActivity2.class);
                intent.putExtra("Email",Email);
                intent.putExtra("FirstName",FirstName);
                intent.putExtra("LastName",LastName);
                intent.putExtra("Gender",Gender);
                startActivity(intent);
                finish();
            }
        });
    }

    private void GoogleGso() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void getInstances() {
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_right_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.logOut:
                mGoogleSignInClient.signOut();
                FirebaseAuth.getInstance().signOut();
//                mAuth.signOut();
                Intent intent = new Intent(ProfileActivity.this,SignInActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
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