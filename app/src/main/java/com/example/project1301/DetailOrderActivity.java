package com.example.project1301;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project1301.Adapter.RecycleViewPaymentAdapter;
import com.example.project1301.model.ItemCart;
import com.example.project1301.model.ItemOrder;
import com.example.project1301.model.Shipment;
import com.example.project1301.spacing.SpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class DetailOrderActivity extends AppCompatActivity implements RecycleViewPaymentAdapter.ItemListener, View.OnClickListener{

    private RecycleViewPaymentAdapter adapter;
    private RecyclerView recyclerView;
    private TextView tvState, tvName, tvPhone, tvAddress;

    private Button btUpdate;
    private List<ItemCart> listItemCart;

    private Shipment smdo;

    private ItemOrder itemOrder;

    private String name="", phone="", addr ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        recyclerView = findViewById(R.id.recycleView);
        tvState = findViewById(R.id.tvState);
        tvName = findViewById(R.id.tvName);
        tvPhone = findViewById(R.id.tvPhone);
        tvAddress = findViewById(R.id.tvAddress);
        btUpdate = findViewById(R.id.btUpdate);
        btUpdate.setOnClickListener(this);
        listItemCart = new ArrayList<>();
        Intent intent = getIntent();
        itemOrder = (ItemOrder) intent.getSerializableExtra("io");
        smdo = (Shipment) intent.getSerializableExtra("mdo_item");
        if(itemOrder != null){
            tvState.setText(itemOrder.getState()+"");
            tvName.setText(itemOrder.getShipment().getName()+"");
            tvPhone.setText(itemOrder.getShipment().getPhone()+"");
            tvAddress.setText(itemOrder.getShipment().getAddress()+"");
            if(itemOrder.getState().toString().toLowerCase().equals("đang giao") || itemOrder.getState().toString().toLowerCase().equals("đã giao") ||itemOrder.getState().toString().toLowerCase().equals("đã hủy")){
                btUpdate.setEnabled(false);
                btUpdate.setBackgroundColor(ContextCompat.getColor(this, R.color.grey));
                Toast.makeText(this, "disable", Toast.LENGTH_SHORT).show();
            }
            for(ItemCart i : itemOrder.getListItemCart()){
                listItemCart.add(i);
            }
        }

        smdo = itemOrder.getShipment();


        if(smdo != null){
            if(smdo.getName() == "" || smdo.getName() == null){
                tvName.setText("Nhập tên");
            } else{
                tvName.setText(smdo.getName()+"");
                name = smdo.getName();
            }
            if(smdo.getPhone() == "" || smdo.getPhone() == null){
                tvPhone.setText("Nhập số điện thoại");
            } else{
                tvPhone.setText(smdo.getPhone()+"");
                phone = smdo.getPhone();
            }
            if(smdo.getAddress() == "" || smdo.getAddress() == null){
                tvAddress.setText("Nhập địa chỉ");
            } else{
                tvAddress.setText(smdo.getAddress()+"");
                addr = smdo.getAddress().toString();
            }
        } else{
           if(itemOrder == null ) {
               tvName.setText("Nhập tên");
               tvPhone.setText("Nhập số điện thoại");
               tvAddress.setText("Nhập địa chỉ");
           }
        }


        adapter = new RecycleViewPaymentAdapter(DetailOrderActivity.this, listItemCart);
        SpacingItemDecoration itemDecoration = new SpacingItemDecoration(30);
        LinearLayoutManager manager = new LinearLayoutManager(DetailOrderActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(adapter);

        adapter.setItemListener(DetailOrderActivity.this);

    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("do_item", itemOrder);
        startActivity(intent);
    }
}