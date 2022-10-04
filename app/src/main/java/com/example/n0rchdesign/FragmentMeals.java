package com.example.n0rchdesign;

import static com.example.n0rchdesign.MainActivity.addItem;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentMeals#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMeals extends Fragment {

    static ArrayList<String> items;
    static ListViewRows adapter;
    static ListView listView;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentMeals() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMeals.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMeals newInstance(String param1, String param2) {
        FragmentMeals fragment = new FragmentMeals();
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
         View inputMeals = inflater.inflate(R.layout.fragment_meals,container,false);
        final EditText inputName = inputMeals.findViewById(R.id.inputProduct);
        final Button addProduct = inputMeals.findViewById(R.id.productButton);
        final ListView listView = inputMeals.findViewById(R.id.productsList);
        final Button customButton = inputMeals.findViewById(R.id.customButton);
        final ImageView deleteBtn = inputMeals.findViewById(R.id.deleteBtn);



        items = new ArrayList<>();
        items.add("Apple - 1 piece, 200g, 150kcal");
        items.add("Egg - 2 pieces, 104g, 174kcal");
        items.add("Baguette");
        items.add("Salmon");
//        items.add("Ham - 4 slices, 100g, 120kcal");
//        items.add("Butter - 1 spoon, 5g, 45kcal");
//        items.add("Cheese - 2 slices, 60g, 90kcal");
//        items.add("Oats - 1 handful, 30g, 130kcal");
        adapter = new ListViewRows(getContext(),items);
        listView.setAdapter(adapter);

        //add product to list view
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String text = inputName.getText().toString();

                if(text == null || text.length()== 0){
                    Toast.makeText(getActivity(), "Please insert product name.", Toast.LENGTH_SHORT).show();
                } else {
                    addItem(text);
                    inputName.setText("");
                    Toast.makeText(getActivity(), "Added " + text, Toast.LENGTH_SHORT).show();
                }
            }
            //add item method for list view
//            public void addItem(String item){
//                items.add(item);
//                adapter.notifyDataSetChanged();
//            }
        });


     //removing item when long click is detected over row
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "Removed " + items.get(i), Toast.LENGTH_SHORT).show();
                removeItem(i);
                return false;
            }
//            removing item method
            public void removeItem(int remove) {
                items.remove(remove);
                listView.setAdapter(adapter);

            }
        });



        //opens custom product panel
        customButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ActivityAddMeal.class);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return inputMeals;

    }
}