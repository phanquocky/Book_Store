package com.hka.shop.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hka.shop.R;
import com.hka.shop.activity.CartActivity;
import com.hka.shop.activity.MainActivity;
import com.hka.shop.model.PurchasedItem;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CartListAdapter extends BaseAdapter {

    private ArrayList<PurchasedItem> listPurchasedItem;
    private Activity activity;
    TextView textViewTotalPrice;

    public CartListAdapter(Activity activity, ArrayList<PurchasedItem> listPurchasedItem, TextView textViewTotalPrice) {
        this.listPurchasedItem = listPurchasedItem;
        this.activity = activity;
        this.textViewTotalPrice = textViewTotalPrice;
    }

    @Override
    public int getCount() {
        return listPurchasedItem.size();
    }

    @Override
    public Object getItem(int i) {
        return listPurchasedItem.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view;
        if (convertView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            view = inflater.inflate(R.layout.cart_layout, null);
        }
        else view = convertView;
        ImageView imageView = view.findViewById(R.id.image_view_cart);
        TextView textViewName = view.findViewById(R.id.tv_name_cart);
        TextView textViewAmount = view.findViewById(R.id.tv_amount_cart);
        TextView textViewPrice = view.findViewById(R.id.tv_price_cart);
        CheckBox checkBox = view.findViewById(R.id.checkbox);



        Picasso.get()
                .load(listPurchasedItem.get(i).getImage())
                .fit()
                .into(imageView);
        textViewName.setText(listPurchasedItem.get(i).getName());
        textViewAmount.setText("Số lượng: " + Integer.toString(listPurchasedItem.get(i).getAmount()));

        int amount = listPurchasedItem.get(i).getAmount();
        int price = Integer.parseInt(listPurchasedItem.get(i).getPrice());
        Long totalPriceOfItem = Integer.toUnsignedLong(amount)*price;
        textViewPrice.setText("Giá: " + totalPriceOfItem.toString());

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b == false){
                    MainActivity.listUnchecked.add(i);
                    int price = Integer.parseInt(MainActivity.listPurchasedItem.get(i).getPrice());
                    MainActivity.totalPrice -= price * MainActivity.listPurchasedItem.get(i).getAmount();
                }else{
                    if (MainActivity.listUnchecked.contains(i)) MainActivity.listUnchecked.remove(i);
                    int price = Integer.parseInt(MainActivity.listPurchasedItem.get(i).getPrice());
                    MainActivity.totalPrice += price * MainActivity.listPurchasedItem.get(i).getAmount();
                }

                Log.e("Quocky", MainActivity.listUnchecked.toString());
                textViewTotalPrice.setText(MainActivity.totalPrice.toString());

            }
        });



        return view;
    }
}
