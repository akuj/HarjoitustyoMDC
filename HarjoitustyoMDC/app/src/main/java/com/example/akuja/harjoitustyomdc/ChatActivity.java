package com.example.akuja.harjoitustyomdc;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    DatabaseReference myRef;
    String TAG = "hei";
    String useri;
    TextView txtview;
    EditText edittext;
    DatabaseReference postsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        txtview = (TextView) findViewById(R.id.txtview);
        edittext = (EditText) findViewById(R.id.editText);

        Bundle bundle = getIntent().getExtras();

        if(bundle.getString("user")!= null)
        {
            useri = bundle.getString("user");
            Context context = getApplicationContext();
            Toast.makeText(context, "Welcome " + useri, Toast.LENGTH_LONG).show();
        }

        if(bundle.getString("user")!= null)
        {
            useri = bundle.getString("user");
            txtview.setText(useri);
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("posts");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();
                txtview.append(value+"\n");
                /*String value = dataSnapshot.getValue(String.class);
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Log.d(TAG,""+data.getValue().toString());
                }
                Log.d(TAG, "Value is: " + value);
                txtview.append(value+"\n");
                // Log.d(TAG,"kalle is "+kalle);*/
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    public void write(View view){
        DatabaseReference newPostRef = myRef.push();
        newPostRef.setValue(new Post(useri, edittext.getText().toString()));
    }

    public static class Post {

        public String user;
        public String message;

        public Post(String user, String message) {
            this.user = user;
            this.message = message;
        }
    }
}
