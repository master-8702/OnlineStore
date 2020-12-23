package com.ibrocorp.onlinestore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartItem extends BaseActivity implements com.ibrocorp.onlinestore.MainAdapter.onItemClickListener {

    RecyclerView rv;
    ArrayList<Product> products ;
    MainAdapter mainadapter;
    TextView tv,tv_productQuantity,tv_IncreaseProductQuantity,tv_DecreaseProductQuantity;
    Button btnContinue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);

        rv=findViewById(R.id.rvCart);
        tv=findViewById(R.id.tv_NumberOfCartItems);
        tv_IncreaseProductQuantity=findViewById(R.id.txtincrease);
        tv_productQuantity=findViewById(R.id.txtquantity);
        tv_DecreaseProductQuantity=findViewById(R.id.txtdecrease);
        products =new ArrayList<>();
        btnContinue=findViewById(R.id.btnContinue);
        loadRecyclerViewData();
        activateToolbar();

     btnContinue.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Toast.makeText(CartItem.this,"Your Order Has Been Sent Successfully",Toast.LENGTH_LONG).show();
             GlobalClass.clearCartItemCounter();
             finish();
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

                products.addAll(GlobalClass.getCartLists());

                LinearLayoutManager layoutManger=new LinearLayoutManager(CartItem.this,LinearLayoutManager.VERTICAL,false);
                rv.setLayoutManager(layoutManger);
                rv.setItemAnimator(new DefaultItemAnimator());
                mainadapter=new MainAdapter(CartItem.this, products,getLocalClassName());
                mainadapter.setOnItemClickListener(CartItem.this);
                rv.setAdapter(mainadapter);
        progressDialog.dismiss();
            }


    @Override
    public void onItemClick(int position, View view) {
      //  Toast.makeText(CartItem.this,"Index "+position+" is selected",Toast.LENGTH_SHORT).show();

            //to remove the clicked item by using the position as the index of the ArrayList
            //and notifying the adapter about the changes that we made
            products.remove(position);
            mainadapter.notifyItemRemoved(position);
            mainadapter.notifyItemRangeChanged(position, products.size());
            GlobalClass.deleteModel(position);   //applying the modification (deletion) of the item on the global class
            supportInvalidateOptionsMenu();


    }
}
