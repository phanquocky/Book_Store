package com.hka.shop.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hka.shop.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InformationActivity extends AppCompatActivity {

    EditText editTextName ;
    EditText editTextPhone;
    EditText editTextAddress;

    Button buttonBack;
    Button buttonConfirm;

    String name;
    String phone;
    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        mapping();
        handleClick();
    }

    private void handleClick() {
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = editTextName.getText().toString().trim();
                phone = editTextPhone.getText().toString().trim();
                address = editTextAddress.getText().toString().trim();

                if (name.length() > 0 && phone.length() > 0 && address.length() > 0){

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    String url = "http://192.168.4.107:81/server/insert_customer.php";

                    StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String id_customer) {
                            boolean check = true;
                            if (id_customer.equals("-1") ){
                                Toast.makeText(InformationActivity.this, "Lỗi insert vào hệ thống", Toast.LENGTH_SHORT).show();
                            }
                            else{

                                String url2 = "http://192.168.4.107:81/server/insert_order.php";
                                StringRequest req2 = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(response.equals("1")){
                                            Toast.makeText(InformationActivity.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                                            MainActivity.listPurchasedItem.clear();
                                            MainActivity.listUnchecked.clear();
                                            MainActivity.totalPrice = 0l;
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);

                                        }
                                        else{
                                            Toast.makeText(InformationActivity.this, "Lỗi insert vào hệ thống", Toast.LENGTH_SHORT).show();
                                        }
                                        Log.e("insert order", response);
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(InformationActivity.this, "Lỗi đường truyền mạng", Toast.LENGTH_SHORT).show();

                                    }
                                }){
                                    @Nullable
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for (int i = 0 ; i < MainActivity.listPurchasedItem.size(); i ++){
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("id_product", Integer.toString(MainActivity.listPurchasedItem.get(i).getId()));
                                                jsonObject.put("id_customer", id_customer);
                                                jsonObject.put("amount", Integer.toString(MainActivity.listPurchasedItem.get(i).getAmount()));
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }

                                        HashMap<String, String> hashMap = new HashMap<String, String>();
                                        hashMap.put("json", jsonArray.toString());
                                        return hashMap;
                                    }
                                };

                                requestQueue.add(req2);
                            }
                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(InformationActivity.this, "Lỗi đường truyền mạng", Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put("customer_name", name);
                            hashMap.put("phone", phone);
                            hashMap.put("address", address);
                            return hashMap;
                        }
                    };

                    requestQueue.add(req);

                }
                else{
                    Toast.makeText( getApplicationContext(), "Bạn cần điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void mapping() {
        editTextName = (EditText) findViewById(R.id.edittext_name);
        editTextPhone = (EditText) findViewById(R.id.edittext_phone);
        editTextAddress = (EditText) findViewById(R.id.edittext_address);

        buttonBack = (Button) findViewById(R.id.btn_back);
        buttonConfirm = (Button) findViewById(R.id.btn_confirm);
    }
}