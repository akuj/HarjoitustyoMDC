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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    DatabaseReference myRef;
    String TAG = "hei";
    String useri;
    TextView txtview;
    EditText edittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        txtview = (TextView) findViewById(R.id.txtview);
        edittext = (EditText) findViewById(R.id.editText);
        Log.d(TAG, "onCreate: heijjsan");
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

        myRef.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Object o = dataSnapshot.getValue();

                Log.d(TAG, "onDataChange: "+o);
                Map<String, Object> value = (Map<String, Object>) o;
                Log.d(TAG, "onDataChange: map "+value);
                //Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();
                Collection asdf =value.values();
                Log.d(TAG, "onDataChange: testing"+asdf);
                for (Object oo:asdf) {
                    String s= oo.toString();
                    Log.d(TAG, "onDataChange: s"+s);
                   int startIndex = s.indexOf("message=");
                    startIndex = startIndex +8 ;
                    //Log.d(TAG, "onDataChange: startindex"+startIndex);
                    int endIndext = s.indexOf(", user=");
                    //Log.d(TAG, "onDataChange: endIndex"+endIndext);
                    String message = s.substring(startIndex, endIndext);
                    Log.d(TAG, "onDataChange: Message: "+message);

                    int nameStartIndex = s.indexOf("user=");
                    nameStartIndex = nameStartIndex + 5;
                    int nameEndIndex = s.indexOf("}");
                    String username = s.substring(nameStartIndex,nameEndIndex);
                    //s.substring()
                    txtview.append(""+username+": "+message+"\n");

                }

                //txtview.append(value+"\n");
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
        txtview.setText("");
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
