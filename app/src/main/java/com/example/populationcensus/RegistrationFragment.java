package com.example.populationcensus;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationFragment extends Fragment {

    private EditText edPassord, edEmail,
            edFirstname, edLastname, edAge, edCity;
    private RadioButton rbMale, rbFemale;
    private Button bSignup;
    private ImageButton bBack;
    private FirebaseAuth firebaseAuth;

    private SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);


        edPassord = view.findViewById(R.id.edPassword);
        edEmail = view.findViewById(R.id.edEmailAddress);
        edFirstname = view.findViewById(R.id.edFirstname);
        edLastname = view.findViewById(R.id.edLastname);
        edAge = view.findViewById(R.id.edAge);
        edCity = view.findViewById(R.id.edCity);

        rbMale = view.findViewById(R.id.rbMale);
        rbFemale = view.findViewById(R.id.rbFemale);

        bBack = view.findViewById(R.id.bBack);
        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainerView, new LoginFragment())
                        .commit();
            }
        });
        bSignup = view.findViewById(R.id.bSignup);

        firebaseAuth = FirebaseAuth.getInstance();

        bSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sex = null;
                if (rbMale.isChecked())
                    sex = "Male";
                else if (rbFemale.isChecked()) {
                    sex = "Female";
                }
                signUp(edEmail.getText().toString(),
                        edPassord.getText().toString(),
                        edFirstname.getText().toString(),
                        edLastname.getText().toString(),
                        Integer.parseInt(edAge.getText().toString()),
                        edCity.getText().toString(),
                        sex);
            }
        });

        return view;
    }

    private void signUp(String email, String password, String first, String last, int age, String city, String sex) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ||
        TextUtils.isEmpty(first) || TextUtils.isEmpty(last) ||
        TextUtils.isEmpty(city) || (age == 0) || !(rbMale.isChecked() || rbFemale.isChecked()))
        {
            Toast.makeText(getContext(), "Заполните поля", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    assert user != null;
                    sendEmailVerification();
                } else {
                    Toast.makeText(getContext(), "Ошибка регистрации", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sharedPreferences = getActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("firstname", first);
        editor.putString("lastname", last);
        editor.putInt("age", age);
        editor.putString("city", city);
        editor.putString("sex", sex);
        editor.apply();
    }
    private void sendEmailVerification() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        assert user != null;
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "На вашу почту было отправлено письмо для подтверждения", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Ошибка подтверждения адреса", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}