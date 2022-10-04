package com.example.n0rchdesign;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class DailyMealsAdapter extends RecyclerView.Adapter<DailyMealsAdapter.ViewHolder> {

    private Context mContext;
    private List<Data> myDataList;
    private String postid, name, item;
    private int calories;


    public DailyMealsAdapter(Context mContext, List<Data> myDataList) {
        this.mContext = mContext;
        this.myDataList = myDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.retrieve_layout, parent, false);
        return new DailyMealsAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Data data = myDataList.get(position);
        holder.item.setText("Meal time: " + data.getItem());
        holder.calorie.setText("Calories: "+data.getCalories());
        holder.date.setText("Date: "+data.getDate());
        holder.foodname.setText("Product: "+data.getProductName());

        switch (data.getItem()){
            case "Breakfast":
                holder.imageView.setImageResource(R.drawable.ic_baseline_breakfast_dining_24);
                break;
            case "Brunch":
                holder.imageView.setImageResource(R.drawable.ic_baseline_brunch_dining_24);
                break;
            case "Lunch":
                holder.imageView.setImageResource(R.drawable.ic_baseline_lunch_dining_24);
                break;
            case "Dinner":
                holder.imageView.setImageResource(R.drawable.ic_baseline_dinner_dining_24);
                break;
            case "Other":
                holder.imageView.setImageResource(R.drawable.ic_baseline_emoji_food_beverage_24);
                break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postid = data.getId();
                name = data.getProductName();
                calories = data.getCalories();
                item = data.getItem();
                
                updateData();
            }

        });

    }

    private void updateData() {

        AlertDialog.Builder myDialog = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View myView = inflater.inflate(R.layout.update_layout, null);

        myDialog.setView(myView);
        final AlertDialog dialog = myDialog.create();

        final TextView mItem = myView.findViewById(R.id.item);
        final EditText mCalories = myView.findViewById(R.id.calories);
        final EditText mName = myView.findViewById(R.id.name);

        mItem.setText(item);

        mCalories.setText(String.valueOf(calories));
        mCalories.setSelection(String.valueOf(calories).length());

        mName.setText(name);
        mName.setSelection(name.length());

        Button updateBtn = myView.findViewById(R.id.update);
        Button deleteBtn = myView.findViewById(R.id.delete);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calories = Integer.parseInt(mCalories.getText().toString());
                name = mName.getText().toString();

                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Calendar cal = Calendar.getInstance();
                String date = dateFormat.format(cal.getTime());

                Data data = new Data(item, date, postid, name, calories);
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                reference.child(postid).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(mContext, "Update successful.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, "Update failed"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                Intent intent=new Intent(mContext.getApplicationContext(), ActivityMealsList.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                mContext.getApplicationContext().startActivity(intent);
            dialog.dismiss();

            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                reference.child(postid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
//                            Toast.makeText(mContext, "Item removed", Toast.LENGTH_SHORT).show();


                        } else {
                            Toast.makeText(mContext, "Couldn't remove item"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                Intent intent=new Intent(mContext.getApplicationContext(), ActivityMealsList.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                mContext.getApplicationContext().startActivity(intent);
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    @Override
    public int getItemCount() {
        return myDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView item, calorie, date, foodname;
            public ImageView imageView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                item = itemView.findViewById(R.id.item);
                calorie = itemView.findViewById(R.id.calorieAmount);
                foodname = itemView.findViewById(R.id.foodname);
                date = itemView.findViewById(R.id.date);
                imageView = itemView.findViewById(R.id.imageView);
            }
        }
}
