package com.example.n0rchdesign;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityEditProfile extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Button saveButton = findViewById(R.id.saveButton);
        TextView bmrNeeded = findViewById(R.id.bmrNeeded);
        Button palBtn = findViewById(R.id.palBtn);
        TextView sedentary = findViewById(R.id.sedentary);
        TextView moderate = findViewById(R.id.moderate);
        TextView light = findViewById(R.id.light);
        TextView daily = findViewById(R.id.daily);
        TextView extreme = findViewById(R.id.extreme);
        TextView intense = findViewById(R.id.intense);


        palBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String palCalc = bmrNeeded.getText().toString();

                float palTotal = Float.parseFloat(palCalc);

                //displays results from PAL
                sedentary.setText(String.format("Sedentary activity: " + "%.0f", palTotal * 1.2) + " kcal.");
                light.setText(String.format("Light activity: " + "%.0f", palTotal * 1.375) + " kcal.");
                moderate.setText(String.format("Moderate activity: " + "%.0f", palTotal * 1.465) + " kcal.");
                daily.setText(String.format("Daily activity: " + "%.0f", palTotal * 1.550) + " kcal.");
                intense.setText(String.format("Intense activity: " + "%.0f", palTotal * 1.725) + " kcal.");
                extreme.setText(String.format("Extreme activity: " + "%.0f", palTotal * 1.9) + " kcal.");
            }
        });

        //saves user settings and returns to main activity as fragmentProfile
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}