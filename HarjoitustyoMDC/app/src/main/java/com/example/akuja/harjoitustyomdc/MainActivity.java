package com.example.akuja.harjoitustyomdc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText edittext;
    TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edittext = (EditText) findViewById(R.id.editText);
    }

    public void enter(View view){
        String username = edittext.getText().toString();

        Intent intent = new Intent(MainActivity.this, ChatActivity.class);
        intent.putExtra("user" ,username);
        startActivity(intent);
    }
}
