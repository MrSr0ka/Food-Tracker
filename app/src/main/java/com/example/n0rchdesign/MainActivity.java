package com.example.n0rchdesign;


import static com.example.n0rchdesign.FragmentMeals.adapter;
import static com.example.n0rchdesign.FragmentMeals.items;
import static com.example.n0rchdesign.FragmentMeals.listView;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.n0rchdesign.databinding.ActivityMainBinding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new FragmentProfile());

        //this is navigation bar part where Navi switch between fragments

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(item ->{
            switch(item.getItemId()){
                case R.id.profile:
                    replaceFragment(new FragmentProfile());
                    break;
                case R.id.meals:
//                    replaceFragment(new FragmentMeals());
                    startActivity(new Intent(this, ActivityMealsList.class));
                    break;
                case R.id.tools:
                    replaceFragment(new FragmentTools());
                    break;
                case R.id.settings:
                    replaceFragment(new FragmentSettings());
                    break;
            }

        return true;
        });

            loadContent();
    }

    // onLoadContent - when the app is opened, it reads from the itemsList.txt file directly to FragmentMeals
    // and displays saved items

    public void loadContent(){
        File path = getApplicationContext().getFilesDir();
        File readFrom = new File(path, "itemsList.txt");
        byte[] content = new byte[(int) readFrom.length()];

        FileInputStream stream = null;
        try {
            stream = new FileInputStream(readFrom);
            stream.read(content);

            String  s = new String(content);
            s = s.substring(1, s.length() - 1);
            String split[] = s.split(", ");

            items = new ArrayList<>(Arrays.asList(split));
            adapter = new ListViewRows(this, items);
            FragmentMeals.listView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    // onDestroy - when the app is closed or killed, it saves all the items from FragmentMeals to
    // itemsList.txt file

    @Override
    protected void onDestroy() {
        File path = getApplicationContext().getFilesDir();
        try {
            FileOutputStream writer = new FileOutputStream(new File(path, "itemsList.txt"));
            writer.write(items.toString().getBytes());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    // function to add item to the list

    public static void addItem(String item){
        items.add(item);
        adapter.notifyDataSetChanged();
    }
    // function to remove item from the list

    public static void removeItem(int remove) {
        items.remove(remove);
        listView.setAdapter(adapter);

    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();

    }






}