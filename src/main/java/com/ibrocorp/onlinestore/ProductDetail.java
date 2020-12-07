package com.ibrocorp.onlinestore;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.nex3z.notificationbadge.NotificationBadge;
import com.squareup.picasso.Picasso;

import static com.ibrocorp.onlinestore.MainActivity.EXTRA_CURRENCY;
import static com.ibrocorp.onlinestore.MainActivity.EXTRA_ID;
import static com.ibrocorp.onlinestore.MainActivity.EXTRA_NAME;
import static com.ibrocorp.onlinestore.MainActivity.EXTRA_PRICE;
import static com.ibrocorp.onlinestore.MainActivity.EXTRA_URL;

public class ProductDetail extends AppCompatActivity implements View.OnClickListener{
    TextView tv,tv2;
    ActionMenuView actionMenuView;
    int id; Double price;
    String nom,currency,imageUrl;
    Button btnAddToCart,btnBuyNow;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);

        tv=findViewById(R.id.txtDescription);
        tv.setMovementMethod(new ScrollingMovementMethod());
        btnAddToCart=findViewById(R.id.btnAddToCart);
        btnBuyNow=findViewById(R.id.btnBuyNow);
        tv2=findViewById(R.id.tv_NumberOfCartItems);


       //changed actionmenuview actionMenuView=findViewById(R.id.actionMenuView);
      // final NotificationBadge nb=findViewById(R.id.id_badge);


        Intent intent=getIntent();
        id=intent.getIntExtra(EXTRA_ID,-1);
        nom=intent.getStringExtra(EXTRA_NAME);
        price=intent.getDoubleExtra(EXTRA_PRICE,0.0);
        currency=intent.getStringExtra(EXTRA_CURRENCY);
        imageUrl=intent.getStringExtra(EXTRA_URL);

        ImageView imageView=findViewById(R.id.ivProductDetail);
        TextView productName=findViewById(R.id.tvProductDetailName);
        TextView productPrice=findViewById(R.id.tvProductDetailPrice);
        TextView productDescription=findViewById(R.id.txtDescription);

        Picasso.get().load(imageUrl).fit().centerInside().into(imageView);
        productName.setText(nom);
        productPrice.setText(price.toString());
        productDescription.setText(R.string.description);

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.btnAddToCart:

                         MainModel tempModel = new MainModel(id, nom, price, currency, imageUrl);
                         GlobalClass.setModel(tempModel);
                         GlobalClass.cartItemCounter++;

                        invalidateOptionsMenu();
                         Toast.makeText(ProductDetail.this, "Your Item is Added To The Cart", Toast.LENGTH_SHORT).show();

            }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.cart:
                if(GlobalClass.cartLists.size() !=0){
                    Intent i2 = new Intent(ProductDetail.this, CartItem.class);
                    startActivity(i2);}
                else Toast.makeText(ProductDetail.this,"Your Cart Is Empty",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.notification:
                Toast.makeText(ProductDetail.this, "Notification is selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.filter:
                Toast.makeText(ProductDetail.this, "Filter is selected", Toast.LENGTH_SHORT).show();
                return  true;
            default:
                Toast.makeText(ProductDetail.this, id+" is selected", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item); // the default is false

        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btnAddToCart:
//                MainModel tempModel=new MainModel(id,nom,price,currency,imageUrl);
//
//                GlobalClass.setMode(tempModel);
//                Toast.makeText(ProductDetail.this,"Your Item is Added To The Cart",Toast.LENGTH_SHORT).show();
        }
    }
}
