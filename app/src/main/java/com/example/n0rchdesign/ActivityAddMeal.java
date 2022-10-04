package com.example.n0rchdesign;

import static com.example.n0rchdesign.FragmentMeals.adapter;
import static com.example.n0rchdesign.FragmentMeals.items;
import static com.example.n0rchdesign.FragmentMeals.listView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityAddMeal extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);


        //ads product to the listView inside FragmentMeals and exits current Activity back to FragmentMeals
        Button productButton = findViewById(R.id.productButton);
        TextView productName = findViewById(R.id.productName);
        TextView productServing = findViewById(R.id.productServing);
        TextView productKcal = findViewById(R.id.productKcal);
        productButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = productName.getText().toString();
//                String kcal = productKcal.getText().toString();
//                String serving = productServing.getText().toString();



                if(name == null || name.length()== 0){
                    Toast.makeText(ActivityAddMeal.this, "Please insert product name.", Toast.LENGTH_SHORT).show();
                } else {
                    addItem(name);
//                    addItem(kcal);
//                    addItem(serving);
                    Toast.makeText(adapter.getContext(), "Added " + name, Toast.LENGTH_SHORT).show();
                }

//                //closes the activity
             finish();


            }
//            add item method for list view
            public void addItem(String item){
                items.add(item);
                listView.setAdapter(adapter);
            }


        });
    }


}