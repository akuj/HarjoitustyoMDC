package com.example.akuja.harjoitustyomdc;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText edittext;
    String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edittext = (EditText) findViewById(R.id.editText);
    }

    public void enter(View view){
        username = edittext.getText().toString();

        if(TextUtils.isEmpty(username.trim())){
            Context context = getApplicationContext();
            Toast.makeText(context, "Enter username", Toast.LENGTH_LONG).show();
        }
        else{
            Intent intent = new Intent(MainActivity.this, ChatActivity.class);
            intent.putExtra("user" ,username);
            startActivity(intent);
        }
    }
}