package com.team02.rentaccommodation.rentaccommodation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PostAccommodation extends AppCompatActivity {
    private Button btnPost, btnCancel;
    private EditText txtTitle, txtDescription, txtAddress, txtDistrict, txtPrice;
    private ProgressBar loading;
    private static String URL_POSTACCOM = "https://rentk21t2.000webhostapp.com/postaccommodation.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_post_accommodation );
         btnPost = (Button) findViewById(R.id.btnPost);
         btnCancel = (Button) findViewById(R.id.btnCancel);
         txtTitle = (EditText) findViewById(R.id.txtTitle);
         txtDescription = (EditText) findViewById(R.id.txtDescription);
         txtAddress = (EditText) findViewById(R.id.txtAddress);
         txtDistrict = (EditText) findViewById(R.id.txtDistrict);
         txtPrice = (EditText) findViewById(R.id.txtPrice);
         loading = (ProgressBar) findViewById(R.id.pbLoading);

         btnPost.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 PostAccom();
             }
         });
    }
    private void PostAccom(){
        loading.setVisibility(View.VISIBLE);
        btnPost.setVisibility(View.GONE);

        final String title = txtTitle.getText().toString().trim();
        final String description = txtDescription.getText().toString().trim();
        final String address = txtAddress.getText().toString().trim();
        final String district = txtDistrict.getText().toString().trim();
        final String account_id = "1";
        final String price = txtPrice.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_POSTACCOM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                Toast.makeText(PostAccommodation.this, "Post Success!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(PostAccommodation.this, "Post Fail! " + e.toString(), Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.VISIBLE);
                            btnPost.setVisibility(View.GONE);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PostAccommodation.this, "Post Fail! " + error.toString(), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.VISIBLE);
                        btnPost.setVisibility(View.GONE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("title",title);
                params.put("description",description);
                params.put("address",address);
                params.put("account_id",account_id);
                params.put("district",district);
                params.put("price",price);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
