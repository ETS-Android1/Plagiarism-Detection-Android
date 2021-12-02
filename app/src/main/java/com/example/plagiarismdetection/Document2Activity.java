package com.example.plagiarismdetection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Document2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document2);

        Bundle extras = getIntent().getExtras();
        Button button = (Button) findViewById (R.id.buttonDoc1);
        EditText editText = (EditText) findViewById (R.id.editTextDoc1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().equals("")) {
                    //Toast.makeText(this, 0, "0.1");
                    Toast.makeText(getBaseContext(), "Please Enter Text", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i = new Intent (Document2Activity.this, DocumentCompareActivityLoading.class);
                i.putExtra ("doc0", extras.getString ("doc0"));
                i.putExtra ("doc1", editText.getText().toString ());
                startActivity(i);
            }
        });
    }
}