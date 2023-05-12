package com.hka.shop.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.hka.shop.R;
import com.hka.shop.adapter.ItemListAdapter;
import com.hka.shop.model.Item;
import com.hka.shop.utils.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FashionActivity extends AppCompatActivity {

    private ArrayList<Item> listItem;
    private RecyclerView mRecyclerView;
    private ItemListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fashion);
        setTitle("Thời trang");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getItems();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getItems() {
        if (isNetworkConnected() == true){
            listItem = new ArrayList<Item>();
            mAdapter = new ItemListAdapter(this, listItem);
            mRecyclerView = findViewById(R.id.recyclerviewfashion);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            String url = Server.url + "get_fashions.php";
            RequestQueue redQueue = Volley.newRequestQueue(getApplicationContext());
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject obj = response.getJSONObject(i);
                                    int id = obj.getInt("id");
                                    String name = obj.getString("item_name");
                                    String price = obj.getString("price");
                                    String image = obj.getString("image");
                                    String desc = obj.getString("description");
                                    listItem.add(new Item(id, name, image, price, desc));
                                    mAdapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            redQueue.add(req);
        } else{
            Log.e("Book", "Cannot connect to database");
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}