package com.ibrocorp.onlinestore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private static final String URL_DATA="https://store-api.glitch.me/api/products";

    List<MainModel> mainmodels;
    Context context;
    private onItemClickListener mListener;
    private String caller;  //to hold the name of the caller classs

    public interface onItemClickListener {
        void onItemClick(int position);
            }
    public void setOnItemClickListener(onItemClickListener listener){
        mListener=listener;
    }

    public MainAdapter(Context context, List<MainModel> mainModels, String s){
        this.context=context;
        this.mainmodels=mainModels;
        this.caller=s;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(caller.equals("MainActivity")){
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
            return new ViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
            return new ViewHolder(view);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
       // holder.imageView.setImageResource(mainmodels.get(position).getId());
        holder.textView.setText(mainmodels.get(position).getNom());
        holder.textview2.setText( (mainmodels.get(position).getPrice()).toString());
        Picasso.get().load(mainmodels.get(position).getImageUrl()).into(holder.imageView);

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               // AlertDialog.Builder builder=new AlertDialog.Builder(MainAdapter2.this);
//                Toast.makeText(context,"Some Cart item has been clicked",Toast.LENGTH_SHORT).show();
//
//               // mainadapter.notifyItemRangeChanged(3,mainmodels.size());
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mainmodels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView,textview2;
        public ViewHolder(@NonNull View itemView) {
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
                            mListener.onItemClick(position);
                        }
                    }

                }
            });
        }
    }
}
