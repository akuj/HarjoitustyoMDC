package com.example.akuja.harjoitustyomdc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatActivity extends AppCompatActivity {

    DatabaseReference myRef;
    String TAG = "hei";
    String useri;
    TextView txtview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        txtview = (TextView) findViewById(R.id.txtview);

        Bundle bundle = getIntent().getExtras();

        if(bundle.getString("user")!= null)
        {
            useri = bundle.getString("user");
            txtview.setText(useri);
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference(useri);


    }

    public void write(){
        // Write a message to the database

        myRef.setValue("Hello, World!");
    }

    public void read(){
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
