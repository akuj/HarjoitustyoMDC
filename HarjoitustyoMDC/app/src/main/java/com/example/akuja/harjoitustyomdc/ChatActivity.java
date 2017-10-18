package com.example.akuja.harjoitustyomdc;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    DatabaseReference myRef;
    String useri;
    TextView txtview;
    EditText edittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        txtview = (TextView) findViewById(R.id.txtview);
        edittext = (EditText) findViewById(R.id.editText);

        Bundle bundle = getIntent().getExtras();

        txtview.setMovementMethod(new ScrollingMovementMethod());

        if(bundle.getString("user")!= null)
        {
            useri = bundle.getString("user");
            Context context = getApplicationContext();
            Toast.makeText(context, "Welcome " + useri, Toast.LENGTH_LONG).show();
        }

        if(bundle.getString("user")!= null)
        {
            useri = bundle.getString("user");
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("posts");
        read();

    }

    public void read(){
        myRef.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                txtview.setText("");
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    String s = data.getValue().toString();

                    int startIndex = s.indexOf("message=");
                    startIndex = startIndex +8 ;
                    int endIndext = s.indexOf(", user=");
                    String message = s.substring(startIndex, endIndext);

                    int nameStartIndex = s.indexOf("user=");
                    nameStartIndex = nameStartIndex + 5;
                    int nameEndIndex = s.indexOf("}");
                    String username = s.substring(nameStartIndex,nameEndIndex);

                    txtview.append(username+": "+message+"\n");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("onCancelled", "Failed to read value.", error.toException());
            }
        });
    }
    public void write(View view){
        DatabaseReference newPostRef = myRef.push();
        newPostRef.setValue(new Post(useri, edittext.getText().toString()));
        edittext.setText("");
    }

    public static class Post {

        public String user;
        public String message;

        public Post(){

        }


        public Post(String user, String message) {
            this.user = user;
            this.message = message;
        }
    }
}
