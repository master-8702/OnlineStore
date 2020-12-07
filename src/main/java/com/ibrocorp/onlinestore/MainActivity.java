package com.ibrocorp.onlinestore;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.ActionMenuView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements com.ibrocorp.onlinestore.MainAdapter.onItemClickListener {

    private static final String URL_DATA="https://store-api.glitch.me/api/products";
    public static final String EXTRA_ID="id";
    public static final String EXTRA_NAME="nom";
    public static final String EXTRA_PRICE="price";
    public static final String EXTRA_CURRENCY="currency";
    public static final String EXTRA_URL="imageUrl";
    RecyclerView rv,rv2;
    ArrayList<MainModel>  mainmodels;
    MainAdapter mainadapter;
    MenuItem menuItem;
    TextView bagdeCounter;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv=findViewById(R.id.recycler_view);
        rv2=findViewById(R.id.recycler_view2);
        mainmodels=new ArrayList<>();
        loadRecyclerViewData();


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

                try {


                    for (int i=0;i<response.length();i++){
                        JSONObject itemobject=response.getJSONObject(i);

                        MainModel model=new MainModel(
                                itemobject.getInt("id"), itemobject.getString("nom"), itemobject.getDouble("price"), itemobject.getString("currency"),
                                itemobject.getString("imageUrl"));
                        mainmodels.add(model);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
                LinearLayoutManager layoutManger=new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false);
                rv.setLayoutManager(layoutManger);
                rv.setItemAnimator(new DefaultItemAnimator());
                mainadapter=new MainAdapter(MainActivity.this,mainmodels,getLocalClassName());
                mainadapter.setOnItemClickListener(MainActivity.this);
                rv.setAdapter(mainadapter);

                rv2.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
                rv2.setItemAnimator(new DefaultItemAnimator());
                rv2.setAdapter(mainadapter);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
        //adding the request string to the request queue

        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        menuItem=menu.findItem(R.id.cart);
        if(GlobalClass.cartItemCounter==0){
            menuItem.setActionView(null);
        }else{
            menuItem.setActionView(R.layout.toolbar_notification_icon);
            View view=menuItem.getActionView();
            bagdeCounter=view.findViewById(R.id.tv_NumberOfCartItems);
            bagdeCounter.setText(String.valueOf(GlobalClass.getCartItemCounter()));
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

        switch (item.getItemId()) {
            case R.id.cart:
                if(GlobalClass.cartLists.size() !=0){
                Intent i2 = new Intent(MainActivity.this, CartItem.class);
                startActivity(i2);}
                else Toast.makeText(MainActivity.this,"Your Cart Is Empty",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.notification:
                Toast.makeText(MainActivity.this, "Notification is selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.filter:
                Toast.makeText(MainActivity.this, "Filter is selected", Toast.LENGTH_SHORT).show();
                return  true;
            default:
                Toast.makeText(MainActivity.this, id+" is selected", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item); // the default is false

        }

    }


    public void onClick(View view) {
        new Throwable().printStackTrace();
//        switch (view.getId()){
//            case R.id.imageViewMain:
//                new Throwable().printStackTrace();
//                Intent i=new Intent(MainActivity.this,ProductDetail.class);
//                startActivity(i);
//                new Throwable().printStackTrace();
//                break;
//
//
//        }
    }

   //the one we created in the main adapter class
    @Override
    public void onItemClick(int position) {
    Intent productDetailIntent=new Intent(this,ProductDetail.class);
   MainModel clickedItem=mainmodels.get(position);
        productDetailIntent.putExtra(EXTRA_ID,clickedItem.id);
        productDetailIntent.putExtra(EXTRA_NAME,clickedItem.nom);
        productDetailIntent.putExtra(EXTRA_PRICE,clickedItem.price);
        productDetailIntent.putExtra(EXTRA_CURRENCY,clickedItem.currency);
        productDetailIntent.putExtra(EXTRA_URL,clickedItem.imageUrl);

        startActivity(productDetailIntent);
    }


}