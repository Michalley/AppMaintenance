package com.example.appmaintenance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static com.example.appmaintenance.FBreff.mAuth;
import static com.example.appmaintenance.FBreff.refUsers;

public class ActivityLogIn extends AppCompatActivity {

    Intent si;

    EditText etE, etP;
    CheckBox cb;
    Button btnLI;
    String email,password,checkuid,value;
    int level;
    User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        etE = (EditText) findViewById(R.id.etE);
        etP = (EditText) findViewById(R.id.etP);
        cb = (CheckBox) findViewById(R.id.cb);
        btnLI = (Button) findViewById(R.id.btnLI);

    }


    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences settings=getSharedPreferences("PREFS_NAME",0);
        Boolean isChecked=settings.getBoolean("isChecked",false);
        checkuid=mAuth.getUid();
        if (mAuth.getCurrentUser()!=null && isChecked) {
            refUsers.child(checkuid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    value = String.valueOf(dataSnapshot.child("level").getValue());
                    if (value=="1"){
                        si = new Intent(ActivityLogIn.this, mainMaintenance.class);
                        si.putExtra("level",value);
                        startActivity(si);
                    }
                    if ((value=="2")||(value=="3")){
                        si = new Intent(ActivityLogIn.this,mainRUser.class);
                        si.putExtra("level",value);
                        startActivity(si);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    public void login(View view) {
        email = etE.getText().toString();
        password = etP.getText().toString();
        final ProgressDialog pd=ProgressDialog.show(this,"Login","Connecting...",true);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        pd.dismiss();
                        if (task.isSuccessful()) {
                            final SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putBoolean("stayConnect", cb.isChecked());
                            editor.commit();
                            Log.d("MainActivity", "signinUserWithEmail:success");

                            checkuid = mAuth.getInstance().getCurrentUser().getUid();
                            refUsers.child(checkuid).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    User use = dataSnapshot.getValue(User.class);
                                    level = use.getLevel();

                                    if (level==1){
                                        si = new Intent(ActivityLogIn.this,mainMaintenance.class);
                                        startActivity(si);
                                    }

                                    if (level==2){
                                        si = new Intent(ActivityLogIn.this,mainRUser.class);
                                        startActivity(si);
                                    }

                                    if (level==3){
                                        si = new Intent(ActivityLogIn.this,mainRUser.class);
                                        startActivity(si);
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Log.d("MainActivity","signinUserWithEmail:fail");
                                    Toast.makeText(ActivityLogIn.this,"e-mail or password are wrong!",Toast.LENGTH_LONG).show();
                                }
                            });
                        } else {
                            Log.d("MainActivity", "signinUserWithEmail:fail");
                            Toast.makeText(ActivityLogIn.this, "e-mail or password are wrong!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void GoRegister(View view) {
        si = new Intent(ActivityLogIn.this,ActivityRegister.class);
        startActivity(si);
    }



}
