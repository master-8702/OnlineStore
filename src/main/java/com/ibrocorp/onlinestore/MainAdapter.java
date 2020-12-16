package com.ibrocorp.onlinestore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>  {

    List<Product> mainmodels;
    Context context;
    private onItemClickListener mListener;
    private String caller;  //to hold the name of the caller classs in order to decide which layout model to infalte for the Recycler View


    // creating an interface (custom On Item Click Listener) for the adapter in  order to identify which item is clicked inside the recycler view
    public interface onItemClickListener {
        void onItemClick(int position, View view);
            }
            //setting the custom OnItemClickListener on the OnItemClickListener Object.
    public void setOnItemClickListener(onItemClickListener listener){
        mListener=listener;
    }
//here is the constructor for the adapter. to instanciate object attributes
    public MainAdapter(Context context, List<Product> products, String s){
        this.context=context;
        this.mainmodels= products;
        this.caller=s;
    }

    //here we decide which layout model to inflate. it's like a model design for the single recycler view item
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(caller.equals("MainActivity")){
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.main_view_item_model, parent, false);
            return new ViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_view_item_model, parent, false);
            return new ViewHolder(view);
        }


    }
//here we do the Binding ... binding the view(the ml or the recycler view) with its corresponding value
//and we used picasos library in order to fetch and display the images
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.textView.setText(mainmodels.get(position).getNom());
        holder.textview2.setText( (mainmodels.get(position).getPrice()).toString());
        Picasso.get().load(mainmodels.get(position).getImageUrl()).into(holder.imageView);

    }

    //here we can get the total number of items in the dataset (in the adapter)
    @Override
    public int getItemCount() {
        return mainmodels.size();
    }

    //here we create the view holder for a single item in the dataset
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView,textview2;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.image_view);
            textView=itemView.findViewById(R.id.text_view);
            textview2=itemView.findViewById(R.id.text_view_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null){
                        int position =getBindingAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position,itemView);
                            view.setId(((int)getItemId()));
                        }
                    }

                }
            });
        }
    }
}
