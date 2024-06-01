package com.example.populationcensus;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class ProfileFragment extends Fragment {
    private TextView name, secondname, age, city, sex, bApply, bCancel;
    private Button bEdit, bAdd;
    private SharedPreferences pref;
    private Dialog dialog;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        pref = getActivity().getSharedPreferences("User", Context.MODE_PRIVATE);

        name = view.findViewById(R.id.tvName);
        secondname = view.findViewById(R.id.tvSecondname);
        age = view.findViewById(R.id.tvAge);
        city = view.findViewById(R.id.tvCity);
        sex = view.findViewById(R.id.tvSex);
        bEdit = view.findViewById(R.id.bEdit);
        bAdd = view.findViewById(R.id.bAdd);


        update();

        bEdit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.edit_user);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawable(getActivity().getDrawable(R.drawable.dialog_bg));
                bApply = dialog.findViewById(R.id.bApply);
                bCancel = dialog.findViewById(R.id.bCancel);

                EditText edFirstname, edLastname, edAge, edCity;
                edFirstname = dialog.findViewById(R.id.edEditName);
                edLastname = dialog.findViewById(R.id.edEditSecondName);
                edAge = dialog.findViewById(R.id.edEditAge);
                edCity = dialog.findViewById(R.id.edEditCity);

                edFirstname.setText(pref.getString("firstname", "defname"));
                edLastname.setText(pref.getString("lastname", "defname"));
                edAge.setText(String.valueOf(pref.getInt("age", 0)));
                edCity.setText(pref.getString("city", "defcity"));
                dialog.show();

                bCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                bApply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("firstname", edFirstname.getText().toString());
                        editor.putString("lastname", edLastname.getText().toString());
                        editor.putInt("age", Integer.parseInt(edAge.getText().toString()));
                        editor.putString("city", edCity.getText().toString());
                        editor.apply();
                        dialog.dismiss();
                        Toast.makeText(getContext(), "User data updated", Toast.LENGTH_SHORT).show();
                        update();

                    }
                });
            }
        });

        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference;
                String USER_KEY = "user";
                String DB_URL = "https://population-census-94717-default-rtdb.europe-west1.firebasedatabase.app/";
                databaseReference = FirebaseDatabase.getInstance(DB_URL).getReference(USER_KEY);
                User user = new User(databaseReference.getKey(),
                        pref.getString("email", ""),
                        pref.getString("firstname", "Name"),
                        pref.getString("secondname", "Secondname"),
                        pref.getInt("age", 0),
                        pref.getString("city", "City"),
                        pref.getString("sex", "Sex"));
                databaseReference.push().setValue(user);
            }
        });
        return view;
    }

    public void update(){
        name.setText(pref.getString("firstname", "Name"));
        secondname.setText(pref.getString("lastname", "Secondname"));
        age.setText(String.valueOf(pref.getInt("age", 0)));
        city.setText(pref.getString("city", "City"));
        sex.setText(pref.getString("sex", "Sex"));
    }
}