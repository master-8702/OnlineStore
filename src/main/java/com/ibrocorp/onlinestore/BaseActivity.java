package com.ibrocorp.onlinestore;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
// This class is used for doing some common works among all activities.
//like activating the toolbar, instantiating toolbar items (menu items), handling their click events
public class BaseActivity extends AppCompatActivity {
    private Toolbar mToolBar;
    private Menu Mmenu;
    private MenuItem menuItem;

    public Toolbar activateToolbar(){
        if(mToolBar==null){
            mToolBar=findViewById(R.id.appBar);
            if(mToolBar!= null){
                setSupportActionBar(mToolBar);


                mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                       switch(item.getItemId()){
                           case R.id.cart:
                           case R.id.iv_cart_icon:
                           case R.id.tv_NumberOfCartItems:
                               if(GlobalClass.getCartItemCounter() >0){
                                   Intent i2 = new Intent(BaseActivity.this, CartItem.class);
                                   startActivity(i2);}
                               else Toast.makeText(BaseActivity.this,"Your Cart Is Empty",Toast.LENGTH_SHORT).show();
                               return true;
                           case R.id.notification:
                               Toast.makeText(BaseActivity.this, "Notification is selected", Toast.LENGTH_SHORT).show();
                               return true;
                           case R.id.filter:
                               Toast.makeText(BaseActivity.this, "Filter is selected", Toast.LENGTH_SHORT).show();
//                Intent intent=new Intent(MainActivity.this,NewTrial.class);
//                startActivity(intent);

                               return  true;

                           default:
                               Toast.makeText(BaseActivity.this, item.getItemId() +" is selected", Toast.LENGTH_SHORT).show();

                       }

                       return false;
                    }
                });
            }
        }


        return mToolBar;
    }

    //here we override the onCreateOptionsMenu method of all activities as one rather than overriding it in every activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //inflating the menu.xml on the ActionBar
        getMenuInflater().inflate(R.menu.menu, menu);
        Mmenu = menu;
//here we check for the number of items in the cart and if it is zero we leave the ActionBar as it is
//means we don't show any number on the cart icon,
//and if is is above zero we will set the action view (toolbar_notification_icon.xml) to the cart icon
//in order to display the number of items on cart
//at last we add on click listener on the menu item which is covered by the new action layout.
// because if we put action layout on it we can no longer use the normal menuItemClickListener
// that's why we have to set a specific OnClick Listener on that Menu item.

        menuItem = menu.findItem(R.id.cart);
        if (GlobalClass.getCartItemCounter() < 1) {
            menuItem.setActionView(null);
        } else {
            menuItem.setActionView(R.layout.toolbar_notification_icon);
            View v = menuItem.getActionView();
            TextView tv = v.findViewById(R.id.tv_NumberOfCartItems);
            tv.setText(String.valueOf(GlobalClass.getCartItemCounter()));
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(GlobalClass.getCartItemCounter()>0){
                    Intent intent=new Intent(BaseActivity.this,CartItem.class);
                    startActivity(intent);
                    }
                }
            });
        }

            return true;
    }

    //this method will update(refresh) our ActionBar after we change number of items on the cart (badge counter).
    @Override
    protected void onResume() {
        super.onResume();
        supportInvalidateOptionsMenu();
    }
}
