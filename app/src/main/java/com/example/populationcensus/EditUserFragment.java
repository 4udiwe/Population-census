package com.example.populationcensus;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class EditUserFragment extends Fragment {
    private EditText edName, edSecondname, edAge, edCity;
    private Button bCancel, bApply;
    private SharedPreferences pref;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_user, container, false);

        pref = getActivity().getSharedPreferences("User", Context.MODE_PRIVATE);

        bApply = view.findViewById(R.id.bApply);
        bCancel = view.findViewById(R.id.bCancel);

        edName = view.findViewById(R.id.edEditName);
        edSecondname = view.findViewById(R.id.edEditSecondName);
        edAge = view.findViewById(R.id.edEditAge);
        edCity = view.findViewById(R.id.edEditCity);

        edName.setText(pref.getString("firstname", "name"));
        edSecondname.setText(pref.getString("lastname", "lastname"));
        edAge.setText(String.valueOf(pref.getInt("age", 0)));
        edCity.setText(pref.getString("city", "city"));

        bApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("firstname", edName.getText().toString());
                editor.putString("lastname", edSecondname.getText().toString());
                editor.putInt("age", Integer.parseInt(edAge.getText().toString()));
                editor.putString("city", edCity.getText().toString());
                editor.apply();
                Toast.makeText(getContext(), "User data updated", Toast.LENGTH_SHORT).show();
            }
        });

        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().hide(EditUserFragment.this).commit();
            }
        });

        return view;
    }
}