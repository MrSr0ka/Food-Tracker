package com.example.n0rchdesign;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListViewRows extends ArrayAdapter<String> {
    public static ArrayList<String> list;
    public static Context context;
        public ListViewRows(Context context, ArrayList<String>items){
            super(context, R.layout.list_row, items);
            this.context = context;
            list = items;

        }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if(convertView == null){
                LayoutInflater layoutInflater=  (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.list_row, null);

                TextView number = convertView.findViewById(R.id.number);
                number.setText(position + 1 + ".");

                TextView name = convertView.findViewById(R.id.name);
                name.setText(list.get(position));

//                TextView kcal = convertView.findViewById(R.id.productKcal);
//                kcal.setText(list.get(position));
//
//                TextView serving = convertView.findViewById(R.id.productServing);
//                serving.setText(list.get(position));

                ImageView deleteBtn = convertView.findViewById(R.id.deleteBtn);
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MainActivity.removeItem(position);
                    }
                });


            }
        return convertView;
    }
}
