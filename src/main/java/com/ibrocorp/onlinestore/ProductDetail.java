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

import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;

import com.squareup.picasso.Picasso;

import static com.ibrocorp.onlinestore.MainActivity.EXTRA_CURRENCY;
import static com.ibrocorp.onlinestore.MainActivity.EXTRA_ID;
import static com.ibrocorp.onlinestore.MainActivity.EXTRA_NAME;
import static com.ibrocorp.onlinestore.MainActivity.EXTRA_PRICE;
import static com.ibrocorp.onlinestore.MainActivity.EXTRA_URL;
//in this class we will display one specific product that the user wishes to see in detail
// with bigger product image,Description,rating ... about the product
public class ProductDetail extends BaseActivity {
    TextView tv,bagdeCounter;
    MenuItem menuItem;
    int id; Double price;
    String nom,currency,imageUrl;
    Button btnAddToCart,btnBuyNow;
    Menu Mmenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);

        tv=findViewById(R.id.txtDescription);
        tv.setMovementMethod(new ScrollingMovementMethod());
        btnAddToCart=findViewById(R.id.btnAddToCart);
        btnBuyNow=findViewById(R.id.btnBuyNow);
        activateToolbar();

        //here we receive the data using savedInsatnces from MainActivity/the first activity().
        //when the user clicks on a specific product he/she will be redirected to this page with the clicked item details.
        // and we assign the coming datas to this class views

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

        //here we assign onClickListener on the ADD to Cart button
        //if the user clicks it we will create a new tempo model of product and fill it with the clicked item data
        //then we increment the number of items in the Global class
        //finally updating (refreshing) the Action Bar to reflect the number of items change
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.btnAddToCart:

                         Product tempModel = new Product(id, nom, price, currency, imageUrl);
                         GlobalClass.setModel(tempModel);
                         GlobalClass.cartItemCounter++;
                        //This method will help us to refresh (Redraw) the action bar to reflect new changes
                        supportInvalidateOptionsMenu();

                         Toast.makeText(ProductDetail.this, "Your Item is Added To The Cart", Toast.LENGTH_SHORT).show();

            }
            }
        });
    }

}
