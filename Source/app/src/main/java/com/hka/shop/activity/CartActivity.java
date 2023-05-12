package com.hka.shop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hka.shop.R;
import com.hka.shop.adapter.CartListAdapter;

public class CartActivity extends AppCompatActivity {

    ListView listViewCart;
    TextView tvCart;

    TextView tvTotalPrice;
    Button buttonBuy;
    Button buttonPay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        tvCart = (TextView)findViewById(R.id.textview_cart);
        buttonBuy = (Button)findViewById(R.id.button_buy);
        buttonPay = (Button)findViewById(R.id.button_pay);

        tvTotalPrice = (TextView) findViewById(R.id.tv_total_price);
        MainActivity.totalPrice = calcTotalPrice();
        tvTotalPrice.setText(MainActivity.totalPrice.toString());

        if(MainActivity.listPurchasedItem.size() > 0) tvCart.setText("");
        else tvCart.setText("Giỏ hàng đang trống");
        listViewCart = (ListView) findViewById(R.id.listview_cart);

        Log.e("size", Integer.toString(MainActivity.listPurchasedItem.size()));

        CartListAdapter adapter = new CartListAdapter(this, MainActivity.listPurchasedItem, tvTotalPrice);
        listViewCart.setAdapter(adapter);


        handleOnClick();


    }

    private void handleOnClick() {
        buttonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        buttonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.listPurchasedItem.size() == 0){
                    Toast.makeText(getApplicationContext(),"Giỏ hàng đang trống",Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), InformationActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        MainActivity.listUnchecked.clear();
        Log.e("Clear", MainActivity.listPurchasedItem.toString());
        super.onPause();
    }

    private Long calcTotalPrice() {
        Long totalPrice = 0l;
        for (int i = 0 ; i < MainActivity.listPurchasedItem.size(); i ++){
            int price = Integer.parseInt(MainActivity.listPurchasedItem.get(i).getPrice());
            totalPrice += price * MainActivity.listPurchasedItem.get(i).getAmount();
        }

        return totalPrice;
    }
}