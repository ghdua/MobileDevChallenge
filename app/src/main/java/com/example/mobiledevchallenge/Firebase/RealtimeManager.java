package com.example.mobiledevchallenge.Firebase;

import com.example.mobiledevchallenge.Model.Client;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RealtimeManager {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Clientes");
    public boolean InsertCliente(Client pClient){
        boolean inserted = false;
        String key = databaseReference.push().getKey();
        if (key != null)
            if(pClient!=null) {
                databaseReference.child(key).setValue(pClient);
                inserted = true;
            }
        return inserted;
    }
}
