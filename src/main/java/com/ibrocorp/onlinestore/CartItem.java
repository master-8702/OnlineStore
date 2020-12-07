package com.ibrocorp.onlinestore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.nex3z.notificationbadge.NotificationBadge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CartItem extends AppCompatActivity implements com.ibrocorp.onlinestore.MainAdapter.onItemClickListener {

    private static final String URL_DATA="https://store-api.glitch.me/api/products";

    RecyclerView rv;
    ArrayList<MainModel> mainmodels;
    MainAdapter mainadapter;
    TextView tv;
    Button btnContinue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);

        rv=findViewById(R.id.rvCart);
        tv=findViewById(R.id.tv_NumberOfCartItems);
        mainmodels=new ArrayList<>();
        btnContinue=findViewById(R.id.btnContinue);
        loadRecyclerViewData();

     btnContinue.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Toast.makeText(CartItem.this,"Your Order Has Been Sent Successfully",Toast.LENGTH_LONG).show();
             GlobalClass.clearCartItemCounter();
             Intent intent=new Intent(CartItem.this,MainActivity.class);
             startActivity(intent);

         }
     });
    }

    private void loadRecyclerViewData() {
        //until the data from the internet is fetched
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();

        //fetching data from the internet using volley
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, URL_DATA,null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                progressDialog.dismiss();

                    for (MainModel m:GlobalClass.cartLists) {
                        mainmodels.add(m);
                    }


                LinearLayoutManager layoutManger=new LinearLayoutManager(CartItem.this,LinearLayoutManager.VERTICAL,false);
                rv.setLayoutManager(layoutManger);
                rv.setItemAnimator(new DefaultItemAnimator());
                mainadapter=new MainAdapter(CartItem.this,mainmodels,getLocalClassName());
                mainadapter.setOnItemClickListener(CartItem.this);
                rv.setAdapter(mainadapter);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(CartItem.this,error.getMessage(),Toast.LENGTH_SHORT);

            }
        });
        //adding the request string to the request queue

        requestQueue.add(jsonArrayRequest);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }


    public void onClick(View view) {
        switch (view.getId()){

            case R.id.filter:
                mainmodels.remove(2);
                mainadapter.notifyItemRemoved(3);
                mainadapter.notifyItemRangeChanged(3,mainmodels.size());


        }
    }

    @Override
    public void onItemClick(int position) {
      //  Toast.makeText(CartItem.this,"Index "+position+" is selected",Toast.LENGTH_SHORT).show();

            //to remove the clicked item by using the position as the index of the ArrayList
            //and notifying the adapter about the changes that we made
            mainmodels.remove(position);
            mainadapter.notifyItemRemoved(position);
            mainadapter.notifyItemRangeChanged(position,mainmodels.size());
            GlobalClass.deleteModel(position);   //applying the modification (deletion) of the item on the global class



    }
}
