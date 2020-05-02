package com.example.appmaintenance;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * The type F breff.
 */
public class FBreff {

    /**
     * The constant mAuth.
     */
    public static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    /**
     * The constant FBDB.
     */
    public static FirebaseDatabase FBDB = FirebaseDatabase.getInstance();

    /**
     * The constant refUsers.
     */
    public static DatabaseReference refUsers = FBDB.getReference("Users");

    /**
     * The constant refCat.
     */
    public static DatabaseReference refCat = FBDB.getReference("Category");

    /**
     * The constant refAre.
     */
    public static DatabaseReference refAre = FBDB.getReference("Area");

    /**
     * The constant refCom.
     */
    public static DatabaseReference refCom = FBDB.getReference("Complain");

    /**
     * The constant refComP.
     */
    public static DatabaseReference refComP = FBDB.getReference("Complain");

    /**
     * The constant refHis.
     */
    public static DatabaseReference refHis = FBDB.getReference("History");

    /**
     * The constant mStorageRef.
     */
    public static StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();

    /**
     * The constant ref.
     */
    public static StorageReference ref = FirebaseStorage.getInstance().getReference().child("imageComplain/");

}
