package com.ibrocorp.onlinestore;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
//this is the main Activity (the first screen) of our app
public class MainActivity extends BaseActivity implements  MainAdapter.onItemClickListener {

    private static final String URL_DATA="https://store-api.glitch.me/api/products";
    public static final String EXTRA_ID="id";
    public static final String EXTRA_NAME="nom";
    public static final String EXTRA_PRICE="price";
    public static final String EXTRA_CURRENCY="currency";
    public static final String EXTRA_URL="imageUrl";
    RecyclerView rv,rv2;
    ArrayList<Product> products;
    MainAdapter mainadapter;

// here we assign some variables and Recycler views and populate them with initial source of Data
// which is Json file on a certain server
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activateToolbar();
        rv = findViewById(R.id.recycler_view);
        rv2 = findViewById(R.id.recycler_view2);

        //creating an array list to hold the lists coming from the server temporarly
        products = new ArrayList<>();
        loadRecyclerViewData();


    }
//this method here will fetch the data from the web using volleys library and storing that data on temporary array list
    // finally populating the recycler views from the array list.
    private void loadRecyclerViewData() {
        //showing the user Loading text until the data from the internet is fetched
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();

        //fetching data from the internet using volley
        RequestQueue requestQueue= Volley.newRequestQueue(this);   //creating new request queue and JsonArrayRequest object
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, URL_DATA,null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                progressDialog.dismiss();  //hiding the text "Loading "

                try {
            //here we populate the tempo Arraylist object by iterating through JsonArrayRequest response.
                    for (int i=0;i<response.length();i++){
                        JSONObject itemobject=response.getJSONObject(i);

                        Product model=new Product(
                                itemobject.getInt("id"), itemobject.getString("nom"), itemobject.getDouble("price"), itemobject.getString("currency"),
                                itemobject.getString("imageUrl"));
                        products.add(model);  //adding each product to the array list

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
        //here we assign the orientation of the Linear Layout Manager(horizonatl or vertical)
        //this setting will decide the style of the recycler view (Horizontal or vertical scrolling recycler view)
                LinearLayoutManager layoutManger=new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false);
                rv.setLayoutManager(layoutManger);
                rv.setItemAnimator(new DefaultItemAnimator());
        //here we pass the products ArrayList and the class name for the adapter.
        //the class will help us in the Adapter class (to decide which recycler vies model to inflate for this class)
                mainadapter=new MainAdapter(MainActivity.this, products,getLocalClassName());
                mainadapter.setOnItemClickListener(MainActivity.this);
                rv.setAdapter(mainadapter);

        //here we populate the second recycler view the same as the first one
                rv2.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
                rv2.setItemAnimator(new DefaultItemAnimator());
                rv2.setAdapter(mainadapter);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this,"Can Not Load the Data B",Toast.LENGTH_SHORT).show();

            }
        });
        //adding the request string to the request queue (it's like SQL query fro the database)
        requestQueue.add(jsonArrayRequest);
    }


   //the one we created in the main adapter class
    //in this OnItem Click method we respond by instanciating a Product detail activity and filling with the clicked item values
    //we pass the data using Intents PutExtra methods
    @Override
    public void onItemClick(int position, View view) {

    Intent productDetailIntent=new Intent(this,ProductDetail.class);
   Product clickedItem= products.get(position);
        productDetailIntent.putExtra(EXTRA_ID,clickedItem.id);
        productDetailIntent.putExtra(EXTRA_NAME,clickedItem.nom);
        productDetailIntent.putExtra(EXTRA_PRICE,clickedItem.price);
        productDetailIntent.putExtra(EXTRA_CURRENCY,clickedItem.currency);
        productDetailIntent.putExtra(EXTRA_URL,clickedItem.imageUrl);

        startActivity(productDetailIntent);
    }


}