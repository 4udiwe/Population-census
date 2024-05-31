package com.example.populationcensus;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ProfileFragment extends Fragment {
    private TextView name, secondname, age, city, sex;
    private Button bEdit, bAdd;
    private SharedPreferences pref;


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

        name.setText(pref.getString("firstname", "Name"));
        secondname.setText(pref.getString("lastname", "Secondname"));
        age.setText(String.valueOf(pref.getInt("age", 0)));
        city.setText(pref.getString("city", "City"));
        sex.setText(pref.getString("sex", "Sex"));

        bEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragmentContainerEditUser, new EditUserFragment(), null)
                        .commit();
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
}