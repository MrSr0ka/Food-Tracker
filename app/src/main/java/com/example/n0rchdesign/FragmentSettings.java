package com.example.n0rchdesign;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSettings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSettings extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentSettings() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSettings.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSettings newInstance(String param1, String param2) {
        FragmentSettings fragment = new FragmentSettings();
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

        View settingsProfile = inflater.inflate(R.layout.fragment_settings, container, false);
        TextView loginOn = settingsProfile.findViewById(R.id.loginOn);
        TextView userName = settingsProfile.findViewById(R.id.name);
        TextView email = settingsProfile.findViewById(R.id.email);
        Button logoutBtn = settingsProfile.findViewById(R.id.logout);
        CircleImageView profilePic = settingsProfile.findViewById(R.id.profile_image);

        FirebaseAuth mAuth;
        String onlineUserId = "";
        DatabaseReference usersRef;

        mAuth = FirebaseAuth.getInstance();
        onlineUserId = mAuth.getCurrentUser().getUid();
        usersRef = FirebaseDatabase.getInstance().getReference().child("users").child(onlineUserId);


        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loginOn.setText("Last logged in: " + snapshot.child("logedinon").getValue().toString());
                userName.setText(snapshot.child("name").getValue().toString());
                email.setText(snapshot.child("email").getValue().toString());

                Glide.with(FragmentSettings.this).load(snapshot.child("profilepictureurl").getValue().toString()).into(profilePic);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Daily Spend Tracker")
                        .setMessage("Are you sure you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(getContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                Intent intent2 = new Intent(Intent.ACTION_MAIN);
                                intent2.addCategory(Intent.CATEGORY_HOME);
                                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent2);

                                getActivity().finish();
                                System.exit(0);
                            }
                        }).setNegativeButton("No", null)
                        .show();
            }
        });
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_settings, container, false);
        return settingsProfile;
    }
}