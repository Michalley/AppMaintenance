package com.example.appmaintenance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static com.example.appmaintenance.FBreff.mAuth;
import static com.example.appmaintenance.FBreff.refUsers;

/**
 * The type Profile.
 */
public class Profile extends AppCompatActivity {

    /**
     * The Tv n.
     */
    TextView tvN, /**
     * The Tv e.
     */
    tvE, /**
     * The Tv p.
     */
    tvP, /**
     * The Tv l.
     */
    tvL;

    /**
     * The Uid.
     */
    String uid;
    /**
     * The Value 1.
     */
    int value1;
    /**
     * The T.
     */
    Intent t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvN = (TextView) findViewById(R.id.tvN);
        tvE = (TextView) findViewById(R.id.tvE);
        tvP = (TextView) findViewById(R.id.tvP);
        tvL = (TextView) findViewById(R.id.tvL);

        getUser();


    }

    /**
     * Gets user.
     */
    public void getUser() {
        uid = mAuth.getInstance().getCurrentUser().getUid();
        refUsers.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User us = dataSnapshot.getValue(User.class);
                String name = us.getName();
                String lastname = us.getlName();
                tvN.setText(name+" "+lastname);
                String email = us.getEmail();
                tvE.setText(email);
                String phone = us.getPhone();
                tvP.setText(phone);
                int level = us.getLevel();
                if (level==1){
                    tvL.setText("Maintenance");
                }
                if (level==2){
                    tvL.setText("Responsible");
                }
                if (level==3){
                    tvL.setText("Student");
                }
                value1=us.getLevel();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("History");
        if (value1==1) {
            menu.add("Category");
            menu.add("Area");
        }
        menu.add("Back To Main");
        menu.add("Credits");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //activity menu selection
        String st = item.getTitle().toString();
        if (st.equals("History")) {
            t = new Intent(this, History.class);
            startActivity(t);
        }
        if (st.equals("Credits")) {
            Toast.makeText(this, "Credits", Toast.LENGTH_LONG).show();
        }

        if (st.equals("Category")) {
            t = new Intent(this,ActivityCategory.class);
            startActivity(t);
        }
        if (st.equals("Area")) {
            t = new Intent(this,ActivityArea.class);
            startActivity(t);
        }

        if (st.equals("Back To Main")) {
            if (value1 == 1) {
                t = new Intent(this, mainMaintenance.class);
                startActivity(t);
            }
            if (value1==2 || value1==3){
                t = new Intent(this, mainRUser.class);
                startActivity(t);
            }
        }
        return true;
    }

}
