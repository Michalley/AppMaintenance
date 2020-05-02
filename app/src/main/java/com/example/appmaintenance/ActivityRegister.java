package com.example.appmaintenance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static com.example.appmaintenance.FBreff.mAuth;
import static com.example.appmaintenance.FBreff.refUsers;

/**
 * The type Activity register.
 */
public class ActivityRegister extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


    /**
     * The Si.
     */
    Intent si;

    /**
     * The Et n.
     */
    EditText etN, /**
     * The Et fn.
     */
    etFN, /**
     * The Et e.
     */
    etE, /**
     * The Et p.
     */
    etP, /**
     * The Et pn.
     */
    etPN;
    /**
     * The Sp.
     */
    Spinner sp;
    /**
     * The Cb.
     */
    CheckBox cb;
    /**
     * The Btn r.
     */
    Button btnR;
    /**
     * The Name.
     */
    String name, /**
     * The Last name.
     */
    lastName, /**
     * The Email.
     */
    email, /**
     * The Phone.
     */
    phone, /**
     * The Password.
     */
    password, /**
     * The Uid.
     */
    uid, /**
     * The Checkuid.
     */
    checkuid, /**
     * The Value.
     */
    value;
    /**
     * The Level.
     */
    int level;
    /**
     * The U.
     */
    User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        etN = (EditText) findViewById(R.id.etN);
        etFN = (EditText) findViewById(R.id.etFN);
        etE = (EditText) findViewById(R.id.etE);
        etP = (EditText) findViewById(R.id.etP);
        etPN = (EditText) findViewById(R.id.etPN);
        sp = (Spinner) findViewById(R.id.sp);
        cb = (CheckBox) findViewById(R.id.cb);
        btnR = (Button) findViewById(R.id.btnR);

        getSupportActionBar().hide();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.level_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setOnItemSelectedListener(this);
        sp.setAdapter(adapter);


    }

    /**
     * Register.
     *
     * @param view the view
     */
    public void register(View view) {
        name=etN.getText().toString();
        phone=etPN.getText().toString();
        email=etE.getText().toString();
        password=etP.getText().toString();
        lastName=etFN.getText().toString();
        final ProgressDialog pd=ProgressDialog.show(this,"Register","Registering...",true);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        pd.dismiss();
                        if (task.isSuccessful()) {
                            SharedPreferences settings=getSharedPreferences("PREFS_NAME",0);
                            SharedPreferences.Editor editor=settings.edit();
                            editor.putBoolean("stayConnect",cb.isChecked());
                            editor.commit();
                            Log.d("MainActivity", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            uid = user.getUid();
                            u=new User(name,lastName,email,phone,level,uid);
                            refUsers.child(uid).setValue(u);
                            Toast.makeText(ActivityRegister.this, "Successful registration", Toast.LENGTH_LONG).show();

                            if (level==1){
                                si = new Intent(ActivityRegister.this, mainMaintenance.class);
                                startActivity(si);
                            }
                            if (level==2){
                                si = new Intent(ActivityRegister.this,mainRUser.class);
                                startActivity(si);
                            }
                            if (level == 3) {
                                si = new Intent(ActivityRegister.this,mainRUser.class);
                                startActivity(si);
                            }

                        } else {
                            if (task.getException()instanceof FirebaseAuthUserCollisionException)
                                Toast.makeText(ActivityRegister.this, "User with e-mail already exist!", Toast.LENGTH_LONG).show();
                            else {
                                Log.w("MainActivity", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(ActivityRegister.this, "User create failed.",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (position== 1){
            level=1;
        }
        if (position==2){
            level=2;
        }
        if (position==3){
            level=3;
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) { }


    /**
     * Go sign in.
     *
     * @param view the view
     */
    public void GoSignIn(View view) {
        si = new Intent(ActivityRegister.this,ActivityLogIn.class);
        startActivity(si);
    }


}
