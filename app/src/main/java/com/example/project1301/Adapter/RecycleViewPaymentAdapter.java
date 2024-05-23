package com.example.project1301.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1301.R;
import com.example.project1301.model.Cart;
import com.example.project1301.model.ItemCart;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RecycleViewPaymentAdapter extends RecyclerView.Adapter<RecycleViewPaymentAdapter.ViewHolder>{

    private Context context;
    private List<ItemCart> list;

    private ItemListener itemListener;

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public ItemCart getItem(int position){
        return list.get(position);
    }


    public RecycleViewPaymentAdapter(Context context, List<ItemCart> list) {
        this.context = context;
        this.list = list;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_payment, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemCart item = list.get(position);
        holder.img.setImageResource(item.getFood().getImg());
        holder.name.setText(item.getFood().getName());
        holder.price.setText(item.getFood().getPrice());
        holder.moTa.setText(item.getFood().getMoTa());
        holder.showSl.setText(item.getSl()+"");
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView img;
        private TextView name, price, moTa ,showSl;

        public ViewHolder(@NonNull View view) {
            super(view);
            img = view.findViewById(R.id.img);
            name = view.findViewById(R.id.tname);
            price = view.findViewById(R.id.tprice);
            moTa = view.findViewById(R.id.tdesc);
            showSl = view.findViewById(R.id.showSl);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(itemListener != null){
                itemListener.onItemClick(view, getAdapterPosition());
            }
        }
    }
    public interface  ItemListener{
        void onItemClick(View view, int position);
    }

}
