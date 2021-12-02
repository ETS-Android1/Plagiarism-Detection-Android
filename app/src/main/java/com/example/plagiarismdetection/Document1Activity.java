package com.example.plagiarismdetection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

public class Document1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document1);

        Button button = (Button) findViewById (R.id.buttonDoc0);
        EditText editText = (EditText) findViewById (R.id.editTextDoc0);
        /*
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("key");
            //The key argument here must match that used in the other activity
        }
        */

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().equals("")) {
                    Toast.makeText(getBaseContext(), "Please Enter Text", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i = new Intent (Document1Activity.this, Document2Activity.class);
                i.putExtra ("doc0", editText.getText().toString());
                startActivity(i);
            }
        });


    }
}