package com.e.ishar.front_end;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String res = intent.getStringExtra("Response");
        Toast.makeText(this, "Result activity" + res, Toast.LENGTH_SHORT).show();
    }

}
