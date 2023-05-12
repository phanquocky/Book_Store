package com.hka.shop.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hka.shop.adapter.ItemListAdapter;
import com.hka.shop.R;
import com.hka.shop.model.Item;
import com.hka.shop.utils.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookActivity extends AppCompatActivity {

    private ArrayList<Item> listItem;
    private RecyclerView mRecyclerView;
    private ItemListAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        setTitle("Sách");
        if (isNetworkConnected() == true){
            listItem = new ArrayList<Item>();
            mAdapter = new ItemListAdapter(this, listItem);
            mRecyclerView = findViewById(R.id.recyclerviewbook);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://192.168.4.102:81/server/get_items.php?page=1";

//            String url = Server.url + "get_books.php";

            RequestQueue redQueue = Volley.newRequestQueue(getApplicationContext());
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.e("Quocky", "Onresponse");
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
                    Log.e("Quocky", "OnErrorResponse123");
                }
            });
            redQueue.add(req);
        } else{
            Log.e("Book", "Cannot connect to database");
        }

        listItem.add(new Item(1, "Sach 1", "https://kenh14cdn.com/203336854389633024/2021/1/3/photo-1-16096337476961612322578.jpg", "10000", "Xuất bản từ năm 1940 nhưng How To Read A Book vẫn luôn là một trong những cuốn sách bán chạy trong thời gian rất dài. Người đọc sách thông minh là người biết áp dụng các phương pháp và kỹ năng đọc khác nhau cho các loại văn bản khác nhau, để vừa thu được thông tin nhanh vừa hiểu thấu đáo các vấn đề được nêu, trong chừng mực thời gian cho phép và tùy theo mục đích của mình.\n" +
                "\n" +
                "Đọc sách không chỉ đọc cho sang, mà phải đọc để gặt hái lợi ích về cho xứng với thời gian đã bỏ ra. \"Đọc\" dưới bất kỳ hình thức nào cũng là một hoạt động. Vì thế, cho dù bạn đọc cái gì, ít nhiều cũng cần có tính tích cực. Người ta không thể đọc hoàn toàn thụ động, nghĩa là đọc mà mắt không di chuyển và đầu óc thì mơ màng"));
        listItem.add(new Item(2, "Sach 2", "https://kenh14cdn.com/203336854389633024/2021/1/3/photo-1-16096337476961612322578.jpg", "20000", "Xuất bản từ năm 1940 nhưng How To Read A Book vẫn luôn là một trong những cuốn sách bán chạy trong thời gian rất dài. Người đọc sách thông minh là người biết áp dụng các phương pháp và kỹ năng đọc khác nhau cho các loại văn bản khác nhau, để vừa thu được thông tin nhanh vừa hiểu thấu đáo các vấn đề được nêu, trong chừng mực thời gian cho phép và tùy theo mục đích của mình.\n" +
                "\n" +
                "Đọc sách không chỉ đọc cho sang, mà phải đọc để gặt hái lợi ích về cho xứng với thời gian đã bỏ ra. \"Đọc\" dưới bất kỳ hình thức nào cũng là một hoạt động. Vì thế, cho dù bạn đọc cái gì, ít nhiều cũng cần có tính tích cực. Người ta không thể đọc hoàn toàn thụ động, nghĩa là đọc mà mắt không di chuyển và đầu óc thì mơ màng"));
        listItem.add(new Item(3, "Sach 3", "https://kenh14cdn.com/203336854389633024/2021/1/3/photo-1-16096337476961612322578.jpg", "30000", "Xuất bản từ năm 1940 nhưng How To Read A Book vẫn luôn là một trong những cuốn sách bán chạy trong thời gian rất dài. Người đọc sách thông minh là người biết áp dụng các phương pháp và kỹ năng đọc khác nhau cho các loại văn bản khác nhau, để vừa thu được thông tin nhanh vừa hiểu thấu đáo các vấn đề được nêu, trong chừng mực thời gian cho phép và tùy theo mục đích của mình.\n" +
                "\n" +
                "Đọc sách không chỉ đọc cho sang, mà phải đọc để gặt hái lợi ích về cho xứng với thời gian đã bỏ ra. \"Đọc\" dưới bất kỳ hình thức nào cũng là một hoạt động. Vì thế, cho dù bạn đọc cái gì, ít nhiều cũng cần có tính tích cực. Người ta không thể đọc hoàn toàn thụ động, nghĩa là đọc mà mắt không di chuyển và đầu óc thì mơ màng"));
        listItem.add(new Item(4, "Sach 4", "https://kenh14cdn.com/203336854389633024/2021/1/3/photo-1-16096337476961612322578.jpg", "40000", "Xuất bản từ năm 1940 nhưng How To Read A Book vẫn luôn là một trong những cuốn sách bán chạy trong thời gian rất dài. Người đọc sách thông minh là người biết áp dụng các phương pháp và kỹ năng đọc khác nhau cho các loại văn bản khác nhau, để vừa thu được thông tin nhanh vừa hiểu thấu đáo các vấn đề được nêu, trong chừng mực thời gian cho phép và tùy theo mục đích của mình.\n" +
                "\n" +
                "Đọc sách không chỉ đọc cho sang, mà phải đọc để gặt hái lợi ích về cho xứng với thời gian đã bỏ ra. \"Đọc\" dưới bất kỳ hình thức nào cũng là một hoạt động. Vì thế, cho dù bạn đọc cái gì, ít nhiều cũng cần có tính tích cực. Người ta không thể đọc hoàn toàn thụ động, nghĩa là đọc mà mắt không di chuyển và đầu óc thì mơ màng"));
        listItem.add(new Item(5, "Sach 5", "https://kenh14cdn.com/203336854389633024/2021/1/3/photo-1-16096337476961612322578.jpg", "50000", "Xuất bản từ năm 1940 nhưng How To Read A Book vẫn luôn là một trong những cuốn sách bán chạy trong thời gian rất dài. Người đọc sách thông minh là người biết áp dụng các phương pháp và kỹ năng đọc khác nhau cho các loại văn bản khác nhau, để vừa thu được thông tin nhanh vừa hiểu thấu đáo các vấn đề được nêu, trong chừng mực thời gian cho phép và tùy theo mục đích của mình.\n" +
                "\n" +
                "Đọc sách không chỉ đọc cho sang, mà phải đọc để gặt hái lợi ích về cho xứng với thời gian đã bỏ ra. \"Đọc\" dưới bất kỳ hình thức nào cũng là một hoạt động. Vì thế, cho dù bạn đọc cái gì, ít nhiều cũng cần có tính tích cực. Người ta không thể đọc hoàn toàn thụ động, nghĩa là đọc mà mắt không di chuyển và đầu óc thì mơ màng"));
        listItem.add(new Item(6, "Sach 6", "https://kenh14cdn.com/203336854389633024/2021/1/3/photo-1-16096337476961612322578.jpg", "60000", "Xuất bản từ năm 1940 nhưng How To Read A Book vẫn luôn là một trong những cuốn sách bán chạy trong thời gian rất dài. Người đọc sách thông minh là người biết áp dụng các phương pháp và kỹ năng đọc khác nhau cho các loại văn bản khác nhau, để vừa thu được thông tin nhanh vừa hiểu thấu đáo các vấn đề được nêu, trong chừng mực thời gian cho phép và tùy theo mục đích của mình.\n" +
                "\n" +
                "Đọc sách không chỉ đọc cho sang, mà phải đọc để gặt hái lợi ích về cho xứng với thời gian đã bỏ ra. \"Đọc\" dưới bất kỳ hình thức nào cũng là một hoạt động. Vì thế, cho dù bạn đọc cái gì, ít nhiều cũng cần có tính tích cực. Người ta không thể đọc hoàn toàn thụ động, nghĩa là đọc mà mắt không di chuyển và đầu óc thì mơ màng"));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
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


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}