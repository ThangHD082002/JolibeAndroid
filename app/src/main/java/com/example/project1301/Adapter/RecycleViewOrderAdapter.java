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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1301.R;
import com.example.project1301.model.Cart;
import com.example.project1301.model.ItemCart;
import com.example.project1301.model.ItemOrder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RecycleViewOrderAdapter extends RecyclerView.Adapter<RecycleViewOrderAdapter.ViewHolder>{

    private Context context;
    private List<ItemOrder> list;

    private ItemListener itemListener;

    private int sl = 1;

    private FirebaseDatabase firebaseDatabase;

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public ItemOrder getItem(int position){
        return list.get(position);
    }


    public RecycleViewOrderAdapter(Context context, List<ItemOrder> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemOrder io= list.get(position);
        holder.tvDate.setText(io.getDateCreate()+"");
        holder.tvPayment.setText(io.getPayment().getName()+"");
        holder.tvTotal.setText(io.getTotal()+" đ");
        holder.tvState.setText(io.getState()+"");
        if(io.getState().equals("chờ xác nhận")){
            holder.tvState.setTextColor(ContextCompat.getColor(context, R.color.red_sign_in));
        }
        if(io.getState().equals("chuẩn bị")){
            holder.tvState.setTextColor(ContextCompat.getColor(context, R.color.yellow_sign_in));
        }
        if(io.getState().equals("đang giao")){
            holder.tvState.setTextColor(ContextCompat.getColor(context, R.color.teal_200));
            holder.btCancel.setEnabled(false);
            holder.btCancel.setBackgroundColor(ContextCompat.getColor(context, R.color.grey));
        }
        if(io.getState().equals("đã giao")){
            holder.tvState.setTextColor(ContextCompat.getColor(context, R.color.green));
            holder.btCancel.setEnabled(false);
            holder.btCancel.setBackgroundColor(ContextCompat.getColor(context, R.color.grey));
        }

        holder.btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!v.isEnabled()) {
                    Toast.makeText(context, "Không thể hủy", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Có thể hủy", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tvDate, tvPayment, tvTotal, tvState;
        private Button btCancel;

        public ViewHolder(@NonNull View view) {
            super(view);
            tvDate = view.findViewById(R.id.tvDate);
            tvPayment = view.findViewById(R.id.tvPayment);
            tvTotal = view.findViewById(R.id.tvTotal);
            tvState = view.findViewById(R.id.tvState);
            btCancel = view.findViewById(R.id.btnCancel);
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
