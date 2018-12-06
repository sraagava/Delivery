package in.mangoo.mangoodeliver;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigInteger;
import java.sql.Driver;

import static android.content.ContentValues.TAG;


public class ProfileFragment extends Fragment {


    private TextView name_tv, phone_tv, count_tv, empid_tv,pass_tv, signout_tv;
    private String name, empid, phone;
    private ProgressBar progressBar;
    private int count;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Driverpool");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        initializeUI(view);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("in.mangoo.mangoodeliver",Context.MODE_PRIVATE);
        name = sharedPreferences.getString("name","");

        if(name.isEmpty()){
            getDetails();
        }
        else {
            Log.i("Fetch","Fetch from SharedPref");
            setFromSPref();
        }

        pass_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                //firebaseAuth.getUid();
               Log.i("dsgd","idhu pudhusu");
            }
        });

        signout_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("dgsa","Sign out dada");
                signoutProcess();
            }
        });

        return view;
    }




    private void initializeUI(View view){
        name_tv = (TextView)view.findViewById(R.id.nametv);
        phone_tv = (TextView)view.findViewById(R.id.phonetv);
        count_tv = (TextView)view.findViewById(R.id.counttv);
        empid_tv = (TextView)view.findViewById(R.id.empidtv);
        pass_tv = (TextView)view.findViewById(R.id.passwordtv);
        signout_tv = (TextView)view.findViewById(R.id.signouttv);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar2);
    }


    private void setFromSPref(){

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("in.mangoo.mangoodeliver",Context.MODE_PRIVATE);

        name = sharedPreferences.getString("name","");
        phone = sharedPreferences.getString("phone","");
        empid = sharedPreferences.getString("empid","");
        count = sharedPreferences.getInt("count",0);

        setValuesforTVs(name, empid, phone, count);
    }


    private void getDetails(){
        progressBar.setVisibility(View.VISIBLE);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("/Driverpool/");
        final SharedPreferences sharedPreferences = getContext().getSharedPreferences("in.mangoo.mangoodeliver",Context.MODE_PRIVATE);
        String uid = sharedPreferences.getString("uid","");
        Log.i("dsg","UID"+uid);

        myRef = myRef.child(uid);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("INFO", "Value is " + dataSnapshot.toString());

                name = dataSnapshot.child("name").getValue(String.class);
                phone = dataSnapshot.child("phone").getValue(String.class);
                count = dataSnapshot.child("ordercount").getValue(Integer.class);
                empid = dataSnapshot.child("empid").getValue(String.class);

                Log.i("INFO",name);
                Log.i("INFO",empid);
                Log.i("INFO",phone);
                Log.i("INFO",Integer.toString(count));

                setValuesforTVs(name, empid, phone, count);

                progressBar.setVisibility(View.GONE);

                sharedPreferences.edit().putString("name",name).apply();
                sharedPreferences.edit().putString("phone",phone).apply();
                sharedPreferences.edit().putString("empid",empid).apply();
                sharedPreferences.edit().putInt("count",count).apply();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext().getApplicationContext(), "DATABASE ERROR" + databaseError.getMessage() , Toast.LENGTH_SHORT);
            }
        });
    }


    private void setValuesforTVs(String name, String empid, String phone, int count){
        name_tv.setText(name);
        empid_tv.setText("#" + empid);
        phone_tv.setText(phone);
        count_tv.setText(Integer.toString(count));
    }

    private void signoutProcess(){

        new AlertDialog.Builder(getContext())
                .setIcon(R.drawable.ic_baseline_warning_24px)
                .setTitle("Sign Out? ")
                .setMessage("Signing out will prevent you from accessing the database. Do you want to proceed?")
                .setPositiveButton("Sign Out", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        signoutorginal();
                    }
                }).setNegativeButton("No", null)
                  .show();
    }

    private void signoutorginal(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("in.mangoo.mangoodeliver",Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
        Intent intent = new Intent(this.getContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
        Toast.makeText(getContext(),"Signed out !", Toast.LENGTH_LONG);
    }

}
