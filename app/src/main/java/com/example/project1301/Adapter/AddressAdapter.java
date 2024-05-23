package com.example.project1301.Adapter;

import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1301.R;

import java.util.ArrayList;
import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {

    private List<Address> addressList;
    private ItemListener itemListener;
//
    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }


    public AddressAdapter() {
        addressList = new ArrayList<>();

    }

    public Address getAddress(int position){
        return addressList.get(position);
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_map, parent, false);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        Address address = addressList.get(position);
        holder.textView.setText(address.getAddressLine(0));
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public class AddressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textView;

        public AddressViewHolder(@NonNull View view) {
            super(view);
            textView = view.findViewById(R.id.text1);
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
