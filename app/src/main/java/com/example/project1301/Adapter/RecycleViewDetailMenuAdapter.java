package com.example.project1301.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1301.R;
import com.example.project1301.model.Food;

import java.util.List;

public class RecycleViewDetailMenuAdapter extends RecyclerView.Adapter<RecycleViewDetailMenuAdapter.ViewHolder>{

    private Context context;
    List<Food> list;


    public RecycleViewDetailMenuAdapter(Context context, List<Food> list) {
        this.context = context;
        this.list = list;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_two, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food item = list.get(position);
        holder.img.setImageResource(item.getImg());
        holder.name.setText(item.getName());
        holder.price.setText(item.getPrice());
        holder.moTa.setText(item.getMoTa());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView img;
        private TextView name, price, moTa;

        private Button btPay;

        public ViewHolder(@NonNull View view) {
            super(view);
            img = view.findViewById(R.id.item_img);
            name = view.findViewById(R.id.item_name);
            price = view.findViewById(R.id.item_price);
            moTa = view.findViewById(R.id.item_desc);
            btPay = view.findViewById(R.id.item_btPay);


        }
    }

}
