package com.example.plagiarismdetection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DocumentCompareActivityLoading extends AppCompatActivity {

    public int getImage(String imageName) {

        int drawableResourceId = this.getResources().getIdentifier(imageName, "drawable", this.getPackageName());

        return drawableResourceId;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_compare_loading);
        Glide.with(getBaseContext()).load(getImage("loading")).into((ImageView)findViewById(R.id.imageView));
        Bundle extras = getIntent ().getExtras ();
        // Log.d("api_params0", extras.getString ("doc0").toString());
        // Log.d("api_params1", extras.getString ("doc1").toString());

        RequestQueue queue = Volley.newRequestQueue (this);
        String url = getString (R.string.api_root) + "/document_similarity";
        Log.d("api_url", url);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getBaseContext(), "Worked", Toast.LENGTH_LONG).show();
                        Log.d ("api_call", response);
                        Intent i = new Intent (DocumentCompareActivityLoading.this, ResultActivity.class);
                        i.putExtra ("doc0", extras.getString ("doc0").toString ());
                        i.putExtra ("doc1", extras.getString ("doc1").toString ());
                        i.putExtra ("result", response);
                        startActivity(i);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_LONG).show();
                Log.d("api_error", error.toString());
            }
        })
        {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put ("doc_0", extras.getString ("doc0").toString());
                params.put ("doc_1", extras.getString ("doc1").toString());
                Log.d("api_params0", extras.getString ("doc0"));
                Log.d("api_params1", extras.getString ("doc1"));
                return params;
            }
        };
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 500000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 500000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
                Toast.makeText(getBaseContext(), "Timed Out!", Toast.LENGTH_LONG).show ();
                Log.d("api_timeout_error", error.toString());
            }
        });

        queue.add (stringRequest);
    }
}