package in.mangoo.mangoodeliver;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Collections;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private EditText empid, pass;
    private Button signinbutton;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private FrameLayout frameLbase , frameLoverlay;
    TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        initializeUI();

        firebaseAuth = FirebaseAuth.getInstance();

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("in.mangoo.mangoodeliver",Context.MODE_PRIVATE);
        String emp = sharedPreferences.getString("id", "");
        String p = sharedPreferences.getString("passs", "");

        if(!emp.isEmpty()){
           // frameLbase.setEnabled(false);
            frameLoverlay.setVisibility(View.VISIBLE);
            Log.i("SharedPreferences","Found old data"+emp);
            Intent intent = new Intent(MainActivity.this,BaseActivity.class);
          //  frameLbase.setEnabled(true);
            frameLoverlay.setVisibility(View.GONE);
            startActivity(intent);
            finish();
        }

        signinbutton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                if(validate()){
                   // frameLbase.setEnabled(false);
                    frameLoverlay.setVisibility(View.VISIBLE);
                    final String emp = empid.getText().toString().trim() + "@gmail.com";
                    final String p = pass.getText().toString();
                    firebaseAuth.signInWithEmailAndPassword(emp,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                SharedPreferences sharedPreferences = getSharedPreferences("in.mangoo.mangoodeliver",Context.MODE_PRIVATE);
                                sharedPreferences.edit().putString("id",emp).apply();
                                sharedPreferences.edit().putString("passs",p).apply();
                                Log.i("sharedPreferences","id and pass added");
                                Toast.makeText(MainActivity.this, "Successfully Signed In!",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this,BaseActivity.class);
                                String uid= firebaseAuth.getCurrentUser().getUid().toString();
                                sharedPreferences.edit().putString("uid",uid).apply();
                                startActivity(intent);
                                finish();
                            }
                            if(!task.isSuccessful()) {
                                tv.setTextColor(getResources().getColor(R.color.red));
                              //  frameLbase.setEnabled(true);
                                frameLoverlay.setVisibility(View.GONE);
                                tv.setText("Driver credentials invalid!");
                                tv.setVisibility(View.VISIBLE);
                                empid.setText("");
                                pass.setText("");
                            }
                        }


                    });
                }
            }
        });

    }

    private void initializeUI(){
        empid = (EditText)findViewById(R.id.employeeid);
        pass = (EditText)findViewById(R.id.password);
        signinbutton = (Button)findViewById(R.id.signin);
        tv = (TextView)findViewById(R.id.textView);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        frameLbase = (FrameLayout) findViewById(R.id.frame_base);
        frameLoverlay = (FrameLayout)findViewById(R.id.frame_overlay);
    }

    private boolean validate(){
        String emp = empid.getText().toString();
        String p= pass.getText().toString();
        if(emp.isEmpty() || p.isEmpty()){
            Toast.makeText(MainActivity.this,"Enter Id and Password!",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}


/*
            <EditText
                android:id="@+id/employeeid"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:ems="10"
                android:hint="Employee ID"
                android:inputType="textPersonName"
                android:outlineAmbientShadowColor="@color/red_mangoo" />
*/
