package com.hka.shop.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.hka.shop.adapter.ItemListAdapter;
import com.hka.shop.model.Item;

import com.hka.shop.R;
import com.hka.shop.model.PurchasedItem;
import com.hka.shop.utils.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnBook;
    Button btnElectronic;
    Button btnFashion;
    Button btnConsumer;

    ViewFlipper viewFlipper;

    private ArrayList<Item> listItem;
    private RecyclerView mRecyclerView;
    private ItemListAdapter mAdapter;

    public static ArrayList<PurchasedItem> listPurchasedItem;
    public static Long totalPrice = 0l;
    public static ArrayList<Integer> listUnchecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapping();
        initViewFlipper();
        handleButton();
        getNewestProducts();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.ic_cart_foreground);
//        actionBar.setIcon(R.mipmap.ic_cart_foreground);
//        actionBar.setCustomView(imageView);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT, Gravity.RIGHT
                | Gravity.CENTER_VERTICAL);
        layoutParams.rightMargin = 20;

        imageView.setLayoutParams(layoutParams);
        actionBar.setCustomView(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void mapping() {

        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        if (listPurchasedItem == null) listPurchasedItem = new ArrayList<PurchasedItem>();
        if (listUnchecked == null) listUnchecked = new ArrayList<Integer>();

    }

    private void initViewFlipper() {
        ArrayList<String> bannerURLs = new ArrayList<String>();
        bannerURLs.add("https://i.ibb.co/t26K0GL/banner0.jpg");
        bannerURLs.add("https://i.ibb.co/bJTMTXq/banner1.jpg");
        for (String url : bannerURLs) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.get().load(url).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation animIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        Animation animOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
        viewFlipper.setInAnimation(animIn);
        viewFlipper.setOutAnimation(animOut);
    }

    private void handleButton(){
        btnBook = (Button) findViewById(R.id.btn_book);
        btnElectronic = (Button) findViewById(R.id.btn_electronic);
        btnFashion = (Button) findViewById(R.id.btn_fashion);
        btnConsumer =(Button) findViewById(R.id.btn_consumer);

        btnBook.setOnClickListener(this);
        btnElectronic.setOnClickListener(this);
        btnFashion.setOnClickListener(this);
        btnConsumer.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        Intent intent;
        switch (view.getId())
        {
            case R.id.btn_book:
            {
                intent = new Intent(MainActivity.this, BookActivity.class);
                break;
            }

            case R.id.btn_electronic:
            {
                intent = new Intent(MainActivity.this, ElectronicActivity.class);
                break;
            }
            case R.id.btn_fashion:
            {
                intent = new Intent(MainActivity.this, FashionActivity.class);
                break;
            }
            case R.id.btn_consumer:
            {
                intent = new Intent(MainActivity.this, ConsumerActivity.class);
                break;
            }
            //handle multiple view click events
            default:
                Log.e("default", "default button");
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
        startActivity(intent);
    }

    private void getNewestProducts() {
        if (isNetworkConnected() == true){
            listItem = new ArrayList<Item>();
            mAdapter = new ItemListAdapter(this, listItem);
            mRecyclerView = findViewById(R.id.recyclerviewnewest);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            String url = Server.url + "get_newest.php";
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