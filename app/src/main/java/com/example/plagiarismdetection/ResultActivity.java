package com.example.plagiarismdetection;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Debug;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle extras = getIntent ().getExtras ();
        String doc0 = extras.getString ("doc0").toString ();
        String doc1 = extras.getString ("doc1").toString ();
        String result = extras.getString ("result").toString ();

        TextView doc0View = (TextView) findViewById (R.id.doc0);
        TextView doc1View = (TextView) findViewById (R.id.doc1);

        doc0View.setText (doc0);
        doc1View.setText (doc1);

        try {
            JSONObject json = new JSONObject (result);
            String doc_similarity = json.getString ("doc_similarity");
            Log.d("Document Similarity", doc_similarity);
            JSONArray jsonArray = json.getJSONArray ("sen_similarity");
            int length = jsonArray.length ();
            // Log.d ("api_sorted_size", String.valueOf (length));
            List<List<Double> > similarityValues = new ArrayList<List<Double> >();

            for (int i = 0; i < length; i++) {
                JSONArray row = jsonArray.getJSONArray (i);
                double similarity = row.getDouble(0);

                JSONArray doc_0 = row.getJSONArray(1);
                int start_0 = doc_0.getInt (0);
                int end_0 = doc_0.getInt (1);

                JSONArray doc_1 = row.getJSONArray(2);
                int start_1 = doc_1.getInt (0);
                int end_1 = doc_1.getInt (1);

                List<Double> similarityValue = new ArrayList <Double> ();
                similarityValue.add (similarity);
                similarityValue.add ((double) start_0);
                similarityValue.add ((double) end_0);
                similarityValue.add ((double) start_1);
                similarityValue.add ((double) end_1);
                similarityValues.add (i, similarityValue);//new ArrayList <Double> ();
            }
            java.util.Collections.sort(similarityValues, new java.util.Comparator<List<Double>> () {
                public int compare(List<Double> a, List<Double> b) {
                    return a.get(0).compareTo(b.get(0));
                }
            });
            Spannable wordtoSpandoc0 = new SpannableString(doc0);
            Spannable wordtoSpandoc1 = new SpannableString(doc1);
            for (List<Double> row : similarityValues) {
                double similarity = row.get (0);
                int start_0 = row.get (1).intValue ();
                int end_0 = row.get (2).intValue ();
                int start_1 = row.get (3).intValue ();
                int end_1 = row.get (4).intValue ();
                if (similarity > 0.75) {
                    wordtoSpandoc0.setSpan(new BackgroundColorSpan(Color.RED), start_0, end_0, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    wordtoSpandoc1.setSpan(new BackgroundColorSpan(Color.RED), start_1, end_1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                else if (similarity > 0.5 && similarity < 0.75) {
                    wordtoSpandoc0.setSpan(new BackgroundColorSpan(Color.BLUE), start_0, end_0, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    wordtoSpandoc1.setSpan(new BackgroundColorSpan(Color.BLUE), start_1, end_1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                else if (similarity < 0.5 && similarity > 0.25) {
                    wordtoSpandoc0.setSpan(new ForegroundColorSpan(Color.GREEN), start_0, end_0, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    wordtoSpandoc1.setSpan(new ForegroundColorSpan(Color.GREEN), start_1, end_1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
            doc0View.setText(wordtoSpandoc0);
            doc1View.setText(wordtoSpandoc1);
        }
        catch (JSONException e) {
            e.printStackTrace ();
        }
    }
}