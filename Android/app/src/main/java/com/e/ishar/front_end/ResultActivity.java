package com.e.ishar.front_end;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        TextView txt1 = (TextView)findViewById(R.id.res);
        TextView txt2 = (TextView)findViewById(R.id.prob);
        Intent intent = getIntent();
        String res = intent.getStringExtra("Response");
        String prob = intent.getStringExtra("Probability");
        txt1.setText("Result "+ res);
        txt2.setText("Probability "+prob);
        Toast.makeText(this, "Result activity " + res + " " + prob, Toast.LENGTH_SHORT).show();
    }

}
