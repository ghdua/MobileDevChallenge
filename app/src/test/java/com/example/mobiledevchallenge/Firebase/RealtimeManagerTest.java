package com.example.mobiledevchallenge.Firebase;

import com.example.mobiledevchallenge.Model.Client;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import junit.framework.TestCase;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import io.mockk.MockKAnnotations;
import io.mockk.impl.annotations.InjectMockKs;
import io.mockk.impl.annotations.MockK;
import io.mockk.impl.annotations.RelaxedMockK;

public class RealtimeManagerTest extends TestCase {

    @MockK
    @InjectMockKs
    private DatabaseReference databaseReference;
    AutoCloseable openMocks;
    private Client client;
    public void setUp() throws Exception {
        super.setUp();
        openMocks = MockitoAnnotations.openMocks(this);
        client = new Client();
    }

    public void testInsertCliente() {
        assertNotNull(client);
    }

    @Test
    @Ignore
    public void testInsertClient(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Clientes");
        boolean inserted = false;
        String key = databaseReference.push().getKey();
        if (key != null)
            if(client!=null) {
                databaseReference.child(key).setValue(client);
                inserted = true;
            }
        assertTrue(inserted);
    }
}