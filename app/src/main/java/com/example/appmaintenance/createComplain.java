package com.example.appmaintenance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.example.appmaintenance.FBreff.mAuth;
import static com.example.appmaintenance.FBreff.ref;
import static com.example.appmaintenance.FBreff.refAre;
import static com.example.appmaintenance.FBreff.refCat;
import static com.example.appmaintenance.FBreff.refCom;
import static com.example.appmaintenance.FBreff.refUsers;

public class createComplain extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static final int PICK_IMAGE = 100;

    EditText etN, etNO;
    LinearLayout layout;
    TextView tvD, tvT;
    ImageView ivC;
    RadioGroup rg;
    RadioButton rb1,rb2;
    Spinner spAr,spCat;
    Uri ImageData;
    Intent t;

    String date,time,category,zone,note,name,value1,value2,user,uid,pic,st1,st2;
    int state,emergency,position1,position2;

    ArrayList<String> aa1 = new ArrayList<>();
    ArrayList<String> aa2 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_complain);

        layout = (LinearLayout) findViewById(R.id.layout);
        etN = (EditText) findViewById(R.id.tvN);
        etNO = (EditText) findViewById(R.id.etNo);
        tvD = (TextView) findViewById(R.id.tvD);
        tvT = (TextView) findViewById(R.id.tvT);
        spAr = (Spinner) findViewById(R.id.spAr);
        spAr.setOnItemSelectedListener(this);
        spCat = (Spinner) findViewById(R.id.spCat);
        spCat.setOnItemSelectedListener(this);
        ivC = (ImageView) findViewById(R.id.ivC);
        rg = (RadioGroup) findViewById(R.id.rg1);
        rb1 = (RadioButton) findViewById(R.id.rb1);
        rb2 = (RadioButton) findViewById(R.id.rb2);

        rb2.setChecked(true);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        time = format.format(calendar.getTime());
        tvT.setText(time);

        SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date mydate = new Date();
        date = timeStampFormat.format(mydate);
        tvD.setText(date);

        fillSpinnerA();
        fillSpinnerC();
        getUser();

        ivC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            ImageData = data.getData();


            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),ImageData);
                ivC.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

            final StorageReference reff = ref.child(ImageData.getLastPathSegment());

            reff.putFile(ImageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    reff.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            pic = String.valueOf(uri);
                            Toast.makeText(createComplain.this,pic,Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
        }
    }

    public void fillSpinnerA (){
        refAre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                aa1.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    aa1.add(item.getValue().toString());
                }
                ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(createComplain.this, android.R.layout.simple_list_item_1, aa1);
                spAr.setAdapter(adapter3);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void fillSpinnerC () {
        refCat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                aa2.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    aa2.add(item.getValue().toString());
                }
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(createComplain.this, android.R.layout.simple_list_item_1, aa2);
                spCat.setAdapter(adapter2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    public void getUser() {
        uid = mAuth.getInstance().getCurrentUser().getUid();
        refUsers.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User us = dataSnapshot.getValue(User.class);
                value1 = us.getName();
                value2 = us.getlName();
                user = value1+" "+value2;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(createComplain.this,"not working",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void save(View view) {

        name = etN.getText().toString();
        note = etNO.getText().toString();
        state=0;

        if (rb1.isChecked()){
            emergency=1;
        }
        if (rb2.isChecked()){
            emergency=2;
        }

        AlertDialog.Builder adb;
        adb = new AlertDialog.Builder(this);
        adb.setTitle("Saving...");
        adb.setMessage("Are You Sure?");
        adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                t = new Intent(createComplain.this,mainRUser.class);
                WriteDataBase(user,date,time,category,zone,emergency,state,note,name,pic);
                startActivity(t);
            }
        });
        adb.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog ad = adb.create();
        ad.show();

    }

    public void WriteDataBase(String user,String date,String time,String category,String zone,int emergency,int state,String note,String name,String pic) {
        Complain c = new Complain(user,date,time,category,zone,emergency,state,note,name,pic);
        refCom.child(name).setValue(c);
    }

    public void Back(View view) {
        Intent t = new Intent (createComplain.this, mainRUser.class);
        startActivity(t);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        st1=spAr.getSelectedItem().toString();
        st2 = spCat.getSelectedItem().toString();

        if (parent.getId() == spCat.getId()){
            position1=position;
            zone = st1;
        }

        if (parent.getId() == spCat.getId()){
            position2=position;
            category = st2;
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
