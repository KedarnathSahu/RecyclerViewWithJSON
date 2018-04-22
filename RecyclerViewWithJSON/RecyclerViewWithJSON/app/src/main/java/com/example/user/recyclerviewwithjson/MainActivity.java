package com.example.user.recyclerviewwithjson;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private static final String URL_DATA="https://api.myjson.com/bins/1fgll7";
    private RecyclerView recyclerView;

    private ArrayList<ListItem> listItems;

    MyAdapter adapter;

    android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        listItems=new ArrayList<>();

        /*for(int i=0;i<10;i++){
            ListItem listItem=new ListItem("HEADING "+(i+1),"Description");
            listItems.add(listItem);
        }
        adapter=new MyAdapter(listItems,this);
        recyclerView.setAdapter(adapter);*/

        loadRecyclerViewData();
    }

    private void loadRecyclerViewData() {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONArray jsonArray=new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject o = jsonArray.getJSONObject(i);
                                ListItem item=new ListItem(o.getString("name"),o.getString("bio"),o.getString("imageurl"));
                                listItems.add(item);
                            }
                            adapter=new MyAdapter(listItems,getApplicationContext());
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"ERROR: "+error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items,menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(MainActivity.this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText=newText.toLowerCase();
        ArrayList<ListItem> newList=new ArrayList<>();
        for(ListItem listItem : listItems){
            String name=listItem.getHead().toLowerCase();
            if(name.contains(newText)){
                newList.add(listItem);
            }
        }
        adapter.setFilter(newList);
        return true;
    }
}