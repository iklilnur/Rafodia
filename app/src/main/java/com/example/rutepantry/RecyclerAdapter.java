package com.example.rutepantry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<ArrayList<String>> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private String type;

    // data is passed into the constructor
    RecyclerAdapter(Context context, ArrayList<ArrayList<String>> data, String inputType) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.type = inputType;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(this.type == "pantry"){
            view = mInflater.inflate(R.layout.groceries_item, parent, false);
        }
        else if(this.type == "category_items"){
            view = mInflater.inflate(R.layout.category_item, parent, false);
        }
        else{
            view = mInflater.inflate(R.layout.groceries_item, parent, false);
        }

        return new ViewHolder(view);

    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        List<String> data = mData.get(position);
        if(this.type == "pantry"){
            holder.date.setText(data.get(0));
            holder.quantity.setText("Qty: "+data.get(1));
        }
        else if(type == "category_items"){
            holder.itemName.setText(data.get(1));
        }
        else{
            holder.date.setText(data.get(0));
            holder.quantity.setText("Qty: "+data.get(1));
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ConstraintLayout constraintLayout;
        TextView date;
        TextView quantity;
        TextView itemName;

        ViewHolder(View itemView) {
            super(itemView);
            if(type =="pantry"){
                constraintLayout = itemView.findViewById(R.id.grocery);
                date = itemView.findViewById(R.id.tanggal);
                quantity = itemView.findViewById(R.id.qty);
                constraintLayout.setOnClickListener(this);
            }
            else if(type == "category_items"){
                itemName = itemView.findViewById(R.id.itemName);
                constraintLayout = itemView.findViewById(R.id.categoryItem);
                constraintLayout.setOnClickListener(this);
            }
            else{
                constraintLayout = itemView.findViewById(R.id.grocery);
                date = itemView.findViewById(R.id.tanggal);
                quantity = itemView.findViewById(R.id.qty);
                constraintLayout.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id).get(0);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
