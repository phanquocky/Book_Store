package com.hka.shop.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hka.shop.R;
import com.hka.shop.activity.DetailActivity;
import com.hka.shop.model.Item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {

    private ArrayList<Item> listItem;
    private LayoutInflater mInflater;
    private Context mContext;

    public ItemListAdapter(Context context, ArrayList<Item> listItem) {
        mInflater = LayoutInflater.from(context);
        this.listItem = listItem;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        String mCurrentName = listItem.get(position).getName();
        String mCurrentImage = listItem.get(position).getImage();
        String mCurrentPrice = listItem.get(position).getPrice();
        String mCurrentDescription = listItem.get(position).getDescription();
        int id = listItem.get(position).getId();

        holder.textViewItemName.setText(mCurrentName);
        holder.textViewItemPrice.setText(mCurrentPrice);
        holder.textViewItemDescription.setMaxLines(2);
        holder.textViewItemDescription.setEllipsize(TextUtils.TruncateAt.END);
        Picasso.get()
                .load(mCurrentImage)
                .fit()
                .into(holder.imageViewItem);

        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("name", mCurrentName);
                intent.putExtra("price", mCurrentPrice);
                intent.putExtra("image", mCurrentImage);
                intent.putExtra("description", mCurrentDescription);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        LinearLayout layoutItem;
        public ImageView imageViewItem;
        public TextView textViewItemName;
        public TextView textViewItemPrice;
        public TextView textViewItemDescription;
        ItemListAdapter mAdapter;

        public ItemViewHolder(@NonNull View itemView, ItemListAdapter adapter) {
            super(itemView);
            imageViewItem = itemView.findViewById(R.id.imageview_item);
            textViewItemName = itemView.findViewById(R.id.tv_name_item);
            textViewItemPrice = itemView.findViewById(R.id.tv_price);
            textViewItemDescription = itemView.findViewById(R.id.tv_description);
            mAdapter = adapter;
            layoutItem = (LinearLayout) itemView.findViewById(R.id.layout_item);
        }
    }


}
