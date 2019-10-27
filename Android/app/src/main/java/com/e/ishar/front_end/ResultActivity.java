package com.e.ishar.front_end;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        TextView result = (TextView)findViewById(R.id.res);
        TextView probability = (TextView)findViewById(R.id.prob);
        TextView conclu = (TextView)findViewById(R.id.result);
        TextView symp = (TextView)findViewById(R.id.symptoms);
        Intent intent = getIntent();
        String res = intent.getStringExtra("Response");
        String prob = intent.getStringExtra("Probability");
        result.setText("Result : "+ res);
        probability.setText("Probability : "+prob);
        double decide = Double.parseDouble(res);
        double score = Double.parseDouble(prob.substring(0,5));
        double mid;
        String end;
        if(decide == 1)
        {
            mid = score*100;
            end = "have cataract";
            symp.setVisibility(View.VISIBLE);
        }
        else
        {
            mid = 100 - (score*100);
            end = "do not have cataract";
        }

        String finalStr = "We are " + mid+ "% confident that you "+end;
        conclu.setText(finalStr);
//        Toast.makeText(this, "Result activity " + res + " " + prob, Toast.LENGTH_SHORT).show();
    }

}
