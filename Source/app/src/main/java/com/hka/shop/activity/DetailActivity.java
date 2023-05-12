package com.hka.shop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.hka.shop.R;
import com.hka.shop.adapter.ItemListAdapter;
import com.hka.shop.model.Item;
import com.hka.shop.model.PurchasedItem;
import com.hka.shop.utils.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    ImageView imgView;
    TextView txtName;
    TextView txtPrice;
    TextView txtDescription;
    Spinner spinner;
    Button btnAddCart;

    int id ;
    String name ;
    String price;
    String image;
    String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("Quocky", "Oncreate Detail");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle("Chi tiết sản phẩm");
        imgView = (ImageView) this.findViewById(R.id.imageview_item);
        txtName = (TextView) this.findViewById(R.id.tv_name_item);
        txtPrice = (TextView) this.findViewById(R.id.tv_price);
        txtDescription = (TextView) this.findViewById(R.id.tv_description);

        spinner = (Spinner)this.findViewById(R.id.spinner);
        btnAddCart = (Button)this.findViewById(R.id.btn_addItem);

        Integer[] spinnerData = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, spinnerData );
        spinner.setAdapter(arrayAdapter);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("id");
            name = extras.getString("name");
            price = extras.getString("price");
            image = extras.getString("image");
            description = extras.getString("description");

            Log.e("Item", Integer.toString(id) + name + price );
            txtName.setText(name);
//            DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###");
            txtPrice.setText("Giá tiền" + price + "đ");
            txtDescription.setText(description);
            Picasso.get()
                    .load(image)
                    .fit()
                    .into(imgView);

        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean flag = false;
                int amount = Integer.parseInt(spinner.getSelectedItem().toString());
                if(MainActivity.listPurchasedItem.size() > 0){
                    for (int i = 0 ; i < MainActivity.listPurchasedItem.size() ; i++){
                        if(id == MainActivity.listPurchasedItem.get(i).getId()){
                            Log.e("id", Integer.toString(id));
                            MainActivity.listPurchasedItem.get(i).setAmount(amount);
                            flag = true;
                        }
                    }
                    if (flag == false){
                        Log.e("Quocky", "add one flag == false");
                        MainActivity.listPurchasedItem.add(new PurchasedItem(id, name, price, image, amount));
                    }
                }
                else{
                    Log.e("Quocky", "add one for first item");
                    MainActivity.listPurchasedItem.add(new PurchasedItem(id, name, price, image, amount));
                }

                Intent intent = new Intent(DetailActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });



//            if (isNetworkConnected() == true){
//                String url = Server.url + "query.php?id=" + itemID;
//                RequestQueue redQueue = Volley.newRequestQueue(getApplicationContext());
//                JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null,
//                        new Response.Listener<JSONArray>() {
//                            @Override
//                            public void onResponse(JSONArray response) {
//                                for (int i = 0; i < response.length(); i++) {
//                                    try {
//                                        JSONObject obj = response.getJSONObject(i);
//                                        int id = obj.getInt("id");
//                                        txtName.setText(obj.getString("item_name"));
//                                        txtPrice.setText(obj.getString("price"));
//                                        txtDescription.setText(obj.getString("description"));
//                                        Picasso.get()
//                                                .load(obj.getString("image"))
//                                                .fit()
//                                                .into(imgView);
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                });
//                redQueue.add(req);
//            } else{
//                Log.e("Internet", "Cannot connect to database");
//            }

        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}