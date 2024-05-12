package com.example.project1301.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1301.R;
import com.example.project1301.model.ItemHomeOne;

import java.util.List;

public class RecycleViewHomOneAdapter extends RecyclerView.Adapter<RecycleViewHomOneAdapter.ViewHolder>{

    private Context context;
    List<ItemHomeOne> list;

    private ItemListener itemListener;

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }


    public RecycleViewHomOneAdapter(Context context, List<ItemHomeOne> list) {
        this.context = context;
        this.list = list;
    }

    public void setList(List<ItemHomeOne> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public ItemHomeOne getItem(int position){
        return list.get(position);
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_one_home, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemHomeOne item = list.get(position);
        holder.img.setImageResource(item.getImg());
        holder.name.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView img;
        private TextView name;

        public ViewHolder(@NonNull View view) {
            super(view);
            img = view.findViewById(R.id.img);
            name = view.findViewById(R.id.tvName);
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
