package com.example.n0rchdesign;

import static java.lang.Integer.parseInt;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class ActivityMealsList extends AppCompatActivity {

        private Toolbar toolbar;
        private TextView amounttxtview;
        private RecyclerView recyclerView;
        private FloatingActionButton fab;

        private FirebaseAuth mAuth;
        private DatabaseReference ref;
        private String onlineUserId = "";
        private ProgressDialog loader;

        private DailyMealsAdapter dailyMealsAdapter;
        private List<Data> myDataList;
        private Button backBtn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals_list);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Daily Food-Tracker");

        amounttxtview = findViewById(R.id.amountTextView);

        fab = findViewById(R.id.fab);

        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityMealsList.this, MainActivity.class);
                startActivity(intent);

            }
        });

        mAuth = FirebaseAuth.getInstance();
        onlineUserId = mAuth.getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference().child("Products").child(onlineUserId);
        loader = new ProgressDialog(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProductEaten();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        myDataList = new ArrayList<>();
        dailyMealsAdapter = new DailyMealsAdapter(ActivityMealsList.this, myDataList);
        recyclerView.setAdapter(dailyMealsAdapter);

        readItems();

    }

        private void readItems(){
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Calendar cal = Calendar.getInstance();
            String date = dateFormat.format(cal.getTime());

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products").child(onlineUserId);
            Query query = reference.orderByChild("date").equalTo(date);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        Data data = dataSnapshot.getValue(Data.class);

                        myDataList.add(data);
                        dailyMealsAdapter.notifyDataSetChanged();
                    }


                int totalCalories = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object total = map.get("calories");

                    try {
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalCalories += pTotal;
                    } catch (Exception e) {
                        System.out.println("wrong input");

                    }
                    amounttxtview.setText("Total calories today " + totalCalories + "KCAL");


                }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }


    private void addProductEaten() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);

        View myView = inflater.inflate(R.layout.input_layout, null);
        myDialog.setView(myView);

        final AlertDialog dialog =  myDialog.create();
        dialog.setCancelable(false);

        final Spinner itemsSpinner = myView.findViewById(R.id.spinner);
        ArrayAdapter<String> productsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.products));
        productsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemsSpinner.setAdapter(productsAdapter);

        final EditText calories = myView.findViewById(R.id.calories);
        final EditText productName = myView.findViewById(R.id.foodname);

        final Button save = myView.findViewById(R.id.save);
        final Button cancel = myView.findViewById(R.id.cancel);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mCalories = calories.getText().toString();
                String mProductName = productName.getText().toString();
                String item = itemsSpinner.getSelectedItem().toString();

                if (mCalories.isEmpty()){
                    calories.setError("Calories are required.");
                    return;
                }

                if(mProductName.isEmpty()){
                    productName.setError("Product name is required.");
                    return;
                }

                if(item.equals("Select")){
                    Toast.makeText(ActivityMealsList.this, "Select valid meal time.", Toast.LENGTH_SHORT).show();
                }

                else {
                    loader.setMessage("Adding item to database.");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    String id = ref.push().getKey();
                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    Calendar cal = Calendar.getInstance();
                    String date = dateFormat.format(cal.getTime());
                    //this is version with more options
//                    Data data =  new Data(item, date,id, mProductName,mServing, mProteins, mFats, mCarbs, Integer.parseInt(mCalories));

                    Data data =  new Data(item, date,id, mProductName, parseInt(mCalories));
                    ref.child(id).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(ActivityMealsList.this, "Item added successfully.", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(ActivityMealsList.this, "Failed to add product.", Toast.LENGTH_SHORT).show();
                            }
                            loader.dismiss();
                        }

                    });
                }
                dialog.dismiss();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });



        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            dialog.dismiss();
            }
        });

        dialog.show();
    }

}