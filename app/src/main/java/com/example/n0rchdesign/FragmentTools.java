package com.example.n0rchdesign;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentTools#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTools extends Fragment {



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentTools() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentTools.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentTools newInstance(String param1, String param2) {
        FragmentTools fragment = new FragmentTools();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View inputTools = inflater.inflate(R.layout.fragment_tools,container,false);
        final EditText bmrWeight = inputTools.findViewById(R.id.bmrWeight);
        final EditText bmrHeight = inputTools.findViewById(R.id.bmrHeight);
        final EditText bmrAge =  inputTools.findViewById(R.id.bmrAge);
        final RadioButton bmrMale = inputTools.findViewById(R.id.maleBmr);
        final RadioButton bmrFemale = inputTools.findViewById(R.id.femaleBmr);
        final TextView bmrResults = inputTools.findViewById(R.id.bmrResults);
        final Button bmrButton = inputTools.findViewById(R.id.bmrButton);
        final ImageView modalButton = inputTools.findViewById(R.id.modalButton);

        //calculate button
        bmrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // takes user input and apply BMI formula
                final String weightInput = bmrWeight.getText().toString();
                final String heightInput = bmrHeight.getText().toString();
                final String ageInput = bmrAge.getText().toString();

                //floats input from form
                final float weight = Float.parseFloat(weightInput);
                final float height = Float.parseFloat(heightInput);
                final float age = Float.parseFloat(ageInput);
                float bmr = 0;
                final float bmrMaleFormula = (float) (10 * weight + 6.25 * height - 5 * age + 5);
                final float bmrFemaleFormula = (float) (10 * weight + 6.25 * height - 5 * age - 161);

                //if weight, height and age fields are empty, application stops and requires data input

                //radio buttons - changing formula for specified sex
                // if bmrMale = true then bmr =  bmrMaleFormula, else bmrFemaleFormula;
                if (bmrMale.isChecked()){
                    bmr = bmrMaleFormula;
                } else if (bmrFemale.isChecked()) {
                    bmr = bmrFemaleFormula;
                }
                //if no sex is selected give notification

                //radio buttons - adding PAL multiplier for BMR

                /* display results of calculations in text format below form */
                bmrResults.setText("Your BMR is: " + String.format("%.0f", bmr) + " kcal.");
                Toast.makeText(getActivity(), "BMR Calculated", Toast.LENGTH_SHORT).show();

            }});

          //popup window with information about medical method used to calculate bmr
           modalButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   AlertDialog.Builder alert = new AlertDialog.Builder(inputTools.getContext());
                   alert.setTitle("Mifflin St Jeor equation");
                   alert.setMessage("Mifflin St Jeor is revised version of Harris-Benedict equation." +
                           " It is the most accurate equation today to establish estimated BMR" +
                           " Revised in 1990, it is the youngest equation today.");


                   alert.setPositiveButton("Back", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int modalButton) {

                       }
                   });
                   alert.show();
               }


           });



        // Inflate the layout for this fragment but this was wrong
//        return inflater.inflate(R.layout.fragment_tools, container, false);
        return inputTools;
    }
}