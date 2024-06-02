package com.example.populationcensus;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import kotlin.Pair;

public class StatisticsFragment extends Fragment {

    private ArrayList<User> userArrayList;
    private DatabaseReference databaseReference;
    private ArrayList<String> cities;
    private ArrayList<Integer> citiespopulation;
    private ArrayList<Integer> ages;
    private ValueEventListener valueEventListener;
    private PieChart pieChart;
    private TextView tv1city, tv2city, tv3city, tv1pop, tv2pop, tv3pop;

    private static final String USER_KEY = "user", DB_URL = "https://population-census-94717-default-rtdb.europe-west1.firebasedatabase.app/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        databaseReference = FirebaseDatabase.getInstance(DB_URL).getReference(USER_KEY);
        userArrayList = new ArrayList<>();
        //getDataFromDB();
        cities = new ArrayList<>();
        citiespopulation = new ArrayList<>();
        ages = new ArrayList<>();
        ages.add(0);
        ages.add(0);
        ages.add(0);

        tv1city = view.findViewById(R.id.tv1City);
        tv2city = view.findViewById(R.id.tv2City);
        tv3city = view.findViewById(R.id.tv3City);
        tv1pop = view.findViewById(R.id.tv1pop);
        tv2pop = view.findViewById(R.id.tv2pop);
        tv3pop = view.findViewById(R.id.tv3pop);


        pieChart = view.findViewById(R.id.piechart);

        valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    userArrayList.add(user);
                }
                collectStats();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

    private void collectStats() {
        for (User user : userArrayList){
            String city = user.getCity();
            if (cities.contains(city)){
                int population = citiespopulation.get(cities.indexOf(city));
                population++;
                citiespopulation.set(cities.indexOf(city), population);
            }
            else {
                cities.add(city);
                citiespopulation.add(1);
            }

            int age = user.getAge();
            if (age < 20){
                int number = ages.get(0);
                ages.set(0, number + 1);
            } else if (20 < age && age < 30) {
                int number = ages.get(1);
                ages.set(1, number + 1);
            }
            else {
                int number = ages.get(2);
                ages.set(2, number + 1);
            }
        }

        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(ages.get(0), "Under 20"));
        entries.add(new PieEntry(ages.get(1), "20 - 30"));
        entries.add(new PieEntry(ages.get(2), "Older 30"));

        PieDataSet pieDataSet = new PieDataSet(entries, "");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setCenterTextSizePixels(100);
        pieChart.setEntryLabelTextSize(18);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.animateXY(1000, 1000);
        pieChart.invalidate();

        System.out.println(cities.size());
        System.out.println(citiespopulation.size());


        int max = Collections.max(citiespopulation);
        int maxindex = citiespopulation.indexOf(max);
        tv1city.setText(cities.get(maxindex));
        tv1pop.setText(String.valueOf(citiespopulation.set(maxindex, 0)));
        max = Collections.max(citiespopulation);
        maxindex = citiespopulation.indexOf(max);
        tv2city.setText(cities.get(maxindex));
        tv2pop.setText(String.valueOf(citiespopulation.set(maxindex, 0)));
        max = Collections.max(citiespopulation);
        maxindex = citiespopulation.indexOf(max);
        tv3city.setText(cities.get(maxindex));
        tv3pop.setText(String.valueOf(citiespopulation.set(maxindex, 0)));



    }
}