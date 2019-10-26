package com.e.ishar.front_end;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UploadActivity extends AppCompatActivity {

    private Button Uploadt,ChooseBt;
    private ImageView imgView;
    private final int IMG_REQUEST = 1;
    private Bitmap bitmap;
    private String uploadUrl= "http://192.168.43.179:5000/success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        ChooseBt = (Button)findViewById(R.id.chBt);
        Uploadt = (Button)findViewById(R.id.uploadBt);
        imgView = (ImageView)findViewById((R.id.imgView));

    }

    public void openGalleryAndChoose(View view)
    {
        Intent intent =  new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null )
        {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                imgView.setImageBitmap(bitmap);
                imgView.setVisibility(View.VISIBLE);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void uploadImage(View view)
    {
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, uploadUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Toast.makeText(UploadActivity.this,"response is " + response , Toast.LENGTH_LONG).show();
                            JSONObject jsonObject = new JSONObject(response);
                            String resp = jsonObject.getString("res");
//                            Toast.makeText(UploadActivity.this, "Other " + resp, Toast.LENGTH_SHORT).show();
                            imgView.setImageResource(0);
                            imgView.setVisibility(View.GONE);

                            Intent intent = new Intent(UploadActivity.this,ResultActivity.class);
                            intent.putExtra("Response",resp);
                            startActivity(intent);

                        } catch (JSONException e) {
//                            Toast.makeText(MainActivity.this, "error occured here", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error occured", Toast.LENGTH_LONG).show();
            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("image",imageToString(bitmap));
                return params;
            }
        };

        MySingleton.getmInstance(UploadActivity.this).addToRequestQue(stringRequest);
        Toast.makeText(this,"Made the request", Toast.LENGTH_LONG).show();
    }


    private String imageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);
    }
}
